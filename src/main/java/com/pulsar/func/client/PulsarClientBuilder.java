package com.pulsar.func.client;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.PulsarClientImpl;

/**
 * pulsar client builder
 *
 * @author pidan
 * @date 2022/6/5 16:31
 */
public class PulsarClientBuilder {
    private static PulsarClient client;
    private static final String PULSAR_SERVER_URL = "pulsar://localhost:6650";

    private PulsarClientBuilder() {
    }

    public static PulsarClient getInstance() {
        if (client == null) {
            try {
                client = PulsarClient.builder().serviceUrl(PULSAR_SERVER_URL).build();
            } catch (PulsarClientException e) {
                e.printStackTrace();
            }
        }
        return client;
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
