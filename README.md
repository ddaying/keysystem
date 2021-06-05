## TODO

#### 1. 기본 환경 구축
- [X] 프로젝트 세팅
- [X] 기본 Entity 정의
- [X] JPA 구성 후 연동 테스트 (Mysql / H2)

#### 2. Key 정보 등록 API
- [X] 1) policy-number (number)
- [X] 2) claim-number (string)

#### 3. Key 발급 API
- [] 1) GET /api/keyData/policy-number
- [] 2) GET /api/keyData/claim-number

---

## 고려 사항

---

## 메모 사항
- number 타입의 증서번호를 redis 로 처리 하기 위해 embedded 형태로 설정함
  -> 실제 개발/운영 레벨에서는 별도 RedisConfig 를 생성하여 remote redis 를 바라보도록 한다.

---
