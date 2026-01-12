# Source Directory
webapp\_custom\u1\properties\mybatis

# Directory Role
다중 DBMS 환경을 지원하기 위한 데이터베이스 종류별 MyBatis SQL 매퍼 관리 체계 정의

# 상세 구성 및 역할
- `mysql/`: MySQL 엔진에 최적화된 SQL 매퍼 파일 저장 (예: `article.xml`)
- `oracle/`: Oracle 문법이 적용된 SQL 매퍼 파일 저장
- `mssql/`: MS-SQL용 SQL 매퍼 파일 저장

# 관리 규칙
1. 쿼리 문법의 차이가 있는 경우 각 DBMS 폴더 내에 동일한 파일명으로 관리
2. 프레임워크 설정에 의해 시스템 구동 시 해당 DBMS 폴더의 리소스가 우선 로드됨
