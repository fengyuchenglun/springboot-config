package com.duanledexianxianxian.core.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 通过@value加载配置参数
 * @author duanledexianxianxian
 */
@Component
@Data
public class SystemValueProperties {
    /**
     * 名称
     */
    @Value("${system.name}")
    private String name;
    /**
     * 编码
     */
    @Value("${system.code}")
    private String code;
}
