package com.pulsar.func.constant;

/**
 * com.pulsar.func.constant
 * CommonConstant
 *
 * @author pidan
 * @date 2022/3/29
 */
public class CommonConstant {
    //Producer
    public final static int BATCHING_MAX_PUBLISH_DELAY = 10;
    public final static int SEND_TIMEOUT = 10;

    //Consumer
    public final static String TOPIC_CONSUMER_SPLIT = ":";

    //topic
    public final static String INPUT_TOPIC = "input_topic";
    public final static String OUTPUT_TOPIC = "output_topic";
    public final static String LOG_TOPIC = "inner_log_topic";
    //subscription
    public final static String INPUT_SUBSCRIPTION = "input_topic_c";
    public final static String OUTPUT_SUBSCRIPTION = "output_topic_c";
    public final static String LOG_TOPIC_SUBSCRIPTION = "inner_log_topic_c";

    //script exec thread pool
    public final static int DEFAULT_WORK_QUEUE = 100;

    //script
    public final static String REVERSE_SCRIPT = "/scripts/reverse.sh";
}
