package com.duanledexianxianxian.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统配置类
 * @author duanledexianxianxian
 */
@Component
@ConfigurationProperties(prefix = "system")
@Data
public class SystemProperties {
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
}
