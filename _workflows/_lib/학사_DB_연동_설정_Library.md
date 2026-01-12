# 학사 DB 연동 설정 개발 라이브러리

## 1. 개요 (Context)
- 학사 로그인 연동을 위해 기존 시스템(MariaDB/BASIC) 외에 학사 DB(Oracle/HAKSA)에 대한 멀티 데이터소스 접속 설정이 필요함.
- 지니웍스 프레임워크의 표준 방식을 준수하면서도, 매퍼 리프레시와 명시적 세션 주입 패턴을 적용하여 개발 편의성을 확보함.

## 2. 해결 방안 (Solution)
- **데이터소스**: `JiniworksDataSourceFactory`를 사용하여 `HAKSA` 알리야스 기반으로 동적 생성.
- **세션 관리**: `RefreshableSqlSessionFactoryBean`을 사용하여 XML 매퍼 수정 시 서버 재기동 없이 반영되도록 구성.
- **매퍼 연결**: `mybatis:scan`의 `factory-ref` 속성을 사용하여 `custom.mapper.haksa` 패키지만 특정 세션(`sqlSessionHaksa`)을 사용하도록 분리.
- **코드 패턴**: `AppMapper`를 상속받은 `HaksaAppMapper`에서 `@Resource(name = "sqlSessionHaksa")`를 통해 세션을 명시적으로 주입받아 사용.

## 3. 관련 레퍼런스 (References)
- [Common/AppMapper_상속_및_멀티데이터소스_통합_레퍼런스](../_ref/common/AppMapper_%EC%83%81%EC%86%8D_%EB%B0%8F_%EB%A9%80%ED%8B%B0%EB%8D%B0%EC%9D%B4%ED%84%B0%EC%86%8C%EC%8A%A4_%ED%86%B5%ED%95%A9_%EB%A0%88%ED%8D%BC%EB%9F%B0%EC%8A%A4.md)

## 4. 회고 및 개선 (Retrospective)
- 초기 계획에서 하드코딩된 설정을 사용하려 했으나, 사용자의 가이드를 통해 지니웍스 표준 팩토리 방식으로 전환하여 범용성을 확보함.
- 추후 다른 외부 DB 연동 시에도 동일한 `Factory -> Adapter -> Refreshable Session -> Specific Scan` 패턴을 재사용할 수 있음.
