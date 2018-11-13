package net.aooms.core.authentication;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Maps;
import net.aooms.core.AoomsVar;
import net.aooms.core.id.IDGenerator;
import net.aooms.core.id.IDType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 身份认证信息，默认未登录时为Ghost用户
 * Created by 风象南(yuboon) on 2018/11/1
 */
public class AuthenticationInfo implements Serializable {

    private static final String GHOST = "Ghost";

    private static final long serialVersionUID = 1L;

    // 唯一会话ID，即使同一用户，此ID也不一样，允许一个用户多处登录。
    private String sessionId = IDGenerator.getStringValue(IDType.UUID);

    private String id;

    private String userName;

    private String userNickname;

    private String account;

    private String sex;

    private boolean isAdmin;

    private boolean isGhost;

    private String phone;

    private String photo;

    private String email;

    private String orgId;

    private String orgName;

    private String orgCode;

    private String orgNickname;

    // 数据权限代码
    private String dataPermission;

    // token
    private String token;

    // 登录时间
    private String loginTime = DateUtil.now();

    private List<String> roleIds = new ArrayList<String>();

    private List<String> roleNames = new ArrayList<String>();

    /* 可访问资源模块  */
    private List<String> resources = new ArrayList<String>();

    // 数据权限 <ModuelId,Record>
    //private Map<String,List<DataLimit>> dataLimits = Maps.newHashMap();

    // 扩展属性
    private Map<String,Object> data = Maps.newHashMap();

    /**
     * 获取角色ID串,逗号隔开
     */
    public String getRoleIdsStr(){
        StringBuilder builder = new StringBuilder("''");
        for (String role : roleIds) {
            builder.append(",");
            builder.append("'" + role + "'");
        }
        return builder.toString();
    }

    /**
     * 获取角色Name串,逗号隔开
     */
    public String getRoleNamesStr(){
        StringBuilder builder = new StringBuilder("''");
        for (String role : roleNames) {
            builder.append(",");
            builder.append("'" + role + "'");
        }
        return builder.toString();
    }

    // 设置为Ghost用户
    public AuthenticationInfo ghost(){
        this.id = GHOST + IDGenerator.getStringValue(IDType.UUID);
        this.userName = GHOST;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isAdmin() {
        return (this.id.equals(AoomsVar.ADMIN));
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrgNickname() {
        return orgNickname;
    }

    public void setOrgNickname(String orgNickname) {
        this.orgNickname = orgNickname;
    }

    public String getDataPermission() {
        return dataPermission;
    }

    public void setDataPermission(String dataPermission) {
        this.dataPermission = dataPermission;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isGhost() {
        return GHOST.equals(userName);
    }

}

