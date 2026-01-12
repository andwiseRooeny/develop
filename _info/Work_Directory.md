# 프로젝트 작업 디렉토리 가이드 (Work Directory)

이 문서는 프로젝트의 주요 폴더별 역할과 구조를 정의합니다. 모든 경로는 프로젝트 루트(`C:\dev\eclipse\workspace\u1\u1\src\main`) 기준 상대 경로로 표기됩니다.

## 📁 주요 경로 일람

### 1. 자바 구현 경로 (`java/custom`)
- **역할**: 프로젝트의 커스텀 비즈니스 로직(Controller, Service, Mapper 등)이 구현되는 핵심 공간입니다.
- **주요 하위 구조**:
  - `app/web`: 컨트롤러 클래스
  - `mapper/haksa`: 학사 관련 데이터 접근 계층
  - `login`: 로그인 및 보안 관련 로직

### 2. 웹 인프라 설정 (`webapp/WEB-INF`)
- **역할**: 웹 어플리케이션의 보안 리소스 및 표준 설정이 위치합니다.
- **주요 구성**:
  - `web.xml`: 어플리케이션 배치 기술서
  - `lib`: 프로젝트 참조 라이브러리 (Jar)
  - `jsp`: 화면 표현 레이어 (View)
  - `config.JWXE`: 프레임워크 초기 구동 설정

### 3. 커스텀 설정 관리 (`webapp/_custom/u1/properties`)
- **역할**: 프로젝트 고유의 세부 설정 파일들이 집약된 경로입니다.
- **주요 구성**:
  - `config/spring/`: 스프링 빈 설정 XML (DataSource, MyBatis 등)
  - `mybatis/`: SQL 쿼리 매퍼 XML
  - `data-source/`: DB 멀티 데이터소스 설정 (`.JWXE`)
  - `config.JWXE`: 시스템 운영 및 DB 연결 정보

### 4. 프론트엔드 공통 모듈 (`webapp/_custom/u1/_common/board/common`)
- **역할**: 일반게시판 및 CMS 모듈의 화면(View) 구성을 위한 공통 레이아웃과 기능별 JSP가 위치합니다.
- **주요 구성**:
  - `listLayout.jsp`, `viewLayout.jsp`, `writeLayout.jsp`: 기능별 표준 레이아웃
  - `list/`, `view/`, `write/`, `reply/`: 각 화면의 세부 컴포넌트 폴더
  - `password/`: 패스워드 확인 및 보안 입력 화면

### 5. 가이드 구현 경로 (`webapp/_custom/u1/jsp`)
- **역할**: UCMLite_Guide와 같은 시스템 가이드 및 특수 목적의 JSP 화면이 구현되는 경로입니다.
- **주요 구성**:
  - `UCMLite_Guide.jsp`: 시스템 가이드 메인 화면

---
> [!NOTE]
> 새로운 주요 경로가 추가되거나 역할이 변경될 경우, 반드시 이 문서를 최신화하여 팀 내 지식 격차를 방지합니다.
