# Reference Index (통합 레퍼런스 색인)

본 프로젝트의 기술 표준(Common) 및 특화 로직(Custom)에 대한 전체 레퍼런스 목록입니다.

| 분류 | 계층 | 분류 코드 | 주제 | 내용 요약 | 핵심 기술 | 링크 |
|:---|:---|:---|:---|:---|:---|:---|
| **Common** | Application | `app-controller` | LoginController 통합 | 다중 사이트 세션 관리와 Referer 보안 검증 통합 처리 | Spring MVC, Session | [app-controller_LoginController_레퍼런스.md](./common/application/app-controller_LoginController_레퍼런스.md) |
| **Common** | Application | `app-interceptor` | Spring MVC 인터셉터 | 관리자 인증, 로깅, 보안 IP 체크 등 요청 전후 처리 | Spring MVC, Interceptor | [app-interceptor_Spring_MVC_인터셉터_레퍼런스.md](./common/application/app-interceptor_Spring_MVC_인터셉터_레퍼런스.md) |
| **Common** | Persistence | `pers-query` | MyBatis CRUD 표준 | CLOB/JSON 핸들링, OGNL 동적 SQL, 멀티 DBMS 대응 | MyBatis, Oracle, MySQL | [pers-query_MyBatis_CRUD_표준_레퍼런스.md](./common/persistence/pers-query_MyBatis_CRUD_표준_레퍼런스.md) |
| **Common** | Persistence | `pers-query` | MyBatis DBMS별 매퍼 | DB 타입별 매퍼 위치 지정 및 파티셔닝 전략 | MyBatis, XML Config | [pers-query_MyBatis_DBMS별_매퍼_레퍼런스.md](./common/persistence/pers-query_MyBatis_DBMS별_매퍼_레퍼런스.md) |
| **Common** | Persistence | `pers-mapper` | BoardMapper 통합 | 게시판 데이터 접근 매퍼 인터페이스 및 쿼리 규격 | MyBatis, Mapper | [pers-mapper_BoardMapper_레퍼런스.md](./common/persistence/pers-mapper_BoardMapper_레퍼런스.md) |
| **Common** | Persistence | `pers-datasource` | AppMapper 멀티데이터소스 | AppMapper 확장 패턴 및 HAKSA 멀티 데이터소스 연동 | Spring, MyBatis, DataSource | [pers-datasource_AppMapper_멀티데이터소스_레퍼런스.md](./common/persistence/pers-datasource_AppMapper_멀티데이터소스_레퍼런스.md) |
| **Common** | Persistence | `pers-datasource` | Spring MyBatis Config | Spring-MyBatis 연동 설정 통합 명세 | Spring, MyBatis | [pers-datasource_Spring_MyBatis_Config_레퍼런스.md](./common/persistence/pers-datasource_Spring_MyBatis_Config_레퍼런스.md) |
| **Common** | Infrastructure | `infra-util` | JSON 처리 유틸리티 | Jackson ObjectMapper 싱글톤 래핑 및 타입 세이프 역직렬화 | Java, Jackson, JSON | [infra-util_JSON_처리_유틸리티_레퍼런스.md](./common/infrastructure/infra-util_JSON_처리_유틸리티_레퍼런스.md) |
| **Common** | Infrastructure | `infra-config` | config.JWXE 통합 | 시스템 프로퍼티 및 DBMS 연결 설정 | Config, JWXE | [infra-config_config_JWXE_레퍼런스.md](./common/infrastructure/infra-config_config_JWXE_레퍼런스.md) |
| **Common** | Infrastructure | `infra-config` | Java Dev Reference | 개발 환경 참조 정보 | Java | [infra-config_Java_Dev_Reference_레퍼런스.md](./common/infrastructure/infra-config_Java_Dev_Reference_레퍼런스.md) |
| **Common** | Infrastructure | `infra-config` | 개발 언어 정의 Java | 프로젝트 권장 자바 버전별 특징 및 구현 가이드 | Java 8, Java 17 | [infra-config_개발_언어_정의_Java_레퍼런스.md](./common/infrastructure/infra-config_개발_언어_정의_Java_레퍼런스.md) |
| **Common** | Infrastructure | `infra-config` | 클라이언트 ID 정의 | 사이트별 클라이언트 ID 설정 규격 | Config, JWXE | [infra-config_클라이언트_ID_정의_레퍼런스.md](./common/infrastructure/infra-config_클라이언트_ID_정의_레퍼런스.md) |
| **Custom** | Application | `app-controller` | U1LoginController 특화 | 프론트/관리자 통합 로그인 및 사이트별 동적 리다이렉션 | Spring MVC, AuthService | [app-controller_U1LoginController_레퍼런스.md](./custom/application/app-controller_U1LoginController_레퍼런스.md) |
| **Custom** | Infrastructure | `infra-integration` | UCM Lite 연동 명세 | 학사 시스템과 외부 클라우드(O365, Gsuite) SSO 연동 규약 | SSO, O365, Gsuite | [infra-integration_UCM_Lite_연동_명세_레퍼런스.md](./custom/infrastructure/infra-integration_UCM_Lite_연동_명세_레퍼런스.md) |
| **Custom** | Infrastructure | `infra-integration` | UCM Lite JSP 구현 | HttpURLConnection 기반 서버 간 통신 및 JDK 호환성 대응 | JSP, HTTP, TLS | [infra-integration_UCM_Lite_JSP_구현_레퍼런스.md](./custom/infrastructure/infra-integration_UCM_Lite_JSP_구현_레퍼런스.md) |
| **Custom** | Infrastructure | `infra-integration` | 유원대 학사 연계 | 유원대학교 학사 시스템 연동 전용 데이터 인터페이스 | ERP, Interface | [infra-integration_유원대_학사_연계_레퍼런스.md](./custom/infrastructure/infra-integration_유원대_학사_연계_레퍼런스.md) |

> [!NOTE]
> 위 목록은 `개발-레퍼런스-정리` 워크플로우를 통해 자동으로 관리됩니다. 레퍼런스 추가 시 해당 워크플로우를 실행하여 인덱스를 갱신하십시오.
