package net.aooms.core.module.mybatis;

import cn.hutool.core.lang.Assert;
import com.google.common.collect.Lists;
import net.aooms.core.AoomsConstants;
import net.aooms.core.datasource.DynamicDataSourceHolder;
import net.aooms.core.module.mybatis.record.PagingRecord;
import net.aooms.core.module.mybatis.record.Record;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.Connection;
import java.util.List;


/**
 * 基于record的数据库操作对象Db
 * Created by 风象南(cheereebo) on 2018/9/11
 */
@Component
public class Db {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    public Connection getConnection() {
        return sqlSessionTemplate.getConnection();
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionTemplate.getSqlSessionFactory();
    }

    private SqlSession getSqlSession(){
        return sqlSessionTemplate;
    }

    /**
     * 注意：开启	@Transactional 时无法使用该方法
     */
    public Db use(String name) {
        Assert.notNull(name,"datasource name is not allow null !");
        // 如果包含在当前spring事务中，拒绝操作
        if(TransactionSynchronizationManager.isActualTransactionActive()){
            throw new UnsupportedOperationException("Cannot be used in spring transactions !");
        }

        if (!DynamicDataSourceHolder.containsDataSource(name)) {
            logger.error("datasource [{}] not found !",name);
        } else {
            logger.info("use datasource [{}]",name);
            // 设置动态数据源上下文
            DynamicDataSourceHolder.setDataSource(name);
        }

        return this;
    }

    /**
     * 注意：开启	@Transactional 时无法使用该方法
     */
    public void useOn(String name) {
        Assert.notNull(name,"datasource name is not allow null !");
        // 如果包含在当前spring事务中，拒绝操作
        if(TransactionSynchronizationManager.isActualTransactionActive()){
            throw new UnsupportedOperationException("Cannot be used in spring transactions !");
        }

        if (!DynamicDataSourceHolder.containsDataSource(name)) {
            logger.error("datasource [{}] not found !",name);
        } else {
            logger.info("on datasource [{}]",name);
            // 设置动态数据源上下文
            DynamicDataSourceHolder.setDataSource(name);
        }
    }

    /**
     * 注意：开启	@Transactional 时无法使用该方法
     */
    public void useOff(String name) {
        logger.info("off datasource [{}]",name);
        // 如果包含在当前spring事务中，拒绝操作
        if(TransactionSynchronizationManager.isActualTransactionActive()){
            throw new UnsupportedOperationException("Cannot be used in spring transactions !");
        }
        DynamicDataSourceHolder.removeDataSource();
    }

    /**
     * 保存对象
     * @return 
     * @ 
     */
    public int insert(String tableName, Record record) {
        Assert.notNull(record,"record must not be null");
        record.put(MyBatisConst.TABLE_NAME_PLACEHOLDER,tableName);
        return getSqlSession().insert(MyBatisConst.MS_RECORD_INSERT, record);
    }

    /**
     * 更新对象
     * @return
     * @
     */
    public int update(String tableName, Record record){
        Assert.notNull(record,"vo must not be null");
        record.put(MyBatisConst.TABLE_NAME_PLACEHOLDER,tableName);
        return getSqlSession().update(MyBatisConst.MS_RECORD_UPDATE, record);
    }

    /**
     * 删除对象
     * @return
     * @
     */
    public int delete(String tableName, Record record)  {
        Assert.notNull(record,"record must not be null");
        record.put(MyBatisConst.TABLE_NAME_PLACEHOLDER,tableName);
        return getSqlSession().delete(MyBatisConst.MS_RECORD_DELETE, record);
    }

    /**
     * 删除对象
     * @return
     * @
     */
    public int deleteByPrimaryKey(String tableName, Object primaryKeyValue){
        Record record = new Record();
        record.set(AoomsConstants.ID,primaryKeyValue);
        return delete(tableName,record);
    }

