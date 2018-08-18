package net.aooms.mybatis.service;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import net.aooms.mybatis.entity.User;
import net.aooms.mybatis.mapper.GenericDaoSupport;
import net.aooms.mybatis.mapper.UserMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private BaseMapper baseMapper;

	@Autowired
	private SqlSessionTemplate sessionTemplate;

	@Autowired
	private GenericDaoSupport genericDaoSupport;

	public boolean deleteAll() {
		userMapper.deleteAll();
		return true;
	}

	public List<User> selectListBySQL() {
		return userMapper.selectListBySQL();
	}

	public List<Map<String,Object>> selectMap() {
		User u = new User();


		//genericDaoSupport.

		List<Map<String,Object>> datas = (List<Map<String, Object>>) recordDaoSupport.findForList("Demo.selectListBySQL",null);
		System.err.println("datas = " + datas.size());

		//baseMapper.selectMapsPage(new Page(1,1),new EntityWrapper(new User()));
		//sqlSession.selectOne("23123");
		//sessionTemplate.getConfiguration().getMappedStatement("")
		//u.setId(System.currentTimeMillis());
		//baseMapper.insert(u);
		//baseMapper.deleteById(1L);

		return userMapper.selectMap();
	}

	public List<User> selectListByWrapper(Wrapper wrapper) {
		return userMapper.selectListByWrapper(wrapper);
	}
}