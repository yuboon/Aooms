package net.aooms.core;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

/**
 * Aooms 启动
 * Created by 风象南(cheereebo) on 2018-02-06
 */
//@SpringBootApplication
//@SpringBootApplication(scanBasePackages = {"your.project.packages"})
//@EnableDiscoveryClient(autoRegister = true)
//@MapperScan("net.aooms.core.module.mybatis.mapper")//将项目中对应的mapper类的路径加进来就可以了
public class AoomsApplication {


    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AoomsApplication.class);
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

