package com.itheima.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 代码声明队列和交换机
@Configuration
public class FanoutConfig {

    @Bean
    public FanoutExchange fanoutExchange() {
//        return new FanoutExchange("fanoutExchange");
        return ExchangeBuilder.fanoutExchange("hmall.fanout").build();
    }

    // 声明一个队列
    @Bean
    public Queue fanoutQueue1() {
        return new Queue("fanoutQueue1");
    }

    @Bean
    public Binding fanoutBinding1() {
        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
    }


}
