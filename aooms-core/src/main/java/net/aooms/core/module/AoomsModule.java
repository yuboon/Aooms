package net.aooms.core.module;

import net.oschina.j2cache.CacheChannel;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 框架模块访问接口
 * Created by cccyb on 2018-04-18
 */
public class AoomsModule {

    private static AoomsModule INSTANCE;

    @Autowired
    private CacheChannel j2Cache;

    @Autowired
    private GenericDao genericDao;

    public void instance(AoomsModule aoomsModule){
        INSTANCE = aoomsModule;
    }

    public static AoomsModule getInstance(){
        return INSTANCE;
    }

    public GenericDao getGenericDao() {
        return genericDao;
    }

    public CacheChannel getJ2Cache() {
        return j2Cache;
    }




}