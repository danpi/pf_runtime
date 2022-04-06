package com.pulsar.func.client.producer;

import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.junit.Assert;
import org.junit.Test;

/**
 * com.pulsar.func.client.producer
 * ProducerManagerTest
 *
 * @author hbn
 * @date 2022/3/29
 */

public class ProducerManagerTest {
    private static String topicName = "demo2";
    private static final String MESSAGE_TEMPLATE = "pulsar-message-%s";

    @Test
    public void getProducerTest() {
        ProducerManager producerManager = new ProducerManager();
        Boolean ret = producerManager.getProducerByTopic(topicName);
        Assert.assertTrue(ret);
        for (int i = 0; i < 5; i++) {
            producerManager.sendMessage(topicName, String.format(MESSAGE_TEMPLATE, i));
        }
    }
}
