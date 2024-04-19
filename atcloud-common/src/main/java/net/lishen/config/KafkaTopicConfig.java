package net.lishen.config;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.config
 * @Project：atcloud-meter
 * @name：KafkaTopicConfig
 * @Date：2024-02-22 20:04
 * @Filename：KafkaTopicConfig
 */
public class KafkaTopicConfig {
    /**
     * 压测
     */
    public static final String STRESS_TOPIC_NAME = "stress_report_topic";
    /**
     * 接口自动化
     */
    public static final String API_TOPIC_NAME = "api_report_topic";
    /**
     * ui自动化
     */
    public static final String UI_TOPIC_NAME = "ui_report_topic";
    /**
     * 报告状态的topic
     */
    public static final String REPORT_STATE_TOPIC_NAME = "report_state_topic";
}
