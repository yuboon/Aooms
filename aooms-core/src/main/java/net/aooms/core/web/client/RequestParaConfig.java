package net.aooms.core.web.client;

import java.io.File;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 请求参数配置
 * @author Administrator
 *
 */
public class RequestParaConfig {
	
	private String url;
	
	private Map<String,Object> params = Maps.newHashMap();
	
	private Map<String,File> files = Maps.newHashMap();

	public RequestParaConfig(String url) {
		this.url = url;
	}
	
	public RequestParaConfig(String url, Map<String, Object> params) {
		this.url = url;
		this.params = params;
	}
	
	public RequestParaConfig(String url, Map<String, Object> params, Map<String, File> files) {
		this.url = url;
		this.params = params;
		this.files = files;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Object> getParams() {
		// 合并参数
		params.putAll(files);
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public Map<String, File> getFiles() {
		return files;
	}

	public void setFiles(Map<String, File> files) {
		this.files = files;
	}
	
}

