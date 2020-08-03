package com.duanledexianxianxian.core;


import com.duanledexianxianxian.core.config.SystemProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * springboot 应用程序入口
 * @author duanledexianxianxian
 */
@SpringBootApplication
public class SpringWebApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext= (ApplicationContext) SpringApplication.run(SpringWebApplication.class, args);
        SystemProperties properties=applicationContext.getBean(SystemProperties.class);
        System.out.println("用户的当前工作目录:"+System.getProperty("user.dir"));
        // 打印出配置参数
        System.out.println(properties.getName());

    }


}
