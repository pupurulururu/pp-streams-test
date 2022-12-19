package com.pp.rrr.ppstreamtest.serde;

import com.pp.rrr.ppstreamtest.model.Event;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class EventSerdes implements Serde<Event> {
    @Override
    public Serializer<Event> serializer() {
        return new EventSerializer();
    }

    @Override
    public Deserializer<Event> deserializer() {
        return new EventDeserializer();
    }
}
