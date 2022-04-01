package com.pulsar.func.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * com.pulsar.func.utils
 * CommonUtils
 *
 * @author hbn
 * @date 2022/3/31
 */

public class CommonUtils {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    public static String getScriptFullPath(String basePath, String script) {
        return basePath + script;
    }
}
