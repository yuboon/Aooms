package net.aooms.mybatis.dialect;

import org.apache.ibatis.session.RowBounds;

/**
 * 数据库方言
 * Created by cccyb on 2018-08-23
 */
public interface Dialect {

    public String pagingQuery(String originalSql,RowBounds rowBounds);

}
