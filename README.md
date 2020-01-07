jmlim-chat
==========
스프링 웹소켓을 사용한 채팅방.

~~spring-4.0.3 release
spring-security 3.2
jpa2(hibernate entity manager)~~

~~데이터베이스
apache-derby 10.10.1.1 사용~~

## 레거시 코드 spring boot로 전환
- 기존코드 legecy 브랜치로 뺌.
- Backend
    - spring 4.0.3 --> spring boot 2.2.2
    - jpa2(hibernate entity manager) --> spring data jpa
    - spring-security 3.2 --> spring boot starter security
    - maven --> gradle
    - 사진올리기는 제거 함. (추후 S3를 사용하여 작업 예정)
    - websocket messaging 처리 시 rabbitmq stomp 사용 (설치방법 추후에 정리 예정)
    - session 은 일단 db에서 관리
    - 테스트 코드 작성 필요
    - 버전만 올리는거라 금방 할줄 알았는데 생각보다 시간이 오래걸림...ㅡ.ㅡ
    - 현재 접속자 목록 가져오기 기능은 일단 디비에서 처리하도록 하였음.. (다른 저장소로 변경 예정.)
        - mysql (docker 로 8.0.17 설치, 테스트용임.)
        - https://jmlim.github.io/docker/2019/07/30/docker-mysql-setup/
- Frontend
    - jsp -> pebble template 으로 전환 
        - https://pebbletemplates.io/wiki/guide/spring-boot-integration  
        - https://github.com/hectorlf/pebble-spring-security-extension
    - 개선 예정

### rabbitmq 설치 자료 (ubuntu 18.04) 
  - https://www.rabbitmq.com/install-debian.html 
    - 이거보고 하는게 제일 정확 함. 다른데서 보고 했다가 에러나서 재 설치했음..

> 혹시 몰라서 history 기록
~~~
296  curl -fsSL https://github.com/rabbitmq/signing-keys/releases/download/2.0/rabbitmq-release-signing-key.asc | sudo apt-key add -
  297  sudo apt-key adv --keyserver "hkps://keys.openpgp.org" --recv-keys "0x0A9AF2115F4687BD29803A206B73A36E6026DFCA"
  298  sudo apt-get install apt-transport-https
  299  deb http://dl.bintray.com/rabbitmq-erlang/debian bionic erlang-21.x
  300  sudo vim /etc/apt/sources.list.d/bintray.erlang.list
  301  sudo apt-get update -y
  302  sudo apt-get install -y erlang-base                         erlang-asn1 erlang-crypto erlang-eldap erlang-ftp erlang-inets                         erlang-mnesia erlang-os-mon erlang-parsetools erlang-public-key                         erlang-runtime-tools erlang-snmp erlang-ssl                         erlang-syntax-tools erlang-tftp erlang-tools erlang-xmerl
  303  vim  /etc/apt/preferences.d/erlang
  304  wget -O - "https://packagecloud.io/rabbitmq/rabbitmq-server/gpgkey" | sudo apt-key add -
  305  sudo apt-get update -y
  306  sudo apt-get install curl gnupg -y
  307  curl -fsSL https://github.com/rabbitmq/signing-keys/releases/download/2.0/rabbitmq-release-signing-key.asc | sudo apt-key add -
  308  sudo apt-get install apt-transport-https
  309  sudo tee /etc/apt/sources.list.d/bintray.rabbitmq.list <<EOF
deb https://dl.bintray.com/rabbitmq-erlang/debian bionic erlang
deb https://dl.bintray.com/rabbitmq/debian bionic main
EOF

  310  sudo apt-get update -y
  311  sudo apt-get install rabbitmq-server -y --fix-missing
  312  sudo service rabbitmq-server stop
  313  sudo service rabbitmq-server start
  314  sudo service rabbitmq-server status
  315  rabbitmq-plugins enable rabbitmq_stomp
  316  rabbitmq-plugins enable rabbitmq_web_stomp
  317  sudo rabbitmq-plugins list
  318  rabbitmq-plugins enable rabbitmq_stomp
  319  sudo rabbitmq-plugins enable rabbitmq_stomp
  320  sudo rabbitmq-plugins list
~~~

### rabbitmq stomp (ubuntu 18.04) 설정 관련

 - list 확인
~~~
jmlim@jmlim-X555LAB:~$ sudo rabbitmq-plugins list
Listing plugins with pattern ".*" ...
 Configured: E = explicitly enabled; e = implicitly enabled
 | Status: * = running on rabbit@jmlim-X555LAB
 |/
