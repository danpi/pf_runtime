package com.pulsar.func.executor;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * com.pulsar.func.executor
 * FunctionExecutorTest
 *
 * @author hbn
 * @date 2022/3/30
 */

public class FunctionExecutorTest {
    private static FunctionExecutor functionExecutor;

    @BeforeClass
    public static void beforeClass() {
        functionExecutor = new FunctionExecutor();
        functionExecutor.start();
    }

    @Test
    public void functionExecScript() throws InterruptedException {
        String bashPath = System.getProperty("user.dir");
        //the common data
        String inputString = "123  abcd @#ad";
        functionExecutor.getScriptExecutorService().execute(new ScriptTask(bashPath + "/scripts/reverse.sh", inputString));
        Thread.sleep(1000);
    }

/*    @Test
    public void execScriptTest() {
        String bashPath = System.getProperty("user.dir");
        //the common data
        String inputString = "123  abcd @#ad";
        String outputStr = FunctionExecutor.getInstance().execBashScripts(bashPath + "/scripts/reverse.sh", inputString);
        Assert.assertEquals(outputStr, StringUtils.reverse(inputString));

        //the critical data
        inputString = "";
        outputStr = FunctionExecutor.getInstance().execBashScripts(bashPath + "/scripts/reverse.sh", inputString);
        Assert.assertEquals(outputStr, StringUtils.reverse(inputString));

        inputString = null;
        outputStr = FunctionExecutor.getInstance().execBashScripts(bashPath + "/scripts/reverse.sh", inputString);
        Assert.assertEquals(outputStr, StringUtils.reverse(inputString));
    }*/
}
