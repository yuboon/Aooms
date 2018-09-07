package net.aooms.demo.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import io.shardingsphere.core.transaction.event.TransactionEvent;
import io.shardingsphere.core.transaction.listener.TransactionListener;
import io.shardingsphere.transaction.bed.sync.BestEffortsDeliveryListener;
import net.aooms.core.Constants;
import net.aooms.core.datasource.DS;
import net.aooms.core.datasource.DynamicDataSourceHolder;
import net.aooms.core.module.dbsharding.SoftTransactional;
import net.aooms.core.module.mybatis.SqlPara;
import net.aooms.core.module.mybatis.dao.GenericDao;
import net.aooms.core.module.mybatis.record.PagingRecord;
import net.aooms.core.module.mybatis.record.Record;
import net.aooms.core.service.GenericService;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.RuntimeMBeanException;
import java.util.List;

@Service
public class UserService extends GenericService {

	@Autowired
	private SqlSessionTemplate sessionTemplate;

	@Autowired
	private GenericDao genericDao;

	//@DS
	//@Transactional
	@SoftTransactional
	//@DS
	public void query() {
		/*Record record1 = Record.NEW();
		record1.set(Constants.ID,System.currentTimeMillis());
		record1.set("name","lisi");
		//genericDaoSupport.delete("user",record1);
		//genericDaoSupport.batchDelete("user",records,2);
		System.err.println("pathCode = " + getPathString("code"));

		genericDaoSupport.insert("user", record1);*/

		//System.err.println("Record = " + JSON.toJSONString(recordPaging,SerializerFeature.WriteMapNullValue));

		//getResult().set(Constants.Result.DATA,recordPaging);



		//PagingRecord records2 = genericDaoSupport.findList("Demo.selectListBySQL",sqlParaFromDataBoss());
		//System.err.println("PagingRecord = " + JSON.toJSONString(records2));

		/*Record record = genericDao.findByPrimaryKey("user","1534904693057-0");
		System.err.println(JSON.toJSONString(record));*/
		//Record record2 = genericDao.findByPrimaryKey("user","1534904693057-0");
		//System.err.println("secord:" + JSON.toJSONString(record2));
		//Record R2 = genericDao.findByPrimaryKey("user","1534904693057-0");
		//System.err.println("master:" + JSON.toJSONString(R2));

		//PagingRecord R3 = genericDao.findList("selectMaster", SqlPara.SINGLETON);

		/*
		List<Record> recordList = Lists.newArrayList();
		for(int i = 0 ;i < 2; i++){
			Record record1 = Record.NEW();
			long id = System.currentTimeMillis();
			record1.set("id", defaultKeyGenerator.generateKey());
			record1.set("name",12);
			record1.set("role",6);
			recordList.add(record1);
		}
*/

		//this.proxy(getClass()).slave();

		DefaultKeyGenerator defaultKeyGenerator = new DefaultKeyGenerator();
		Record record1 = Record.NEW();
		long id = System.currentTimeMillis();
		record1.set("order_id", defaultKeyGenerator.generateKey());
		record1.set("user_id",12);
		record1.set("status","NEW");

		genericDao.insert("t_order",record1);


  throw new RuntimeException("事务报错");
		//获取session1
		/*SqlSession session1 = sessionTemplate.getSqlSessionFactory().openSession();
		List<Record> records = session1.selectList("Demo.selectListBySQL");
		System.out.println(records);
		session1.close();

		SqlSession session2 = sessionTemplate.getSqlSessionFactory().openSession();
		List<Record> records2 = session2.selectList("Demo.selectListBySQL");
		System.out.println(records2);
		session2.close();*/

//		Record r = genericDao.findObject("Demo.selectListBySQL", SqlPara.SINGLETON);
//		System.err.println("r = " + JSON.toJSONString(r));
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

        // this.master();

		//proxy(this.getClass()).slave();

		// 自我调用不走aspect问题
		// ((UserService)AopContext.currentProxy()).slave();

		//return Lists.newArrayList();//userMapper.selectMap(maps);
	}

	@Transactional
	//@DS("slave")
	public void slave() {

		Record R2 = genericDao.findByPrimaryKey("user","1534904693057-0");
		System.err.println("slave:" + JSON.toJSONString(R2));

		Record record1 = Record.NEW();
		record1.set(Constants.ID,System.currentTimeMillis());
		record1.set("name","lisi3");
		genericDao.insert("user",record1);

		throw new RuntimeException("事务测试");


		//throw new RuntimeException("不能保存");
	}

}