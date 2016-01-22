package com.tjazi.messagesrouter.core.config;

import com.tjazi.messagesrouter.core.endpoints.queuehandlers.MessagesRouterEndpoint;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Krzysztof Wasiak on 22/01/2016.
 */

@Configuration
public class AmqpConfiguration {
    @Value("${profilescreator.inputqueuename}")
    private String queueName;

    @Value("${profilescreator.exchangename}")
    private String exchangeName;

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }

    @Bean
    MessageConverter messageConverter() {
        return new JsonMessageConverter();
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter(MessageConverter messageConverter, MessagesRouterEndpoint routerEndpoint) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(routerEndpoint, messageConverter);
        messageListenerAdapter.setDefaultListenerMethod("routeMessage");
        return messageListenerAdapter;
    }

    @Bean
    SimpleMessageListenerContainer container(
            ConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();

        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);

        container.setMessageListener(messageListenerAdapter);

        return container;
    }

    @Bean
    MessagesRouterEndpoint messagesRouterEndpoint() {
        return new MessagesRouterEndpoint();
    }

}
