package net.aooms.core.module.mybatis.dao;

import cn.hutool.core.lang.Assert;
import com.google.common.collect.Lists;
import net.aooms.core.module.mybatis.MyBatisConst;
import net.aooms.core.module.mybatis.SqlPara;
import net.aooms.core.module.mybatis.record.Record;
import net.aooms.core.module.mybatis.record.RecordPaging;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

@Component
public class GenericDaoSupport implements GenericDao {
  
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public Connection getConnection() {
        return sqlSessionTemplate.getConnection();
    }

    @Override
    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionTemplate.getSqlSessionFactory();
    }

    /**
     * 保存对象
     * @return 
     * @ 
     */
    public int insert(String tableName, Record record) {
        Assert.notNull(record,"record must not be null");
        record.put(MyBatisConst.TABLE_NAME_PLACEHOLDER,tableName);
        return sqlSessionTemplate.insert(MyBatisConst.MS_RECORD_INSERT, record);
    }

    /**
     * 更新对象
     * @return
     * @
     */
    public int update(String tableName, Record record){
        Assert.notNull(record,"record must not be null");
        record.put(MyBatisConst.TABLE_NAME_PLACEHOLDER,tableName);
        return sqlSessionTemplate.update(MyBatisConst.MS_RECORD_UPDATE, record);
    }

    /**
     * 删除对象
     * @return
     * @
     */
    public int delete(String tableName, Record record)  {
        Assert.notNull(record,"record must not be null");
        record.put(MyBatisConst.TABLE_NAME_PLACEHOLDER,tableName);
        return sqlSessionTemplate.delete(MyBatisConst.MS_RECORD_DELETE, record);
    }

    /** 
     * 批量保存
     */
    public int batchInsert(String tableName, List<Record> records){
       return batchExecute(tableName,MyBatisConst.MS_RECORD_INSERT,records,-1);
    }

    /**
     * 批量保存
     */
    public int batchInsert(String tableName, List<Record> records,int batchSize){
        return batchExecute(tableName,MyBatisConst.MS_RECORD_INSERT,records,batchSize);
    }

    /**
     * 批量更新
     */
    @Override
    public int batchUpdate(String tableName, List<Record> records) {
        return batchExecute(tableName,MyBatisConst.MS_RECORD_UPDATE,records,-1);
    }

    /**
     * 批量更新
     */
    @Override
    public int batchUpdate(String tableName, List<Record> records,int batchSize) {
        return batchExecute(tableName,MyBatisConst.MS_RECORD_UPDATE,records,batchSize);
    }

    /**
     * 批量删除
     * @return
     * @
     */
    public int batchDelete(String tableName, List<Record> records){
        return batchExecute(tableName,MyBatisConst.MS_RECORD_DELETE,records,-1);
    }

    /**
     * 批量删除
     * @return
     * @
     */
    public int batchDelete(String tableName, List<Record> records,int batchSize){
        return batchExecute(tableName,MyBatisConst.MS_RECORD_DELETE,records,batchSize);
    }

    /**
     * 保存对象
     * @return
     * @
     */
    public int update(String mappedStatementId, SqlPara sqlpara) {
        return sqlSessionTemplate.update(mappedStatementId, sqlpara.getParams());
    }

    @Override
    public Record findObject(String mappedStatementId, SqlPara sqlPara) {
        List<Record> records = sqlSessionTemplate.selectList(mappedStatementId,sqlPara.getParams());
        if(records.size() > 0){
            return records.get(0);
        }
        return null;
    }

    @Override
    public Record findObjectOrCreate(String mappedStatementId, SqlPara sqlPara) {
        Record record = findObject(mappedStatementId,sqlPara);
        if(record == null) return Record.NEW();
        return record;
    }

    @Override
    public RecordPaging findList(String mappedStatementId, SqlPara sqlPara) {
        RecordPaging recordPaging = null;
        List<Record> records = Lists.newArrayList();
        if(sqlPara.isPaging()){
            sqlPara.set(MyBatisConst.CRUD_QUERY_COUNT_PLACEHOLDER,true);
            Record totalRecord = sqlSessionTemplate.selectOne(mappedStatementId,sqlPara.getParams());
            int total = totalRecord.getInteger("count");
            if(total > 0){
                sqlPara.removeInternalKey();
                sqlPara.set(MyBatisConst.CRUD_QUERY_PAGING_PLACEHOLDER,true);
                records = sqlSessionTemplate.selectList(mappedStatementId,sqlPara.getParams(),new RowBounds(sqlPara.getPage(),sqlPara.getLimit()));
                sqlPara.removeInternalKey();
            }
            recordPaging = new RecordPaging(sqlPara.getPage(),sqlPara.getLimit(),records,total,sqlPara.isPaging());
        }else{
            records = sqlSessionTemplate.selectList(mappedStatementId,sqlPara.getParams());
            recordPaging = new RecordPaging(sqlPara.getPage(),sqlPara.getLimit(),records,records.size(),sqlPara.isPaging());
        }
        return recordPaging;
    }

    /**
     * 批量执行
     * @return
     * @
     */
    private int batchExecute(String tableName,String msId,List<Record> records,int batchSize){
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        // 批量执行器
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        try{
            if(records != null){
                int total = records.size();
                if(batchSize < 0) batchSize = total;

                int forSize = total / batchSize + 1;
                for(int index = 0; index < forSize; index++){
                    int size = (index + 1) * batchSize;
                    if(size > total){
                        size = total;
                    }

                    for(int i = index * batchSize; i < size; i++){
                        records.get(i).put(MyBatisConst.TABLE_NAME_PLACEHOLDER,tableName);
                        sqlSession.update(msId, records.get(i));
                    }
                    sqlSession.flushStatements();
                    sqlSession.commit();
                    sqlSession.clearCache();
                };
            }
        }finally{
            sqlSession.close();
        }
        return records != null ? records.size() : 0;
    }

      
} 