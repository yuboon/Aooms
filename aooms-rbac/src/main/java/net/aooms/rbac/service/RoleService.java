package net.aooms.rbac.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
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
	public void findPermissionByRoleId() {
        SqlPara sqlPara = SqlPara.fromDataBoss().tableAlias("p").and("is_halfselect");
        System.err.println("sql:" + sqlPara.get("_ANDS_"));
		List<String> resourceIds = db.findList(RbacMapper.PKG.by("RoleMapper.findPermissionByRoleId"),sqlPara);
		this.setResultValue("resourceIds", resourceIds);
	}

    @Transactional
    public void insertPermission() {
        String resourceIds = getParaString("resourceIds");
        String halfResourceIds = getParaString("halfResourceIds");

        // 删除旧数据
        db.update(RbacMapper.PKG.by("RoleMapper.deletePermissionByRoleId"),SqlPara.empty().set("role_id",getParaString("role_id")));

        if(StrUtil.isNotBlank(resourceIds)){
            JSONArray ids = JSONArray.parseArray(resourceIds);
            List<Record> list = Lists.newArrayList();
            ids.forEach(id -> {
                Record record = Record.empty();
                record.set(AoomsVar.ID,IDGenerator.getStringValue());
                record.set("role_id",getParaString("role_id"));
                record.set("resource_id",id);
                record.set("is_halfselect",AoomsVar.NO);
                record.set("create_time",DateUtil.now());
                list.add(record);
            });

            db.batchInsert("aooms_rbac_permission",list,100);
        }

        if(StrUtil.isNotBlank(halfResourceIds)){
            JSONArray ids = JSONArray.parseArray(halfResourceIds);
            List<Record> list = Lists.newArrayList();
            ids.forEach(id -> {
                Record record = Record.empty();
                record.set(AoomsVar.ID,IDGenerator.getStringValue());
                record.set("role_id",getParaString("role_id"));
                record.set("resource_id",id);
                record.set("is_halfselect",AoomsVar.YES);
                record.set("create_time",DateUtil.now());
                list.add(record);
            });

            db.batchInsert("aooms_rbac_permission",list,100);
        }
    }
}