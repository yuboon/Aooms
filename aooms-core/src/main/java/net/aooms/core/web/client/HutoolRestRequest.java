/*
package net.aooms.core.web.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sdp.core.dt.Dto;
import com.sdp.core.dt.DtoResult;
import com.sdp.soa.BsSoaCenter;
import com.sdp.utils.NetRequest;

import cn.hutool.http.HttpUtil;

*/
/**
 * web请求客户端
 * @author daasan
 *
 *//*

public class HutoolRestRequest {
	
	//slf4j.log
	private Logger log = LoggerFactory.getLogger(HutoolRestRequest.class);

	public String post(RequestParaConfig requestParaConfig) {
		String result = HttpUtil.post(requestParaConfig.getUrl(), requestParaConfig.getParams());
		return result;
	}
	
	public String get(RequestParaConfig requestParaConfig) {
		String result = HttpUtil.get(requestParaConfig.getUrl(), requestParaConfig.getParams());
		return result;
	}

	public DtoResult postRest(RequestParaConfig requestParaConfig) {
		DtoResult result = Dto.getResult();
		String serviceAddress = BsSoaCenter.discover(requestParaConfig.getUrl());
		if(null == serviceAddress){
			throw new RestRequestException("by key "+ requestParaConfig.getUrl() +" not found service!");
		}
		
		String json = HttpUtil.post(serviceAddress, requestParaConfig.getParams());
		log.info("RestRequst.Body = {}", json);
		if(null != json){
			result.setRsData(NetRequest.jsonStrToHashMap(json));
		}
		return result;
	}
	
}

*/
