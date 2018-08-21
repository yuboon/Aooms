package net.aooms.mybatis.mapper;

import org.apache.ibatis.session.SqlSessionFactory;

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
     * 批量保存对象
     * @return
     * @
     */
    public int batchInsert(String str, List objs);
      
    /** 
     * 修改对象
     * @return 
     * @ 
     */  
    public int update(String str, Object obj) ;

    /**
     * 批量修改对象
     */
    public int batchUpdate(String str, List objs);

    /**
     * 删除对象
     * @return
     * @
     */
    public int delete(String str, Object obj) ;

    /**
     * 批量删除对象
     * @return
     * @
     */
    public int batchDelete(String str, List objs );

    /** 
     * 查找单个对象
     * @return 
     * @ 
     */  
    public <T> T findForObject(String str, Object obj) ;
  
    /** 
     * 查找对象集合
     * @return 
     * @
     */
    public <T> List<T> findForList(String str, Object obj) ;
      
}