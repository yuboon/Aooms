package net.aooms.core;

import net.aooms.core.datasource.DynamicDataSource;
import net.aooms.core.module.mybatis.dao.GenericDao;
import net.oschina.j2cache.CacheChannel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

/**
 * 框架模块访问接口
 * Created by cccyb on 2018-04-18
 */
public class Aooms {

    // 框架core包
    public static final String CORE_PACKAGE = "net.aooms.core";

    private static Aooms INSTANCE;

    @Autowired
    private CacheChannel j2Cache;

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private DataSource dataSource;

    void instance(Aooms aooms){
        INSTANCE = aooms;
    }

    public static Aooms getInstance(){
        return INSTANCE;
    }

    public GenericDao getGenericDao() {
        return genericDao;
    }

    public CacheChannel getJ2Cache() {
        return j2Cache;
    }

    public DynamicDataSource getDynamicDataSource() {
        return (DynamicDataSource) dataSource;
    }


}