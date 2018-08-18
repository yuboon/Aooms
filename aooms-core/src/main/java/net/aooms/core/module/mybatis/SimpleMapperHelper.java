package net.aooms.core.module.mybatis;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class SimpleMapperHelper {
    public static final XMLLanguageDriver XML_LANGUAGE_DRIVER = new XMLLanguageDriver();
    /**  
     * 获取泛型类型  
     */  
    public static Class getEntityClass(Class<?> mapperClass){  
        Type[] types = mapperClass.getGenericInterfaces();
        Class<?> entityClass = null;  
        for (Type type : types) {  
            if (type instanceof ParameterizedType) {
                ParameterizedType t = (ParameterizedType) type;  
                //判断父接口是否为 BaseMapper.class  
                if (t.getRawType() == EntityMapper.class) {
                    //得到泛型类型  
                    entityClass = (Class<?>) t.getActualTypeArguments()[0];  
                    break;  
                }  
            }  
        }  
        return entityClass;  
    }  
  
    /**  
     * 替换 SqlSource  
     */  
    public static void changeMs(MappedStatement ms) throws Exception {
        String msId = ms.getId();  
        //标准msId为 包名.接口名.方法名  
        int lastIndex = msId.lastIndexOf(".");  
        String methodName = msId.substring(lastIndex + 1);  
        String interfaceName = msId.substring(0, lastIndex);  
        Class<?> mapperClass = Class.forName(interfaceName);  
        //判断是否继承了通用接口  
        if(BaseMapper.class.isAssignableFrom(mapperClass)){  
            //判断当前方法是否为通用 select 方法  
            if (methodName.equals("select")) {  
                Class entityClass = getEntityClass(mapperClass);  
                //必须使用<script>标签包裹代码  
                StringBuffer sqlBuilder = new StringBuffer("<script>");  
                //简单使用类名作为包名  
                sqlBuilder.append("select * from ").append(entityClass.getSimpleName());
                Field[] fields = entityClass.getDeclaredFields();
                sqlBuilder.append(" <where> ");  
                for (Field field : fields) {  
                    //使用 if 标签进行动态判断  
                    sqlBuilder.append("<if test=\"").append(field.getName()).append("!=null\">");
                    //字段名直接作为列名  
                    sqlBuilder.append(" and ")
                              .append(field.getName())
                              .append(" = #{")  
                              .append(field.getName())  
                              .append("}");  
                    sqlBuilder.append("</if>");  
                }  
                sqlBuilder.append("</where>");  
                sqlBuilder.append("</script>");  
                //解析 sqlSource  
                SqlSource sqlSource = XML_LANGUAGE_DRIVER.createSqlSource(
                        ms.getConfiguration(),   
                        sqlBuilder.toString(),   
                        entityClass);  
                //替换  
                MetaObject msObject = SystemMetaObject.forObject(ms);
                msObject.setValue("sqlSource", sqlSource);  
            }  
        }  
    }  
  
}  