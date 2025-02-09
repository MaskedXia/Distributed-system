package com.pipe.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ComsumerTest {
    public static void main(String[] args) {
        // 配置
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "hadoop100:9092,hadoop102:9092,hadoop103:9092");
        // 消费者组
        properties.put("group.id", "my-group01"); // consumer group
        // 自动提交
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        // 创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        //查看分区信息
//        List<PartitionInfo> partitions = consumer.partitionsFor("my-topic");
//
//        for (PartitionInfo partition : partitions) {
//            System.out.println("Partition: " + partition.partition());
//            System.out.println("Leader: " + partition.leader());
//        }

        // 订阅主题
        consumer.subscribe(Arrays.asList("test2"));

        while (true) {
            // 1s 拉一批数据
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("topic = %s, partition = %d, offset = %d, key = %s, value = %s%n",
                        record.topic(), record.partition(), record.offset(), record.key(), record.value());
            }
        }
    }
}
