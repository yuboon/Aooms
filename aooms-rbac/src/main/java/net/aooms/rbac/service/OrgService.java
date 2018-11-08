package net.aooms.rbac.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import net.aooms.core.AoomsVar;
import net.aooms.core.id.IDGenerator;
import net.aooms.core.module.mybatis.Db;
import net.aooms.core.module.mybatis.Roper;
import net.aooms.core.module.mybatis.SqlExpression;
import net.aooms.core.module.mybatis.SqlPara;
import net.aooms.core.record.Record;
import net.aooms.core.record.RecordGroup;
import net.aooms.core.service.GenericService;
import net.aooms.core.util.Kv;
import net.aooms.core.util.TreeUtils;
import net.aooms.rbac.mapper.RbacMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 机构管理
 * Created by 风象南(yuboon) on 2018-10-08
 */
@Service
public class OrgService extends GenericService {

    private static int permissionLen = 4;
    private static char permissionSplicChar = '.';


    @Autowired
    private Db db;

    @Transactional(readOnly = true)
    public void findList() {
        SqlPara sqlPara = SqlPara.fromDataBoss().paging();
        sqlPara.and("status")
                .andLikeStart("data_permission","org_name","org_shortname","org_code")
                .orGroup(new SqlExpression("parent_org_id",Roper.Eq), new SqlExpression("id",Roper.Eq,"parent_org_id"))
        ;

        String statementId = getStatementId(RbacMapper.class,"OrgMapper.findList");
		RecordGroup recordGroup = db.findRecords(statementId,sqlPara);
		this.setResultValue(AoomsVar.RS_DATA, recordGroup);
	}

	@Transactional(readOnly = true)
	public void findTree() {
		String statementId = getStatementId(RbacMapper.class,"OrgMapper.findList");
		RecordGroup recordGroup = db.findRecords(statementId,SqlPara.SINGLETON);

        TreeUtils treeUtils = new TreeUtils(recordGroup.getList());
        treeUtils.setParentIdKey("parent_org_id");
        treeUtils.setDefaultValue(Kv.fkv("icon","el-icon-news"));
        List<Record> treeRecords = treeUtils.listTree(AoomsVar.TREE_ROOT, true);

		this.setResultValue(AoomsVar.RS_TREE, treeRecords);
	}

	@Transactional
	public void insert() {
		Record record = Record.empty();
		record.set(AoomsVar.ID,IDGenerator.getStringValue());
		record.setByJsonKey("formData");
		record.set("create_time", DateUtil.now());
		String parentId = record.getString("parent_org_id");
		String orgPermission = this.permissionCode(parentId);
		record.set("org_permission", orgPermission);
        record.set("org_level", this.orgLevel(parentId) + 1);
        record.set("data_permission", this.dataPermission(parentId,orgPermission));
        db.insert("aooms_rbac_org",record);

		// 返回前台
		record.set("icon","el-icon-news");
		this.setResultValue(AoomsVar.RS_VO, record);
	}

	@Transactional
	public void update() {
        Record record = Record.empty();
        record.setByJsonKey("formData");
        record.set("update_time",DateUtil.now());
        /*String parentId = record.getString("parent_org_id");
        String orgPermission = this.permissionCode(parentId);
        record.set("org_permission", orgPermission);
        record.set("org_level", this.orgLevel(record.getString("parent_org_id")) + 1);
        record.set("data_permission", this.dataPermission(parentId,orgPermission));*/
        db.update("aooms_rbac_org",record);
        this.rebuildDataPermission();

        record.set("icon","el-icon-news");
        this.setResultValue(AoomsVar.RS_VO, record);
    }

    @Transactional
    public void test() {
        Record record = Record.empty();
        record.set("id","262224019124654080");
        record.set("org_name",getParaString("org_name"));
        db.update("aooms_rbac_org",record);

        Record r2 = Record.empty();
        record.set("id","ROOT");
        r2.set("update_time2",DateUtil.now());
        db.update("aooms_rbac_org",r2);

    }

    @Transactional
    public void updateStatus() {
        Record record = Record.empty();
        record.set(AoomsVar.ID, getParaString("id"));
        record.set("status", getParaString("status"));
        record.set("update_time",DateUtil.now());
        db.update("aooms_rbac_org",record);
    }

