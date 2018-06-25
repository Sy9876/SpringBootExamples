# curl模拟ajax请求

curl -i  -H "Accept: application/json" -H "X-Requested-With: XMLHttpRequest"

# spring security and angular

https://spring.io/guides/tutorials/spring-security-and-angular-js/

# spring security API doc

https://docs.spring.io/spring-security/site/docs/4.2.3.RELEASE/apidocs/

# references and guides

https://docs.spring.io/spring-security/site/docs/4.2.3.RELEASE/reference/htmlsingle

https://docs.spring.io/spring-security/site/docs/4.2.3.RELEASE/guides/html5/


# schema

``` sql
create table [user] (
  id varchar(50), 
  name varchar(50), 
  password varchar(50), 
  status char(1),
  primary key (id)
);

insert into [user] values ('1', 'sy', 'shenyue', '1');
insert into [user] values ('2', 'guest', 'guest', '1');
insert into [user] values ('3', 'admin', 'admin', '1');

create table my_menu (
  id varchar(50), 
  name varchar(50), 
  url varchar(255), 
  primary key (id)
);
insert into my_menu values ('1', 'void', '/void.do');
insert into my_menu values ('2', 'count', '/count.do');
insert into my_menu values ('3', 'user', '/user.do');
insert into my_menu values ('4', 'menus', '/menus.do');

create table my_role (
  id varchar(50), 
  name varchar(50), 
  primary key (id)
);
insert into my_role values ('1', 'guest');
insert into my_role values ('2', 'user');
insert into my_role values ('3', 'admin');

create table my_user_role (
  id varchar(50), 
  role_id varchar(50), 
  user_id varchar(50), 
  primary key (id)
);
insert into my_user_role values ('1', '1', '2');  // guest, guest
insert into my_user_role values ('2', '2', '1');  // user, sy
insert into my_user_role values ('3', '3', '3');  // admin, admin

create table my_menu_role (
  id varchar(50), 
  role_id varchar(50), 
  menu_id varchar(50), 
  primary key (id)
);
insert into my_menu_role values ('1', '3', '1');  // admin /void.do
insert into my_menu_role values ('2', '3', '2');  // admin /count.do
insert into my_menu_role values ('3', '3', '3');  // admin /user.do
insert into my_menu_role values ('4', '2', '1');  // user /void.do
insert into my_menu_role values ('5', '2', '2');  // user /count.do
insert into my_menu_role values ('6', '1', '1');  // guest /void.do
insert into my_menu_role values ('7', '3', '4');  // admin /menus.do

select * from [user];

select u.id, u.name as userName, mr.name as roleName, mm.name as menuName, mm.url as menuUrl
from [user] u
join my_user_role mur on mur.user_id=u.id 
join my_role mr on mr.id =mur.role_id
join my_menu_role mmr on mmr.role_id=mr.id
join my_menu mm on mm.id=mmr.menu_id
;


```


``` bat
set OPTS=-v 
set C=curl -i --cookie cookiejar.txt --cookie-jar cookiejar.txt -H "Accept: application/json" -H "X-Requested-With: XMLHttpRequest"

%C% "http://localhost:8080/void.do"

%C% "http://localhost:8080/login.do?name=sy&password=shenyue"

%C% "http://localhost:8080/count.do"

%C%  "http://localhost:8080/user.do?name=sy"




%C%  "http://localhost:8080/public/sendMsg.do"

%C%  "http://localhost:8080/public/sendInt.do?count=10"

%C%  "http://localhost:8080/public/sendDto.do"

%C%  "http://localhost:8080/public/sendRecord.do"


```

## REST API present sessionid in X-Auth-Token

``` bat

set CX=curl -i -H "Accept: application/json" -H "X-Requested-With: XMLHttpRequest"

%CX% "http://localhost:8080/void.do"

%CX% "http://localhost:8080/login.do?name=sy&password=shenyue"
%CX% "http://localhost:8080/login.do?name=admin&password=admin"

set X_AUTH_TOKEN=
%CX% -H "X-Auth-Token: %X_AUTH_TOKEN%" "http://localhost:8080/count.do"

%CX% -H "X-Auth-Token: %X_AUTH_TOKEN%" "http://localhost:8080/user.do?name=sy"

%CX% -H "X-Auth-Token: %X_AUTH_TOKEN%" "http://localhost:8080/menus.do?name=sy"

```



## kafka commands

```
cd %KAFKA_HOME%

set BROKERLIST=127.0.0.1:9092
set ZKLIST=127.0.0.1:2181
set TOPIC=myTopic

bin\windows\kafka-topics.bat --zookeeper %ZKLIST% --list

bin\windows\kafka-topics.bat --zookeeper %ZKLIST% --create --topic %TOPIC% --partitions 1 --replication-factor 1

bin\windows\kafka-topics.bat --zookeeper %ZKLIST% --describe


bin\windows\kafka-run-class.bat kafka.admin.ConsumerGroupCommand --bootstrap-server %BROKERLIST% --list


bin\windows\kafka-console-producer.bat --broker-list %BROKERLIST% --topic %TOPIC%

# bin\windows\kafka-console-consumer.bat --zookeeper %ZKLIST% --topic %TOPIC% --from-beginning
bin\windows\kafka-console-consumer.bat --bootstrap-server %BROKERLIST% --topic %TOPIC% --from-beginning

set KAFKA_LOG=kafka-logs\myTopic-0\00000000000000000000.log
#set KAFKA_LOG=kafka-logs\myTopic-0\00000000000000000000.index
bin\windows\kafka-run-class.bat kafka.tools.DumpLogSegments --print-data-log --files %KAFKA_LOG%



bin\windows\kafka-run-class.bat kafka.admin.ConsumerGroupCommand --bootstrap-server %BROKERLIST% --offsets --describe --group g1
Note: This will not show information about old Zookeeper-based consumers.
Consumer group 'g1' has no active members.

TOPIC           PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID     HOST            CLIENT-ID
myTopic         0          10237           11353           1116            -               -               -


```


## 测试结果

producer设置
spring.kafka.producer.valueSerializer=org.springframework.kafka.support.serializer.JsonSerializer
后，可用通过

```
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private KafkaTemplate<String, Integer> intKafkaTemplate;
	
	@Autowired
	private KafkaTemplate<String, DemoKafkaDto> dtoKafkaTemplate;
	
```

发送各种类型的数据。

consumer不做特殊设置，使用String参数的listener即可接收

```
@KafkaListener(topics = "myTopic", groupId="g1")
	@KafkaHandler()
	public void listen3(String payload) throws Exception { 
		logger.info("listen3 myTopic payload: " + payload);
	}
```

得到：
listen3 myTopic payload: "foo1"
listen3 myTopic payload: 10
listen3 myTopic payload: {"v1":null,"v2":null,"v3":0,"v4":null}


producer不使用JsonSerializer的话，无法发送String以外的消息



