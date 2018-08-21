package net.aooms.mybatis.dao;

import cn.hutool.core.lang.Assert;
import net.aooms.mybatis.MyBatisConst;
import net.aooms.mybatis.record.Record;
import org.apache.ibatis.session.ExecutorType;
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
     * 批量更新
     * @return 
     * @ 
     */  
    public int batchInsert(String str, List objs){
        return sqlSessionTemplate.insert(str, objs);  
    }  
      
    /** 
     * 修改对象
     * @return 
     * @ 
     */  
    public int update(String str, Object obj){
        return sqlSessionTemplate.update(str, obj);  
    }  
  
    /** 
     * 批量更新
     * @return
     * @ 
     */  
    public int batchUpdate(String str, List objs){
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        //批量执行器  
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        try{  
            if(objs != null){
                for(int i = 0,size = objs.size();i < size;i++){
                    sqlSession.update(str, objs.get(i));  
                }  
                sqlSession.flushStatements();  
                sqlSession.commit();  
                sqlSession.clearCache();  
            }  
        }finally{  
            sqlSession.close();  
        }
        return objs.size();
    }  
      
    /** 
     * 批量更新
     * @return 
     * @ 
     */  
    public int batchDelete(String str, List objs ){
        return sqlSessionTemplate.delete(str, objs);
    }  
      
    /** 
     * 删除对象
     * @return 
     * @ 
     */  
    public int delete(String str, Object obj)  {
        return sqlSessionTemplate.delete(str, obj);
    }  
       
    /** 
     * 查找对象
     * @return 
     * @ 
     */  
    public <T> T findForObject(String str, Object obj)  {
        return sqlSessionTemplate.selectOne(str, obj);
    }  
  
    /** 
     * 查找对象
     * @return 
     * @ 
     */  
    public <T> List<T> findForList(String str, Object obj)  {
        return sqlSessionTemplate.selectList(str, obj);  
    }  

  /*  public Object findForMap(String str, Object obj, String key, String value)  {
        return sqlSessionTemplate.selectMap(str, obj, key);
    } */
      
} 