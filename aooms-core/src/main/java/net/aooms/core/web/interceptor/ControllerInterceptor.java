package net.aooms.core.web.interceptor;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.aooms.core.Aooms;
import net.aooms.core.AoomsVar;
import net.aooms.core.web.annotation.ClearInterceptor;
import net.aooms.core.web.service.CallServiceController;
import net.aooms.core.web.service.ServiceConfiguration;
import net.aooms.core.web.service.ServiceConfigurations;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * CallService 拦截器
 * Created by 风象南(yuboon) on 2018-04-19
 */
public class ControllerInterceptor extends AoomsAbstractInterceptor {

    public ControllerInterceptor(String[] pathPatterns, String[] ignores) {
        super(pathPatterns, ignores);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod)handler;

            Class<?> clazz = handlerMethod.getBean().getClass();
            Method method = handlerMethod.getMethod();

            List<Class<? extends AoomsAbstractInterceptor>> globalSkipInterceptors = this.aoomsInterceptorRegistryProxy.getSkipInterceptors();
            List<Class<? extends AoomsAbstractInterceptor>> globalInterceptors = this.aoomsInterceptorRegistryProxy.getInterceptors();

            List<Class<? extends AoomsAbstractInterceptor>> interceptors = Lists.newArrayList();
            Set<Class<? extends AoomsAbstractInterceptor>> skipInterceptors = Sets.newHashSet();
            // 全局清除
            skipInterceptors.addAll(globalSkipInterceptors);

            if(clazz != CallServiceController.class){
                // normal controller
                ClearInterceptor classClearInterceptor = clazz.getAnnotation(ClearInterceptor.class);
                ClearInterceptor clearInterceptor = method.getAnnotation(ClearInterceptor.class);

                if(classClearInterceptor != null && classClearInterceptor.value() != null){
                    skipInterceptors.addAll(Arrays.asList(classClearInterceptor.value()));
                }

                if(clearInterceptor != null && clearInterceptor.value() != null){
                    skipInterceptors.addAll(Arrays.asList(clearInterceptor.value()));
                }
            }else{
                // call service controller
                ServiceConfigurations serviceConfigurations = Aooms.self().getServiceConfigurations();
                Map<String, String> pathVars = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                String serviceBeanName = pathVars.get("serviceBeanName");
                String methodName = pathVars.get("method");
                ServiceConfiguration serviceConfiguration = serviceConfigurations.getServiceConfiguration(serviceBeanName);
                if (serviceConfiguration != null) {
                    // 类上
                    List<Class<? extends AoomsAbstractInterceptor>> clasSkipInterceptors = serviceConfiguration.getSkipInterceptors();
                    request.setAttribute(AoomsVar.SKIP_INTERCEPTOR_ON_CLASS, clasSkipInterceptors);

                    // 方法上
                    List<Class<? extends AoomsAbstractInterceptor>> methodSkipInterceptors = serviceConfiguration.getMethodSkipInterceptors(methodName);
                    request.setAttribute(AoomsVar.SKIP_INTERCEPTOR_ON_METHOD, methodSkipInterceptors);

                    if(clasSkipInterceptors != null){
                        skipInterceptors.addAll(clasSkipInterceptors);
                    }

                    if(methodSkipInterceptors != null){
                        skipInterceptors.addAll(methodSkipInterceptors);
                    }
                }
            }

            for (Class<? extends  AoomsAbstractInterceptor> globalInterceptor : globalInterceptors){
                if(!skipInterceptors.contains(globalInterceptor)){
                    interceptors.add(globalInterceptor);
                }
            }

            // 可用拦截器
            request.setAttribute(AoomsVar.INTERCEPTORS, interceptors);
        }

        return true;
    }


}