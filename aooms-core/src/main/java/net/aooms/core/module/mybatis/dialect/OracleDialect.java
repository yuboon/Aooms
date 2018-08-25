package net.aooms.core.module.mybatis.dialect;

import org.apache.ibatis.session.RowBounds;

/**
 * Oracle方言
 * Created by cccyb on 2018-08-23
 */
public class OracleDialect implements Dialect {

    @Override
    public String pagingQuery(String originalSql,RowBounds rowBounds) {
        throw new RuntimeException(" OracleDialect is not implementation ");
    }

}
