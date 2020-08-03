package com.duanledexianxianxian.core;


import com.duanledexianxianxian.core.config.SystemEnvironmentProperties;
import com.duanledexianxianxian.core.config.SystemProperties;
import com.duanledexianxianxian.core.config.SystemPropertySourceValueProperties;
import com.duanledexianxianxian.core.config.SystemValueProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * springboot 应用程序入口
 *
 * @author duanledexianxianxian
 */
@SpringBootApplication
public class SpringWebApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                SpringApplication.run(SpringWebApplication.class, args);
        SystemProperties systemProperties = applicationContext.getBean(SystemProperties.class);
        SystemValueProperties valueProperties = applicationContext.getBean(SystemValueProperties.class);
        SystemEnvironmentProperties environmentProperties = applicationContext.getBean(SystemEnvironmentProperties.class);
        SystemPropertySourceValueProperties propertySourceValueProperties = applicationContext.getBean(SystemPropertySourceValueProperties.class);

        System.out.println("用户的当前工作目录:" + System.getProperty("user.dir"));
        // 通过@value加载配置方式
        System.out.println("@Value:" + valueProperties);
        System.out.println("@ConfigurationProperties:" + systemProperties);
        System.out.println("Environment:" + environmentProperties.getEnvironment().getProperty("system.name"));
        System.out.println("@PropertySource+@Value+@ConfigurationProperties:" + propertySourceValueProperties);

    }


}
