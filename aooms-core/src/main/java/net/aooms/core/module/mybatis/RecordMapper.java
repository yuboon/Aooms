package net.aooms.core.module.mybatis;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jfinal.plugin.activerecord.Record;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * Created by cccyb on 2018-08-13
 */
@Component
public interface RecordMapper {

    @SelectProvider(type=RecordMapper.class,method = "sql")
    public Map<String,Object> insert(Record record);

    default String sql(){

        return "select * from user";
    }


}