package net.aooms.mybatis.dao;

import net.aooms.mybatis.SqlPara;
import net.aooms.mybatis.record.Record;
import net.aooms.mybatis.record.RecordDelete;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

public interface GenericDao {

    /**
     * 获取Connection
     * @return
     */
    public Connection getConnection();

    /**
     * 获取SqlSessionFactory
     * @return
     */
    public SqlSessionFactory getSqlSessionFactory();

    /** 
     * 保存对象
     * @return 
     * @ 
     */  
    public int insert(String tableName, Record record);

    /**
     * 修改对象
     * @return
     * @
     */
    public int update(String tableName, Record record);

    /**
     * 删除对象
     * @return
     * @
     */
    public int delete(String tableName, Record record);

    /**
     * 批量保存对象
     */
    public int batchInsert(String tableName, List<Record> records);

    /**
     * 批量保存对象
     */
    public int batchInsert(String tableName, List<Record> records,int batchSize);

    /**
     * 批量修改对象
     */
    public int batchUpdate(String tableName, List<Record> records);

    /**
     * 批量修改对象
     */
    public int batchUpdate(String tableName, List<Record> records,int batchSize);

    /**
     * 批量删除对象
     * @return
     * @
     */
    public int batchDelete(String tableName, List<Record> record);

    /**
     * 批量删除对象
     * @return
     * @
     */
    public int batchDelete(String tableName, List<Record> record,int batchSize);

    /**
     * 执行xml文件任意新增、修改、删除语句
     * @param mappedStatementId
     * @param sqlpara
     * @return
     */
    public int update(String mappedStatementId, SqlPara sqlpara);

    /**
     * 查找单个对象
     * @return
     * @
     */
    public <T> T findObject(String mappedStatementId, SqlPara sqlPara);

    /**
     * 查找单个对象
     * @return
     * @
     */
    public <T> T findObjectOrCreate(String mappedStatementId, SqlPara sqlPara) ;
  
    /** 
     * 查找对象集合
     * @return 
     * @
     */
    public <T> List<T> findList(String mappedStatementId, SqlPara sqlPara);

    /**
     * 查找对象集合
     * @return
     * @
     */
    public <T> List<T> findListPage(String str, Object obj);
      
}