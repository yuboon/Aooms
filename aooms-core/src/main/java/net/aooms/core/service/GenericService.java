package net.aooms.core.service;

import net.aooms.core.data.DataBoss;
import net.aooms.core.data.DataPara;
import net.aooms.core.data.DataResult;
import net.aooms.core.module.mybatis.MappedStatementId;
import net.aooms.core.module.mybatis.MapperPackage;
import net.aooms.core.module.mybatis.SqlPara;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;

/**
 * 公共Service
 * Created by 风象南(cheereebo) on 2018/9/7
 */
public class GenericService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public <T> T proxy(Class<T> obj){
		return ((T) AopContext.currentProxy());
	}

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
		return DataBoss.self().getPara();
	}

	/**
	 * 获取响应
	 * @return
	 */
	public DataResult getResult(){
		return DataBoss.self().getResult();
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

	/**
	 * 获取statementId
	 */
	public String getStatementId(Class<? extends MapperPackage> mapperPackageClass, String method){
		return MappedStatementId.getStatementId(mapperPackageClass,method);
	}
}