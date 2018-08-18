package net.aooms.core.module.mybatis;

import com.baomidou.mybatisplus.MybatisMapperAnnotationBuilder;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.toolkit.GlobalConfigUtils;
import com.jfinal.plugin.activerecord.Record;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by cccyb on 2018-08-13
 * //http://www.iteye.com/news/32753
 */
@Component
public interface EntityMapper<T> {

    @SelectProvider(type = EntitySelectProvider.class,method = "sql")
    public void selectList();

   /* BaseMapper

    public void insert(String str);

    public void insert(String str);

    public void insert(String str);

    public void insert(String str);*/

}