package net.aooms.core.property;


import net.aooms.core.property.loader.YamlPropertyLoaderFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 测试自定义配置文件映射
 * Created by cccyb on 2018-02-06
 */
@Component
@PropertySource(value = "/aooms/my2.yml" ,factory = YamlPropertyLoaderFactory.class)
// prefix 必须包含点
@ConfigurationProperties(prefix = "demo")
public class PropertyTest {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}