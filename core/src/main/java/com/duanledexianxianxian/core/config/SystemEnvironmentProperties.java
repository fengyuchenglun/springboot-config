package com.duanledexianxianxian.core.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 通过Environment加载配置参数
 * @author duanledexianxianxian
 */
@Component
@Data
public class SystemEnvironmentProperties {
    @Autowired
    private Environment environment;
}
