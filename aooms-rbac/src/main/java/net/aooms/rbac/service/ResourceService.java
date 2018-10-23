package net.aooms.rbac.service;

import cn.hutool.core.date.DateUtil;
import net.aooms.core.AoomsConstants;
import net.aooms.core.id.IDGenerator;
import net.aooms.core.module.mybatis.Db;
import net.aooms.core.module.mybatis.SqlPara;
import net.aooms.core.record.PagingRecord;
import net.aooms.core.record.Record;
import net.aooms.core.service.GenericService;
import net.aooms.core.util.Kv;
import net.aooms.core.util.TreeUtils;
import net.aooms.rbac.mapper.RbacMapperPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统资源管理
 * Created by 风象南(cheereebo) on 2018-10-08
 */
@Service
public class ResourceService extends GenericService {

    @Autowired
    private Db db;

    @Transactional(readOnly = true)
    public void findList() {
        SqlPara sqlPara = SqlPara.fromDataBoss().paging();
        sqlPara.and("status","parent_resource_id");

        String statementId = getStatementId(RbacMapperPackage.class,"ResourceMapper.findList");
		PagingRecord pagingRecord = db.findList(statementId,sqlPara);
		this.setResultValue(AoomsConstants.Result.DATA,pagingRecord);
	}

	@Transactional(readOnly = true)
	public void findTree() {
		String statementId = getStatementId(RbacMapperPackage.class,"ResourceMapper.findList");
		PagingRecord pagingRecord = db.findList(statementId,SqlPara.SINGLETON);

        TreeUtils treeUtils = new TreeUtils(pagingRecord.getList());
        treeUtils.setParentIdKey("parent_resource_id");
        treeUtils.setConvertValueKey(Kv.fkv("resource_name","label"));
        treeUtils.setRetainOriginal(true);
        treeUtils.setDefaultValue(Kv.fkv("icon","el-icon-news"));
        List<Record> treeRecords = treeUtils.listTree(AoomsConstants.TREE_ROOT);

		this.setResultValue(AoomsConstants.Result.TREE, treeRecords);
	}

	@Transactional
	public void insert() {
		Record record = Record.empty();
		record.set(AoomsConstants.ID,IDGenerator.getStringValue());
		record.setByJsonKey("formData");
		record.set("create_time", DateUtil.now());
		db.insert("aooms_rbac_resource",record);

		record.set("icon","el-icon-news");
		this.setResultValue(AoomsConstants.Result.RECORD, record);
	}

	@Transactional
	public void update() {
        Record record = Record.empty();
        record.setByJsonKey("formData");
        record.set("update_time",DateUtil.now());
        db.update("aooms_rbac_resource",record);

        //record.convertValueKey(Kv.fkv("resource_name","label"),false);
        record.set("icon","el-icon-news");
        this.setResultValue(AoomsConstants.Result.RECORD, record);
    }

    @Transactional
    public void updateStatus() {
        Record record = Record.empty();
        record.set(AoomsConstants.ID, getParaString("id"));
        record.set("status", getParaString("status"));
        record.set("update_time",DateUtil.now());
        db.update("aooms_rbac_resource",record);
    }

	@Transactional
	public void delete() {
        List<Object> ids = this.getListFromJson("ids",AoomsConstants.ID);
		db.batchDelete("aooms_rbac_resource",ids.toArray());
	}


}