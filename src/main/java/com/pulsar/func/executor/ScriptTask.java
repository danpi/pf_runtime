package com.pulsar.func.executor;

import com.pulsar.func.client.PulsarClientManager;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * com.pulsar.func.executor
 * functionScriptTask
 *
 * @author hbn
 * @date 2022/3/31
 */

public class ScriptTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ScriptTask.class);

    private String path = "";
    private String inputMessage = "";
    private boolean debug = true;
    private Consumer<String> consumer;
    private Message<String> message;

    public ScriptTask(String path, String inputMessage) {
        this.path = path;
        this.inputMessage = inputMessage;
    }

    public ScriptTask(String path, String inputMessage, boolean debug, Consumer<String> consumer, Message<String> message) {
        this.path = path;
        this.inputMessage = inputMessage;
        this.debug = debug;
        this.consumer = consumer;
        this.message = message;
    }

    public String execBashScripts(String filePath, String inputString) {
        logger.info("start execBashScripts for inputString={}", inputString);
        if (inputString == null) {
            logger.warn("inputString is null");
            return null;
        }
        String result = null;
        try {
            String[] commands = {"/bin/sh", filePath, inputString};
            Process process = Runtime.getRuntime().exec(commands);
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            result = in.readLine();
            in.close();
            process.waitFor();
        } catch (Exception e) {
            if (!debug) {
                PulsarClientManager.getInstance().deliverLogTopic(ExceptionUtils.getStackTrace(e));
            }
            logger.error("execBashScripts failed,inputString={},exception={}", inputString, e);
        }

        if (!debug) {
            PulsarClientManager.getInstance().redeliverOutPutTopic(result);
        }
        logger.info("execBashScripts result={}", result);
        return result;
    }

    @Override
    public void run() {
        execBashScripts(path, inputMessage);
        try {
            consumer.acknowledge(message);
        } catch (PulsarClientException e) {
            if (!debug) {
                PulsarClientManager.getInstance().deliverLogTopic(ExceptionUtils.getStackTrace(e));
            }
            logger.error("acknowledge failed,message={},exception={}", new String(message.getData()), e);
        }
    }
}
