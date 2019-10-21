package com.duanledexianxianxian.demo.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * kafka消费者测试
 *
 * @author fengyuchenglun
 * @version 1.0.0
 */
@Component
public class TestConsumer {

    /**
     * Listen.
     *
     * @param record the record
     * @throws Exception the exception
     */
    @KafkaListener(topics = "topic_first")
    public void listen(ConsumerRecord<?, ?> record) throws Exception {
        System.out.printf("topic = %s, offset = %d, value = %s \n", record.topic(), record.offset(), record.value());
    }
}
