package com.duanledexianxianxian.demo.kafka.listener;

import com.alibaba.fastjson.JSONObject;
import com.duanledexianxianxian.demo.kafka.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.*;

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

        ThreadFactory namedThreadFactory = new BasicThreadFactory.Builder().namingPattern("producer-kafka-pool-%d").daemon(true).build();

        ExecutorService pool = new ThreadPoolExecutor(5, 200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        int i = 0;
        while (true) {
            int finalI = i;
            pool.execute(() -> {
                this.sendData(finalI);
                // sleep 5 seconds
            });

            try {
                Thread.sleep(new Random().nextInt(6) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }

    private void sendData(int i) {
        Student student = new Student();
        student.setStudentId(String.valueOf(i));
        student.setName("duanledexianxianxian" + new Random().nextInt(20));
        student.setAge(new Random().nextInt(19) + 6);
        student.setSex(Byte.valueOf(String.valueOf((new Random().nextInt(2)))));
        log.info("Thread:{}  Send Data:{}", Thread.currentThread().getName(), student);
        kafkaTemplate.send("topic_first", JSONObject.toJSONString(student));
    }
}
