package org.example.spring.rabbitmq;

import com.sun.jna.platform.win32.OaIdl;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class MessagingRabbitmqApplication implements RabbitListenerConfigurer {

    /**
     * ??
     */
    static final String topicExchangeName = "spring-boot-exchange";

    /**
     * 队列名称
     */
    static final String queueName = "spring-boot";

    /**
     * 定义一个 rabbitmq 消息队列，有什么用
     */
    @Bean
    Queue queue() {
        /**
         * name: 队列名称
         * durable： 是否持久化
         * exclusive: 是否独享、排外的。如果设置为true，定义为排他队列。则只有创建者可以使用此队列。也就是private私有的。默认 false
         * autoDelete: 是否自动删除。也就是临时队列。当最后一个消费者断开连接后，会自动删除。默认 false
         */
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }

    @Bean
    public Queue queueTwo() {
        return new Queue("test", false);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("test-exchange");
    }

    @Bean
    public Binding fanoutBinding(Queue queueTwo, FanoutExchange exchange) {
        return BindingBuilder.bind(queueTwo).to(exchange);
    }

//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//                                             MessageListenerAdapter listenerAdapter) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(queueName);
//        container.setMessageListener(listenerAdapter);
//        return container;
//    }

//    @Bean
//    public SimpleMessageListenerContainer container(SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory) {
//        SimpleRabbitListenerEndpoint endpoint = new SimpleRabbitListenerEndpoint();
//        endpoint.setQueueNames("queue.1");
//        endpoint.setMessageListener(message -> {
//        });
//        return rabbitListenerContainerFactory.createListenerContainer(endpoint);
//    }

//    @Bean
//    MessageListenerAdapter listenerAdapter(Receiver receiver) {
//        return new MessageListenerAdapter(receiver, "receiveMessage");
//    }

    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder(MessagingRabbitmqApplication.class).web(WebApplicationType.NONE).run(args).close();

//        TimeUnit.HOURS.sleep(2);
//        SpringApplication.run(MessagingRabbitmqApplication.class, args).close();
    }

    @Resource
    private Receiver receiver;
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        SimpleRabbitListenerEndpoint endpoint = new SimpleRabbitListenerEndpoint();
        endpoint.setId("abc");
        endpoint.setQueueNames(queueName);
        endpoint.setMessageListener(new MessageListenerAdapter(receiver, "receiveMessage"));
        registrar.registerEndpoint(endpoint);

        SimpleRabbitListenerEndpoint endpoint2 = new SimpleRabbitListenerEndpoint();
        endpoint2.setId("123");
        endpoint2.setQueueNames("test");
        endpoint2.setMessageListener(m -> {
            byte[] bytes = m.getBody();
            System.out.println(new String(bytes));
        });

        registrar.registerEndpoint(endpoint2);
    }
}
