package net.aooms.core.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * server 配置文件映射
 * Created by 风象南(cheereebo) on 2018-03-06
 */
@Component
@ConfigurationProperties("server")
public class PropertyServer {

    private int port;

    private String contextPath;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}