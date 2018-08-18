package net.aooms.core.module.mybatis.starter;

import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.mapper.ISqlInjector;
import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.baomidou.mybatisplus.toolkit.StringUtils;

/**
 * Created by cccyb on 2018-08-13
 */
public class GlobalConfig {
    private Integer idType;
    private Boolean dbColumnUnderline;
    /** @deprecated */
    @Deprecated
    private String sqlInjector;
    /** @deprecated */
    @Deprecated
    private String metaObjectHandler;
    private Integer fieldStrategy;
    private Boolean refreshMapper;
    private Boolean isCapitalMode;
    private String identifierQuote;
    private String logicDeleteValue = null;
    private String logicNotDeleteValue = null;
    /** @deprecated */
    @Deprecated
    private String keyGenerator;
    private Boolean sqlParserCache;

    public GlobalConfig() {
    }

    public Integer getIdType() {
        return this.idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public Boolean getDbColumnUnderline() {
        return this.dbColumnUnderline;
    }

    public void setDbColumnUnderline(Boolean dbColumnUnderline) {
        this.dbColumnUnderline = dbColumnUnderline;
    }

    public String getSqlInjector() {
        return this.sqlInjector;
    }

    public void setSqlInjector(String sqlInjector) {
        this.sqlInjector = sqlInjector;
    }

    public String getMetaObjectHandler() {
        return this.metaObjectHandler;
    }

    public void setMetaObjectHandler(String metaObjectHandler) {
        this.metaObjectHandler = metaObjectHandler;
    }

    public Integer getFieldStrategy() {
        return this.fieldStrategy;
    }

    public void setFieldStrategy(Integer fieldStrategy) {
        this.fieldStrategy = fieldStrategy;
    }

    public Boolean getCapitalMode() {
        return this.isCapitalMode;
    }

    public void setCapitalMode(Boolean capitalMode) {
        this.isCapitalMode = capitalMode;
    }

    public String getIdentifierQuote() {
        return this.identifierQuote;
    }

    public void setIdentifierQuote(String identifierQuote) {
        this.identifierQuote = identifierQuote;
    }

    public Boolean getRefreshMapper() {
        return this.refreshMapper;
    }

    public void setRefreshMapper(Boolean refreshMapper) {
        this.refreshMapper = refreshMapper;
    }

    public String getLogicDeleteValue() {
        return this.logicDeleteValue;
    }

    public void setLogicDeleteValue(String logicDeleteValue) {
        this.logicDeleteValue = logicDeleteValue;
    }

    public String getLogicNotDeleteValue() {
        return this.logicNotDeleteValue;
    }

    public void setLogicNotDeleteValue(String logicNotDeleteValue) {
        this.logicNotDeleteValue = logicNotDeleteValue;
    }

    public String getKeyGenerator() {
        return this.keyGenerator;
    }

    public void setKeyGenerator(String keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    public Boolean getSqlParserCache() {
        return this.sqlParserCache;
    }

    public void setSqlParserCache(Boolean sqlParserCache) {
        this.sqlParserCache = sqlParserCache;
    }

    public GlobalConfiguration convertGlobalConfiguration() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        GlobalConfiguration globalConfiguration = new GlobalConfiguration();
        if (StringUtils.isNotEmpty(this.getIdentifierQuote())) {
            globalConfiguration.setIdentifierQuote(this.getIdentifierQuote());
        }

        if (StringUtils.isNotEmpty(this.getLogicDeleteValue())) {
            globalConfiguration.setLogicDeleteValue(this.getLogicDeleteValue());
        }

        if (StringUtils.isNotEmpty(this.getLogicNotDeleteValue())) {
            globalConfiguration.setLogicNotDeleteValue(this.getLogicNotDeleteValue());
        }

        if (StringUtils.isNotEmpty(this.getSqlInjector())) {
            globalConfiguration.setSqlInjector((ISqlInjector)Class.forName(this.getSqlInjector()).newInstance());
        }

        if (StringUtils.isNotEmpty(this.getMetaObjectHandler())) {
            globalConfiguration.setMetaObjectHandler((MetaObjectHandler)Class.forName(this.getMetaObjectHandler()).newInstance());
        }

        if (StringUtils.isNotEmpty(this.getKeyGenerator())) {
            globalConfiguration.setKeyGenerator((IKeyGenerator)Class.forName(this.getKeyGenerator()).newInstance());
        }

        if (StringUtils.checkValNotNull(this.getIdType())) {
            globalConfiguration.setIdType(this.getIdType());
        }

        if (null != this.getDbColumnUnderline()) {
            globalConfiguration.setDbColumnUnderline(this.getDbColumnUnderline());
        }

        if (StringUtils.checkValNotNull(this.getFieldStrategy())) {
            globalConfiguration.setFieldStrategy(this.getFieldStrategy());
        }

        if (StringUtils.checkValNotNull(this.getRefreshMapper())) {
            globalConfiguration.setRefresh(this.getRefreshMapper());
        }

        if (StringUtils.checkValNotNull(this.getCapitalMode())) {
            globalConfiguration.setCapitalMode(this.getCapitalMode());
        }

        if (null != this.getSqlParserCache()) {
            globalConfiguration.setSqlParserCache(this.getSqlParserCache());
        }

        return globalConfiguration;
    }
}
