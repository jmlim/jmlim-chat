jmlim-chat
==========
스프링 웹소켓을 사용한 채팅방.

~~spring-4.0.3 release
spring-security 3.2
jpa2(hibernate entity manager)~~

~~데이터베이스
apache-derby 10.10.1.1 사용~~

### 레거시 코드 spring boot로 전환
- 기존코드 legecy 브랜치로 뺌.
- Backend
    - spring 4.0.3 --> spring boot 2.2.2
    - jpa2(hibernate entity manager) --> spring data jpa
    - spring-security 3.2 --> spring boot starter security()
    - maven --> gradle
    - 사진올리기는 제거 함. (추후 S3를 사용하여 작업 예정)
    - websocket messaging 처리 시 rabbitmq stomp 사용 (설치방법 추후에 정리 예정)
    - session 은 일단 db에서 관리
    - 테스트 코드 작성 필요
    - 버전만 올리는거라 금방 할줄 알았는데 생각보다 시간이 오래걸림...ㅡ.ㅡ
    - 현재 접속자 목록 가져오기 기능은 일단 디비에서 처리하도록 하였음.. (다른 저장소로 변경 예정.)
- Frontend
    - 일단 그대로 둠..
    - 개선 예정
