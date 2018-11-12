package net.aooms.core.web.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.aooms.core.web.interceptor.AoomsAbstractInterceptor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service 配置
 * Created by 风象南(cheereebo) on 2018/11/12
 */
public class ServiceConfiguration {

    private String beanName;
    private List<String> postMethods = Lists.newArrayList();
    private List<String> getMethods = Lists.newArrayList();
    private List<String> methods = Lists.newArrayList(); // 不指定是全部方法
    private List<String> excludeMethods = Lists.newArrayList();
    private List<Class<? extends AoomsAbstractInterceptor>> skipInterceptors = Lists.newArrayList();
    private Map<String,List<Class<? extends AoomsAbstractInterceptor>>> methodsSkipInterceptors = Maps.newHashMap();

    public ServiceConfiguration(String beanName) {
        this.beanName = beanName;
    }

    public ServiceConfiguration skipInterceptors(Class<? extends AoomsAbstractInterceptor>... interceptorArray){
        for(Class<? extends AoomsAbstractInterceptor> interceptor : interceptorArray){
            skipInterceptors.add(interceptor);
        }
        return this;
    }

    public ServiceConfiguration methodSkipInterceptors(String method,Class<? extends AoomsAbstractInterceptor>... interceptorArray){
        methodsSkipInterceptors.put(method, Arrays.asList(interceptorArray));
        return this;
    }

    public ServiceConfiguration methodsForPost(String... methodArray){
        for(String method : methodArray){
            postMethods.add(method);
            methods.add(method);
        }
        return this;
    }

    public ServiceConfiguration methodsForGet(String... methodArray){
        for(String method : methodArray){
            getMethods.add(method);
            methods.add(method);
        }
        return this;
    }

    public ServiceConfiguration methods(String... methodArray){
        for(String method : methodArray){
            methods.add(method);
        }
        return this;
    }

    public List<String> getMethods(){
        return methods;
    }

    public List<Class<? extends AoomsAbstractInterceptor>> getSkipInterceptors(){
        return skipInterceptors;
    }

    public List<Class<? extends AoomsAbstractInterceptor>> getMethodSkipInterceptors(String method){
        return methodsSkipInterceptors.get(method);
    }

    public ServiceConfiguration excludeMethods(String... methodArray){
        for(String method : methodArray){
            excludeMethods.add(method);
        }
        return this;
    }

    public boolean isPostMethod(String methodName){
        return postMethods.contains(methodName);
    }

    public boolean isGetMethod(String methodName){
        return getMethods.contains(methodName);
    }

    public boolean containsMethod(String methodName){
        return methods.contains(methodName);
    }

    public boolean isExcludeMethod(String methodName){
        return excludeMethods.contains(methodName);
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
