package net.aooms.core.web.interceptor;


import cn.hutool.core.collection.CollectionUtil;
import net.aooms.core.Aooms;
import net.aooms.core.AoomsVar;
import net.aooms.core.databoss.DataBoss;
import net.aooms.core.web.service.ServiceConfiguration;
import net.aooms.core.web.service.ServiceConfigurations;
import net.oschina.j2cache.cache.support.util.SpringUtil;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * CallService 拦截器
 * Created by 风象南(yuboon) on 2018-04-19
 */
public class CallServiceInterceptor extends AoomsAbstractInterceptor {

    public CallServiceInterceptor(String[] pathPatterns, String[] ignores) {
        super(pathPatterns, ignores);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        ServiceConfigurations serviceConfigurations = Aooms.self().getServiceConfigurations();

        Map<String,String> pathVars = (Map)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String serviceBeanName = pathVars.get("serviceBeanName");
        String method = pathVars.get("method");
        Object bean = SpringUtil.getBean(serviceBeanName);
        ServiceConfiguration serviceConfiguration = serviceConfigurations.getServiceConfiguration(serviceBeanName);
        if(serviceConfiguration != null){
            // 类上
            List<Class<? extends AoomsAbstractInterceptor>> skipInterceptors = serviceConfiguration.getSkipInterceptors();
            request.setAttribute(AoomsVar.SKIP_INTERCEPTOR_ON_CLASS,skipInterceptors);

            // 方法上
            List<Class<? extends AoomsAbstractInterceptor>> methodSkipInterceptors = serviceConfiguration.getMethodSkipInterceptors(method);
            request.setAttribute(AoomsVar.SKIP_INTERCEPTOR_ON_METHOD,methodSkipInterceptors);
        }
        return true;
    }


}