package com.pp.rrr.ppstreamtest;

import com.pp.rrr.ppstreamtest.model.Event;
import com.pp.rrr.ppstreamtest.serde.EventSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PpStreamTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PpStreamTestApplication.class, args);
    }


    public void init(){

        StreamsBuilder builder = new StreamsBuilder();

        KTable<String, Event> sid_uid = builder.table("topic_name", Consumed.with(Serdes.String(), new EventSerdes()));



    }

}
