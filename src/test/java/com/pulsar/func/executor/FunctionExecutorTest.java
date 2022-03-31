package com.pulsar.func.executor;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * com.pulsar.func.executor
 * FunctionExecutorTest
 *
 * @author hbn
 * @date 2022/3/30
 */

public class FunctionExecutorTest {
    @Test
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
    }
}
