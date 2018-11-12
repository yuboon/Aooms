package net.aooms.rbac;

import net.aooms.core.AoomsSetting;
import net.aooms.core.web.AoomsWebMvcConfigurer;
import net.aooms.core.web.interceptor.ParamInterceptor;
import net.aooms.core.web.service.ServiceConfiguration;
import net.aooms.core.web.service.ServiceConfigurations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 框架配置
 * Created by 风象南(yuboon) on 2018-09-18
 */
public class RbacSetting extends AoomsSetting {

    @Override
    public void configCallService(ServiceConfigurations serviceConfigurations) {
        ServiceConfiguration userServiceConfiguration = new ServiceConfiguration("userService");
        userServiceConfiguration.methodsForPost("update","insert","delete","updateStatus","updatePassword");
        //userServiceConfiguration.methodSkipInterceptors("update",ParamInterceptor.class);

        serviceConfigurations.register(userServiceConfiguration);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFinish() {

    }
}