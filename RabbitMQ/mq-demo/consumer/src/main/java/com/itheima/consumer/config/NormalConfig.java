package com.itheima.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NormalConfig{

    @Bean
    public DirectExchange directExchange() {
//        return new FanoutExchange("fanoutExchange");
        return ExchangeBuilder.directExchange("normal.direct").build();
    }

    // 声明一个队列
    @Bean
    public Queue directQueue1() {
        return QueueBuilder.durable("normal.queue").deadLetterExchange("dlx.direct").build();
    }

    @Bean
    public Binding directBinding1() {
        return BindingBuilder.bind(directQueue1()).to(directExchange()).with("hi");
    }


}
