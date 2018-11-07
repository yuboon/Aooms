package net.aooms.rbac.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import net.aooms.core.AoomsVar;
import net.aooms.core.id.IDGenerator;
import net.aooms.core.module.mybatis.Db;
import net.aooms.core.module.mybatis.SqlPara;
import net.aooms.core.record.Record;
import net.aooms.core.record.RecordGroup;
import net.aooms.core.service.GenericService;
import net.aooms.core.util.PasswordHash;
import net.aooms.rbac.mapper.RbacMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户管理
 * Created by 风象南(yuboon) on 2018-09-17
 */
@Service
public class UserService extends GenericService {

    @Autowired
    private Db db;

    @Transactional(readOnly = true)
    //@DS("slave")
    public void findList() {
        SqlPara sqlPara = SqlPara.fromDataBoss().paging();
        sqlPara.tableAlias("t").and("status","org_id")
               .andLikeStart("user_name","account","phone","user_nickname","email")
               .gte("create_time","update_time")
               .lteCp("create_time","create_time_end")
               .lteCp("update_time","update_time_end")
		       .tableAlias("o")
		       .andLikeStart("data_permission")
		;

        String statementId = getStatementId(RbacMapper.class,"UserMapper.findList");
		RecordGroup recordGroup = db.findList(statementId,sqlPara);
		this.setResultValue(AoomsVar.RS_DATA, recordGroup);
	}

	@Transactional
	public void insert() {
		Record record = Record.empty();
		record.set(AoomsVar.ID,IDGenerator.getStringValue());
		record.setByJsonKey("formData");
		record.set("create_time", DateUtil.now());
		String password = record.getString("password");
		if(StrUtil.isNotBlank(password)){
			record.set("password", PasswordHash.createHash(password));
		}
		db.insert("aooms_rbac_user",record);
	}

	@Transactional
	public void update() {
        Record record = Record.empty();
        record.setByJsonKey("formData");
        record.set("update_time",DateUtil.now());
		String password = record.getString("password");
		if(StrUtil.isNotBlank(password)){
			record.set("password", PasswordHash.createHash(password));
		}
        db.update("aooms_rbac_user",record);
	}

	@Transactional
	public void updateStatus() {
		Record record = Record.empty();
		record.set(AoomsVar.ID, getParaString("id"));
		record.set("status", getParaString("status"));
		record.set("update_time",DateUtil.now());
		db.update("aooms_rbac_user",record);
	}

	@Transactional
	public void delete() {
        List<Object> ids = this.getListFromJson("ids", AoomsVar.ID);
		db.batchDelete("aooms_rbac_user",ids.toArray());
	}
}