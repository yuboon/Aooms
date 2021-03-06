package net.aooms.core.record;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 树模型
 * Created by 风象南(yuboon) on 2018/9/7
 */
public class RecordTree extends Record implements Serializable {

    private static final long serialVersionUID = 1L;

    public RecordTree() {
        List<RecordTree> children = Lists.newArrayList();
        this.setChildren(children);
        this.setLeaf(true);
    }

    public String getId() {
        return this.getString("id");
    }

    public void setId(String id) {
        this.set("id", id);
    }

    public String getText() {
        return this.getString("text");
    }

    public void setText(String text) {
        this.set("text", text);
    }

    public Boolean getLeaf() {
        return this.getBoolean("leaf");
    }

    public void setLeaf(boolean leaf) {
        this.set("leaf", leaf);
    }

    public String getIconCls() {
        return this.getString("iconCls");
    }

    public void setIconCls(String iconCls) {
        this.set("iconCls", iconCls);
    }

    public String getIcon() {
        return this.getString("icon");
    }

    public void setIcon(String icon) {
        this.set("icon", icon);
    }

    public List<RecordTree> getChildren() {
        return (List<RecordTree>) this.get("children");
    }

    public void setChildren(List<RecordTree> children) {
        this.set("children", children);
    }


}
