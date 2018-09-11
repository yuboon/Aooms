package net.aooms.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.WebApplicationContext;


//@SpringBootApplication
//@SpringBootApplication(scanBasePackages = {"net.aooms.core","net.aooms.example"})
//@EnableDiscoveryClient(autoRegister = true)
/*@MapperScan("net.aooms.core.module.mybatis.mapper")//将项目中对应的mapper类的路径加进来就可以了 */

@SpringBootApplication(scanBasePackages = {"net.aooms.example"})
@EnableHystrix
@EnableTransactionManagement
@EnableAspectJAutoProxy(exposeProxy = true)
public class DemoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

       /* SpringApplication application = new SpringApplication(DemoApplication.class);
        // application.addInitializers(new YamlPropertiesContextInitializer());

        // 优先级高于appliaction.profiles.active
        //application.setAdditionalProfiles("dev");
        ApplicationContext applicationContext = application.run(args);

        //AnnotationConfigApplicationContext annotationConfigApplicationContext = (AnnotationConfigApplicationContext) applicationContext.getParentBeanFactory();
        //annotationConfigApplicationContext.register(AoomsConfiguration.class);

        //ApplicationContext context = new AnnotationConfigApplicationContext(AoomsConfiguration.class);
        //SpringApplication.run(AoomsBoot.class,args);*/

        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoApplication.class);
    }

    @Override
    protected WebApplicationContext run(SpringApplication application) {
        return super.run(application);
    }
}