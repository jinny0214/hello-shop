# hello-shop

# 🛒 간단한 쇼핑 웹 애플리케이션

Spring Boot, Spring Security, OAuth2, JPA를 활용하여 구현한 간단한 쇼핑 웹 애플리케이션입니다.  
회원가입 및 로그인 기능을 포함하며, 상품 주문/조회/수정/취소, 회원 관리 등 기본적인 CRUD 기능을 제공합니다.

## 🔧 기술 스택

- **Backend**: Java 11, Spring Boot, Spring Security, Spring Data JPA, OAuth2 Client
- **Frontend**: Thymeleaf (또는 사용한 템플릿 엔진), HTML/CSS, JavaScript
- **Database**: MySQL
- **Build Tool**: Gradle 또는 Maven
- **Version Control**: Git

## 📦 주요 기능

### 🔐 인증 및 인가
- 일반 회원가입 / 로그인 / 로그아웃
- OAuth2를 이용한 소셜 로그인 (예: Google)
- Spring Security 기반 인증 및 권한 관리

### 👤 회원 기능
- 회원 가입 / 정보 수정 / 탈퇴
- 관리자 권한으로 회원 목록 조회

### 🛍️ 상품 기능
- 상품 목록, 상세 조회
- 상품 등록 / 수정 / 삭제 (관리자 전용)

### 📦 주문 기능
- 상품 주문
- 주문 내역 조회
- 주문 취소

## 📁 프로젝트 구조

```bash
src
├── main
│   ├── java/jpabook
│   │   ├── auth           # OAuth2 관련 인증 처리
│   │   ├── config         # Spring Security, JPA, OAuth2 등 설정
│   │   ├── controller     # 웹 요청 처리 컨트롤러
│   │   ├── domain         # 엔티티 클래스
│   │   ├── exception      # 예외 처리
│   │   ├── repository     # JPA 리포지토리 인터페이스
│   │   ├── service        # 비즈니스 로직 처리
│   │   └── JpashopApplication.java # 메인 애플리케이션 클래스
│   └── resources
│       ├── static         # 정적 리소스(css, js 등)
│       ├── templates      # HTML 템플릿 (Thymeleaf 등)
│       └── application.yml# 환경설정 파일
```

### 📝 향후 개선 사항

- ✅ **테스트 코드 작성**
  - 통합 테스트(SpringBootTest) 추가
  - 인증/인가, 주문 처리 등 주요 비즈니스 로직 검증

- 🎨 **프론트엔드 UI/UX 개선**
  - Thymeleaf 템플릿 개선 또는 Vue.js 등 SPA 프레임워크 도입

- 🔒 **보안 강화**
  - CSRF, CORS, 세션 타임아웃 설정 최적화

- 📊 **관리자 기능 강화**
  - 회원 통계, 주문 통계 대시보드
  - 관리자 전용 상품/주문 관리 페이지

- 🔍 **고급 검색 기능 추가**
  - 상품명, 카테고리, 가격 범위 검색
  - 정렬 및 필터 기능


