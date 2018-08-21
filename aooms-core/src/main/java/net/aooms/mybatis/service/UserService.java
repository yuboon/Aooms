package net.aooms.mybatis.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.aooms.core.configuration.Vars;
import net.aooms.mybatis.entity.User;
import net.aooms.mybatis.dao.GenericDaoSupport;
import net.aooms.mybatis.record.Record;
import net.aooms.mybatis.mapper.UserMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

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

		List<Record> records = Lists.newArrayList();

		IntStream.range(0,2).forEach(index->{
			String id = System.currentTimeMillis() + "-" + index;
			Record record1 = Record.NEW();
			record1.set(Vars.ID,id);
			record1.set("name","lisi" + index);
			records.add(record1);
		});

		//genericDaoSupport.insert("user",record);
		genericDaoSupport.batchInsert("user",records);


		//genericDaoSupport.update("user",record1);
		//int size = genericDaoSupport.delete("user",record1);

		//genericDaoSupport.

		//List<Map<String,Object>> datas = (List<Map<String, Object>>) genericDaoSupport.findForList("Demo.selectListBySQL",null);
		//System.err.println("datas = " + datas.size());

		//baseMapper.selectMapsPage(new Page(1,1),new EntityWrapper(new User()));
		//sqlSession.selectOne("23123");
		//sessionTemplate.getConfiguration().getMappedStatement("")
		//u.setId(System.currentTimeMillis());
		//baseMapper.insert(u);
		//baseMapper.deleteById(1L);

		Map<String,Object> maps = Maps.newHashMap();
		maps.put("id",System.currentTimeMillis());
		maps.put("name","zhangsan");

		return Lists.newArrayList();//userMapper.selectMap(maps);
	}

	public List<User> selectListByWrapper(Wrapper wrapper) {
		return userMapper.selectListByWrapper(wrapper);
	}
}