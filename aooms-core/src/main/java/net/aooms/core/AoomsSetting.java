package net.aooms.core;

import cn.hutool.core.util.ReflectUtil;
import com.google.common.collect.Lists;
import net.aooms.core.Aooms;
import net.aooms.core.service.GenericService;
import net.aooms.core.util.SpringUtils;
import net.aooms.core.web.service.ServiceConfiguration;
import net.aooms.core.web.service.ServiceConfigurations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * AoomsSetting 框架配置
 * Created by 风象南(yuboon) on 2018-10-08
 */
public abstract class AoomsSetting {

    // 启动回调
    public abstract void onStart();

    // CallServcie 配置
    public abstract void configCallService(ServiceConfigurations serviceConfigurations);

    // 启动完成回调
    public abstract void onFinish();

}