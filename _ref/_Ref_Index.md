# Reference Index (통합 레퍼런스 색인)

본 프로젝트의 기술 표준(Common) 및 특화 로직(Custom)에 대한 전체 레퍼런스 목록입니다.

| 분류 | 주제 | 내용 요약 | 핵심 기술 | 링크 |
| :--- | :--- | :--- | :--- | :--- |
| **Common** | MyBatis CRUD 표준 | MyBatis 이용 시 준수해야 할 데이터 처리 및 멀티 DBMS 대응 표준 정의 | MyBatis, SQL, Oracle/MySQL | [01_MyBatis_CRUD_표준_통합_레퍼런스.md](./common/01_MyBatis_CRUD_표준_통합_레퍼런스.md) |
| **Common** | JSON 처리 유틸리티 | Jackson ObjectMapper 싱글톤 래핑 및 타입 세이프 역직렬화 표준 | Java, Jackson, JSON | [02_JSON_처리_유틸리티_통합_레퍼런스.md](./common/02_JSON_처리_유틸리티_통합_레퍼런스.md) |
| **Common** | AppMapper 상속 및 멀티데이터소스 | AppMapper 확장 패턴 및 HAKSA 멀티 데이터소스 연동 가이드 | Spring, MyBatis, DataSource | [AppMapper_상속_및_멀티데이터소스_통합_레퍼런스.md](./common/AppMapper_상속_및_멀티데이터소스_통합_레퍼런스.md) |
| **Common** | LoginController 공통 로직 | 사용자 인증 및 세션 관리 공통 클래스(egovframe 기반) 분석 | Spring MVC, Session | [LoginController_통합_레퍼런스.md](./common/LoginController_통합_레퍼런스.md) |
| **Common** | BoardMapper 통합 레퍼런스 | 게시판 데이터 접근을 위한 매퍼 인터페이스 및 쿼리 규격 | MyBatis, Mapper | [BoardMapper_통합_레퍼런스.md](./common/BoardMapper_통합_레퍼런스.md) |
| **Common** | MyBatis DBMS별 매퍼 분리 | DB 타입별(Oracle/MySQL) 매퍼 위치 지정 및 파티셔닝 전략 | MyBatis, XML Config | [MyBatis_DBMS별_매퍼_분리_구조.md](./common/MyBatis_DBMS별_매퍼_분리_구조.md) |
| **Common** | Spring MVC 인터셉터 흐름 | 요청 전처리 및 후처리를 위한 인터셉터 구성 및 흐름도 | Spring MVC, Interceptor | [Spring_MVC_인터셉터_흐름_dispatcher-servlet.md](./common/Spring_MVC_인터셉터_흐름_dispatcher-servlet.md) |
| **Common** | Spring MyBatis Config 통합 | 프로젝트 전반의 Spring-MyBatis 연동 설정 통합 명세 | Spring, MyBatis | [Spring_MyBatis_Config_통합_레퍼런스.md](./common/Spring_MyBatis_Config_통합_레퍼런스.md) |
| **Common** | 개발 언어 정의 (Java 8/17) | 프로젝트 권장 자바 버전별 특징 및 구현 가이드 | Java 8, Java 17 | [개발_언어_정의_Java8_17.md](./common/개발_언어_정의_Java8_17.md) |
| **Custom** | U1LoginController 특화 로직 | 프론트/관리자 통합 로그인 처리 및 사이트별 동적 리다이렉션 로직 | Spring MVC, AuthService | [01_U1LoginController_통합_레퍼런스.md](./custom/01_U1LoginController_통합_레퍼런스.md) |
| **Custom** | UCM Lite 연동 명세 | 학사 시스템과 외부 클라우드(O365, Gsuite) 간의 SSO 연동 규약 | SSO, O365, Gsuite | [02_UCM_Lite_연동_명세_통합_레퍼런스.md](./custom/02_UCM_Lite_연동_명세_통합_레퍼런스.md) |
| **Custom** | UCM Lite JSP 구현 | HttpURLConnection 기반의 서버 간 통신 및 JDK 호환성 대응 로직 | JSP, HTTP, TLS | [03_UCM_Lite_JSP_연동_구현_통합_레퍼런스.md](./custom/03_UCM_Lite_JSP_연동_구현_통합_레퍼런스.md) |
| **Custom** | 유원대 학사 연계 상세 명세 | 유원대학교 학사 시스템과의 연동을 위한 전용 데이터 인터페이스 | ERP, Interface | [유원대_학사_연계_시스템_상세_통합_명세.md](./custom/유원대_학사_연계_시스템_상세_통합_명세.md) |

> [!NOTE]
> 위 목록은 `개발-레퍼런스-정리` 워크플로우를 통해 자동으로 관리됩니다. 레퍼런스 추가 시 해당 워크플로우를 실행하여 인덱스를 갱신하십시오.
