package net.aooms.core;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import net.aooms.core.exception.AoomsExceptions;
import net.aooms.core.util.LogUtils;
import net.aooms.core.util.SpringUtils;
import net.aooms.core.web.AoomsWebMvcConfigurer;
import net.aooms.core.web.service.ServiceConfigurations;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;

/**
 * 框架启动后运行
 * Created by 风象南(yuboon) on 2018/11/14
 */
@Component
@Order(1)
public class AoomsApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments applicationArguments) {
        LogUtils.info("Aooms("+ Aooms.VERSION +") Start Sucess, At " + DateUtil.now());

        ApplicationContext context = SpringUtils.getApplicationContext();
        ServiceConfigurations serviceConfigurations = context.getBean(ServiceConfigurations.class);
        AoomsWebMvcConfigurer webMvcConfigurer = (AoomsWebMvcConfigurer) context.getBean("webMvcConfigurer");

        String[] beanNames = context.getBeanNamesForType(AoomsSetting.class);
        LogUtils.info("SettingBeanNames = " + JSON.toJSONString(beanNames));
        for(String name : beanNames){
            AoomsSetting settingBean = (AoomsSetting)context.getBean(name);
            settingBean.configInterceptor(webMvcConfigurer.getInterceptorRegistryProxy());
            settingBean.configService(serviceConfigurations);
        }

        //Set<Class<?>> classes = ClassScaner.scanPackageByAnnotation("net",AoomsSettingBean.class);
        //System.err.println(classes.size());
    }

}