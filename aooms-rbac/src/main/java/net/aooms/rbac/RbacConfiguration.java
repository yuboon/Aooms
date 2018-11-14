package net.aooms.rbac;

import cn.hutool.core.util.ArrayUtil;
import net.aooms.core.AoomsSetting;
import net.aooms.core.web.interceptor.AoomsInterceptorRegistryProxy;
import net.aooms.core.web.interceptor.DemoModeInterceptor;
import net.aooms.core.web.service.ServiceConfiguration;
import net.aooms.core.web.service.ServiceConfigurations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 * Created by 风象南(yuboon) on 2018-09-18
 */
@Configuration
public class RbacConfiguration {

    @Bean
    public AoomsSetting rbacSetting(){
        return new AoomsSetting() {
            @Override
            public void configService(ServiceConfigurations serviceConfigurations) {
                ServiceConfiguration userServiceConfiguration = new ServiceConfiguration("userService");
                userServiceConfiguration.methodsForPost("update","insert","delete","updateStatus","updatePassword");
                //userServiceConfiguration.methodSkipInterceptors("update",ParamInterceptor.class);
                serviceConfigurations.register(userServiceConfiguration);
            }

            @Override
            public void configInterceptor(AoomsInterceptorRegistryProxy interceptorRegistryProxy) {
                String[] excludes = new String[]{"/**/find*","/**/login","/**/logout"};
                String[] ignores = ArrayUtil.addAll(interceptorRegistryProxy.getIgnores(),excludes);
                interceptorRegistryProxy.addInterceptor(new DemoModeInterceptor(interceptorRegistryProxy.getPathPatterns(),ignores));
            }
        };
    }
}