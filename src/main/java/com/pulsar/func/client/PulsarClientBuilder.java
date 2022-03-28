package com.pulsar.func.client;

import org.apache.pulsar.client.api.PulsarClient;

public class PulsarClientBuilder {
    private static PulsarClient client;
    private static final String PULSAR_SERVER_URL = "pulsar://localhost:6650";

    private PulsarClientBuilder() {

    }
}
