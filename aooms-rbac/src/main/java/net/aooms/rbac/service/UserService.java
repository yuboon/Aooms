package net.aooms.rbac.service;

import cn.hutool.core.date.DateUtil;
import net.aooms.core.AoomsConstants;
import net.aooms.core.id.IDGenerator;
import net.aooms.core.module.mybatis.Db;
import net.aooms.core.module.mybatis.Roper;
import net.aooms.core.module.mybatis.SqlExpression;
import net.aooms.core.module.mybatis.SqlPara;
import net.aooms.core.record.PagingRecord;
import net.aooms.core.record.Record;
import net.aooms.core.service.GenericService;
import net.aooms.rbac.mapper.RbacMapperPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户管理
 * Created by 风象南(cheereebo) on 2018-09-17
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
               .andLikeAfter("user_name","account","phone","user_nickname","email")
               .gte("create_time","update_time")
               .lteCp("create_time","create_time_end")
               .lteCp("update_time","update_time_end")
		       .tableAlias("o")
		       .andLikeAfter("data_permission")
		;

        String statementId = getStatementId(RbacMapperPackage.class,"UserMapper.findList");
		PagingRecord pagingRecord = db.findList(statementId,sqlPara);
		this.setResultValue(AoomsConstants.Result.DATA,pagingRecord);
	}

	@Transactional
	public void insert() {
		Record record = Record.empty();
		record.set(AoomsConstants.ID,IDGenerator.getStringValue());
		record.setByJsonKey("formData");
		record.set("create_time", DateUtil.now());
		db.insert("aooms_rbac_user",record);
	}

	@Transactional
	public void update() {
        Record record = Record.empty();
        record.setByJsonKey("formData");
        record.set("update_time",DateUtil.now());
        db.update("aooms_rbac_user",record);
	}

	@Transactional
	public void updateStatus() {
		Record record = Record.empty();
		record.set(AoomsConstants.ID, getParaString("id"));
		record.set("status", getParaString("status"));
		record.set("update_time",DateUtil.now());
		db.update("aooms_rbac_user",record);
	}

	@Transactional
	public void delete() {
        List<Object> ids = this.getListFromJson("ids",AoomsConstants.ID);
		db.batchDelete("aooms_rbac_user",ids.toArray());
	}
}