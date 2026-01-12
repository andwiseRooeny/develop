# Source Path
webapp\WEB-INF\config.JWXE

# Reference Code
```properties
############################################
# 클라이언트 아이디
# 세부설정은 /_custom/xxx/properties/config.JWXE 에서 한다.
############################################
client=u1
```

# 클라이언트 ID 정의
1. **식별자 명칭**: `u1`
2. **역할**: JWXE 프레임워크 내에서 현재 프로젝트를 식별하는 고유 키
3. **영향 범위**:
   - `/_custom/u1/` 경로 내의 프로퍼티 및 리소스 로드 기준
   - 데이터베이스 접속 및 시스템 환경 설정의 구분자
