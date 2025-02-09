package com.pipe.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ProducerTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 配置
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "hadoop100:9092,hadoop102:9092,hadoop103:9092");
        properties.put("acks", "all"); //kafka返回策略
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //创建生产者
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // 发送消息
//        for (int i = 0; i < 100; i++) {
//            Future<RecordMetadata> future = producer.send(new ProducerRecord<>("test", "key" + i, "value" + i));
//            future.get();
//            System.out.println("Send message to Kafka: " + "key" + i + ", " + "value" + i);
//        }

        //异步回调
//        for (int i = 0; i < 100; i++) {
//            // recordMetadata 消息元数据
//            producer.send(new ProducerRecord<>("test", "key" + i, "value" + i), (recordMetadata, e) -> {
//                if (e == null) {
//                    System.out.println("Send message to Kafka: " + recordMetadata.topic() + ", " + recordMetadata.partition() + ", " + recordMetadata.offset());
//                } else {
//                    System.out.println("Failed to send message to Kafka: " + e.getMessage());
//                }
//            });
//        }
        int iMax = 10000;
        int i = 0;
        while (i < iMax) {
            for (; i < 100; i++) {
                Thread.sleep(1000);
                // recordMetadata 消息元数据
                producer.send(new ProducerRecord<>("test2", "key" + i, "value" + i), (recordMetadata, e) -> {
                    if (e == null) {
                        System.out.println("Send message to Kafka: " + recordMetadata.topic() + ", " + recordMetadata.partition() + ", " + recordMetadata.offset());
                    } else {
                        System.out.println("Failed to send message to Kafka: " + e.getMessage());
                    }
                });
            }

        }


        // 关闭生产者
        producer.close();

    }
}
