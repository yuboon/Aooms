package net.aooms.core.module.mybatis.dialect;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库方言选择器
 * Created by 风象南(cheereebo) on 2018-08-23
 */
public class DialectSelector {

    private static Dialect defaultDialect = new MysqlDialect();
    private static Dialect mysqlDialect = new MysqlDialect();
    private static Dialect oracleDialect = new OracleDialect();

    private static Map<String,Dialect> dialectMap = new HashMap();
    static {
        dialectMap.put("com.mysql.jdbc.Driver",mysqlDialect);
        dialectMap.put("oracle.jdbc.OracleDriver",oracleDialect);
    }

    public Dialect selector(String driviClass){
        Dialect dialect = dialectMap.get(driviClass);
        if(dialect != null){
            return dialect;
        }

        return defaultDialect;
    }

}
