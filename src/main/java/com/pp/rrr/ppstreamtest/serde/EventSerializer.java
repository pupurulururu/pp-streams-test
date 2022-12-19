package com.pp.rrr.ppstreamtest.serde;

import com.google.gson.Gson;
import com.pp.rrr.ppstreamtest.model.Event;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public class EventSerializer implements Serializer<Event> {

    private Gson gson = new Gson();

    @Override
    public byte[] serialize(String topic, Event data) {
        if (data == null) return null;
        return gson.toJson(data).getBytes(StandardCharsets.UTF_8);
    }
}
