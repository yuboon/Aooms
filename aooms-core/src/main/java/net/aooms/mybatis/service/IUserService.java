package net.aooms.mybatis.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import net.aooms.mybatis.entity.User;

import java.util.List;
import java.util.Map;

/**
 *
 * User 表数据服务层接口
 *
 */
public interface IUserService {

	boolean deleteAll();

	public List<User> selectListBySQL();

	public List<Map<String,Object>> selectMap();

	public List<User> selectListByWrapper(Wrapper wrapper);
}