package net.aooms.core.module.mybatis.dialect;

import org.apache.ibatis.session.RowBounds;

/**
 * Mysql方言
 * Created by 风象南(yuboon) on 2018-08-23
 */
public class MysqlDialect implements Dialect {

    @Override
    public String pagingQuery(String originalSql, RowBounds rowBounds) {
        int start = (rowBounds.getOffset() - 1) * rowBounds.getLimit();
        String countsql = "select * from (" + originalSql + ") _table limit " + start + "," + rowBounds.getLimit();
        return  countsql;
    }
}
