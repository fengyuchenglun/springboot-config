package com.duanledexianxianxian.demo.kafka.listener;

import com.alibaba.fastjson.JSONObject;
import com.duanledexianxianxian.demo.kafka.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * @author duanledexianxianxian
 * @date 2019/10/19 0:44
 * @since 1.0.0
 */
@Component
@Slf4j
public class AppListener implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        //labdam方式
        new Thread(() -> {
            log.info(Thread.currentThread().getName() + ":使用lambda表达式创建线程");
            while (true) {
                Student student = new Student();
                student.setStudentId(String.valueOf(LocalDateTime.now().getNano()));
                student.setName(Thread.currentThread().getName());
                student.setAge(String.valueOf(new Random().nextInt()));
                log.info("Send Data:{}", student);
                kafkaTemplate.send("topic_first", JSONObject.toJSONString(student));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
