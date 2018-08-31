package net.aooms.core.module.mybatis.dao;

import net.aooms.core.module.mybatis.SqlPara;
import net.aooms.core.module.mybatis.record.Record;
import net.aooms.core.module.mybatis.record.PagingRecord;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import java.sql.Connection;
import java.util.List;

public interface GenericDao {

    /**
     * 数据源指定,使用完即可还原
     * @return
     */
    public GenericDao use(String name);

    /**
     * 数据源指定打开，后续该线程一系列操作均使用该数据源，useOff 时还原
     * @return
     */
    public void useOn(String name);

    /**
     * 数据源指定关闭，还原为默认数据源
     * @return
     */
    public void useOff(String name);

    /**
     * SqlSessionTemplate
     * @return
     */
    public SqlSessionTemplate getSqlSessionTemplate();

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
     * 根据主键查找对象
     * @return
     * @
     */
    public <T> T findByPrimaryKey(String tableName, String primaryKeyValue);

    /**
     * 根据主键查找对象
     * @return
     * @
     */
    public <T> T findByPrimaryKey(String tableName, String primaryKeyColumn , String primaryKeyValue);

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
    public PagingRecord findList(String mappedStatementId, SqlPara sqlPara);

      
}