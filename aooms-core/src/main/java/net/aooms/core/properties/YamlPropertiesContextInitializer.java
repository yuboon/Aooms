package net.aooms.core.properties;


import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * 自定义yaml文件加载
 * Created by cccyb on 2018-02-06
 */
public class YamlPropertiesContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        try {
            Resource resource = applicationContext.getResource("classpath:/aooms/my.yml");
            String name = StringUtils.stripFilenameExtension(resource.getFilename());
            YamlPropertySourceLoader sourceLoader = new YamlPropertySourceLoader();
            PropertySource<?> yamlProperties = sourceLoader.load(name, resource, null);
            applicationContext.getEnvironment().getPropertySources().addFirst(yamlProperties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}