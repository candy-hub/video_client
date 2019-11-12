package com.video.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    //创建消息队列
    @Bean
    public Queue queue(){
        return new Queue("mail");
    }

    //创建交换机
    @Bean
    public TopicExchange exchange(){
        //命名为TopicExchange名称：topicExchange
        return new TopicExchange("topicExchange");
    }

    //将交换机绑定到消息队列上
    @Bean
    public Binding bindingExchangeMassage(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("topic.me");
    }

}
