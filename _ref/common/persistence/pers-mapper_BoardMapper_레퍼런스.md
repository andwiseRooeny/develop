# 핵심 요약 및 노하우
전자정부 프레임워크 기반의 고성능 게시판 매퍼 인터페이스로, MyBatis `ResultHandler`를 통한 **대용량 데이터 스트리밍 처리**와 다양한 식별자 형식을 수용하는 **Object 파라미터 설계 기법**, 그리고 관리자/사용자 역할별 **쿼리 분리 및 파라미터 재사용 노하우**가 적용됨.

# Source Path
java\com\andwise\jw\cms\mapper\BoardMapper.java

# Reference Code

## 1. 전자정부 프레임워크 @Mapper 인터페이스 규격
```java
@Mapper("boardMapper")
public interface BoardMapper {
    // MyBatis XML의 namespace와 매핑되어 SQL 호출 담당
}
```

## 2. 범용 식별자 처리를 위한 Object 파라미터 설계
```java
public BoardMstVO selectBoardMst( Object boardNo );
public String selectCustomFieldInfo( Object boardNo );
public void destroyArticle( Object articleNo );
```
- **노하우**: `boardNo`가 DB 환경이나 호출 경로에 따라 `String` 또는 `Integer`로 넘어올 수 있는 상황을 `Object` 타입으로 유연하게 처리함.

## 4. 대용량 데이터 조회를 위한 MyBatis ResultHandler 활용
```java
public void selectArticlePagingList(Map<String, Object> params, ResultHandler<Map<String,Object>> resultHandler );
```
- **노하우**: 수만 건의 게시물을 한 번에 메모리에 올리지 않고, `ResultHandler`를 통해 건별로 가공/처리하여 시스템 부하를 최소화하는 대용량 시그니처 설계.

## 6. 역할별(통합/콘텐츠관리자) 검색 쿼리 분리 구조
```java
// 통합/개발 관리자용 게시판 리스트
public List<BoardMstVO> selectBoardPagingList( Map<String,Object> params );

// 콘텐츠 관리자용 게시판 리스트 (권한 및 타겟 분리)
public List<BoardMstVO> selectConentAdmBoardPagingList(Map<String, Object> params);
```
- **노하우**: 유사한 검색 결과라도 관리 주체와 권한 범위에 따라 매퍼 메서드를 명확히 분리하여 쿼리 튜닝 및 유지보수 효율 증대.
