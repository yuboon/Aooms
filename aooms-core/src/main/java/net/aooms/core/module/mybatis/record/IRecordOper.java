package net.aooms.core.module.mybatis.record;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;

public interface IRecordOper {

    void process();

}
