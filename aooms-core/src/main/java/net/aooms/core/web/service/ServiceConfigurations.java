package net.aooms.core.web.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.aooms.core.web.interceptor.AoomsAbstractInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * CallService 配置操作
 * Created by 风象南(cheereebo) on 2018/11/12
 */
public class ServiceConfigurations {

    private static final Map<String,ServiceConfiguration> serviceConfigurationMaps = Maps.newHashMap();

    public ServiceConfigurations register(ServiceConfiguration serviceConfiguration){
        serviceConfigurationMaps.put(serviceConfiguration.getBeanName(),serviceConfiguration);
        return this;
    }

    public ServiceConfiguration getServiceConfiguration(String beanName){
        return serviceConfigurationMaps.get(beanName);
    }

    public boolean containsServiceConfiguration(String beanName){
        return serviceConfigurationMaps.containsKey(beanName);
    }

}
