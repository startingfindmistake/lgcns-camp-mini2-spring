# LG CNS AM Inspire Camp — Mini Project 2

## 설명
- LG CNS AM Inspire Camp에서 진행한 두 번째 Mini Project입니다. Spring Boot 기반으로 REST API, 인증(JWT), Redis 토큰 관리, JPA 연동, OpenAPI 문서화를 포함합니다.

## 주요 기능
- 사용자 인증: Access / Refresh 토큰 (JWT), 리프레시 토큰 회전 및 Redis 기반 무효화
- 캐시/세션/토큰 저장: Redis 연동
- API 문서: Springdoc(OpenAPI)으로 Swagger UI 제공

## 기술 스택
- Java, Spring Boot
- Spring Web / WebFlux, Spring Data JPA
- Spring Data Redis
- Spring Validation
- springdoc-openapi

## 시작
1. 요구사항: JDK 17+, Gradle wrapper
2. 설정: 데이터베이스 및 Redis 연결, JWT/외부 키(필요시) 등을 `application.yaml` 또는 환경변수로 설정
3. 빌드 및 실행:
   - 빌드: `./gradlew clean build`
   - 실행(개발): `./gradlew bootJar`
   - 실행(배포): `java -jar build/libs/*.jar`

## API 문서
- Swagger UI: `/swagger-ui/index.html`