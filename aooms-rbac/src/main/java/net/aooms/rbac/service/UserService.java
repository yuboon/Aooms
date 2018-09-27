package net.aooms.rbac.service;

import cn.hutool.core.date.DateUtil;
import net.aooms.core.AoomsConstants;
import net.aooms.core.id.IDGenerator;
import net.aooms.core.module.mybatis.Db;
import net.aooms.core.module.mybatis.SqlPara;
import net.aooms.core.module.mybatis.record.PagingRecord;
import net.aooms.core.module.mybatis.record.Record;
import net.aooms.core.service.GenericService;
import net.aooms.rbac.mapper.RbacMapperPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户管理
 * Created by 风象南(cheereebo) on 2018-09-17
 */
@Service
public class UserService extends GenericService {

    @Autowired
    private Db db;

    @Transactional(readOnly = true)
    public void findList() {
		PagingRecord pagingRecord = db.findList(getStatementId(RbacMapperPackage.class,"UserMapper.findList"), SqlPara.fromDataBoss());
		this.setResultValue(AoomsConstants.Result.DATA,pagingRecord);
	}

	@Transactional
	public void insert() {
		Record record = Record.NEW();
		record.set(AoomsConstants.ID,IDGenerator.getLongValue());
		record.setByJsonKey("formData");
		record.set("create_time", DateUtil.now());
		db.insert("aooms_rbac_user",record);
	}

	@Transactional
	public void update() {
        Record record = Record.NEW();
        record.setByJsonKey("formData");
        record.set("update_time",DateUtil.now());
        db.update("aooms_rbac_user",record);
	}

	@Transactional
	public void delete() {
		db.deleteByPrimaryKey("user","root1");
	}
}