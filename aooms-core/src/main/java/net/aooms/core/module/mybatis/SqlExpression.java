package net.aooms.core.module.mybatis;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.aooms.core.databoss.DataBoss;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Sql Expression
 * Created by 风象南(cheereebo) on 2018/10/25
 */
public class SqlExpression {

    private String column;

    private String valueKey;

    private Object value;

    private Roper roper;


    public SqlExpression(String column, Roper roper) {
        this.column = column;
        this.valueKey = column;
        this.roper = roper;
    }

    public SqlExpression(String column, Roper roper,Object value) {
        this.column = column;
        this.valueKey = column;
        this.roper = roper;
        this.value = value;
    }

    public SqlExpression(String column, Roper roper, String valueKey) {
        this.column = column;
        this.valueKey = valueKey;
        this.roper = roper;
    }

    public SqlExpression(String column, Roper roper, String valueKey, Object value) {
        this.column = column;
        this.valueKey = valueKey;
        this.roper = roper;
        this.value = value;
    }

    public String toSqlString(String alias){
        if(roper == Roper.LikeStart){
            return  "instr(" + alias + column + ", #{"+ valueKey +"}) = 1";
        }else if(roper == Roper.Like){
            return  "instr(" + alias + column + ", #{"+ valueKey +"}) > 0";
        }else{
            return  alias + column + roper.getOper() + "#{"+ valueKey +"}";
        }
    }


    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getValueKey() {
        return valueKey;
    }

    public void setValueKey(String valueKey) {
        this.valueKey = valueKey;
    }

    public Roper getRoper() {
        return roper;
    }

    public void setRoper(Roper roper) {
        this.roper = roper;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
