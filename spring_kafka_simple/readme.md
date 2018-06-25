# curl模拟ajax请求

```

set CX=curl -i -H "Accept: application/json" -H "X-Requested-With: XMLHttpRequest"

%CX% "http://localhost:8080/public/sendKafkaMsgStr.do"

%CX% "http://localhost:8080/public/sendKafkaMsgInt.do"

%CX% "http://localhost:8080/public/sendKafkaMsg.do"

%CX% "http://localhost:8080/public/sendKafkaMsg2.do"

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


