# kakaopay-coupon


### 개발환경
- JAVA8
- Spring Boot 2.4.1
  - Spring Data JPA
  - Spring Security
- Gradle
- Junit5
- JWT(io.jsonwebtoken)
- H2

#### 빌드
``` bash
./gradlew bootJar  
```
#### 실행방법
``` bash
java -Dspring.profiles.active=local -jar coupon-0.0.1-SNAPSHOT.jar
```
----

## 문제 해결
- 쿠폰코드 생성
  - 현재 시간(ms)을 기준으로 16진수 문자 코드로 치환하여 11자리 생성 
  - UUID 생성한 코드 중 난수로 4자리 선택하여 4자리 선출
  - 중복 코드 방지를 위해 현재시간과 난수문자로 결합 
  - 쿠폰코드의 무결성 방지를 위해 15자리 코드를 해시함수로 2자리 생성
  - 쿠폰생성시간(11)+난수문자(4)+해쉬코드문자(2) = 총 17자리 코드
- 쿠폰 알림 송
  - 10시마다 만료된 쿠폰을 알림을 발송하도록 Scheduling 기능사용
  - 개선해야될점 : Batch 알림을 기존 어플리케이션에 영향을 주지 않고 따로 독립하여 배포 할 수 있도록 요구됨
- 쿠폰 만료상태 관리
  - 방안
    - 하루 정각에 배치를 돌려 쿠폰상태를 확인하여 만료상태로 변경
    - 실시간으로 쿠폰을 조회할때 현재 날짜와 만료날짜를 비교하여 만료상태를 표시
  - 2가지 방안 중 후자를 선택
- 대량 쿠폰생성
  - 현재 개발된 쿠폰생성 API 는 한번에 많은 쿠폰생성을 하기 위한 요구사항에 적합하지 않음.
  - 현재 스펙인 JPA 에서는 id 전략이 identity 일 경우 auto_increment 후에 영속화해야하므로 JPA 에서 지원 안됨.
  - 개선해야될점 : auto_increment 를 사용하지 않거나 다른 방법으로 구현해야함.
    후자의 경우 bulk insert 를 위한 방안으로 JDBC template 같은 방법으로 batch insert 를 구현해야 한다. 
- 보안
  - JWT 를 이용한 인증기능 추가 (알고리즘 : Hmac SHA256) 
  - Spring Security 를 사용하고 기존기능에 확장하여 개발함. package : com.kakaopay.coupon.auth.security
  - 인가를 통해 권한을 가진 유저만 호출 할 수 있도록 했으며 유효한 토큰정보 유저번호를(no)를 가지고 있어 사용자를 식별
  - 인증 Request Header : ex) Authorization: Bearer XXXXXXXXXXXXXX...
  - 쿠폰을 사용자에게 발급하는 API 는 일반 유저가 API 에 접근하면 안되기 때문에 어드민 권한을 고려해야함.
  - 에러 발생시 사용자에게 시스템오류를 상세히 알려주지 않도록 감추어야 하며 잘못된 요청이나 오류처리를 위한 
    공통에러 메세지를 고려하기 위해 Spring Boot 에서 지원하고있는 ErrorAttributes 를 오버라이딩 하여 사용함.
