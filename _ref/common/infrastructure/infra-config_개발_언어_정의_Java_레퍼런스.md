# 개발 언어 정의: Java 8 & Java 17 병행

## 기술 사양
1. Java 8 (Legacy Support, 안정성 전용)
2. Java 17 (Modern Features, Spring Boot 3+ 호환)

## 빌드 구성 (Gradle 예시)
```gradle
// 멀티 모듈 프로젝트에서의 버전 지정
subprojects {
    if (it.name.contains('legacy')) {
        sourceCompatibility = 1.8
        targetCompatibility = 1.8
    } else {
        sourceCompatibility = 17
        targetCompatibility = 17
    }
}
```

## 핵심 참조 코드 (Java 8 vs 17)

### Optional 처리 (Java 8+)
```java
// Java 8
Optional.ofNullable(val).orElseGet(() -> "default");
```

### Record 및 Text Blocks (Java 17)
```java
// Java 17
public record User(String name, int age) {}

String query = """
    SELECT * 
    FROM members 
    WHERE id = ?
    """;
```
