package net.aooms.mybatis.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 */
@TableName("user")
public class User implements Serializable{

    @TableId
    private long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 这里故意演示注解可无
     */
    @TableField("testType")
    @TableLogic
    private Integer testType;

    /**
     * 测试插入填充
     */
    @TableField(fill = FieldFill.INSERT,value="testDate")
    private Date testDate;

    private Long role;

    public User() {
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