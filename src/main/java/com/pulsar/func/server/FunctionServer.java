package com.pulsar.func.server;

import com.pulsar.func.client.PulsarClientManager;
import com.pulsar.func.executor.FunctionExecutor;
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
        //bootstrap params
        String inputTopic = args[0];
        String outputTopic = args[1];

        //FunctionExecutor init
        if (!FunctionExecutor.getInstance().start()) {
            logger.error("FunctionExecutor start failed");
            System.exit(-1);
        }

        //PulsarClientManager init
        if (!PulsarClientManager.getInstance().init(inputTopic, outputTopic)) {
            logger.error("PulsarClientManager init failed");
            System.exit(-1);
        }

        //server shutdown
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
    }
}
