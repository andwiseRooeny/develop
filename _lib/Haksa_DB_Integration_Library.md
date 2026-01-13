# 학사 DB 연동 및 HaksaMemberMapper 구현 라이브러리

## 1. Problem & Solution (해결 과제 및 방안)
- **상황**: 프로젝트 메인 DB(MariaDB/Fox)와는 별개인 **외부 학사 DB(Oracle/SWD)**에 접속하여 로그인 인증 및 회원 정보를 조회해야 하는 요구사항 발생. 기존 시스템은 환경변수 하나로 전역 DB가 결정되는 구조여서 단순 설정 변경만으로는 이중 접속이 불가능했음.
- **해결**: 
  1. 기존 설정을 건드리지 않고 **독립된 Spring 설정 파일(`context-haksa-datasource.xml`)**을 생성.
  2. 학사 DB 전용 **DataSource**와 **SqlSessionFactory**를 별도로 빈으로 등록.
  3. **MapperScanner**를 사용하여 `com.andwise.jw.client.u1.custom.mapper` 패키지만 학사 DB 세션에 연결되도록 격리.

## 2. Reference Library (참조 지식 모음)
이 문제를 해결하기 위해 조합된 레퍼런스들입니다.

- [Spring_MyBatis_Config_통합_레퍼런스.md](../_ref/common/persistence/pers-datasource_Spring_MyBatis_Config_레퍼런스.md) - (기존 데이터소스 구조가 `Globals.DbType`에 종속적임을 확인)
- [MyBatis_DBMS별_매퍼_분리_구조.md](../_ref/common/persistence/pers-query_MyBatis_DBMS별_매퍼_레퍼런스.md) - (Oracle 전용 쿼리 파일(`haksa_member.xml`)을 `oracle/` 폴더에 위치시켜야 함을 확인)
- [_Reference_For_Java_Dev.md](../_ref/common/infrastructure/infra-config_Java_Dev_Reference_레퍼런스.md) - (커스텀 Mapper 인터페이스의 패키지 네이밍 규칙 준수)
- [BoardMapper_통합_레퍼런스.md](../_ref/common/persistence/pers-mapper_BoardMapper_레퍼런스.md) - (전자정부 프레임워크 호환 `@Mapper` 어노테이션 규격 확인)

## 3. Key Decision (의사결정 근거)
- **멀티 데이터소스 전략**: 메인 DB 커넥션 풀을 공유하지 않고 별도로 구성했습니다. 이는 학사 DB 장애가 발생하더라도 메인 CMS 서비스는 영향받지 않도록 하는 **격리(Isolation) 효과**를 위해서입니다.
- **패키지 스캔 분리**: `HaksaMemberMapper`만 별도 패키지로 관리하고 스캔 범위를 좁혔습니다. 만약 전역 스캔을 사용했다면 기존 Mapper들이 학사 DB를 바라보는 충돌이 발생했을 것입니다.

## 4. Improvement (향후 개선점)
- **보안 강화**: 현재 `context-haksa-datasource.xml`에 DB 비밀번호가 평문으로 노출되어 있습니다. 향후 프로퍼티 파일 분리 및 암호화 처리가 필요합니다.
- **로그인 로직 고도화**: 현재는 단순 DB 함수 호출이지만, 향후 세션 처리(Spring Security 연동 등)가 필요할 수 있습니다.
