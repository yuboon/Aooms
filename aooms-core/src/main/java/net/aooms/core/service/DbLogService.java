package net.aooms.core.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.common.IpHelper;
import net.aooms.core.AoomsVar;
import net.aooms.core.authentication.AuthenticationInfo;
import net.aooms.core.authentication.SSOAuthentication;
import net.aooms.core.databoss.DataBoss;
import net.aooms.core.databoss.DataPara;
import net.aooms.core.databoss.DataResult;
import net.aooms.core.id.IDGenerator;
import net.aooms.core.module.mybatis.Db;
import net.aooms.core.module.mybatis.MappedStatementId;
import net.aooms.core.module.mybatis.MapperPackage;
import net.aooms.core.module.mybatis.SqlPara;
import net.aooms.core.record.Record;
import net.aooms.core.web.AoomsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 日志Service
 * Created by 风象南(yuboon) on 2018/9/7
 */
@Service
public class DbLogService extends GenericService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Db db;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void saveLog(Record record){
		db.insert("aooms_admin_log",record);
	}

}