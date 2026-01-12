# _Reference_For_Java_Dev.md

## 주제 정의
- **대상**: 자바 개발 (클라이언트 커스텀 경로)
- **경로**: `src/main/client/u1/java`
- **목표**: Java 8/17 병행 환경에서의 표준 개발 규칙 및 보안/인증 처리 가이드 제공

## 참조 레퍼런스
- [개발_언어_정의_Java8_17.md](./개발_언어_정의_Java8_17.md)
- [LoginController_통합_레퍼런스.md](./LoginController_통합_레퍼런스.md)
- [MyBatis_DBMS별_매퍼_분리_구조.md](./MyBatis_DBMS별_매퍼_분리_구조.md)

## 구현 가이드

### 1. 자바 버전 및 문법 표준
- **버전 정책**: 
  - 신규 모듈 및 Spring Boot 3+ 환경: **Java 17** (Record, Text Blocks 활용 권장)
  - 레거시 유지보수 모듈: **Java 8**
- **핵심 문법**:
  - Null 처리는 `Optional` 사용 (`Optional.ofNullable(val).orElseGet(...)`)
  - 복잡한 쿼리는 Java 17의 Text Blocks (`"""`) 사용

### 2. 컨트롤러 보안 및 인증 처리
- **Referer 검증**: 외부 URL 리다이렉트 방지를 위해 `Util.getHostHTTP(request)`를 통한 화이트리스트 검증 필수.
- **사이트 식별**: `PATH.getSiteIdFromUrl(referer)`를 통해 `SiteId`를 추출하고, 사이트별 설정에 따른 동적 처리를 수행.
- **데이터 전달**: 인증 실패 등 상태 전달 시 `RedirectAttributes.addFlashAttribute` 사용.

### 3. 데이터 접근 (MyBatis)
- **매퍼 분리**: DBMS별 특성(Oracle, MySQL 등)에 따라 매퍼 파일을 분리하여 관리 (`MyBatis_DBMS별_매퍼_분리_구조.md` 참조).

### 4. 추가 권장 사항
- 대상 경로(`client/u1/java/custom`)에 클래스 생성 시 패키지 구조는 `com.andwise.jw.client.u1.custom` 형식을 따를 것을 권장합니다.
