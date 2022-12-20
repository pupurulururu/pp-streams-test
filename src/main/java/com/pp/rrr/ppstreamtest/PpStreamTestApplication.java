package com.pp.rrr.ppstreamtest;

import com.pp.rrr.ppstreamtest.model.Event;
import com.pp.rrr.ppstreamtest.serde.EventSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

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

        Topology topology = table_print();

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

    public Topology table_print() {

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> stream = builder.stream(appConfig.getKafkaInputTopic(), Consumed.with(Serdes.String(), Serdes.String())).map(
                (sid,uid) -> KeyValue.pair(sid, StringUtils.isEmpty(uid) ? "unknown_"+sid : uid));

        stream.print(Printed.toSysOut());
        stream.toTable().toStream().print(Printed.toSysOut());


        return builder.build();
    }

}
