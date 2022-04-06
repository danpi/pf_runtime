package com.pulsar.func.client.consumer;

import com.pulsar.func.client.PulsarClientBuilder;
import com.pulsar.func.client.producer.ProducerManager;
import com.pulsar.func.constant.CommonConstant;
import org.apache.pulsar.client.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * com.pulsar.func.client.consumer
 * consumerManager
 *
 * @author hbn
 * @date 2022/3/29
 */

public class ConsumerManager {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerManager.class);

    private ConcurrentHashMap<String, Consumer<String>> consumerMap = new ConcurrentHashMap<>();

    /**
     * bootstrap a consumer with MessageListener
     *
     * @param topic
     * @param subscription
     * @param listener
     * @return
     */
    public synchronized Boolean bootstrapConsumer(String topic, String subscription, MessageListener<String> listener) {
        //todo check input
        String tc = topic + CommonConstant.TOPIC_CONSUMER_SPLIT + subscription;
        if (consumerMap.containsKey(tc)) {
            logger.info("bootstrapConsumer already!");
            return true;
        }
        Consumer<String> consumer = PulsarClientBuilder.getInstance().buildConsumer(topic, subscription, listener);
        if (consumer == null) {
            logger.error("bootstrapConsumer failed,topic={},subscription={}", topic, subscription);
            return false;
        }
        consumerMap.put(tc, consumer);
        return true;
    }

    /**
     * close consumer
     *
     * @param topic
     * @param subscription
     * @return
     */
    public synchronized boolean closeConsumer(String topic, String subscription) {
        boolean ret = true;
        String tc = topic + CommonConstant.TOPIC_CONSUMER_SPLIT + subscription;
        logger.info("consumer should be close, topic={},subscription={}", topic, subscription);
        if (!consumerMap.containsKey(tc)) {
            return true;
        }
        Consumer<String> consumer = consumerMap.remove(topic);
        try {
            consumer.close();
        } catch (PulsarClientException e) {
            logger.error("consumer close failed, topic={}, subscription={}, ex={}", topic, subscription, e);
            ret = false;
        }
        return ret;
    }

    public void stop() {
        for (Map.Entry<String, Consumer<String>> consumerEntry : consumerMap.entrySet()) {
            try {
                consumerEntry.getValue().close();
            } catch (PulsarClientException e) {
                logger.error("close producer failed, topic:subscription={}", consumerEntry.getKey());
            }
        }
        consumerMap.clear();
    }
}
