package com.pp.rrr.ppstreamtest;

import com.pp.rrr.ppstreamtest.model.Event;
import com.pp.rrr.ppstreamtest.serde.EventSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.StateStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Properties;

@SpringBootApplication
public class PpStreamTestApplication implements ApplicationRunner {

    @Autowired
    private AppConfig appConfig;


    public static void main(String[] args) {
        SpringApplication.run(PpStreamTestApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Properties properties = getProperties();

        Topology topology = stream_print2();

        KafkaStreams streams = new KafkaStreams(topology, properties);

        streams.start();
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, appConfig.getKafkaBootStrapServers());
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, appConfig.getApplicationId());
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        return properties;
    }

    public Topology stream_print() {

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> stream = builder.stream(appConfig.getKafkaInputTopic(), Consumed.with(Serdes.String(), Serdes.String())).map(
                (sid,uid) -> KeyValue.pair(sid, StringUtils.isEmpty(uid) ? "unknown_"+sid : uid));

        stream.print(Printed.toSysOut());
        KTable<String, String> stringStringKTable = stream.toTable();
        stringStringKTable.toStream().print(Printed.toSysOut());


        return builder.build();
    }

    //stream -> table ->steam.print()랑 똑같다
    public Topology simple_table_tostream_print() {

        StreamsBuilder builder = new StreamsBuilder();

        builder.table(appConfig.getKafkaInputTopic(), Consumed.with(Serdes.String(), Serdes.String())).toStream().print(Printed.toSysOut());

        return builder.build();
    }


    /*
    [KTABLE-TOSTREAM-0000000007]: s1, 4
    [KTABLE-TOSTREAM-0000000007]: s2, 3
     */
    public Topology stream_print2() {

        StreamsBuilder builder = new StreamsBuilder();

        KGroupedStream<String, String> groupByKey = builder.stream(appConfig.getKafkaInputTopic(), Consumed.with(Serdes.String(), Serdes.String())).map(
                (sid, uid) -> KeyValue.pair(sid, StringUtils.isEmpty(uid) ? "unknown_" + sid : uid)).groupByKey();

        groupByKey.count().toStream().print(Printed.toSysOut());

        return builder.build();
    }



}
