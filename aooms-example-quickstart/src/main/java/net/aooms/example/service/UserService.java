package net.aooms.example.service;

import com.alibaba.fastjson.JSON;
import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import net.aooms.core.AoomsConstants;
import net.aooms.core.id.IDGenerator;
import net.aooms.core.id.IDType;
import net.aooms.core.module.dbsharding.SoftTransactional;
import net.aooms.core.module.mybatis.Db;
import net.aooms.core.module.mybatis.SqlPara;
import net.aooms.core.module.mybatis.record.PagingRecord;
import net.aooms.core.module.mybatis.record.Record;
import net.aooms.core.service.GenericService;
import net.aooms.example.vo.UserVo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService extends GenericService {

    @Autowired
    private Db db;

	public void findList() {
		PagingRecord pagingRecord = db.findList("", SqlPara.fromDataBoss());
		// 返回值
		setResultValue(AoomsConstants.Result.DATA,pagingRecord);
	}

	@Transactional
	public void insert() {

		// record 模式
		Record record1 = Record.NEW();
		record1.set(AoomsConstants.ID,IDGenerator.getLongValue());
		record1.set("name","lisi3");
		db.insert("user",record1);

		UserVo userVo = new UserVo();
		userVo.setId(IDGenerator.getLongValue());
		userVo.setName("wangwu");
		Record record2 = Record.NEW().fromBean(userVo);
		db.insert("user",record2);

	}

	@Transactional
	public void update() {

		// record 模式
		Record record = db.findByPrimaryKey("user","root");
		if(record != null){
			record.set("name","zhaoliu");
			db.update("user",record);
		}

	}

	@Transactional
	public void delete() {
		db.deleteByPrimaryKey("user","root1");
	}
}