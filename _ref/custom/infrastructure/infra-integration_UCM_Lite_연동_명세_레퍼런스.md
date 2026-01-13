# 종합 요약 및 노하우
본 문서는 학사 시스템과 외부 클라우드 솔루션(UCM Lite) 간의 SSO 연동을 위한 규격서입니다. 사용자 식별 정보 및 그룹 정보를 표준화된 파라미터로 전달하여 기술적 일관성을 유지합니다.

# Referenced Assets
- [UCMLite_Guide.jsp](file:///c:/dev/eclipse/workspace/u1/u1/src/main/_develop/_info/u1/UCMLite_Guide.jsp)
- [UCM Lite Guide.pptx](file:///c:/dev/eclipse/workspace/u1/u1/src/main/_develop/_info/u1/UCM Lite Guide.pptx)

# 핵심 선언

## 1. UCM Lite 연동 필수 파라미터 규격
연동 호출 시 HTTP 요청 또는 세션을 통해 다음 데이터가 반드시 전달되어야 함.
- **userId**: 학번 또는 사번 (Unique ID)
- **userclass**: 'S'(Student), 'F'(Faculty/Staff) 구분값

## 2. SSO 링크 액션 프로세스
사용자가 학사 시스템 로그인 후 관련 링크 클릭 시 다음 순서로 동작함.
1. 학사 로그인 세션에서 사용자 정보 추출
2. 연동 가이드 파일(`UCMLite_Guide.jsp` 등) 호출 시 파라미터 매핑
3. UCM Lite 인증 서버로 리다이렉트 및 Home 이동

## 3. 클라우드 서비스(O365/Gsuite) 가공 로직
사용자별 클라우드 계정 생성 권한을 제어하기 위한 플래그 처리.
- **office365 / gsuite**: 'Y' (생성/접속 허용), 'N' (비활성)
- 해당 값 누락 시 UCM Lite 내부 권한 정책에 따라 홈 이동이 제한될 수 있음.

# Source Specifics
```jsp
// [필수값] 사용자 식별 정보 및 그룹 구분
String userId = (String)session.getAttribute("USER_ID"); 
String userclass = (String)session.getAttribute("USER_CLASS"); // S or F

// [선택값] 서비스 활성화 여부
String office365 = "Y";
String gsuite = "Y";
```
