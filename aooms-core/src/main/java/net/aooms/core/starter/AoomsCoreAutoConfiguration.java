package net.aooms.core.starter;

import com.baomidou.kisso.SSOConfig;
import net.aooms.core.AoomsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动装载aooms-core工程
 */
@Configuration
public class AoomsCoreAutoConfiguration {


    @Bean
    public AoomsConfiguration aoomsConfiguration(){
        return new AoomsConfiguration();
    }



}
