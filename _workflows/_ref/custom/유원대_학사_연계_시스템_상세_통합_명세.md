# 유원대 학사 연계 시스템 상세 명세 (통합 레퍼런스)

> [!NOTE]
> 이 문서는 유원대학교 학사 회원 연계 시스템 구축을 위해 선택된 7가지(1, 2, 3, 4, 6, 7, 8) 핵심 옵션을 통합하여 작성되었습니다.

## 1. 인프라 및 테스트 환경 명세 (옵션 6)
### 1.1. 핵심 요약 및 노하우
유원대 학사 DB 연계 및 인증 테스트를 위한 물리적 접속 정보와 계정 명세입니다.

### 1.2. 주요 명세/가이드
- **DB 주소**: `202.30.58.130:1521` (SID: `SWD`)
- **연계 계정**: `YTIS_GONET` / `DUDEHDGONET0818`
- **테스트 계정**: `20426003` / `U1test#01`

---

## 2. 데이터 인터페이스 명세 (옵션 7)
### 2.1. 핵심 요약 및 노하우
제공된 보안 뷰(`VIEW_GONET_V_YDUAUTH`)의 컬럼 상세와 비즈니스 의미입니다.

### 2.2. 주요 명세/가이드
| 컬럼명 | 설명 | 비고 |
| :--- | :--- | :--- |
| **ID** | 사번/학번 | 유니크 키 |
| **NAME** | 이름 | |
| **SOSOKNAME** | 소속(학과/부서) | |
| **GBSINBUN** | 신분 코드 | S, J, D, P, G |

---

## 3. 로그인 연동 기술 프로토콜 및 예외 처리 (옵션 8)
### 3.1. 핵심 요약 및 노하우
`CHECK_EN_PASSWORD` 함수의 규격과 인증 실패/성공 시의 시나리오 정의입니다.

### 3.2. 참조 코드/예시
```sql
-- 리턴값이 'YES'이면 성공, 그 외('NO' 등)는 실패
SELECT YTIS_SYSTEM.CHECK_EN_PASSWORD(#{id}, #{pw}) FROM DUAL;
```

---

## 4. Oracle 11g Thin 연결 및 TNS 설정 가이드 (옵션 1)
### 4.1. 주요 명세/가이드
- **JDBC Driver**: `oracle.jdbc.OracleDriver` (ojdbc6/7 권장)
- **URL**: `jdbc:oracle:thin:@202.30.58.130:1521:SWD`

---

## 5. `CHECK_EN_PASSWORD` 기반 통합 인증 패턴 (옵션 2)
### 5.1. 핵심 요약 및 노하우
암호화된 데이터를 안전하게 검증하기 위해 어플리케이션이 아닌 DB 엔진의 함수에 인증을 위임하는 패턴입니다.

### 5.2. 참조 코드/예시
```java
public boolean authenticate(String id, String rawPw) {
    String status = haksaMapper.loginCheck(id, rawPw);
    return "YES".equals(status);
}
```

---

## 6. 학사 뷰 데이터 매핑 아키텍처 (옵션 3)
### 6.1. 핵심 요약 및 노하우
원천 테이블이 아닌 가상 뷰를 통해 데이터를 조회함으로써 학사 시스템의 물리적 레이아웃 변경에 유연하게 대응합니다.

---

## 7. `GBSINBUN` 기반 동적 권한 매핑 (옵션 4)
### 7.1. 주요 명세/가이드
- **S**: 학생 → `ROLE_STUDENT`
- **J**: 직원 → `ROLE_STAFF`
- **P**: 교수 → `ROLE_PROFESSOR`
- **D**: 조교 → `ROLE_ASSISTANT`
- **G**: 대학원생 → `ROLE_GRADUATE`
