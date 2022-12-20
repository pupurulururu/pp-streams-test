package com.pp.rrr.ppstreamtest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${bootstrap.servers:}")
    private String kafkaBootStrapServers;

    @Value("${application.id:}")
    private String applicationId;

    @Value("${source.topic:}")
    private String kafkaInputTopic;

    @Value("${state.dir}")
    private String stateDir;

    public void setKafkaBootStrapServers(String kafkaBootStrapServers) {
        this.kafkaBootStrapServers = kafkaBootStrapServers;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public void setKafkaInputTopic(String kafkaInputTopic) {
        this.kafkaInputTopic = kafkaInputTopic;
    }

    public String getKafkaBootStrapServers() {
        return kafkaBootStrapServers;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getKafkaInputTopic() {
        return kafkaInputTopic;
    }

    public String getStateDir() {
        return stateDir;
    }

    public void setStateDir(String stateDir) {
        this.stateDir = stateDir;
    }
}
