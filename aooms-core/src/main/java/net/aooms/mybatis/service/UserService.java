package net.aooms.mybatis.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.aooms.core.Vars;
import net.aooms.mybatis.dao.GenericDaoSupport;
import net.aooms.mybatis.entity.User;
import net.aooms.mybatis.record.Record;
import net.aooms.mybatis.record.RecordPaging;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public class UserService extends GenericService {

	@Autowired
	private SqlSessionTemplate sessionTemplate;

	@Autowired
	private GenericDaoSupport genericDaoSupport;

	public void selectMap() {
		User u = new User();

		List<Record> records = Lists.newArrayList();

		IntStream.range(0,5).forEach(index->{
			String id = System.currentTimeMillis() + "-" + index;
			Record record1 = Record.NEW();
			record1.set(Vars.ID,id);
			record1.set("name","lisi" + index);
			records.add(record1);
		});

		Record record1 = Record.NEW();
		record1.set(Vars.ID,System.currentTimeMillis());
		record1.set("name","lisi");
		//genericDaoSupport.delete("user",record1);
		//genericDaoSupport.batchDelete("user",records,2);
		System.err.println("pathCode = " + getPathString("code"));

		//genericDaoSupport.update("Demo.updateById", fromDataBoss());
		RecordPaging recordPaging = genericDaoSupport.findList("Demo.selectListBySQL",sqlParaPagingFromDataBoss());


		System.err.println("Record = " + JSON.toJSONString(recordPaging,SerializerFeature.WriteMapNullValue));

		//getResult().set(Vars.Result.DATA,recordPaging);

		this.setResultValue("idsd","");
		super.setResultValue("name","23");

		//List<Record> records2 = genericDaoSupport.findList("Demo.selectListBySQL",fromDataBoss());
		//System.err.println("RecordPaging = " + JSON.toJSONString(records2));


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



		//return Lists.newArrayList();//userMapper.selectMap(maps);
	}

}