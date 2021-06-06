## DEMO

- Run (port - 8080)
```shell script
./gradlew bootRun
```
<br>

- API 

KEY 정보를 등록하는 API - 숫자형
```shell script
$ curl -X POST 'http://localhost:8080/api/key/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "key": "policy-number",
    "description": "보험 증서 번호에 사용할 KEY 값으로 테이블 PK 로 사용",
    "type": "number",
    "generator": "redis",
    "min-length": 10
}'
```
```
{
  "code": 0,
  "message": "성공"
}
```
<br>

KEY 정보를 등록하는 API - 문자형 (이미 등록된 경우)
```shell script
$ curl -X POST 'http://localhost:8080/api/key/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "key": "claim-number",
    "description": "고객센터에서 고객 문의사항이 접수될 때 사용하는 KEY",
    "type": "string"
}'
```
```
{
  "code": -1001,
  "message": "이미 등록된 키 입니다."
}
```
<br>

KEY 발급
```shell script
$ curl -X GET 'http://localhost:8080/api/key/claim-number'
```
```
{
  "code": 0,
  "message": "성공",
  "data": {
    "value": "47I3-VEE2-DVDP-1YOJ"
  }
}
```
---

## TODO

#### 1. 기본 환경 구축
- [X] 프로젝트 세팅
- [X] 기본 Entity 정의
- [X] JPA 구성 후 연동 테스트 (Mysql / H2)

#### 2. Key 정보 등록 API
- [X] 1) policy-number (number)
- [X] 2) claim-number (string)

#### 3. Key 발급 API
- [X] 1) GET /api/key/policy-number
- [X] 2) GET /api/key/claim-number

---

## 메모 사항
- number 타입의 증서번호를 redis 로 처리 하기 위해 embedded 형태로 설정함
  - 실제 개발/운영 레벨에서는 별도 RedisConfig 를 생성하여 remote redis 를 바라보도록 한다.

- 기본 메트릭은 actuator & prometheus 설정 (`curl -X GET http://localhost:8080/actuator/prometheus`)으로 하였고, API 호출 관련 로깅을 위해 Interceptor 를 추가하여 아래 형태로 출력해보았습니다.
  - [시간] [호출 하는 IP] [호출 하는 API] [http status] [처리시간]

- 과제 의도와 부합하는 로직일지는 모르겠으나, string, number 형태의 키 발급 (필수 API 2번) 에 대한 nGrinder 테스트를 진행해보았습니다.  
  - 프로젝트 내 `그림_1_TEST_NUMBER.png`, `그림2_TEST_STRING.png` 첨부
  - 위와 같은 형태로 트래픽 테스트를 하며 중복 생성 이슈 외 기타 이슈 사항을 체크하곤 하였습니다.
---

## 개선 사항
- 최초 redis connection 시 약 5s 의 지연 시간이 발생
  - embedded redis 관련 이슈인지는 별도 확인 필요
