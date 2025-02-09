package com.itheima.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectConfig {

    @Bean
    public DirectExchange directExchange() {
//        return new FanoutExchange("fanoutExchange");
        return ExchangeBuilder.directExchange("hmall.direct").build();
    }

    // 声明一个队列
    @Bean
    public Queue directQueue1() {
        return new Queue("directQueue1");
    }

    @Bean
    public Binding directBinding1() {
        return BindingBuilder.bind(directQueue1()).to(directExchange()).with("red");
    }


}
