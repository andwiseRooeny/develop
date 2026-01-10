# 핵심 요약
Spring MVC의 인터셉터 체인을 활용하여 관리자 인증, 인가, 데이터 접근 로그 기록, 보안 IP 체크 등 요청의 전후 처리를 담당하는 핵심 흐름 정의

# Source Path
webapp\_custom\u1\properties\config\dispatcher-servlet.xml

# Reference Code
```xml
<mvc:interceptors>
    <!-- 관리자 인증 -->
    <mvc:interceptor>
        <mvc:mapping path="/WEB-INF/admin/**/*.do"/>
        <bean class="com.andwise.jw.cms.web.interceptor.AdminAuthenticationInterceptor" />
    </mvc:interceptor>

    <!-- 어플리케이션 로그 기록 -->
    <mvc:interceptor>
        <mvc:mapping path="/app/board/board.do"/>
        <mvc:mapping path="/app/app.do"/>
        <bean class="com.andwise.jw.cms.web.interceptor.AppLogInterceptor" />
    </mvc:interceptor>

    <!-- 관리자 보안 IP 체크 -->
    <mvc:interceptor>
        <mvc:mapping path="/WEB-INF/admin/**/*.do"/>
        <bean class="com.andwise.jw.cms.sys.web.interceptor.AdminPermissionInterceptor" />
    </mvc:interceptor>

    <!-- 외부 앱 연동 -->
    <mvc:interceptor>
        <mvc:mapping path="/**/*.do"/>
        <mvc:exclude-mapping path="/WEB-INF/admin/**/*.do"/>
        <bean class="com.andwise.jw.app.extapp.ExtAppInterceptor" />
    </mvc:interceptor>
</mvc:interceptors>
```

# 인터셉터 흐름 정의
1. **관리자 가드**: `/admin` 경로 진입 시 `AdminAuthenticationInterceptor`에서 세션 및 IP 정합성 선행 검사
2. **비즈니스 로깅**: 주요 서비스(`app.do`, `board.do`) 호출 시 `AppLogInterceptor`를 통한 활동 내역 기록
3. **보안 제외 정책**: 전역 인터셉터(`ExtAppInterceptor`) 적용 시 관리자 경로는 명시적으로 제외하여 간섭 방지
