package net.aooms.example.service;

import net.aooms.core.AoomsVar;
import net.aooms.core.id.IDGenerator;
import net.aooms.core.module.mybatis.Db;
import net.aooms.core.module.mybatis.SqlPara;
import net.aooms.core.record.Record;
import net.aooms.core.record.RecordGroup;
import net.aooms.core.service.GenericService;
import net.aooms.example.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * simple crud service
 * Created by 风象南(yuboon) on 2018-09-17
 */
@Service
public class UserService extends GenericService {

    @Autowired
    private Db db;

	public void findList() {
		RecordGroup recordGroup = db.findList("UserMapper.findList", SqlPara.fromDataBoss());

		{
			// 返回值
			this.setResultValue(AoomsVar.RS_DATA, recordGroup);
		}
	}

	@Transactional
	public void insert() {

		// record 模式
		Record record1 = Record.empty();
		record1.set(AoomsVar.ID,IDGenerator.getLongValue());
		record1.set("name","lisi3");
		db.insert("user",record1);

		UserVo userVo = new UserVo();
		userVo.setId(IDGenerator.getLongValue());
		userVo.setName("wangwu");
		Record record2 = Record.empty().fromBean(userVo);
		// fromDataBoss(prefix) 该方法可将参数 满足model.xxx 规则的参数构造成record属性
		// 例：http://xxx/insert?record.id=1&record.name=zhangsan&model.role=3&code=02,
		// 通过 fromDataBoss 将提取id,name,role三个属性的值,不会提取code值
		record2.setByKey("model");  // model为参数prefix 格式：model.role , 将会通过model取到role值

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