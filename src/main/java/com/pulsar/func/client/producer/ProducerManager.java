package com.pulsar.func.client.producer;

import com.pulsar.func.client.PulsarClientBuilder;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * com.pulsar.func.client.producer
 * ProducerManager
 *
 * @author hbn
 * @date 2022/3/29
 */

public class ProducerManager {
    private static final Logger logger = LoggerFactory.getLogger(ProducerManager.class);

    private ConcurrentHashMap<String, Producer<String>> producerMap = new ConcurrentHashMap<>();

    public synchronized Boolean getProducerByTopic(String topic) {
        //todo check input
        if (producerMap.containsKey(topic)) {
            return true;
        }

        Producer<String> producer = PulsarClientBuilder.getInstance().buildProducer(topic);
        if (producer == null) {
            logger.error("getProducerByTopic failed,topic={}", topic);
            return false;
        }
        producerMap.put(topic, producer);
        return true;
    }

    public synchronized boolean closeProducer(String topic) {
        boolean ret = true;
        logger.info("producer should be close, topic={}", topic);
        if (!producerMap.containsKey(topic)) {
            return true;
        }
        Producer<String> producer = producerMap.remove(topic);
        try {
            producer.close();
        } catch (PulsarClientException e) {
            logger.error("producer close failed, topic={}, ex={}", topic, e);
            ret = false;
        }
        return ret;
    }

    public boolean sendMessage(String topic, String message) {
        //todo check input
        if (!getProducerByTopic(topic)) {
            logger.error("do not get producer, topic={}", topic);
            return false;
        }

        try {
            Producer<String> producer = producerMap.get(topic);
            MessageId messageId = producer.send(message);
            logger.info("sendMessage success,messageId={}", messageId.toString());
        } catch (PulsarClientException e) {
            logger.error("sendMessage failed,topic={},ex={}", topic, e);
            return false;
        }
        return true;
    }

    public void stop() {
        for (Map.Entry<String, Producer<String>> producer : producerMap.entrySet()) {
            try {
                producer.getValue().close();
            } catch (PulsarClientException e) {
                logger.error("close producer failed, topic={}", producer.getKey());
            }
        }
        producerMap.clear();
    }
}

