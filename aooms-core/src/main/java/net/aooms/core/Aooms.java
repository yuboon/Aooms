package net.aooms.core;

import net.aooms.core.datasource.DynamicDataSource;
import net.aooms.core.module.mybatis.Db;
import net.aooms.core.property.PropertyObject;
import net.aooms.core.web.service.ServiceConfiguration;
import net.aooms.core.web.service.ServiceConfigurations;
import net.oschina.j2cache.CacheChannel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.List;

/**
 * 框架模块访问接口
 * Created by 风象南(yuboon) on 2018-04-18
 */
public class Aooms {

    // 框架core包
    public static final String CorePackage = "net.aooms.core";
    // 框架名
    public static final String NAME = "aooms";
    // 版本号
    public static final String VERSION = "1.0.0";
    // 框架内部上下文路径
    public static final String WebContext = "/" + NAME;
    // 实例
    private static Aooms INSTANCE;

    @Autowired
    private CacheChannel j2Cache;

    @Autowired
    private Db db;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PropertyObject propertyObject;

    @Autowired
    private ServiceConfigurations serviceConfigurations;

    void instance(Aooms aooms){
        INSTANCE = aooms;
    }

    public static Aooms self(){
        return INSTANCE;
    }

    public Db getDb() {
        return db;
    }

    public CacheChannel getJ2Cache() {
        return j2Cache;
    }

    public DynamicDataSource getDynamicDataSource() {
        return (DynamicDataSource) dataSource;
    }

    public PropertyObject getPropertyObject(){
        return propertyObject;
    }

    public ServiceConfigurations getServiceConfigurations(){
        return serviceConfigurations;
    }

}