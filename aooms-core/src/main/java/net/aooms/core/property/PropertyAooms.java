package net.aooms.core.property;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * application.yml 框架配置文件映射
 * Created by 风象南(yuboon) on 2018-11-07
 */
@Component
@ConfigurationProperties(prefix="aooms")
public class PropertyAooms {

    // 是否开发模式
    private boolean devMode;

    // 是否启用服务注册
    private boolean useRegistry;

    // 使用CallService调用的模式
    // manual 手动需要添加后方可使用  auto 无需手动添加，存在Service即可调用
    // private String callServiceMode = "auto";

    public boolean isDevMode() {
        return devMode;
    }

    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    public boolean isUseRegistry() {
        return useRegistry;
    }

    public void setUseRegistry(boolean useRegistry) {
        this.useRegistry = useRegistry;
    }

}