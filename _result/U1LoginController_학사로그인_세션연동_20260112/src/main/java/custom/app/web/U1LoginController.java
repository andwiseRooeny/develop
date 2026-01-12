package custom.app.web;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.RedirectView;

import com.andwise.jw.auth.enu.AdminType;
import com.andwise.jw.auth.service.AuthService;
import com.andwise.jw.auth.vo.LoginResultVO;
import com.andwise.jw.cms.service.SiteService;
import com.andwise.jw.cms.vo.SiteVO;
import com.andwise.jw.member.service.MemberService;
import com.andwise.jw.util.StringUtil;

import md.cms.CMS;
import md.util.Util;

@Controller
public class U1LoginController {
	@Resource(name = "memberService")
	MemberService memberService;

	@Resource(name = "authService")
	AuthService authService;

	@Resource(name = "jsonView")
	AbstractView ajaxView;

	@Autowired
	SiteService siteService;

	@Autowired
	custom.mapper.haksa.HaksaAppMapper haksaAppMapper;

	/**
	 * 프론트 페이지에서 회원 로그인 처리
	 * 
	 * @param request
	 * @param response
	 * @param memberId
	 * @param memberPw
	 * @param referer
	 * @param siteId
	 * @param redirectAttr
	 * @return
	 */
	@RequestMapping(value = "/custom/login/idLoginProc.do", method = RequestMethod.POST)
	public RedirectView memberLogin(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "member_id") String memberId,
			@RequestParam(value = "member_pw") String memberPw,
			@RequestParam(value = "referer") String referer,
			@RequestParam(value = "site_id", required = false) String siteId,
			RedirectAttributes redirectAttr) {

		// -----------------------------------------------
		// 관리자도 로그인 할지여부 YN
		// -----------------------------------------------
		String allLoginYn = CMS.getProp("front.all.login", "N");

		AdminType adminTy = null;
		if ("N".equals(allLoginYn)) { // 일반 회원만 로그인 일 경우
			adminTy = AdminType.NONE;
		}

		return idLogin(request, response, memberId, memberPw, referer, siteId, adminTy, redirectAttr);

	}

	/**
	 * 로그인
	 * 
	 * @param request
	 * @param response
	 * @param memberId
	 * @param memberPw
	 * @param referer
	 * @param siteId
	 * @param adminType    (로그인 처리 대상) NONE 일경우는 일반 회원만 로그인 된다. null 일경우는 관리자를 포함한
	 *                     모든 회원이 대상
	 * @param redirectAttr
	 * @return
	 */
	@RequestMapping(value = "/custom/login/loginProc.do", method = RequestMethod.POST)
	public RedirectView idLogin(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "member_id") String memberId,
			@RequestParam(value = "member_pw") String memberPw,
			@RequestParam(value = "referer") String referer,
			@RequestParam(value = "site_id", required = false) String siteId,
			@RequestParam(value = "adminType", required = false) AdminType adminType,
			RedirectAttributes redirectAttr) {

		// --------------------------------------------------------------
		// 보안 관련
		// referer 변조를 막는다.
		// --------------------------------------------------------------
		if (CMS.getProp("login.referer.check", "Y").equals("Y") && !StringUtil.isEmpty(referer)) {

			if (referer.startsWith("/") || referer.startsWith(Util.getHostHTTP(request))
					|| referer.startsWith(Util.getHostHTTPS(request))) {
				// pass
			} else {
				// --------------------------------------------------------------
				// 리다이렉트될 페이지가 동일한 호스트가 아니면 익셉션 발생
				// --------------------------------------------------------------
				throw new SecurityException(String.format("[%s][%s]로그인 후 리다이렉트할 url 변조 url=[%s]",
						Util.getCurDate("yyyy-MM-dd HH:mm:ss"), CMS.getRemoteAddr(request), referer));
			}
		}

		// -------------------------------------
		// 로그인 시도
		// -------------------------------------
		Set<AdminType> admTySet = null;
		if (adminType != null) {
			admTySet = new HashSet<AdminType>();
			admTySet.add(adminType);
		}
		LoginResultVO loginResultVO = authService.login(request, memberId, memberPw, admTySet);

		boolean success = loginResultVO.getIsSuccess();

		// -------------------------------------
		// [학사 연동] 로그인 실패 시 학사 DB 인증 시도
		// -------------------------------------
		if (!success) {

			try {
				java.util.Map<String, Object> haksaParam = new java.util.HashMap<String, Object>();
				haksaParam.put("id", memberId);
				haksaParam.put("pw", memberPw);

				String namespace = "custom.mapper.haksa.HaksaAppMapper.";
				String haksaResult = haksaAppMapper.selectOne(namespace + "checkHaksaPassword", haksaParam);

				if ("YES".equals(haksaResult)) {
					// -------------------------------------
					// 학사 상세 정보 조회 및 세션 주입
					// -------------------------------------
					org.egovframe.rte.psl.dataaccess.util.EgovMap haksaInfo = haksaAppMapper
							.selectOne(namespace + "selectHaksaUserInfo", memberId);

					// -------------------------------------
					// [학사 연동] CMS DB 조회 없이 직접 세션 주입
					// -------------------------------------
					com.andwise.jw.auth.vo.LoginVO loginVO = new com.andwise.jw.auth.vo.LoginVO();
					loginVO.setMemberId(memberId);
					loginVO.setCurrentLoginType(com.andwise.jw.auth.enu.LoginType.ID);
					loginVO.setIpAddress(request.getRemoteAddr());

					if (haksaInfo != null) {
						// 성명 매핑
						if (haksaInfo.get("name") != null) {
							loginVO.setMemberNm((String) haksaInfo.get("name"));
						}

						// 학과(소속) 매핑
						if (haksaInfo.get("sosokname") != null) {
							loginVO.setGroupNm((String) haksaInfo.get("sosokname"));
						}

						// 신분 매핑 및 membertypeCd/Nm 주입
						String sinbunCode = (String) haksaInfo.get("gbsinbun");
						if (sinbunCode != null) {
							String membertypeCd = "";
							String membertypeNm = "";
							if ("S".equals(sinbunCode)) {
								membertypeCd = "Z";
								membertypeNm = "학부생";
							} else if ("J".equals(sinbunCode)) {
								membertypeCd = "Y";
								membertypeNm = "직원";
							} else if ("D".equals(sinbunCode)) {
								membertypeCd = "X";
								membertypeNm = "조교";
							} else if ("P".equals(sinbunCode)) {
								membertypeCd = "W";
								membertypeNm = "교수";
							} else if ("G".equals(sinbunCode)) {
								membertypeCd = "V";
								membertypeNm = "대학원생";
							}

							if (!"".equals(membertypeCd)) {
								loginVO.setMembertypeCd(membertypeCd);
								loginVO.setMembertypeNm(membertypeNm);
								loginVO.setPositionNm(membertypeNm);
							}
						}
					}

					// 세션 직접 주입
					com.andwise.jw.auth.web.SessionMgr.setLoginVO(loginVO);

					// 결과 VO 생성 및 성공 설정
					loginResultVO = new com.andwise.jw.auth.vo.LoginResultVO(request);
					loginResultVO.setLoginVO(loginVO);
					loginResultVO.setIsSuccess(true);
					success = true;
				}
			} catch (Exception e) {
				// 학사 DB 연동 오류 시 로그 기록 후 기존 실패 로직 유지
				e.printStackTrace();
			}

		}

		// -------------------------------------
		// 최종 로그인 실패 처리
		// -------------------------------------
		if (!success) {

			redirectAttr.addFlashAttribute("loginResult", loginResultVO);
			redirectAttr.addAttribute("referer", referer);

			// -------------------------------------
			// 로그인 페이지 확인
			// -------------------------------------
			String loginPage = null;
			if (!StringUtil.isEmpty(siteId)) {
				SiteVO siteVO = siteService.loadVO(siteId);
				loginPage = siteVO.getLoginUrl();
			}
			if (StringUtil.isEmpty(loginPage)) {
				loginPage = "/cms/login/login.do";
			}

			// -------------------------------------
			// 로그인 페이지로 이동
			// -------------------------------------

			return new RedirectView(loginPage, true);

		}

		String redirectPage = null;
		if (StringUtil.isEmpty(referer)) { // 로그인 버튼을 누르고 들어온 페이지
			if (StringUtil.isEmpty(siteId)) {
				redirectPage = "/";
			} else {
				redirectPage = "/" + siteId + "/"; // 사이트 인덱스로 이동
			}
		} else {
			// redirectPage = referer.substring(request.getContextPath().length() );
			redirectPage = referer;
		}

		redirectAttr.addFlashAttribute("loginResult", loginResultVO);

		String host = Util.getHostHttpRelative(request, false);

		return new RedirectView(host + redirectPage, false);

	}
}
