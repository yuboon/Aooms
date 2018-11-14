package net.aooms.core;

import cn.hutool.core.util.ReflectUtil;
import com.google.common.collect.Lists;
import net.aooms.core.Aooms;
import net.aooms.core.service.GenericService;
import net.aooms.core.util.SpringUtils;
import net.aooms.core.web.AoomsWebMvcConfigurer;
import net.aooms.core.web.interceptor.*;
import net.aooms.core.web.service.ServiceConfiguration;
import net.aooms.core.web.service.ServiceConfigurations;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 *
 * AoomsSetting 框架配置
 * Created by 风象南(yuboon) on 2018-10-08
 */
public abstract class AoomsSetting implements WebMvcConfigurer {

    /**
     * Aooms 扩展配置
     *
     * https://blog.csdn.net/superlover_/article/details/80893007  虚拟路径
     */

    // CallServcie 配置
    public abstract void configService(ServiceConfigurations serviceConfigurations);

    // 拦截器配置
    public abstract void configInterceptor(AoomsInterceptorRegistryProxy interceptorRegistryProxy);


}