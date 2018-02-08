package net.aooms.core.properties;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * application.yml 配置文件映射
 * Created by cccyb on 2018-02-06
 */
@Component
@ConfigurationProperties(prefix="spring.application")
public class PropertiesApplication {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}