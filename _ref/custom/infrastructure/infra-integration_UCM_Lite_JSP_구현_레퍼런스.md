# 종합 요약 및 노하우
본 문서는 `UCMLite_Guide.jsp`에 구현된 서버 측 연동 기술을 정의합니다. `HttpURLConnection`을 이용한 POST 통신과 JDK 버전별 호환성 로직을 통해 레거시 환경에서도 안정적인 SSO 연동을 보장하는 것이 기술적 정수입니다.

# Referenced Assets
- [UCMLite_Guide.jsp](file:///c:/dev/eclipse/workspace/u1/u1/src/main/_develop/_info/u1/UCMLite_Guide.jsp)

# 핵심 선언

## 1. JSP 기반 HTTP POST SSO 통신 패턴
외부 인증 서버와 `application/x-www-form-urlencoded` 방식으로 직접 통신하여 인증 결과를 수신함.
- **연동 엔드포인트**: `https://ucm.u1.ac.kr:443` (TLS 1.2 이상 권장)
- **타임아웃 정책**: Connection/Read Timeout 5,000ms 설정

## 1.5. 세션 기반 사용자 식별 정보 연계
연동에 사용되는 핵심 파라미터는 학사 로그인 성공 시 생성된 **보안 세션(`LoginVO`)**에서 동적으로 추출함.
- **userId**: 세션의 학번/사번 정보 매핑.
- **userClass**: 사용자 권한 코드 기반 변환 (S:학생, F:교직원).

## 2. 연동 보안 및 인증 키 관리
고유 API 키와 도메인 정보를 파일 상단에 고정 자산으로 선언하여 일관성을 유지함.
- **api_key**: `fc1d57aa-33da-4b56-baee-ac5753b4ef82`
- **ssodomain**: `ucm.u1.ac.kr`

## 3. SSO 리다이렉션 응답 처리 로직
서버 응답 결과(`result`)를 검증하여 정상적인 `returnUrl` 확보 시 클라이언트 브라우저를 이동시킴.
- **파싱 전략**: 응답이 XML/HTML 태그(`<`)로 시작하지 않는 경우에만 유효한 URL로 인정.
- **이동 방식**: `location.href` 연산을 통한 페이지 전이.

## 4. 멀티 JDK 버전 호환성 가이드
서버 환경(JDK 1.4 ~ 1.8)에 따라 발생할 수 있는 Handshake 또는 프로토콜 오류 대응.
- **TLS 오류 시**: 8001 포트(HTTP) 활용 제안.
- **StackTrace**: JDK 1.8 미만 환경을 고려한 `StackTraceElement` 루프 기반 에러 로깅.

## 5. SSO 연동 아키텍처 및 데이터 플로우
1. [학사시스템] 사용자 인증 및 세션 정보 추출.
2. [JSP 가이드] 파라미터(`userId`, `userClass` 등) 바인딩 및 UCM Lite POST 요청.
3. [UCM Lite] 인증 검증 및 결과 URL 반환.
4. [JSP 가이드] 반환된 URL로 브라우저 리다이렉트.

# Source Specifics
// [세션 연계] 사용자 정보 추출 (학사 로그인 세션 라이브러리 참조)
String userid = (String)request.getAttribute("ucmUserId"); 
String userclass = (String)request.getAttribute("ucmUserClass");

// 핵심 POST 통신 로직
connection = (HttpURLConnection) url.openConnection();
connection.setRequestMethod("POST");
connection.setDoOutput(true);
connection.getOutputStream().write(postDataBytes); 

// 리다이렉션 결정
if(!result.toString().startsWith("<")){
    returnUrl = result.toString().trim();
}
```
