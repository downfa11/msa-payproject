package org.example.money.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.common.RechargingMoneyTask;
import org.example.money.application.port.out.SendRechargingMoneyTaskPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class TaskProducer implements SendRechargingMoneyTaskPort {
    private final KafkaProducer<String, String> producer;
    private final String topic;

    public TaskProducer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                        @Value("${task.topic}")String topic) {

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<>(props);
        this.topic = topic;
    }
    @Override
    public void sendRechargingMoneyTaskPort(RechargingMoneyTask task) {
        this.sendTask(task.getTaskID(),task);
    }

    public void sendTask(String key, RechargingMoneyTask task) {

        //json 형태로 kafka에 produce
        ObjectMapper mapper = new ObjectMapper();
        String jsonStringToProduce;

        try {
            jsonStringToProduce = mapper.writeValueAsString(task);
        } catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
        ProducerRecord<String, String> record = new ProducerRecord<>(topic,key,jsonStringToProduce);
        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                System.out.println("Message sent successfully. Offset: " + metadata.offset());
            } else {
                exception.printStackTrace();
            }
        });
    }
}
