# 핵심 요약 및 노하우
지니웍스 프레임워크의 **동적 데이터소스 생성(Factory 패턴)**과 개발 생산성을 위한 **MyBatis XML 핫 리로딩(Refreshable)**, 그리고 코어/커스텀 영역 간의 유연한 **매퍼 오버라이딩(Multi-Path Resolver)** 기술이 집약된 설정입니다.

# Source Path
webapp/WEB-INF/config/spring/context-jiniworks-datasource.xml
webapp/WEB-INF/config/spring/context-jiniworks-mybatis-mapper.xml
webapp/_custom/u1/properties/config/spring/context-jiniworks-datasource.xml
webapp/_custom/u1/properties/config/spring/context-jiniworks-mybatis-mapper.xml

# Reference Code

## 1. JiniworksDataSourceFactory 기반 동적 데이터소스 생성
```xml
<!-- 지니웍스 환경설정에 의해 DataSource를 생성하는 팩토리 -->
<bean id="basicDataSourceFactory" class="md.database.datasource.JiniworksDataSourceFactory" >
    <property name="alias" value="BASIC" />
</bean>

<!-- DataSource 어댑터: 환경변수(Globals.DbType)에 따라 동적 바인딩 -->
<bean id="dataSource-${Globals.DbType}" class="md.database.datasource.DataSource" >
    <property name="dataSourceFactory" ref="basicDataSourceFactory"/>
</bean>
```
- **노하우**: `Globals.DbType` 변수를 활용하여 개발/운영 환경이나 DB 벤더 변경 시(Oracle↔MySQL 등) 소스 수정 없이 설정만으로 유연하게 대처 가능한 어댑터 패턴 적용.

## 2. RefreshableSqlSessionFactory를 활용한 XML 핫 리로딩
```xml
<bean id="sqlSession" class="com.andwise.jw.mybatis.RefreshableSqlSessionFactoryBean" >
    <!-- <property name="intervalSeconds" value="30" /> -->
    ...
</bean>
```
- **노하우**: 서버 재기동 없이 MyBatis XML 쿼리 수정 사항을 실시간 반영할 수 있는 `Refreshable` 빈 사용. (운영 배포 시엔 `interval`을 주석 처리하거나 길게 설정하여 오버헤드 방지 권장)

## 3. CustomXMLMapperPathResolver를 통한 다중 경로 매퍼 스캔
```xml
<!-- mybatis xml 경로를 탐색하는 커스텀 리졸버 -->
<bean id="resourcePathResolover" class="com.andwise.jw.config.resource.CustomXMLMapperPathResolver" />

<bean id="sqlSession" ... >
    <property name="resourcePathResolover" ref="resourcePathResolover" />
    <property name="mapperLocations">
        <list>
            <!-- 1. Core 영역 매퍼 -->
            <value>/WEB-INF/properties/mybatis/${queryLocation}/*.xml</value>
            <!-- 2. Custom (Override) 영역 매퍼 -->
            <value>/_custom/${customId}/properties/mybatis/${queryLocation}/*.xml</value>
        </list>
    </property>
</bean>
```
- **노하우**: `<list>` 순서에 따라 나중에 로드된 리소스(Custom)가 먼저 로드된 리소스(Core)를 **덮어쓰는(Override)** 구조. 이를 통해 `WEB-INF` 내의 원본 코드를 건드리지 않고 `_custom` 폴더에서 비즈니스 로직을 안전하게 확장/수정 가능.
