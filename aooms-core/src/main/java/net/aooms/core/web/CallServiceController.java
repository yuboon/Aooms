package net.aooms.core.web;

import cn.hutool.core.util.ReflectUtil;
import net.aooms.core.Aooms;
import net.aooms.core.exception.AoomsExceptions;
import net.aooms.core.util.SpringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Service 统一调用入口
 *   注意：仅支持单进程内使用
 * Created by 风象南(yuboon) on 2018-10-08
 */
@RestController
@RequestMapping(Aooms.WebContext + "/call/")
public class CallServiceController extends AoomsAbstractController {

    /**
     * 调用Service
     * @return
     */
    @RequestMapping("/{nameSpace}/{serviceBeanName}/{method}")
    public void callService(){
        String nameSpace = getPathString("nameSpace");
        String serviceBeanName = getPathString("serviceBeanName");
        String method = getPathString("method");

        Object bean = SpringUtils.getApplicationContext().getBean(serviceBeanName);
        ReflectUtil.invoke(bean,method);
        this.renderJson();
    };

}