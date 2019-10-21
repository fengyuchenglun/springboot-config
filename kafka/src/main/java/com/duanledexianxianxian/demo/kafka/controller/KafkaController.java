package com.duanledexianxianxian.demo.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duanledexianxianxian
 * @date 2019/10/18 23:50
 * @since 1.0.0
 */
@RestController
public class KafkaController {
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    @GetMapping("/message/send")
    public boolean send(@RequestParam String message){
        kafkaTemplate.send("testTopic",message);
        return true;
    }
}