	@Transactional
	public void delete() {
        List<Object> ids = this.getListFromJson("ids", AoomsVar.ID);
		db.batchDelete("aooms_rbac_org",ids.toArray());
	}

    /**
     *
     * 私有辅助
     *
     */

    // 生成permissionCode
    private String permissionCode(String parentId){
        String permission;

        synchronized (this.getClass()){
            String statementId = getStatementId(RbacMapper.class,"OrgMapper.findMaxOrgPermission");
            Integer maxPermission = db.findObject(statementId,SqlPara.empty().set("parent_org_id",parentId),Integer.class);
            if(maxPermission == null){
                maxPermission = 0;
            }
            maxPermission += 1;

            permission = maxPermission + "";
            if(permission.length() > permissionLen){
                throw new RuntimeException("The org_permission length is not allowed to be greater than " + permissionLen);
            }

            int start = permission.length();
            for(int index = start; index < permissionLen; index++){
                permission = "0" + permission;
            };
        }
        return permission;
    }

    // 获取orgLevel
    private Integer orgLevel(String parentId){
        String statementId = getStatementId(RbacMapper.class,"OrgMapper.findOrgLevel");
        Integer orgLevel = db.findObject(statementId,SqlPara.empty().set("parent_org_id",parentId),Integer.class);
        if(orgLevel == null){
            orgLevel = 0;
        }
        return orgLevel;
    }

    // 生成data_permission
    private String dataPermission(String parentId,String orgPermission){
        StringBuilder dataPermission = new StringBuilder(orgPermission);
        Record record = db.findByPrimaryKey("aooms_rbac_org", parentId);
        while(record != null){
            String permission = record.getString("org_permission");
            if(StrUtil.isBlank(permission)){
                break;
            }

            dataPermission.insert(0,record.getString("org_permission") + permissionSplicChar);
            record = db.findByPrimaryKey("aooms_rbac_org",record.getString("parent_org_id"));
        }
        return dataPermission.toString();
    }


    // 全局重新生成 data_permission
    public void rebuildDataPermission(){
        String updatePermissionColumnStatementId = getStatementId(RbacMapper.class,"OrgMapper.updatePermissionColumn");
        db.update(updatePermissionColumnStatementId, SqlPara.SINGLETON);

        String statementId = getStatementId(RbacMapper.class,"OrgMapper.findList");
        RecordGroup pr = db.findRecords(statementId,SqlPara.SINGLETON);

        TreeUtils treeUtils = new TreeUtils(pr.getList());
        treeUtils.setParentIdKey("parent_org_id");
        List<Record> trees = treeUtils.listTree(AoomsVar.TREE_ROOT);

        this.rebuild(AoomsVar.TREE_ROOT,"",0,trees);
    }

    //
    private void rebuild(String parentId, String parentPermission,int orgLevel,List<Record> children){
        String statementId = getStatementId(RbacMapper.class,"OrgMapper.findMaxOrgPermission");
        Integer maxPermission = db.findObject(statementId,SqlPara.empty().set("parent_org_id",parentId),Integer.class);
        int index= 0;
        if(maxPermission == null){
            maxPermission = 0;
        }

        for(Record child : children){
            Record newChild = Record.empty();
            newChild.set(AoomsVar.ID, child.get(AoomsVar.ID));

            maxPermission += 1;
            String permission = maxPermission + "";
            if(permission.length() > permissionLen){
                throw new RuntimeException("The org_permission length is not allowed to be greater than " + permissionLen);
            }

            int start = permission.length();
            for(int i = start; i < permissionLen; i++){
                permission = "0" + permission;
            };

            String dataPermission = parentPermission + (StrUtil.isBlank(parentPermission)? "" : permissionSplicChar) + permission;
            int level = orgLevel + 1;
            newChild.set("org_level", level);
            newChild.set("org_permission", permission);
            newChild.set("data_permission", dataPermission);
            db.update("aooms_rbac_org",newChild);

            List<Record> innerChildren = (List<Record>)child.get("children");
            if(innerChildren != null && innerChildren.size() > 0){
                this.rebuild(child.getString("id"), dataPermission, level, innerChildren);
            }
        }
    }

}