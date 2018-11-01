package net.aooms.core.module.mybatis.record;

import cn.hutool.core.util.StrUtil;
import net.aooms.core.AoomsConstants;
import net.aooms.core.module.mybatis.MyBatisConst;
import net.aooms.core.module.mybatis.interceptor.MetaObjectAssistant;
import net.aooms.core.record.Record;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.util.Iterator;
import java.util.Map;

/**
 * RecordUpdate
 * Created by 风象南(yuboon) on 2018/9/7
 */
public class RecordUpdate implements IRecordOper {

    private MetaObject metaObject;

    public RecordUpdate(MetaObject metaObject) {
        this.metaObject = metaObject;
    }

    @Override
    public void process() {
        MappedStatement mappedStatement = MetaObjectAssistant.getMappedStatement(metaObject);
        Object parameterObject = MetaObjectAssistant.getParameterObject(metaObject);
        Record record = (Record) parameterObject;

        String tableName = record.getGeneral(MyBatisConst.TABLE_NAME_PLACEHOLDER);
        String pkName = String.valueOf(record.getOrDefault(MyBatisConst.TABLE_PK_NAME_PLACEHOLDER, AoomsConstants.ID));
        Object pkValue = record.get(pkName);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" update ");
        stringBuilder.append(tableName); // tableName
        stringBuilder.append(" set {} ");
        stringBuilder.append(" where "+ pkName +" = #{"+ pkName +"} ");

        StringBuilder columns = new StringBuilder();
        int index = 0;
        Iterator<String> keyIterator = record.keySet().iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            if (index > 0) {
                columns.append(",");
            }
            columns.append(key).append(" = ").append("#{").append(key).append("}");
            index++;
        }

        String sql = StrUtil.format(stringBuilder, columns);
        //SqlSource sqlSource = new XMLLanguageDriver().createSqlSource(mappedStatement.getConfiguration(), sql, Map.class);
        Configuration configuration = MetaObjectAssistant.getConfiguration(metaObject);
        SqlSource sqlSource = configuration.getLanguageRegistry().getDefaultDriver().createSqlSource(mappedStatement.getConfiguration(), sql, Map.class);
        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);

        MetaObjectAssistant.setDelegateBoundSql(metaObject,boundSql);
        MetaObjectAssistant.setDelegateParameterHandlerBoundSql(metaObject,boundSql);
    }

}