    /**
     * 删除对象
     * @return
     * @
     */
    public int deleteByPrimaryKey(String tableName, String primaryKeyColumn, Object primaryKeyValue){
        Record record = new Record();
        record.put(MyBatisConst.TABLE_PK_NAME_PLACEHOLDER,primaryKeyColumn);
        record.set(primaryKeyColumn,primaryKeyValue);
        return delete(tableName,record);
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
    public int batchUpdate(String tableName, List<Record> records) {
        return batchExecute(tableName,MyBatisConst.MS_RECORD_UPDATE,records,-1);
    }

    /**
     * 批量更新
     */
    public int batchUpdate(String tableName, List<Record> records,int batchSize) {
        return batchExecute(tableName,MyBatisConst.MS_RECORD_UPDATE,records,batchSize);
    }


    /**
     * 批量删除
     * @return
     * @
     */
    public int batchDelete(String tableName, Object... primaryKeyValues){
        int size = primaryKeyValues.length;
        if(size > 0){
            List<Record> records = Lists.newArrayList();
            for (int i  = 0; i< size; i++){
                Record record = new Record();
                record.set(AoomsConstants.ID, primaryKeyValues[i]);
                records.add(record);
            }
            return batchExecute(tableName,MyBatisConst.MS_RECORD_DELETE,records,-1);
        }

        return 0;
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
        return getSqlSession().update(mappedStatementId, sqlpara.getParams());
    }

    public Record findByPrimaryKey(String tableName, Object primaryKeyValue) {
        return findByPrimaryKey(tableName, AoomsConstants.ID ,primaryKeyValue);
    }

    public Record findByPrimaryKey(String tableName, String primaryKeyColumn ,Object primaryKeyValue) {
        SqlPara sqlPara = SqlPara.NEW();
        sqlPara.set(MyBatisConst.CRUD_QUERY_PK_PLACEHOLDER,true);
        sqlPara.set(MyBatisConst.TABLE_NAME_PLACEHOLDER,tableName);
        sqlPara.set(MyBatisConst.TABLE_PK_NAME_PLACEHOLDER,primaryKeyColumn);
        sqlPara.set(primaryKeyColumn,primaryKeyValue);
        List<Record> records = getSqlSession().selectList(MyBatisConst.MS_RECORD_FIND_BY_PK,sqlPara.getParams());
        if(records.size() > 0){
            return records.get(0);
        }
        return null;
    }

    public Record findObject(String mappedStatementId, SqlPara sqlPara) {
        List<Record> records = getSqlSession().selectList(mappedStatementId,sqlPara.getParams());
        if(records.size() > 0){
            return records.get(0);
        }
        return null;
    }

    public Record findObjectOrCreate(String mappedStatementId, SqlPara sqlPara) {
        Record record = findObject(mappedStatementId,sqlPara);
        if(record == null) return Record.NEW();
        return record;
    }

    public PagingRecord findList(String mappedStatementId, SqlPara sqlPara) {
        PagingRecord recordPaging = null;
        List<Record> records = Lists.newArrayList();
        if(sqlPara.isPaging()){
            sqlPara.set(MyBatisConst.CRUD_QUERY_COUNT_PLACEHOLDER,true);
            Record totalRecord = getSqlSession().selectOne(mappedStatementId,sqlPara.getParams());
            int total = totalRecord.getInteger("count");
            if(total > 0){
                sqlPara.removeInternalKey();
                sqlPara.set(MyBatisConst.CRUD_QUERY_PAGING_PLACEHOLDER,true);
                records = getSqlSession().selectList(mappedStatementId,sqlPara.getParams(),new RowBounds(sqlPara.getPage(),sqlPara.getLimit()));
                sqlPara.removeInternalKey();
            }
            recordPaging = new PagingRecord(sqlPara.getPage(),sqlPara.getLimit(),records,total,sqlPara.isPaging());
        }else{
            records = getSqlSession().selectList(mappedStatementId,sqlPara.getParams());
            recordPaging = new PagingRecord(sqlPara.getPage(),sqlPara.getLimit(),records,records.size(),sqlPara.isPaging());
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