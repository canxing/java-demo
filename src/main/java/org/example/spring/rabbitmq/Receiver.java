package org.example.spring.rabbitmq;

import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Component;

/**
 * 定义一个消息队列接收者
 */
@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    /**
     * 接收 rabbitmq 传递过来的消息
     * {@link MessagingRabbitmqApplication#listenerAdapter(Receiver)} 中定义由哪个方法接收消息
     * @param message 消息字符串
     */
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        throw new RuntimeException("test");
//        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
