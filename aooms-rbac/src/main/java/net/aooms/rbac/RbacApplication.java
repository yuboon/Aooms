package net.aooms.rbac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.WebApplicationContext;

/**
 * Application
 * Created by 风象南(yuboon) on 2018-09-12
 */
@SpringBootApplication(scanBasePackages = {"net.aooms.rbac"})
@EnableHystrix
@EnableTransactionManagement
@EnableAspectJAutoProxy(exposeProxy = true)
public class RbacApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RbacApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RbacApplication.class);
    }

    @Override
    protected WebApplicationContext run(SpringApplication application) {
        return super.run(application);
    }
}