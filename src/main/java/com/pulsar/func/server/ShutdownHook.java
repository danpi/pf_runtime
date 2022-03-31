package com.pulsar.func.server;

import com.pulsar.func.client.PulsarClientManager;
import com.pulsar.func.executor.FunctionExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * com.pulsar.func.server
 * ShutdownHook
 *
 * @author hbn
 * @date 2022/3/29
 */

public class ShutdownHook extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(ShutdownHook.class);

    @Override
    public void run() {
        FunctionExecutor.getInstance().stop();
        PulsarClientManager.getInstance().stop();
        logger.info("server shut completely");
    }
}
