package net.aooms.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Aooms 入口核心启动类
 */
@SpringBootApplication
@EnableDiscoveryClient(autoRegister = false)
@EnableWebMvc
public class Aooms {

    public static void main(String[] args) {
        SpringApplication.run(Aooms.class);
    }
}

