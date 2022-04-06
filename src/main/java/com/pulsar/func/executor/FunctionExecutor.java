package com.pulsar.func.executor;

import com.pulsar.func.client.PulsarClientManager;
import com.pulsar.func.constant.CommonConstant;
import com.pulsar.func.server.FunctionServer;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.shade.com.google.common.collect.Queues;
import org.apache.pulsar.shade.org.apache.commons.lang3.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.pulsar.func.constant.CommonConstant.*;

/**
 * com.pulsar.func.executor
 * Executor
 *
 * @author hbn
 * @date 2022/3/29
 */

public class FunctionExecutor {
    private static final Logger logger = LoggerFactory.getLogger(FunctionServer.class);

    private ExecutorService scriptExecutorService = null;

    private static FunctionExecutor instance = new FunctionExecutor();

    public static FunctionExecutor getInstance() {
        return instance;
    }

    public boolean start() {
        scriptExecutorService = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors() * 2,
                60, TimeUnit.SECONDS,
                Queues.newLinkedBlockingQueue(DEFAULT_WORK_QUEUE),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        //记录丢弃的任务
                        logger.warn(r.toString() + "function script task is discard");
                    }
                }
        );
        return true;
    }

    public ExecutorService getScriptExecutorService() {
        return scriptExecutorService;
    }

    public void stop() {
        scriptExecutorService.shutdown();
    }
}
