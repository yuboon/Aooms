package net.aooms.core.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 测试自定义配置文件映射
 * Created by cccyb on 2018-02-06
 */
@Component
@ConfigurationProperties("test")
@PropertySource("/aooms/my.yml")
public class PropertiesTest {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}