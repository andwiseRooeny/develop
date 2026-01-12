# 학사_로그인_세션_연계_Library

## 1. 개요 (Context)
- 기존 CMS 계정이 아닌 외부 학사 DB 계정으로 로그인 시, CMS 세션(`LoginVO`)이 생성되지 않거나 사용자 정보(성명, 소속 등)가 누락되는 문제 해결이 필요했습니다.

## 2. 해결 방안 (Solution)
- **2단계 인증 및 강제 로그인 패턴**:
  1. `HaksaAppMapper`를 통해 학사 DB의 인증 함수(`CHECK_EN_PASSWORD`) 결과 확인.
  2. 인증 성공 시, `authService.login(request, memberId)`를 호출하여 CMS 보안 세션을 강제 생성.
  3. 생성된 `LoginVO` 객체에 학사 상세 뷰(`VIEW_GONET_V_YDUAUTH`) 데이터를 직접 주입(Mapping).

**[핵심 코드 패턴]**:
```java
// 학사 인증 성공 시
if ("YES".equals(haksaResult)) {
    // 1. 상세 정보 조회 (부모 메서드 직접 호출)
    String namespace = "custom.mapper.haksa.HaksaAppMapper.";
    EgovMap haksaInfo = haksaAppMapper.selectOne(namespace + "selectHaksaUserInfo", memberId);
    
    // 2. CMS 강제 세션 생성
    loginResultVO = authService.login(request, memberId);
    
    // 3. 데이터 매핑 (성명, 소속, 신분 등)
    LoginVO loginVO = loginResultVO.getLoginVO();
    loginVO.setMemberNm((String) haksaInfo.get("name"));
    // ... 신분 코드 변환 및 주입
}
```

## 3. 관련 레퍼런스 (References)
- [U1LoginController 통합 레퍼런스](../_ref/custom/01_U1LoginController_통합_레퍼런스.md)
- [AppMapper 상속 및 멀티데이터소스 가이드](./AppMapper_상속_및_멀티데이터소스_연동_가이드_Library.md)

## 4. 회고 및 개선 (Retrospective)
- **회고**: `AuthService`의 강제 로그인 기능을 활용하여 복잡한 세션 생성 로직을 안전하게 처리했습니다.
- **개선**: 학사 정보 조회와 로그인 처리를 별도의 `HaksaAuthService`로 캡슐화한다면 컨트롤러 코드를 더욱 간결하게 유지할 수 있을 것입니다.
