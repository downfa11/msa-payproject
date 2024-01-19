package org.example.taskconsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.example.common.RechargingMoneyTask;
import org.example.common.SubTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Component
public class TaskConsumer {
    private final KafkaConsumer<String, String> consumer;
    private final TaskResultProducer taskResultProducer;

    public TaskConsumer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                        @Value("${task.topic") String topic,TaskResultProducer taskResultProducer) {

        Properties props = new Properties();
        props.put("bootstrap.servers",bootstrapServers);
        props.put("group.id","my-group");
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        // Consume = deserializer

        this.consumer=new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));
        this.taskResultProducer = taskResultProducer;
        Thread consumerThread = new Thread(() -> {
            try{
                while(true){
                    ConsumerRecords<String,String> records = consumer.poll(Duration.ofSeconds(1));
                    ObjectMapper mapper = new ObjectMapper();
                    for (ConsumerRecord<String,String > record : records){
                        //jsonString 형태의 RechargingMoneyTask를 Parsing
                        RechargingMoneyTask task;

                        // task run, produce result
                        try {
                             task = mapper.readValue(record.value(), RechargingMoneyTask.class);
                        }catch (JsonProcessingException e){
                            throw new RuntimeException(e);
                        }

                        for (SubTask subTask : task.getSubTaskList()){
                            // all subtask maybe done

                            subTask.setStatus("success");
                        }

                        this.taskResultProducer.sendTaskResult(task.getTaskID(),task);
                    }
                }
            } finally {
                consumer.close();
            }
        });
        consumerThread.start();


    }
}
