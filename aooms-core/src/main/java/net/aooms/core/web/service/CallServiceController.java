package net.aooms.core.web.service;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.http.HttpUtil;
import net.aooms.core.Aooms;
import net.aooms.core.exception.AoomsExceptions;
import net.aooms.core.property.PropertyAooms;
import net.aooms.core.util.SpringUtils;
import net.aooms.core.web.AoomsAbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Service 统一调用入口
 *   注意：仅支持单进程内使用
 * Created by 风象南(yuboon) on 2018-10-08
 */
@RestController
@RequestMapping(Aooms.WebContext + "/call/")
public class CallServiceController extends AoomsAbstractController {

    @Autowired
    private ServiceConfigurations serviceConfigurations;

    @Autowired
    private PropertyAooms propertyAooms;

    /**
     * 调用Service
     * @return
     */
    @RequestMapping("/{nameSpace}/{serviceBeanName}/{method}")
    public void callService(HttpServletRequest request){
        String serviceBeanName = getPathString("serviceBeanName");
        String method = getPathString("method");

        ServiceConfiguration serviceConfiguration = serviceConfigurations.getServiceConfiguration(serviceBeanName);
        if(serviceConfiguration != null){
            this.invokeMethod(serviceBeanName,method,serviceConfiguration,request);
        }else{
            throw AoomsExceptions.create("Service Bean [" + serviceBeanName + "] no register to ServiceConfigurations");
        };

        // render Result
        this.renderJson();
    };

    private void invokeMethod(String serviceBeanName,String method,ServiceConfiguration serviceConfiguration,HttpServletRequest request){
        Object bean = SpringUtils.getApplicationContext().getBean(serviceBeanName);
        boolean isPost = ("POST".equalsIgnoreCase(request.getMethod()));

        boolean isExcludeMethod = serviceConfiguration.isExcludeMethod(method);
        if(isExcludeMethod){
            throw AoomsExceptions.create("Service Bean [" + serviceBeanName + "] Method [" + method + "] is ExcludeMethod");
        }

        boolean isPostMethod = serviceConfiguration.isPostMethod(method);
        if(isPostMethod){
            if(isPost){
                ReflectUtil.invoke(bean,method);
            }else{
                throw AoomsExceptions.create("Service Bean [" + serviceBeanName + "] Method [" + method + "] is Post Method");
            }
        }else{
            List<String> methods = serviceConfiguration.getMethods();
            if(methods.size() > 0){
                boolean containsMethod = serviceConfiguration.containsMethod(method);
                if(containsMethod){
                    ReflectUtil.invoke(bean,method);
                }else{
                    throw AoomsExceptions.create("Service Bean [" + serviceBeanName + "] Method [" + method + "] is not register");
                }
            }else{
                ReflectUtil.invoke(bean,method);
            }
        }
    }

}