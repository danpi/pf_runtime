package com.pulsar.func.executor;

import com.pulsar.func.server.FunctionServer;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.shade.org.apache.commons.lang3.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * com.pulsar.func.executor
 * Executor
 *
 * @author hbn
 * @date 2022/3/29
 */

public class FunctionExecutor {
    private static final Logger logger = LoggerFactory.getLogger(FunctionServer.class);

    private static FunctionExecutor instance = new FunctionExecutor();

    private static FunctionExecutor getInstance() {
        return instance;
    }

    public void execBashScripts(String filePath, String inputString) {
        try {
            String[] commands = {"/bin/sh", filePath, inputString};
            Process process = Runtime.getRuntime().exec(commands);
//用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
