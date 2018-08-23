package net.aooms.mybatis.dialect;

import org.apache.ibatis.session.RowBounds;

/**
 * Mysql方言
 * Created by cccyb on 2018-08-23
 */
public class MysqlDialect implements Dialect {

    @Override
    public String pagingQuery(String originalSql, RowBounds rowBounds) {
        String countsql = "select * from (" + originalSql + ") _table limit " + rowBounds.getOffset() + "," + rowBounds.getLimit();
        return  countsql;
    }
}
