# 업무명: 학사_DB_연동_및_AppMapper_구현

## 작업 상태: [/] 진행 중 (2026-01-12)

## 📋 진행 내역
- [x] 학사 전용 DB alias(`HAKSA`) 기반 Spring DataSource 설정 (`context-haksa-datasource.xml`)
- [x] 학사 전용 SqlSession 설정 (`context-haksa-mybatis.xml`)
    - [x] `FileNotFoundException` 방지를 위해 매퍼 경로를 `oracle`로 명시적 고정
- [x] `HaksaAppMapper.java` 상속 구조 및 명세 쿼리 메서드 구현
    - [x] 학사 DB 연동 쿼리 구현 (Password Check & User Info)
- [x] MyBatis 표준 레퍼런스(`01_MyBatis_CRUD_표준_통합_레퍼런스.md`) 기반 리팩토링 완료
- [x] 매퍼 로딩 오류 해결 및 경로 고정 (`oracle`)
- [x] `haksa_app.xml` MyBatis 매퍼 파일 생성 및 Oracle 연동 쿼리 구현
    - [x] 패스워드 검증 함수(`CHECK_EN_PASSWORD`) 바인딩
    - [x] 회원 정보 뷰(`VIEW_GONET_V_YDUAUTH`) 바인딩
    - [x] 불필요한 명시적 메서드 제거 (상속 표준 준수)
- [x] `haksa_app.xml` MyBatis 매퍼 파일 생성 및 기본 쿼리 구현

### 📚 참조 문서
- [AppMapper 상속 및 멀티데이터소스 통합 레퍼런스](_develop/_ref/common/AppMapper_상속_및_멀티데이터소스_통합_레퍼런스.md)
- [MyBatis CRUD 표준 통합 레퍼런스](_develop/_ref/common/01_MyBatis_CRUD_표준_통합_레퍼런스.md)
- [Work Directory 정보](_develop/_info/Work_Directory.md)
- [_develop/_ref/common/config_JWXE_통합_레퍼런스.md](file:///c:/dev/eclipse/workspace/u1/u1/src/main/_develop/_ref/common/config_JWXE_통합_레퍼런스.md)
- [webapp/_custom/u1/properties/config/spring/context-jiniworks-datasource.xml](file:///c:/dev/eclipse/workspace/u1/u1/src/main/webapp/_custom/u1/properties/config/spring/context-jiniworks-datasource.xml)

## 📂 수정/생성 파일 목록
- [NEW] `webapp/_custom/u1/properties/config/spring/context-haksa-datasource.xml`
- [NEW] `webapp/_custom/u1/properties/config/spring/context-haksa-mybatis.xml`
- [NEW] `java/custom/mapper/haksa/HaksaAppMapper.java`
- [NEW] `webapp/_custom/u1/properties/mybatis/oracle/haksa_app.xml`
- [DELETE] `webapp/_custom/u1/properties/config/spring/context-cms-datasource.xml`
- [DELETE] `webapp/_custom/u1/properties/config/spring/context-cms-mybatis.xml`
