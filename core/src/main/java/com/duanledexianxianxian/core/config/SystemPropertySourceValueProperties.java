package com.duanledexianxianxian.core.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 通过@value加载配置参数
 * @author duanledexianxianxian
 */
@Component
@ConfigurationProperties(prefix = "system.property")
@PropertySource(value = { "/config/system.properties" })
@Data
public class SystemPropertySourceValueProperties {
    /**
     * 名称
     * //最好设置默认值，否则配置文件即使有相应key，也会应用启动失败
     */
    @Value("${name:null}")
    private String name;
    /**
     * 编码
     * //最好设置默认值，否则配置文件即使有相应key，也会应用启动失败
     */
    @Value("${code:null}")
    private String code;
}
