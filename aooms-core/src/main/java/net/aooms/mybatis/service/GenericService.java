package net.aooms.mybatis.service;

import net.aooms.core.data.DataBoss;
import net.aooms.core.data.DataPara;
import net.aooms.core.data.DataResult;
import net.aooms.mybatis.SqlPara;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class GenericService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 以DataBoss.DataPara为参数创建SqlPara对象
	 * @return
	 */
	public SqlPara sqlParaFromDataBoss() {
		return SqlPara.fromDataBoss();
	}

	/**
	 * 以DataBoss.DataPara为参数创建SqlPara分页对象
	 * @return
	 */
	public SqlPara sqlParaPagingFromDataBoss() {
		return SqlPara.fromDataBoss().paging(getPara().getPage(),getPara().getLimit());
	}

	/**
	 * 获取参数
	 * @return
	 */
	public DataPara getPara() {
		return DataBoss.get().getPara();
	}

	/**
	 * 获取响应
	 * @return
	 */
	public DataResult getResult(){
		return DataBoss.get().getResult();
	}


	/**
	 * 获取参数
	 * @return
	 */
	public String getParaString(String key) {
		return getPara().getString(key);
	}

	/**
	 * 获取参数
	 * @return
	 */
	public Integer getParaInteger(String key) {
		return getPara().getInteger(key);
	}

	/**
	 * 获取参数
	 * @return
	 */
	public String getPathString(String key) {
		return getPara().getPathVar(key);
	}

	/**
	 * 获取参数
	 * @return
	 */
	public Integer getPathInteger(String key) {
		return getPara().getPathVar(key);
	}

	/**
	 * 获取参数
	 * @return
	 */
	public MultipartFile getParaFile(String uploadName) {
		return getPara().getFile(uploadName);
	}

	/**
	 * 获取参数
	 * @return
	 */
	public InputStream getParaInputStream(String uploadName) {
		return getPara().getFileInputStream(uploadName);
	}

	/**
	 * 获取参数
	 * @ret*/
	public Map<String,MultipartFile> getParaFiles() {
		return getPara().getFiles();
	}

	/**
	 * 设置响应值
	 * @return
	 */
	public DataResult setResultValue(String key,Object value){
		return getResult().set(key,value);
	}
}