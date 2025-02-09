package com.itheima.consumer.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ErrorMsgConfig {

    private final RabbitTemplate rabbitTemplate;

    @Bean
    public DirectExchange directExchange() {
//        return new FanoutExchange("fanoutExchange");
        return ExchangeBuilder.directExchange("error.direct").build();
    }

    // 声明一个队列
    @Bean
    public Queue directQueue1() {
        return new Queue("error.queue");
    }

    @Bean
    public Binding directBinding1() {
        return BindingBuilder.bind(directQueue1()).to(directExchange()).with("error");
    }

    @Bean
    public MessageRecoverer messageRecoverer(){
//        return new MessageRecoverer() {
//            @Override
//            public void recover(Message message, Throwable exception) {
//                String msg = new String(message.getBody());
//                System.out.println("重试消费者："+msg);
//                // 重新入队
//                rabbitTemplate.convertAndSend("error.direct", "error", msg);
//            }
//        };
        return new RepublishMessageRecoverer(rabbitTemplate, "error.direct", "error");
    }


}
