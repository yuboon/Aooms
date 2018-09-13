package net.aooms.example.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 */
public class UserVo implements Serializable {

    private long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 这里故意演示注解可无
     */

    private Integer testType;

    /**
     * 测试插入填充
     */
    private Date testDate;

    private Long role;

    public UserVo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTestType() {
        return this.testType;
    }

    public void setTestType(Integer testType) {
        this.testType = testType;
    }

    public Long getRole() {
        return this.role;
    }

    public void setRole(Long role) {
        this.role = role;
    }


    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    @Override
    public String toString() {
        return "User [id=" + this.getId() + ", name=" + name + ", +  testType=" + testType + ", testDate="
                + testDate + ", role=" + role + "]";
    }

}