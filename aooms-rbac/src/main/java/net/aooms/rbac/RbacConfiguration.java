package net.aooms.rbac;

import cn.hutool.core.util.ArrayUtil;
import net.aooms.core.Aooms;
import net.aooms.core.AoomsSetting;
import net.aooms.core.property.PropertyObject;
import net.aooms.core.web.interceptor.AoomsInterceptorRegistryProxy;
import net.aooms.core.web.interceptor.DemoModeInterceptor;
import net.aooms.core.web.service.ServiceConfiguration;
import net.aooms.core.web.service.ServiceConfigurations;
import net.aooms.rbac.controller.*;
import net.aooms.rbac.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.security.RolesAllowed;

/**
 * 配置类
 * Created by 风象南(yuboon) on 2018-09-18
 */
@Configuration
public class RbacConfiguration {

    @Bean
    public RbacLoginController rbacLoginController(){
        return new RbacLoginController();
    }

    @Bean
    public UserController userController(){
        return new UserController();
    }

    @Bean
    public ResourceController resourceController(){
        return new ResourceController();
    }

    @Bean
    public OrgController orgController(){
        return new OrgController();
    }

    @Bean
    public RoleController roleController(){
        return new RoleController();
    }



    @Bean
    public UserService userService(){
        return new UserService();
    }

    @Bean
    public RoleService roleService(){
        return new RoleService();
    }

    @Bean
    public ResourceService resourceService(){
        return new ResourceService();
    }

    @Bean
    public OrgService orgService(){
        return new OrgService();
    }

    @Bean
    public RbacLoginService rbacLoginService(){
        return new RbacLoginService();
    }


    /**
     * 配置
     */
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
            public void configInterceptor(AoomsInterceptorRegistryProxy interceptorRegistryProxy) { }

            @Override
            public void configProps(PropertyObject propertyObject) {

            }

            @Override
            public void onFinish(Aooms aooms) {
            }
        };
    }
}