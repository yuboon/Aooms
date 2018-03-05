package net.aooms.core;

import net.aooms.core.properties.YamlPropertiesContextInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.*;

/**
 * Aooms 启动
 * Created by cccyb on 2018-02-06
 */
@SpringBootApplication
//@SpringBootApplication(scanBasePackages = {"your.project.packages"})
//@EnableDiscoveryClient(autoRegister = true)
//@EnableEurekaClient
public class AoomsBoot {


    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AoomsBoot.class);
        application.addInitializers(new YamlPropertiesContextInitializer());
        // 优先级高于appliaction.profiles.active
        //application.setAdditionalProfiles("dev");
        application.run(args);

        //SpringApplication.run(AoomsBoot.class,args);
    }

}

