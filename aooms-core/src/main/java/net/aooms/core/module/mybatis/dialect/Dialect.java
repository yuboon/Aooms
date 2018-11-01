package net.aooms.core.module.mybatis.dialect;

import org.apache.ibatis.session.RowBounds;

/**
 * 数据库方言
 * Created by 风象南(yuboon) on 2018-08-23
 */
public interface Dialect {

    public String pagingQuery(String originalSql,RowBounds rowBounds);

}
