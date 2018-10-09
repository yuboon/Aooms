package net.aooms.core.module.mybatis;

import com.google.common.collect.Lists;
import net.aooms.core.record.Record;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.session.Configuration;

/**
 * RecordMappedStatment工厂
 * Created by 风象南(cheereebo) on 2018/9/7
 */
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

    public MappedStatement getRecordFindByPkMappedStatment() {
        SqlSource sqlSource = getSqlSource("Record FindByPk MappedStatment");
        MappedStatement.Builder statementBuilder = new MappedStatement.Builder(configuration, MyBatisConst.MS_RECORD_FIND_BY_PK, sqlSource, SqlCommandType.SELECT);

        ResultMap.Builder builder = new ResultMap.Builder(configuration,MyBatisConst.MS_RECORD_FIND_BY_PK + "-Inline",Record.class, Lists.newArrayList());
        statementBuilder.resultMaps(Lists.newArrayList(builder.build()));
        MappedStatement mappedStatement = statementBuilder.build();
        return mappedStatement;
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
