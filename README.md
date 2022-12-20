# pp-streams-test


- sh kafka-console-producer.sh --bootstrap-server 127.0.0.1:9092 --topic topic1 --property "parse.key=true" --property "key.separator=:"
  - s1:u1
- reset
- sh kafka-streams-application-reset.sh --bootstrap-servers localhost:9092 --application-id pp-streams --input-topics topic1 --to-earliest
- 조회
- sh kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic1 --from-beginning --property print.key=true --property key.separator=":"
