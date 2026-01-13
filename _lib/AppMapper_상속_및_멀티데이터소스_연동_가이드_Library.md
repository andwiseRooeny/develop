# AppMapper 상속 및 멀티 데이터소스 연동 실무 가이드

본 가이드는 `AppMapper`를 상속받아 신규 Mapper를 구축하고, 독립적인 DataSource 및 MyBatis 설정을 통해 멀티 데이터소스를 연동하는 표준 절차를 정리합니다.

## 1. 개요 (Context)
기본 DB 외에 별도의 학사(HAKSA) DB를 연동해야 하는 상황에서, 프레임워크의 공통 기능을 활용하기 위해 `AppMapper`를 상속받고 독립적인 SqlSession을 주입해야 했습니다. 특히 매퍼 로딩 시 발생할 수 있는 경로 문제와 세션 팩토리 주입 방식을 실무 사례 중심으로 정의합니다.

## 2. 해결 방안 (Solution)

### 1단계: DataSource 설정 (`context-haksa-datasource.xml`)
표준 `JiniworksDataSourceFactory`를 사용하여 독립적인 커넥션 풀을 생성합니다.
```xml
<bean id="haksa.dataSource" class="com.andwise.jw.cms.datasource.DataSource">
    <property name="alias" value="HAKSA" /> <!-- DB 식별자 -->
    <property name="factory">
        <bean class="com.andwise.jw.cms.datasource.JiniworksDataSourceFactory" />
    </property>
</bean>
```

### 2단계: MyBatis SqlSession 설정 (`context-haksa-mybatis.xml`)
생성한 DataSource를 참조하는 전용 `sqlSessionFactory`를 정의합니다.
- **노하우**: 매퍼 로딩 시 `${queryLocation}`이 오작동할 경우, `oracle` 등 실제 경로를 명시적으로 지정하여 `FileNotFoundException`을 방지합니다.
```xml
<bean id="haksaSqlSession" class="com.andwise.jw.mybatis.RefreshableSqlSessionFactoryBean">
    <property name="dataSource" ref="haksa.dataSource" />
    <property name="configLocation" value="WEB-INF/properties/mybatis/mybatis-config.xml" />
    <property name="mapperLocations" value=".../properties/mybatis/oracle/haksa_app.xml" />
</bean>
```

### 3단계: Java Mapper 구현 (AppMapper 최소화 상속)
`@Repository`로 등록하고, 전용 세션 팩토리를 명시적으로 주입(`@Resource`)받도록 오버라이드합니다. **개별 쿼리 메서드는 작성하지 않고 부모(`AppMapper`)의 메서드를 직접 호출하여 사용합니다.**
```java
@Repository("haksaAppMapper")
public class HaksaAppMapper extends AppMapper {
    @Override
    @Resource(name = "haksaSqlSession") // 전용 세션 팩토리 주입
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }
}
```

### 4단계: 활용 측 호출 방식 (Service/Controller)
매퍼에 메서드를 만들지 않으므로, 호출 시 구문 ID(Namespace + ID)를 직접 지정하여 호출합니다.
```java
// 예시: 학사 사용자 정보 조회
EgovMap info = haksaAppMapper.selectOne("custom.mapper.haksa.HaksaAppMapper.selectHaksaUserInfo", memberId);
```

### 4단계: XML 매퍼 및 SQL 작성 (`haksa_app.xml`)
[MyBatis CRUD 표준 레퍼런스](../_ref/common/persistence/pers-query_MyBatis_CRUD_표준_레퍼런스.md)를 준수하여 작성합니다.
- `sql` 조각을 통한 컬럼 관리
- `camel` (EgovMap) 별칭 사용

## 3. 관련 레퍼런스 (References)
- [AppMapper 상속 및 멀티데이터소스 통합 레퍼런스](../_ref/common/persistence/pers-datasource_AppMapper_멀티데이터소스_레퍼런스.md)
- [MyBatis CRUD 표준 통합 레퍼런스](../_ref/common/persistence/pers-query_MyBatis_CRUD_표준_레퍼런스.md)

## 4. 회고 및 개선 (Retrospective)
- **성과**: 명시적 세션 주입을 통해 상속 구조에서도 멀티 DB를 안정적으로 제어할 수 있게 됨.
- **주의**: Spring 설정 파일에서 클래스 패스(`com.andwise.jw.cms...`) 오타 주의 및 DBMS가 고정된 경우 매퍼 경로를 명시적으로 선언하는 것이 유리함.
