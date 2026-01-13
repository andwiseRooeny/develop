# WebUtil 웹 유틸리티 통합 레퍼런스

## 📐 분류 정보 (Classification)

| 항목 | 값 |
|:---|:---|
| **계층 (Layer)** | Infrastructure |
| **분류 코드** | `infra-util` |
| **저장 유형** | Common |

## 종합 요약 및 노하우
웹 계층의 **유틸리티 허브**로서, Spring MVC FlashMap 래핑, FakeException 기반 컨트롤러 중단 리다이렉트, Referer 보안 파싱, 다국어 메시지 로딩, 동적 Head 리소스 관리 등 컨트롤러-뷰 간 공통 작업을 정적 메서드로 집약합니다.

## Referenced Assets
- `java/com/andwise/jw/util/web/WebUtil.java`

---

## 핵심 선언

### 1. FlashMap 래핑 패턴
Spring MVC `FlashMap`을 래핑하여 리다이렉트 간 일회성 데이터 전달을 단순화합니다.
```java
// 데이터 저장 (만료 20초)
WebUtil.setFlashMessage(request, "flashMessage", data, targetPath);
// 데이터 조회
Object msg = WebUtil.getFlashMessage(request);
```

### 2. 리다이렉트 제어 (FakeException)
`FakeSendRedirectException`을 throw하여 컨트롤러 로직을 즉시 중단하고 리다이렉트합니다. `@ControllerAdvice`에서 반드시 핸들링 필요.
```java
// 컨트롤러 실행 중 즉시 리다이렉트
WebUtil.sendRedirect("/target/path.do");
WebUtil.sendRedirect("/path.do", "param=value", flashMessage);
// 이전 페이지로 이동
WebUtil.sendRedirectToReferer();
```

### 3. Referer 보안 처리
URL 파싱 및 Context Path 제거를 통해 안전한 이전 페이지 경로를 추출합니다.
```java
// Context Path 제외 경로
String referer = WebUtil.getReferer(request); // "/page.do?param=1"
// Context Path 포함
String refererFull = WebUtil.getRefererWidthCtx(request);
```

### 4. 다국어 메시지 로딩
`MessageSource`와 `LocaleResolver`를 통해 현재 로케일 기반 메시지를 로딩합니다.
```java
String msg = WebUtil.getTransMessage("error.required", "필드명");
String msgDefault = WebUtil.getTransMessageDefault(request, "code", "기본값");
```

### 5. Request 파라미터 처리 표준
Request 파라미터를 `Map`으로 변환하며, 배열(`[]`) 자동 처리, 암호화 값 복호화를 지원합니다.
```java
Map<String,Object> params = WebUtil.getRequestMap(request);
Map<String,Object> decoded = WebUtil.getRequestMapDecode(request); // SecureUtils 복호화
List list = WebUtil.getParameterList(params, "items"); // 단일값도 List로 변환
```

### 6. 동적 Head 리소스 관리
CSS/JS를 request attribute에 누적 등록하고, 레이아웃에서 일괄 출력합니다.
```java
// 등록
WebUtil.addCss(request, "page.css", "/css/custom.css");
WebUtil.addJs(request, "page.js", "/js/custom.js");
// 출력 (JSP)
<%= WebUtil.printCss(request, "page.css") %>
<%= WebUtil.printJs(request, "page.js") %>
```
**SEO 메타 설정**: `setPageTitle()`, `setCustomSeoDescription()`, `setCustomSeoKeywords()`

---

> [!NOTE]
> `FakeSendRedirectException` 패턴은 반드시 `@ControllerAdvice`에서 처리해야 하며, 미처리 시 500 에러가 발생합니다.
