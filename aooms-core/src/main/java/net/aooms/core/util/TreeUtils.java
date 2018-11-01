package net.aooms.core.util;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import net.aooms.core.record.Record;

import java.util.List;
import java.util.Set;

/**
 * 树操作工具类
 * Created by 风象南(yuboon) on 2018-10-09
 */
public class TreeUtils {

	private Kv convertValueKey;
    private Kv defaultValue;

	private String childrenKey = "children";
	private String idKey = "id";
    private String textKey = "text";
    private String parentIdKey = "parent_id";
    private String parentTextKey = "parent_name";
    private String leafKey = "leaf";
    private List<? extends  Record> records = Lists.newArrayList();
    private boolean retainOriginal = false;

    public TreeUtils(){

	}

    public TreeUtils(List<? extends  Record> records){
        this.records = records;
    }

    public Kv getConvertValueKey() {
        return convertValueKey;
    }

    public void setConvertValueKey(Kv convertValueKey) {
        this.convertValueKey = convertValueKey;
    }

    public String getChildrenKey() {
		return childrenKey;
	}

	public void setChildrenKey(String childrenKey) {
		this.childrenKey = childrenKey;
	}

	public String getIdKey() {
		return idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}

    public String getTextKey() {
        return textKey;
    }

    public void setTextKey(String textKey) {
        this.textKey = textKey;
    }

    public String getParentTextKey() {
        return parentTextKey;
    }

    public void setParentTextKey(String parentTextKey) {
        this.parentTextKey = parentTextKey;
    }

    public String getParentIdKey() {
		return parentIdKey;
	}

	public void setParentIdKey(String parentIdKey) {
		this.parentIdKey = parentIdKey;
	}

    public String getLeafKey() {
        return leafKey;
    }

    public void setLeafKey(String leafKey) {
        this.leafKey = leafKey;
    }

    public boolean isRetainOriginal() {
        return retainOriginal;
    }

    public void setRetainOriginal(boolean retainOriginal) {
        this.retainOriginal = retainOriginal;
    }