[  ] rabbitmq_amqp1_0                  3.8.2
[  ] rabbitmq_auth_backend_cache       3.8.2
[  ] rabbitmq_auth_backend_http        3.8.2
[  ] rabbitmq_auth_backend_ldap        3.8.2
[  ] rabbitmq_auth_backend_oauth2      3.8.2
[  ] rabbitmq_auth_mechanism_ssl       3.8.2
[  ] rabbitmq_consistent_hash_exchange 3.8.2
[  ] rabbitmq_event_exchange           3.8.2
[  ] rabbitmq_federation               3.8.2
[  ] rabbitmq_federation_management    3.8.2
[  ] rabbitmq_jms_topic_exchange       3.8.2
[E*] rabbitmq_management               3.8.2
[e*] rabbitmq_management_agent         3.8.2
[  ] rabbitmq_mqtt                     3.8.2
[  ] rabbitmq_peer_discovery_aws       3.8.2
[  ] rabbitmq_peer_discovery_common    3.8.2
[  ] rabbitmq_peer_discovery_consul    3.8.2
[  ] rabbitmq_peer_discovery_etcd      3.8.2
[  ] rabbitmq_peer_discovery_k8s       3.8.2
[  ] rabbitmq_prometheus               3.8.2
[  ] rabbitmq_random_exchange          3.8.2
[  ] rabbitmq_recent_history_exchange  3.8.2
[  ] rabbitmq_sharding                 3.8.2
[  ] rabbitmq_shovel                   3.8.2
[  ] rabbitmq_shovel_management        3.8.2
[  ] rabbitmq_stomp                    3.8.2
[  ] rabbitmq_top                      3.8.2
[  ] rabbitmq_tracing                  3.8.2
[  ] rabbitmq_trust_store              3.8.2
[e*] rabbitmq_web_dispatch             3.8.2
[  ] rabbitmq_web_mqtt                 3.8.2
[  ] rabbitmq_web_mqtt_examples        3.8.2
[  ] rabbitmq_web_stomp                3.8.2
[  ] rabbitmq_web_stomp_examples       3.8.
~~~

 - rabbitmq stomp 설정
~~~
sudo rabbitmq-plugins enable rabbitmq_stomp
Enabling plugins on node rabbit@jmlim-X555LAB:
rabbitmq_stomp
The following plugins have been configured:
  rabbitmq_management
  rabbitmq_management_agent
  rabbitmq_stomp
  rabbitmq_web_dispatch
Applying plugin configuration to rabbit@jmlim-X555LAB...
The following plugins have been enabled:
  rabbitmq_stomp

started 1 plugins.

~~~

- list 재 확인
~~~
sudo rabbitmq-plugins list
Listing plugins with pattern ".*" ...
 Configured: E = explicitly enabled; e = implicitly enabled
 | Status: * = running on rabbit@jmlim-X555LAB
 |/
[  ] rabbitmq_amqp1_0                  3.8.2
[  ] rabbitmq_auth_backend_cache       3.8.2
[  ] rabbitmq_auth_backend_http        3.8.2
[  ] rabbitmq_auth_backend_ldap        3.8.2
[  ] rabbitmq_auth_backend_oauth2      3.8.2
[  ] rabbitmq_auth_mechanism_ssl       3.8.2
[  ] rabbitmq_consistent_hash_exchange 3.8.2
[  ] rabbitmq_event_exchange           3.8.2
[  ] rabbitmq_federation               3.8.2
[  ] rabbitmq_federation_management    3.8.2
[  ] rabbitmq_jms_topic_exchange       3.8.2
[E*] rabbitmq_management               3.8.2
[e*] rabbitmq_management_agent         3.8.2
[  ] rabbitmq_mqtt                     3.8.2
[  ] rabbitmq_peer_discovery_aws       3.8.2
[  ] rabbitmq_peer_discovery_common    3.8.2
[  ] rabbitmq_peer_discovery_consul    3.8.2
[  ] rabbitmq_peer_discovery_etcd      3.8.2
[  ] rabbitmq_peer_discovery_k8s       3.8.2
[  ] rabbitmq_prometheus               3.8.2
[  ] rabbitmq_random_exchange          3.8.2
[  ] rabbitmq_recent_history_exchange  3.8.2
[  ] rabbitmq_sharding                 3.8.2
[  ] rabbitmq_shovel                   3.8.2
[  ] rabbitmq_shovel_management        3.8.2
[E*] rabbitmq_stomp                    3.8.2
[  ] rabbitmq_top                      3.8.2
[  ] rabbitmq_tracing                  3.8.2
[  ] rabbitmq_trust_store              3.8.2
[e*] rabbitmq_web_dispatch             3.8.2
[  ] rabbitmq_web_mqtt                 3.8.2
[  ] rabbitmq_web_mqtt_examples        3.8.2
[  ] rabbitmq_web_stomp                3.8.2
[  ] rabbitmq_web_stomp_examples       3.8.2
~~~