package com.pulsar.func.client.consumer;

import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClientException;
import org.junit.Assert;
import org.junit.Test;

/**
 * com.pulsar.func.client.consumer
 * ConsumerManagerTest
 *
 * @author hbn
 * @date 2022/3/29
 */

public class ConsumerManagerTest {
    private static String topicName = "demo2";
    private static String subscription = "demo2_c";

    @Test
    public void consumerTest() {
        ConsumerManager consumerManager = new ConsumerManager();
        Boolean ret = consumerManager.bootstrapConsumer(
                topicName,
                subscription,
                (consumer, msg) -> {
                    System.out.println("Message received: " + new String(msg.getData()));
                    try {
                        consumer.acknowledge(msg);
                    } catch (PulsarClientException e) {
                        System.out.println("ack failed, ex=" + e.getMessage());
                        consumer.negativeAcknowledge(msg);
                    }
                }
        );
        Assert.assertTrue(ret);
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException interruptedException) {
            System.out.println("ex=" + interruptedException.getMessage());
        }
    }
}