    public Kv getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Kv defaultValue) {
        this.defaultValue = defaultValue;
    }

    //
	//-------------------------------- 子级树构造  ---------------------------------
	//
	
	/**
	 * 递归构建树
	 */
	public List<Record> listTree(Object parent_id){
		return listTree(parent_id,false);
	}
	
	/**
	 * 递归构建树
	 * @param includeParent 是否包含当前父节点
	 */
	public List<Record> listTree(Object parent_id,boolean includeParent){
		List<Record> trees = Lists.newArrayList();
		List<Record> children = Lists.newArrayList();

		this.treeMapping(); // 属性映射转换处理
        for(Record m : records){
        	Record mm = (Record)m;
        	if (parent_id.equals(mm.get(parentIdKey))){
				children.add(m);
				treeBuilder(mm.get(idKey),mm);
        	}
        	
        	if(includeParent && parent_id.equals(mm.get(idKey))){
        		m.set(this.childrenKey, children);
        		trees.add(m);
			}
        }
        return includeParent ? trees : children;
	}
	
	private void treeBuilder(Object parent_id,Record node){
		for (Record m : records) {
			Record mm = (Record)m;
			String parent = (String)mm.get(parentIdKey);
			if (parent != null) {
				if (parent_id.equals(parent)) {

					node.set(leafKey,false);
					List<Record> children = (List<Record>)node.get(childrenKey);
					if(children == null){
						children = Lists.newArrayList();
						node.set(childrenKey ,children);
					}
					children.add(mm);
					treeBuilder(mm.get(idKey),mm);
				}
			}
		}
	}
	
	//
	//-------------------------------- 子级ID树构造  ---------------------------------
	//
	
	/**
	 * 递归构建ID参数
	 */
	public List<String> idsTree(Object parent_id){
		return idsTree(parent_id,true);
	}
	
	/**
	 * 递归构建ID参数
	 * @param includeParent 是否包含当前节点
	 */
	public List<String> idsTree(Object parent_id,boolean includeParent){
		List<String> ids = Lists.newArrayList();
		if(includeParent){
			ids.add(String.valueOf(parent_id));
		}
        for(Record m : records){
        	Record mm = (Record)m;
        	if (parent_id.equals(mm.get(parentIdKey))){
        		ids.add(mm.getString(idKey));
        		treeBuilderIds(mm.getString(idKey),ids);
        	}
        }
        return ids;
	}
	
	private void treeBuilderIds(Object parent_id,List<String> ids){
		for (Record m : records) {
			Record mm = (Record)m;
			String parent = (String)mm.get(parentIdKey);
			if (parent != null) {
				if (parent_id.equals(parent)) {
					ids.add(mm.getString(idKey));
					treeBuilderIds(mm.getString(idKey),ids);
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	//
	//-------------------------------- 父级树构造  ---------------------------------
	//
	
	/**
	 * 递归构建树
	 */
	public List<Record> listTreeParent(Object child_id){
		return listTreeParent(child_id,true);
	}

	/**
	 * 递归构建树
	 * @param isIncludeSelf 是否包含当前节点
	 */
	public List<Record> listTreeParent(Object child_id,boolean isIncludeSelf){
		List<Record> trees = Lists.newArrayList();
		String parent_id = "";
		//得到当前节点的父节点
		for (Record t : records) {
			if(child_id.equals(t.getString(idKey))){
				parent_id = t.getString(parentIdKey);
				if(isIncludeSelf){
					trees.add(0,t);
				}
				treeBuilderParent(parent_id,trees);
			}
		}
		
        return trees;
	}
	
	private void treeBuilderParent(Object parent_id,List<Record> trees){
		if(parent_id != null){
			for (Record m : records) {
				Record mm = (Record)m;
				String id = (String)mm.get(idKey);
				if (parent_id.equals(id)) {
					trees.add(0,m);
					treeBuilderParent(mm.get(parentIdKey),trees);
				}
			}
		}
	}
	
	//
	//-------------------------------- 子级ID树构造  ---------------------------------
	//
	
	/**
	 * 递归构建ID参数
	 */
	public List<String> idsTreeParent(Object child_id){
		return idsTreeParent(child_id,true);
	}

	/**
	 * 递归构建ID参数
	 * @param isIncludeSelf 是否包含当前节点
	 */
	public List<String> idsTreeParent(Object child_id,boolean isIncludeSelf){
		List<String> ids = Lists.newArrayList();
		if(isIncludeSelf){
			ids.add(0,String.valueOf(child_id));
		}
		
		String parent_id = "";
		// 得到当前节点的父节点
		for (Record t : records) {
			if(child_id.equals(t.getString(idKey))){
				parent_id = t.getString(parentIdKey);
				treeBuilderIdsParent(parent_id,ids);
			}
		}
		
        return ids;
	}
	
	private void treeBuilderIdsParent(Object parent_id,List<String> ids){
		if(parent_id != null){
			for (Record m : records) {
				Record mm = (Record)m;
				String id = (String)mm.get(idKey);
				if (parent_id.equals(id)) {
					ids.add(0,mm.getString(idKey));
					treeBuilderIdsParent(mm.getString(parentIdKey),ids);
				}
			}
		}
	}
	
	//
	//-------------------------------- 属性映射  ---------------------------------
	//{'id':'id','parent_id':'pId'}
	//
	//

	/**
	 * 属性映射
	 */
	public TreeUtils treeMapping(){
		if(convertValueKey != null){
            Set<String> keys = convertValueKey.keySet();
            for (Record record : records) {
                for(String key : keys){
                    record.set(convertValueKey.getString(key), record.get(key));
                    if(!retainOriginal){
                        record.remove(key); // 移除原始属性
                    }
                }
            }
        };

		if(defaultValue != null){
            Set<String> keys = defaultValue.keySet();
            for (Record record : records) {
                for(String key : keys){
                	if(record.get(key) == null){
						record.set(key, defaultValue.get(key));
					}
                }
            }
        }

		return this;
	}
	
}
