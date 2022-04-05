package com.pulsar.func.executor;

import com.pulsar.func.client.PulsarClientManager;
import org.apache.commons.lang.exception.ExceptionUtils;
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

    public ScriptTask(String path, String inputMessage) {
        this.path = path;
        this.inputMessage = inputMessage;
    }

    public ScriptTask(String path, String inputMessage, boolean debug) {
        this.path = path;
        this.inputMessage = inputMessage;
        this.debug = debug;
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
            e.printStackTrace();
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
    }
}
