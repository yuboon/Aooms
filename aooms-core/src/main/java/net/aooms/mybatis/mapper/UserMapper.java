package net.aooms.mybatis.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import net.aooms.mybatis.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * User 表数据库控制层接口
 */
@Component
public interface UserMapper {

    /**
     * 自定义注入方法
     */
    int deleteAll();

    //@Select("select test_id as id, name, age, test_type from user")
    List<User> selectListBySQL();

    List<Map<String,Object>> selectMap(Map<String,Object> maps);

    List<User> selectListByWrapper(@Param("ew") Wrapper wrapper);

}