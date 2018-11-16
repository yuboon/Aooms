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
		this.setResultValue(AoomsVar.RS_DATA, db.findRecords("UserMapper.findList", SqlPara.SINGLETON));
	}

	@Transactional
	public void insert() {
		Record user = Record.empty().setByJsonKey("form");
		db.insert("t_user",user);
	}

	@Transactional
	public void update() {
		Record user = Record.empty().setByJsonKey("form");
		db.update("t_user",user);
	}

	@Transactional
	public void delete() {
		db.deleteByPrimaryKey("t_user",getParaString("id"));
	}
}