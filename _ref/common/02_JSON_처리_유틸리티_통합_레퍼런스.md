# JSON 처리 유틸리티 통합 레퍼런스

**# 종합 요약 및 노하우**
본 유틸리티는 Jackson `ObjectMapper`를 싱글톤 기반으로 래핑하여 프로젝트 전반의 JSON 직렬화/역직렬화 표준을 정의합니다. 특히 TypeReference를 통한 제네릭 컬렉션 변환을 정형화하여 런타임 타입 세이프티를 확보한 것이 핵심입니다.

**# Referenced Assets**
- [JsonHelper.java](file:///c:/dev/eclipse/workspace/u1/u1/src/main/java/com/andwise/jw/util/JsonHelper.java)

**# 핵심 선언**

## 1. Jackson ObjectMapper 싱글톤 관리 및 래핑 전략
- 내부 `ObjectMapperInstance`를 통해 `ObjectMapper`를 전역적으로 공유하며, `JsonHelper.getMapper()`를 통해 커스텀 설정이 필요한 경우에 대응합니다.

## 2. JSON to Java (Map, List, Object) 역직렬화 표준 패턴
- JSON 문자열뿐만 아니라 `File` 객체를 직접 인자로 받아 내부적으로 파일을 읽고 변환하는 메서드를 오버로딩하여 비즈니스 로직의 간결함을 유지합니다.

## 3. Java to JSON String (Pretty Print 포함) 직렬화 처리 기법
- 단순 직렬화(`toJSONString(obj)`)와 가독성을 위한 프리티 프린트(`toJSONString(obj, true)`)를 지원하여 로그 기록 및 데이터 반환 시의 유연성을 제공합니다.

## 4. TypeReference를 활용한 제네릭 컬렉션 변환 기법
- `List<Map<T, B>>`와 같이 중첩된 제네릭 구조의 경우 익명 클래스 기반 `TypeReference`를 명시적으로 사용하여 타입 소거 문제를 방지합니다.

## 5. JsonHelper 클래스의 역할 및 유틸리티 설계 원칙
- 모든 변환 메서드는 정적(static)으로 선언되어 무상태성(Stateless)을 유지하며, 예외 발생 시 전역 예외 처리기에서 인식 가능한 `CmsIOException`으로 래핑하여 던집니다.

## 6. 데이터 포맷 변환 흐름 및 예외 처리 체계
- 파일 읽기(`TextUtil`), 데이터 매핑(Jackson), 예외 응답(`CmsIOException`)으로 이어지는 정형화된 흐름을 통해 데이터 처리의 안정성을 보장합니다.

**# Source Specifics**

```java
// 핵심 역직렬화 패턴 (TypeReference 활용)
public static <T,B> List<Map<T,B>> toMapList( String jsonStr ) {
    return objectMapper.readValue(jsonStr, new TypeReference<List<Map<T,B>>>(){});
}

// Pretty Print 직렬화 패턴
public static String toJSONString( Object object, boolean isPretty ) {
    if(isPretty) return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString( object );
    return objectMapper.writeValueAsString( object );
}
```
