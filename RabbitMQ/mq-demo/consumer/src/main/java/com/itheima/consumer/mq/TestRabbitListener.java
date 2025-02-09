package com.itheima.consumer.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Map;

@Slf4j
@Component
public class TestRabbitListener {
//    @RabbitListener(queues = "simple.queue")
//    public void receive(String message) {
//        System.out.println("Received: {}" + message);
//        log.info("Received: {}", message);
//    }
//
//    @RabbitListener(queues = "work.queue")
//    public void listenWorkQueue1(String message) throws InterruptedException {
//        System.out.println("listenWorkQueue1: Received: " + message + ", " + LocalTime.now());
//        Thread.sleep(25);
//    }
//
//    @RabbitListener(queues = "work.queue")
//    public void listenWorkQueue2(String message) throws InterruptedException {
//        System.err.println("listenWorkQueue2: Received: " + message + ", " + LocalTime.now());
//        Thread.sleep(200);
//    }
//
//    @RabbitListener(queues = "fanout.queue1")
//    public void listenFanoutQueue1(String message) throws InterruptedException {
//        System.out.println("listenFanoutQueue1: Received: " + message + ", " + LocalTime.now());
//    }
//
//    @RabbitListener(queues = "fanout.queue2")
//    public void listenFanoutQueue2(String message) throws InterruptedException {
//        System.err.println("listenFanoutQueue2: Received: " + message + ", " + LocalTime.now());
//    }
//
//    @RabbitListener(queues = "direct.queue1")
//    public void listenDirectQueue1(String message) throws InterruptedException {
//        System.out.println("listenDirectQueue1: Received: " + message + ", " + LocalTime.now());
//    }
//
//    @RabbitListener(queues = "direct.queue2")
//    public void listenDirectQueue2(String message) throws InterruptedException {
//        System.err.println("listenDirectQueue2: Received: " + message + ", " + LocalTime.now());
//    }
//
//    @RabbitListener(queues = "topic.queue1")
//    public void listenTopicQueue1(Map<String, Object> message) throws InterruptedException {
//        System.out.println("listenTopicQueue1: Received: " + message + ", " + LocalTime.now());
//    }
//
//    @RabbitListener(queues = "topic.queue2")
//    public void listenTopicQueue2(Map<String, Object> message) throws InterruptedException {
//        System.err.println("listenTopicQueue2: Received: " + message + ", " + LocalTime.now());
//    }

    //基于注解创建并使用
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "directAnno.queue", durable = "true"),
            exchange = @Exchange(name = "hamllAnno.direct", type = ExchangeTypes.DIRECT, durable = "true"),
            key = {"red", "blue"}
    ))
    public void listenQueue1(String message) throws InterruptedException {
        System.err.println("listenQueue1: Received: " + message + ", " + LocalTime.now());
    }
    //, arguments = @Argument(name = "x-queue-mode", value = "lazy")
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "directAnno2.queue", durable = "true"),
            exchange = @Exchange(name = "hamllAnno.direct", type = ExchangeTypes.DIRECT, durable = "true"),
            key = {"red", "yellow"}
    ))
    public void listenQueue2(String message) throws InterruptedException {
        System.err.println("listenQueue2: Received: " + message + ", " + LocalTime.now());
    }

    @RabbitListener(queues = "directQueue1")
    public void receive(Message message) {
        System.out.println("Received: {}" + new String(message.getBody()));
        System.out.println("Received ID: {}" + message.getMessageProperties().getMessageId());
        log.info("Received: {}", message);
//        throw new RuntimeException("Invalid!");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "dlx.queue", durable = "true"),
            exchange = @Exchange(name = "dlx.direct", type = ExchangeTypes.DIRECT, durable = "true"),
            key = {"hi"}
    ))
    public void listenDlxQueue(String message) throws InterruptedException {
        System.err.println("listenQueue2: Received: " + message + ", " + LocalTime.now());
    }
}
