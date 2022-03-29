package com.pulsar.func.client;

import com.pulsar.func.constant.CommonConstant;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.client.impl.PulsarClientImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * pulsar client builder
 *
 * @author pidan
 * @date 2022/6/5 16:31
 */
public class PulsarClientBuilder {
    private static final Logger logger = LoggerFactory.getLogger(PulsarClientBuilder.class);
    private static PulsarClient client;
    private static final String PULSAR_SERVER_URL = "pulsar://localhost:6650";

    private static PulsarClientBuilder instance = new PulsarClientBuilder();

    public static PulsarClientBuilder getInstance() {
        if (client == null) {
            try {
                client = PulsarClient.builder().serviceUrl(PULSAR_SERVER_URL).build();
            } catch (PulsarClientException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    /**
     * build a producer which schema is String
     *
     * @param topic
     * @return
     */
    public Producer<String> buildProducer(String topic) {
        try {
            return client.newProducer(Schema.STRING)
                    .topic(topic)
                    .batchingMaxPublishDelay(CommonConstant.BATCHING_MAX_PUBLISH_DELAY, TimeUnit.MILLISECONDS)
                    .sendTimeout(CommonConstant.SEND_TIMEOUT, TimeUnit.SECONDS)
                    .blockIfQueueFull(true).create();
        } catch (PulsarClientException e) {
            logger.error("buildProducer failed, topic={}, ex={}", topic, e);
        }
        return null;
    }

    /**
     * build a consumer which schema is String
     *
     * @param topic
     * @param subscription
     * @return
     */
    public Consumer<String> buildConsumer(String topic, String subscription, MessageListener<String> listener) {
        try {
            return client.newConsumer(Schema.STRING)
                    .topic(topic)
                    .subscriptionName(subscription)
                    .messageListener(listener)
                    .subscribe();
        } catch (PulsarClientException e) {
            logger.error("build consumer failed, topic={},subscription={} ex={}", topic, subscription, e);
        }
        return null;
    }

    public enum Topic {
        /**
         * 测试
         */
        DEMO("demo-topic"),
        ;

        private String name;

        Topic(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum Subscription {

        /**
         * 测试
         */
        DEMO("demo-subscription"),
        ;

        private String name;

        Subscription(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
