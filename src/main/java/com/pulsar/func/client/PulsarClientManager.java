package com.pulsar.func.client;

import com.pulsar.func.client.consumer.ConsumerManager;
import com.pulsar.func.client.producer.ProducerManager;
import com.pulsar.func.constant.CommonConstant;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * com.pulsar.func
 * PulsarClientManager
 *
 * @author hbn
 * @date 2022/3/29
 */

public class PulsarClientManager {
    private static final Logger logger = LoggerFactory.getLogger(PulsarClientManager.class);

    private ProducerManager producerManager = new ProducerManager();
    private ConsumerManager consumerManager = new ConsumerManager();
    private static List<String> topics = Arrays.asList(CommonConstant.INPUT_TOPIC, CommonConstant.OUTPUT_TOPIC, CommonConstant.LOG_TOPIC);

    private static PulsarClientManager instance = new PulsarClientManager();

    public static PulsarClientManager getInstance() {
        return instance;
    }

    /**
     * init for topic & subscription
     *
     * @return
     */
    public boolean init() {
        boolean ret = true;
        //register subscription for input topic
        ret = consumerManager.bootstrapConsumer(CommonConstant.INPUT_TOPIC, CommonConstant.INPUT_SUBSCRIPTION,
                (consumer, msg) -> {
                    System.out.println("Message received: " + new String(msg.getData()));
                    try {
                        //todo executor
                        consumer.acknowledge(msg);
                    } catch (PulsarClientException e) {
                        System.out.println("ack failed, ex=" + e.getMessage());
                        consumer.negativeAcknowledge(msg);
                    }
                });
        if (!ret) {
            logger.error("register subscription for input topic failed");
            return false;
        }
        //初始化生产者
        List<String> failedTopicList = topics.stream().filter(topic -> !producerManager.getProducerByTopic(topic)).collect(Collectors.toList());
        if (!failedTopicList.isEmpty()) {
            return false;
        }
        return true;
    }

    public void stop() {
        producerManager.stop();
        consumerManager.stop();
    }


}
