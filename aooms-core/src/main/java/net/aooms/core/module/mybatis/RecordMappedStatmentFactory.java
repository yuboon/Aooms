package net.aooms.core.module.mybatis;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

public class RecordMappedStatmentFactory {

    private Configuration configuration;

    public RecordMappedStatmentFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public MappedStatement getRecordInsertMappedStatment() {
        SqlSource sqlSource = getSqlSource("Record Insert MappedStatment");
        MappedStatement.Builder statementBuilder = new MappedStatement.Builder(configuration, MyBatisConst.MS_RECORD_INSERT, sqlSource, SqlCommandType.INSERT);
        return statementBuilder.build();
    }

    public MappedStatement getRecordUpdateMappedStatment() {
        SqlSource sqlSource = getSqlSource("Record Update MappedStatment");
        MappedStatement.Builder statementBuilder = new MappedStatement.Builder(configuration, MyBatisConst.MS_RECORD_UPDATE, sqlSource, SqlCommandType.UPDATE);
        return statementBuilder.build();
    }

    public MappedStatement getRecordDeleteMappedStatment() {
        SqlSource sqlSource = getSqlSource("Record Delete MappedStatment");
        MappedStatement.Builder statementBuilder = new MappedStatement.Builder(configuration, MyBatisConst.MS_RECORD_DELETE, sqlSource, SqlCommandType.DELETE);
        return statementBuilder.build();
    }

    private SqlSource getSqlSource(String script){
        SqlSource sqlSource = new SqlSource() {
            @Override
            public BoundSql getBoundSql(Object parameterObject) {
                return new BoundSql(configuration, script, null, null);
            }
        };

        return sqlSource;
    }

}
