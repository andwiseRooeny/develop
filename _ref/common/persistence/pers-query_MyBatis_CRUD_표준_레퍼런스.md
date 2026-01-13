# MyBatis CRUD 표준 통합 레퍼런스

본 문서는 프로젝트 내 MyBatis 쿼리 개발 시 준수해야 할 데이터 처리, 동적 SQL 구성, 그리고 멀티 DBMS 대응 표준을 정의합니다.

# 종합 요약 및 노하우
프레임워크의 MyBatis 환경은 **CamelCase 매핑과 CLOB/JSON 데이터의 자동 객체화**를 기본으로 합니다. `@Ognl@`을 활용한 강력한 동적 제어와 `<sql>`, `<include>` 기반의 모듈화 설계를 통해 쿼리의 재사용성을 극대화하며, Oracle과 MySQL 간의 문법 차이를 `Upsert` 및 `페이징` 등 핵심 영역에서 일관성 있게 핸들링하는 것이 기술적 핵심입니다.

# Referenced Assets
- [mybatis-config.xml](webapp/WEB-INF/properties/mybatis/mybatis-config.xml)
- [article.xml (Oracle)](webapp/WEB-INF/properties/mybatis/oracle/article.xml)
- [board.xml (Oracle)](webapp/WEB-INF/properties/mybatis/oracle/board.xml)
- [member.xml (MySQL)](webapp/WEB-INF/properties/mybatis/mysql/member.xml)

# 핵심 선언

## 1. CLOB & JSON 데이터 정밀 핸들링
비정형 대용량 데이터(CLOB)와 구조화된 데이터(JSON)를 투명하게 로드하고 안전하게 저장하기 위한 규격입니다.

### 조회 (Select)
`ResultMap` 정의 시 `jdbcType="CLOB"`를 명시하거나, 별칭으로 등록된 전용 `TypeHandler`를 연결합니다.
- **표준 핸들러 별칭**: `JsonMapFieldClobType` (CLOB ⇄ Map), `JsonListFieldClobType` (CLOB ⇄ List)
```xml
<resultMap id="clobMap" type="ArticleVO">
    <!-- CLOB -> String 자동 변환 -->
    <result property="articleText" column="article_text" jdbcType="CLOB" javaType="java.lang.String" />
    <!-- CLOB JSON -> Map 자동 변환 (별칭 사용) -->
    <result property="customField" column="custom_field" jdbcType="CLOB" typeHandler="JsonMapFieldClobType" />
</resultMap>
```

### 저장/수정 (Insert/Update)
바인딩 변수 선언 시 `:CLOB`를 명시하여 대용량 데이터 처리 중 발생할 수 있는 드라이버 오류를 방지합니다.
```xml
INSERT INTO jwarticle (article_text) VALUES (#{articleText:CLOB})
```

## 2. OGNL 기반 동적 SQL 제어 표준
`@Ognl@` 커스텀 유틸리티 클래스를 활용하여 파라미터의 타입 체크 및 복합 조건을 선언적으로 제어합니다.
- **주요 메서드**: `isEmpty()`, `isNotEmpty()`, `isNumber()`, `isEqual()`, `isNotEqual()`
```xml
<if test="@Ognl@isNotEmpty(srSearchVal)">
    <choose>
        <when test="@Ognl@isEqual(srSearchKey,'article_title')">
            AND INSTR(UPPER(a.article_title), UPPER(#{srSearchVal})) > 0
        </when>
    </choose>
</if>
```

## 3. 멀티 DBMS 대응 Upsert (MERGE) 패턴
데이터 존재 여부에 따라 Insert/Update를 자동 결정하는 로직을 DBMS별 표준 문법으로 구현합니다.
- **Oracle (MERGE)**: `MERGE INTO [테이블] USING (SELECT 1 FROM DUAL) ...`
- **MySQL (DUPLICATE KEY)**: `INSERT INTO ... ON DUPLICATE KEY UPDATE ...`
```xml
/* Oracle 예시 */
<update id="updateAdminType" parameterType="AdminVO">
    MERGE INTO jwadmin_member d
    USING (SELECT 1 FROM DUAL) s ON (d.member_id = #{memberId})
    WHEN MATCHED THEN UPDATE SET admin_ty = #{adminTy}
    WHEN NOT MATCHED THEN INSERT (member_id, admin_ty) VALUES (#{memberId}, #{adminTy})
</update>
```

## 4. 모듈화된 쿼리 설계 (`sql` & `include`)
반복되는 컬럼 정의나 공통 검색 조건을 분리하여 코드 중복을 제거하고 유지보수성을 높입니다.
```xml
<sql id="articleColumns">
    board_no, article_no, article_title, writer_id, create_dt
</sql>

<select id="selectArticleList" resultMap="articleClob">
    SELECT <include refid="articleColumns"/> FROM jwarticle
    WHERE board_no = #{boardNo}
    <include refid="searchCondition"/>
</select>
```

## 5. Map 결과 처리 및 TypeAlias 표준
조회 결과를 `VO` 객체가 아닌 `Map`으로 받을 때, 프로젝트에 정의된 `TypeAlias`를 사용하여 일관된 데이터 구조(CamelCase)를 보장합니다.

- **표준 별칭**: `camel`, `CamelMap` (내부적으로 `org.egovframe.rte.psl.dataaccess.util.EgovMap` 사용)
- **특징**: DB 컬럼명(`USER_ID`)을 자바 낙타표기법(`userId`)으로 자동 변환하여 매핑합니다.
```xml
<select id="selectHaksaUserInfo" resultType="camel">
    SELECT ID, NAME FROM YTIS_HAKSA.VIEW_GONET_V_YDUAUTH
</select>
```
> [!NOTE]
> `egovMap`은 프레임워크에 따라 별칭으로 자동 등록되어 있을 수 있으나, 명시적 설정인 `camel` 또는 `CamelMap` 사용을 권장합니다.

## 7. 동적 바인딩 보안 표준
동적 쿼리 작성 시 SQL Injection 방지를 위해 바인딩 변수(`#{}`) 사용을 원칙으로 하며, 문자열 치환(`${}`) 사용 시에는 엄격한 검증을 선행합니다.

- **원칙**: 모든 사용자 입력값은 `#{}`를 사용합니다.
- **예외**: 테이블명, 컬럼명 등 SQL 구조를 동적으로 변경해야 하는 경우 `${}`를 사용하되, 반드시 **`@Ognl@isSafeSql`** 검증을 거쳐야 합니다.
```xml
<if test="@Ognl@isNotEmpty(sortCol) and @Ognl@isSafeSql(sortCol)">
    ORDER BY ${sortCol} ASC
</if>
```

---
> [!IMPORTANT]
> 신규 DBMS 추가 시 `queryLocation` 파티셔닝 전략에 따라 전용 폴더(mssql, mysql, oracle 등)에 매퍼를 배치하고 상기 표준을 준수하십시오.

> [!NOTE]
> **수정 이력**
> - 2026-01-12: CLOB 정밀 핸들링, TypeHandler 별칭 규격, 동적 바인딩 보안 표준 보강
> - 2026-01-12: Map 결과 처리(camel/CamelMap) 및 TypeAlias 표준 추가
