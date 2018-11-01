package net.aooms.rbac;

import net.aooms.core.web.AoomsWebMvcConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类
 * Created by 风象南(yuboon) on 2018-09-18
 */
@Configuration
public class RbacConfiguration {

    /**
     * Web Configurer
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        // 可被覆盖的web配置类
        return new AoomsWebMvcConfigurer() {

        };
    }

}