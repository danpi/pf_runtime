package com.pulsar.func.client;

import com.pulsar.func.client.consumer.ConsumerManager;
import com.pulsar.func.client.producer.ProducerManager;
import com.pulsar.func.constant.CommonConstant;
import com.pulsar.func.executor.FunctionExecutor;
import com.pulsar.func.executor.ScriptTask;
import com.pulsar.func.utils.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * com.pulsar.func
 * PulsarClientManager
 *
 * @author hbn
 * @date 2022/3/29
 */

public class PulsarClientManager {
    private static final Logger logger = LoggerFactory.getLogger(PulsarClientManager.class);

    private String basePath = "";
    private String inputTopic = CommonConstant.INPUT_TOPIC;
    private String outputTopic = CommonConstant.OUTPUT_TOPIC;

    private ProducerManager producerManager = new ProducerManager();
    private ConsumerManager consumerManager = new ConsumerManager();
    private static List<String> topics = null;

    private static PulsarClientManager instance = new PulsarClientManager();

    public static PulsarClientManager getInstance() {
        return instance;
    }

    /**
     * init for topic & subscription
     *
     * @return
     */
    public boolean init(String inputTopic, String outputTopic) {
        topics = new ArrayList<>();
        topics.add(CommonConstant.LOG_TOPIC);

        customizedTopicInit(inputTopic, outputTopic);

        boolean ret = true;
        //basePath = System.getProperty("user.dir");
        //register subscription for input topic
        ret = consumerManager.bootstrapConsumer(this.inputTopic, CommonConstant.INPUT_SUBSCRIPTION,
                (consumer, msg) -> {
                    logger.info("Message received,body={}", new String(msg.getData()));
                    try {
                        FunctionExecutor.getInstance().getScriptExecutorService().execute(
                                new ScriptTask(CommonUtils.getScriptFullPath(basePath, CommonConstant.REVERSE_SCRIPT), new String(msg.getData()), false, consumer, msg));
                        //some problems(maybe ack before exec)
                        consumer.acknowledge(msg);
                    } catch (PulsarClientException e) {
                        logger.error("ScriptTask failed,input string={},exception={}", msg.getData(), e);
                        consumer.negativeAcknowledge(msg);
                    }
                });
        if (!ret) {
            logger.error("register subscription for input topic failed");
            return false;
        }
        logger.info("register subscription for input topic success,input topic={},input subscription={}", this.inputTopic, CommonConstant.INPUT_SUBSCRIPTION);
        //初始化生产者
        List<String> failedTopicList = topics.stream().filter(topic -> !producerManager.getProducerByTopic(topic)).collect(Collectors.toList());
        if (!failedTopicList.isEmpty()) {
            logger.info("init producer success");
            return false;
        }
        return true;
    }

    //check customized topic
    private void customizedTopicInit(String inputTopic, String outputTopic) {
        if (topicNameCheck(inputTopic)) {
            this.inputTopic = inputTopic;
        } else {
            logger.warn("topicNameCheck failed,use default input topic={}", inputTopic);
        }

        if (topicNameCheck(outputTopic)) {
            this.outputTopic = outputTopic;
        } else {
            logger.warn("topicNameCheck failed,use default output topic={}", inputTopic);
        }
        topics.add(this.inputTopic);
        topics.add(this.outputTopic);
    }

    //check topicName with some rules
    private boolean topicNameCheck(String topicName) {
        if (StringUtils.isEmpty(topicName)) {
            return false;
        } else {
            //todo check topic name with naming rule
        }
        return true;
    }

    public ProducerManager getProducerManager() {
        return producerManager;
    }

    /**
     * redeliver to output topic
     *
     * @param message
     * @return
     */
    public boolean redeliverOutPutTopic(String message) {
        return producerManager.sendMessage(this.outputTopic, message);
    }

    /**
     * deliver to log topic
     *
     * @param message
     * @return
     */
    public boolean deliverLogTopic(String message) {
        return producerManager.sendMessage(CommonConstant.LOG_TOPIC, message);
    }

    public void stop() {
        producerManager.stop();
        consumerManager.stop();
    }


}
