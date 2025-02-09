package com.itheima.publisher;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Slf4j
@SpringBootTest
class SpringAmqpTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Test
    public void sendMessage() {
        String queueName = "work.queue";
        String message = "";

        for (int i = 0; i <= 50; i++) {
            message = "message_" + i;
            rabbitTemplate.convertAndSend(queueName, message);
        }

    }

    // 往交换机发消息 广播
    @Test
    public void sendMessage2() {
        String exchangeName = "amq.fanout";
//        String queueName = "fanout.queue1";
        String message = "";

        for (int i = 0; i <= 50; i++) {
            message = "message_" + i;
            rabbitTemplate.convertAndSend(exchangeName, null, message);
        }
    }

    // 往交换机发消息 定向
    @Test
    public void sendMessage3() {
        String exchangeName = "amq.direct";
//        String queueName = "fanout.queue1";
        String message = "";

        for (int i = 0; i <= 10; i++) {
            message = "message_" + i;
            rabbitTemplate.convertAndSend(exchangeName, "yellow", message);
        }
    }

    // 往交换机发消息 Topic
    @Test
    public void sendMessage4() {
        String exchangeName = "amq.topic";
//        String queueName = "fanout.queue1";
        String message = "";

        for (int i = 0; i <= 10; i++) {
            message = "message_" + i;
            rabbitTemplate.convertAndSend(exchangeName, "china.news", message);
        }
    }

    @Test
    public void sendMessage5() {
        String exchangeName = "amq.topic";

        Map<String, Object> msg = new HashMap<>();
        msg.put("name", "tom");
        msg.put("age", 25);

        rabbitTemplate.convertAndSend(exchangeName, "china.news", msg);
    }

    @Test
    public void sendMessage6() {

        CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
        cd.getFuture().addCallback(new ListenableFutureCallback<CorrelationData.Confirm>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("spring amqp处理异常 ", ex);
            }

            @Override
            public void onSuccess(CorrelationData.Confirm result) {
                if(result.isAck()){ // result.isAck()，boolean类型，true代表ack回执，false 代表 nack回执
                    log.debug("发送消息成功，收到 ack!");
                }else{ // result.getReason()，String类型，返回nack时的异常描述
                    log.error("发送消息失败，收到 nack, reason : {}", result.getReason());
                }
            }
        });


        String exchangeName = "hmall.direct";

        Map<String, Object> msg = new HashMap<>();
        msg.put("name", "tom");
        msg.put("age", 25);

        rabbitTemplate.convertAndSend(exchangeName, "red", msg, cd);
    }

    @Test
    public void sendMessage7() {

        String exchangeName = "hmall.direct";

        // 非持久化，内存快满写磁盘； 持久化，边写边写磁盘，效率高
        Message msg = MessageBuilder
                .withBody("\"hello world\"".getBytes(StandardCharsets.UTF_8))
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .build();
//        for (int i = 0; i < 100000; i++) {
//            rabbitTemplate.convertAndSend(exchangeName, "red", msg);
//        }

        rabbitTemplate.convertAndSend(exchangeName, "red", msg);

    }

    @Test
    public void sendMessage8() {

        String exchangeName = "normal.direct";
        rabbitTemplate.convertAndSend(exchangeName, "hi", "121414", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("10000");
                return message;
            }
        });
    }

}