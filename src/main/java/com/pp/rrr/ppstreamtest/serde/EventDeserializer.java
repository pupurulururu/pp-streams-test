package com.pp.rrr.ppstreamtest.serde;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pp.rrr.ppstreamtest.model.Event;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

public class EventDeserializer implements Deserializer<Event> {

    private Gson gson =
            new GsonBuilder().create();

    @Override
    public Event deserialize(String topic, byte[] data) {
        if (data == null) return null;
        return gson.fromJson(new String(data, StandardCharsets.UTF_8), Event.class);
    }
}
