# U1LoginController 통합 레퍼런스

본 문서는 `U1LoginController`가 수행하는 프론트/관리자 통합 로그인 처리 절차와 사이트별 동적 리다이렉션 로직 등 특화된 비즈니스 규격을 정의합니다.

# 종합 요약 및 노하우
`U1LoginController`는 프로젝트의 입구에서 **보안 검증(Referer)**, **멀티 사이트 인식(Site_id)**, **통합 인증(AuthService)**을 수행하는 핵심 오케스트레이터입니다. 특히 로그인 실패 시 기존 컨텍스트를 유지하거나, 사이트 설정에 정의된 전용 로그인 페이지로 지능적으로 분기하는 유연한 리다이렉션 아키텍처가 기술적 핵심입니다.

# Referenced Assets
- [U1LoginController.java](java/custom/app/web/U1LoginController.java)

# 핵심 선언

## 1. 로그인 처리 프로세스 명세
사용자 요청 유형에 따라 `memberLogin`에서 `idLogin`으로 내부 전이가 발생하며, `AdminType` 바인딩을 통해 권한 레벨을 정교하게 제어합니다.
- **주요 엔드포인트**:
  - `/custom/login/idLoginProc.do`: 전용 로그인 (관리자 로그인 여부 `front.all.login` 프로퍼티 참조)
  - `/custom/login/loginProc.do`: 통합 로그인 (로그인 처리의 실체)

## 2. 사이트별 동적 리다이렉션 전략
성공/실패 여부와 `site_id` 파라미터 유무에 따라 최적의 랜딩 페이지를 동적으로 결정합니다.
- **실패 시**: `siteVO.getLoginUrl()`을 우선 참조하며, 미설정 시 공통 `/cms/login/login.do`로 이동.
- **성공 시**: `referer`가 있다면 해당 주소로, 없다면 `site_id`를 기반으로 한 사이트 인덱스(`/siteId/`)로 이동.

## 3. 인증 서비스(AuthService) 연동 규약
`memberService`를 직접 호출하지 않고, 추상화된 `authService.login`을 통해 통합 인증을 수행하고 `LoginResultVO`를 통해 결과를 수신합니다.
```java
// 파라미터: request, 아이디, 비밀번호, 권한셋(AdminType)
LoginResultVO loginResultVO = authService.login(request, memberId, memberPw, admTySet);
```

## 4. 컨트롤러 레이어 및 의존성 구조
인증과 보안은 `AuthService`, 데이터 정보는 `MemberService`, 사이트 환경은 `SiteService`에 위임하는 모듈화된 의존성 구조를 가집니다.
- **핵심 주입 서비스**: `MemberService`, `AuthService`, `SiteService`, `jsonView` (Ajax 지원용)

## 5. 시나리오별 데이터 흐름 분석
로그인 결과 및 파라미터를 다음 페이지로 안전하게 전달하기 위해 `RedirectAttributes`의 `addFlashAttribute` 기술을 활용합니다.
- **데이터 보존**: 실패 시 `loginResult` 객체와 `referer` 값을 플래시 속성에 담아 이동 루프를 완성함.
- **성공 처리**: `LoginResultVO`를 전달하고 사이트 루트로의 지능적 호스트 주소 전파(`Util.getHostHttpRelative`).

---
> [!NOTE]
> 본 레퍼런스는 `U1LoginController`의 구현 명세에 집중하며, 공통 보안 로직인 `referer` 체크 등은 [공통 보안 레퍼런스]에서 별도로 다룹니다.
