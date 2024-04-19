package net.lishen.listener;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lishen.config.KafkaTopicConfig;
import net.lishen.req.ReportUpdateReq;
import net.lishen.service.ReportDetailService;
import net.lishen.service.ReportService;
import net.lishen.util.JsonUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.listener
 * @Project：atcloud-meter
 * @name：MQListener
 * @Date：2024-02-22 20:03
 * @Filename：MQListener
 */
@Component
@Slf4j
public class MQListener {

    @Resource
    private ReportDetailService reportDetailService;


    @Resource
    private ReportService reportService;

    /**
     * 消费监听，压测日志详情
     * @param record
     * @param ack
     * @param topic
     */
    @KafkaListener(topics = KafkaTopicConfig.STRESS_TOPIC_NAME,groupId = "joseph-stress-test-gp")
    public void onStressReportDetailMessage(ConsumerRecord<?,?> record, Acknowledgment ack,@Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        //打印消息
        log.info("消费主题：{}，分区：{}，收到消息：{}",record.topic(),record.partition(),record.value());
        reportDetailService.handleStressReportDetailMessage(record.value().toString());

        ack.acknowledge();
    }

    /**
     * 消费监听，接口自动化测试日志详情
     * @param record
     * @param ack
     * @param topic
     */
    @KafkaListener(topics = KafkaTopicConfig.API_TOPIC_NAME,groupId = "joseph-api-test-gp")
    public void onApiReportDetailMessage(ConsumerRecord<?,?> record, Acknowledgment ack,@Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        //打印消息
        log.info("消费主题：{}，分区：{}，收到消息：{}",record.topic(),record.partition(),record.value());
        reportDetailService.handleApiReportDetailMessage(record.value().toString());

        ack.acknowledge();
    }

    /**
     * 消费监听，处理报告状态
     * @param record
     * @param ack
     * @param topic
     */
    @KafkaListener(topics = KafkaTopicConfig.REPORT_STATE_TOPIC_NAME,groupId = "joseph-report-test-gp")
    public void onStressReportState(ConsumerRecord<?,?> record, Acknowledgment ack,@Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        //打印消息
        log.info("消费主题：{}，分区：{}，收到消息：{}",record.topic(),record.partition(),record.value());

        ReportUpdateReq reportUpdateReq = JsonUtil.json2Obj(record.value().toString(), ReportUpdateReq.class);
        reportService.updateReportState(reportUpdateReq);
        ack.acknowledge();
    }
}
