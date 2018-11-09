package net.aooms.rbac.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import net.aooms.core.Aooms;
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
 * 角色管理
 * Created by 风象南(yuboon) on 2018-09-17
 */
@Service
public class RoleService extends GenericService {

    @Autowired
    private Db db;

    @Transactional(readOnly = true)
    public void findList() {
        SqlPara sqlPara = SqlPara.fromDataBoss().paging();
        sqlPara.tableAlias("t").and("status","org_id")
               .andLikeStart("role_name","role_code")
		       .tableAlias("o")
		       .andLikeStart("data_permission")
		;
		RecordGroup recordGroup = db.findRecords(RbacMapper.PKG.by("RoleMapper.findList"),sqlPara);
		this.setResultValue(AoomsVar.RS_DATA, recordGroup);
	}

	@Transactional
	public void insert() {
		Record record = Record.empty();
		record.set(AoomsVar.ID,IDGenerator.getStringValue());
		record.setByJsonKey("formData");
		record.set("is_admin", AoomsVar.NO);
		record.set("create_time", DateUtil.now());
		db.insert("aooms_rbac_role",record);
	}

	@Transactional
	public void update() {
        Record record = Record.empty();
        record.setByJsonKey("formData");
        record.set("update_time",DateUtil.now());
        db.update("aooms_rbac_role",record);
	}

	@Transactional
	public void updateStatus() {
		Record record = Record.empty();
		record.set(AoomsVar.ID, getParaString("id"));
		record.set("status", getParaString("status"));
		record.set("update_time",DateUtil.now());
		db.update("aooms_rbac_role",record);
	}

	@Transactional
	public void delete(){
        List<Object> ids = this.getListFromJson("ids", AoomsVar.ID);
		db.batchDelete("aooms_rbac_role",ids.toArray());
	}

	@Transactional(readOnly = true)
	public void findResourceByRoleId() {
        SqlPara sqlPara = SqlPara.empty().set("role_id",getParaString("role_id"));
		List<String> resourceIds = db.findList(RbacMapper.PKG.by("RoleMapper.findResourceByRoleId"),sqlPara);
		this.setResultValue("resourceIds", resourceIds);
	}
}