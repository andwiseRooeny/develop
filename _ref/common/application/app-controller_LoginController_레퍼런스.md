# LoginController 통합 레퍼런스

# 핵심 요약 및 노하우
프레임워크 표준 인증 서비스의 관문 역할을 수행하며, 다중 사이트 환경에서의 세션 관리와 보안 필터링을 통합 처리합니다. 리다이렉트 URL 변조 방지를 위한 엄격한 호스트 검증과 FlashAttributes를 활용한 상태값 공유가 핵심 기술 노하우입니다.

# Source Path
src/main/java/com/andwise/jw/auth/web/LoginController.java

## 5. 주요 API 엔드포인트 및 메소드 시그니처
- `frontLogin`: 로그인 폼 이동 (`/cms/login/login.do`)
- `memberLogin`: 회원 로그인 래퍼 (`/cms/login/idLoginProc.do`)
- `idLogin`: 실제 인증 로직 수행 (`/cms/login/loginProc.do`)
- `oneTimeLogin`: 비회원 원타임 인증 (`/cms/login/oneTimeLoginProc.do`)
- `logOut`: 세션 무효화 및 로그아웃 (`/cms/login/logOut.do`)
- `isLogin`: 로그인 상태 AJAX API (`/cms/login/isLogin.do`, `/cms/login/loginCheck.do`)

## 1. Spring MVC 기반 보안 검증 및 Referer 변조 방지 규칙
- 외부 URL 리다이렉트 공격 방지를 위한 도메인 검증 로직 적용.
- `Util.getHostHTTP(request)` 기반의 허용된 호스트 체크.

## 4. 사이트 식별 기반 동적 리다이렉션 및 로그아웃 처리 규격
- URL 또는 Referer에서 `SiteId` 추출 후 해당 서비스의 전용 도메인/경로로 분기 처리.
- `PATH.getSiteIdFromUrl(referer)`를 통한 타겟 사이트 확정.

## 6. 인증 실패 시 사이트별 커스텀 로그인 URL 로딩 및 노하우
- `RedirectAttributes.addFlashAttribute("loginResult", vo)`를 이용한 일회성 데이터 전달.
- 사이트 설정(`SiteVO.getLoginUrl()`)에 따른 동적 랜딩 페이지 이동 정책.

# Reference Code
```java
// Referer 보안 검증 (Whitelist 방식)
if(CMS.getProp("login.referer.check", "Y").equals("Y") && !StringUtil.isEmpty(referer)) {
    if( referer.startsWith("/") || referer.startsWith(Util.getHostHTTP(request)) || referer.startsWith(Util.getHostHTTPS(request))) {
        // pass
    } else {
        throw new SecurityException("[Login Security ERROR] URL 변조 감지");
    }
}

// 사이트별 동적 로그인 페이지 결정
String loginPage = null;
if(!StringUtil.isEmpty(siteId)) {
    SiteVO siteVO = siteService.loadVO(siteId);
    loginPage = siteVO.getLoginUrl();
}
if(StringUtil.isEmpty(loginPage)) {
    loginPage = "/cms/login/login.do";
}
return new RedirectView( loginPage, true );
```
