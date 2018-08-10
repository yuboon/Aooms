package net.aooms.mybatis.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import net.aooms.mybatis.entity.User;
import net.aooms.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public boolean deleteAll() {
		userMapper.deleteAll();
		return true;
	}

	@Override
	public List<User> selectListBySQL() {
		return userMapper.selectListBySQL();
	}

	@Override
	public List<Map<String,Object>> selectMap() {
		return userMapper.selectMap();
	}

	@Override
	public List<User> selectListByWrapper(Wrapper wrapper) {
		return userMapper.selectListByWrapper(wrapper);
	}
}