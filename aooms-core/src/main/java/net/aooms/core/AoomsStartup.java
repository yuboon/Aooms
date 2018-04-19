package net.aooms.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Aooms 启动
 * Created by cccyb on 2018-02-06
 */
@SpringBootApplication
//@SpringBootApplication(scanBasePackages = {"your.project.packages"})
//@EnableDiscoveryClient(autoRegister = true)
//@EnableEurekaClient
public class AoomsStartup {


    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AoomsStartup.class);
        // application.addInitializers(new YamlPropertiesContextInitializer());

        // 优先级高于appliaction.profiles.active
        //application.setAdditionalProfiles("dev");
        ApplicationContext applicationContext = application.run(args);

        //AnnotationConfigApplicationContext annotationConfigApplicationContext = (AnnotationConfigApplicationContext) applicationContext.getParentBeanFactory();
        //annotationConfigApplicationContext.register(AoomsConfiguration.class);

        //ApplicationContext context = new AnnotationConfigApplicationContext(AoomsConfiguration.class);
        //SpringApplication.run(AoomsBoot.class,args);
    }

}

