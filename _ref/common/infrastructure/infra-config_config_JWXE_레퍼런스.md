# config_JWXE 통합 레퍼런스

# 핵심 요약 및 노하우
u1 프로젝트의 시스템 구동 및 DB 연결 설정을 정의하는 핵심 프로퍼티 파일입니다. JWXE 프레임워크의 프로젝트별 리소스 격리 정책에 따라 MariaDB 접속 정보와 로컬 개발용 HSQLDB 사용 여부를 제어하며, 효율적인 리소스 관리를 위해 Apache Commons DBCP 설정을 프로젝트 상황에 맞게 튜닝하는 노하우가 적용되었습니다.

# Source Path
src/main/webapp/_custom/u1/properties/config.JWXE

## 5. 핵심 프로퍼티 키워드 및 설정값 요약
- `db_type`: MariaDB 또는 MySQL 지정 (기본: `mariadb`)
- `db_url`: 데이터베이스 접속 JDBC URL 및 스키마 정보
- `maketable`: WAS 구동 시 테이블 자동 생성 여부 (`Y`/`N`)
- `isUseLocalDB`: 로컬 환경에서 테스트용 HSQLDB 사용 여부 (`Y`/`N`)
- `FilterInterceptor`: 모바일 디바이스 체크를 위한 필터 클래스 매핑
- `app.debug`: 화면상에 Model/Session Attribute 노출 여부 (개발용)

## 2. 데이터베이스 커넥션 풀(Apache Commons DBCP) 최적화 설정
- `db_apache_dbcp=Y`: 표준 DBCP 사용 활성화.
- `db_max_active=10`: 동시 접속 최대 허용 커넥션 수.
- `db_max_idle=5`: 대기 상태로 유지할 최대 커넥션 수.
- `db_driver`: MariaDB 전용 드라이버(`org.mariadb.jdbc.Driver`) 라이브러리 지정.

# Reference Code
```properties
# DB 접속 및 풀 설정
db_type=mariadb
db_driver=org.mariadb.jdbc.Driver
db_url=jdbc:mariadb://220.86.242.72:3306/u1cms
db_user=u1cms
db_password=12u1cms#$

# DBCP 튜닝
db_apache_dbcp=Y
db_max_active=10
db_max_idle=5

# 개발 편의 설정
maketable=Y
app.debug=Y
isUseLocalDB=Y
```
