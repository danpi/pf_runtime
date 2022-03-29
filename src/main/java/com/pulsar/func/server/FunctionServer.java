package com.pulsar.func.server;

import com.pulsar.func.client.PulsarClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * com.pulsar.func.server
 * FunctionServer
 *
 * @author hbn
 * @date 2022/3/29
 */

public class FunctionServer {
    private static final Logger logger = LoggerFactory.getLogger(FunctionServer.class);

    public static void main(String[] args) throws Exception {
        //PulsarClientManager init
        if (!PulsarClientManager.getInstance().init()) {
            logger.error("PulsarClientManager init failed");
            System.exit(-1);
        }

        //FunctionExecutor init
        //todo

        //server shutdown
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
    }
}
