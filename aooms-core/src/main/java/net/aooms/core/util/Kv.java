package net.aooms.core.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.Date;
import java.util.HashMap;

/**
 *
 * Kv 数据对象：适用于需要键值对的参数或结果
 * Created by 风象南(cheereebo) on 2018-04-18
 *
 */
public class Kv extends HashMap {
	
	public Kv() {

	}

	public Kv(String key, Object value) {
		this.put(key, value);
	}

	public static Kv empty() {
		return new Kv();
	}

	/**
	 * 实例创建并赋值
	 * @param key
	 * @param value
	 * @return
	 */
	public static Kv fkv(String key, Object value) {
		return new Kv().kv(key, value);
	}

	public static Kv fkv(String key) {
		return new Kv().kv(key, key);
	}

	public Kv kv(String key, Object value) {
		this.put(key, value);
		return this;
	}

	public Kv kv(String key) {
		this.put(key, key);
		return this;
	}

	public String getString(String key){
		return String.valueOf(this.get(key));
	}

	public Integer getInteger(String key){
		return Integer.parseInt(getString(key));
	}

	public Long getLong(String key){
		return Long.parseLong(getString(key));
	}

	public Float getFloat(String key){
		return Float.parseFloat(getString(key));
	}

	public Double getDouble(String key){
		return Double.parseDouble(getString(key));
	}

	public Date getDate(String key){
		return DateUtil.parseDate(getString(key));
	}

	public DateTime getDateTime(String key){
		return DateUtil.parseDateTime(getString(key));
	}

	public Boolean getBoolean(String key){
		return Boolean.parseBoolean(getString(key));
	}

}
