package net.aooms.core.web.interceptor;


import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.kisso.common.IpHelper;
import net.aooms.core.Aooms;
import net.aooms.core.AoomsVar;
import net.aooms.core.authentication.AuthenticationInfo;
import net.aooms.core.authentication.SSOAuthentication;
import net.aooms.core.databoss.DataResult;
import net.aooms.core.exception.AoomsExceptions;
import net.aooms.core.id.IDGenerator;
import net.aooms.core.record.Record;
import net.aooms.core.service.DbLogService;
import net.aooms.core.util.SpringUtils;
import net.aooms.core.web.AoomsContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 日志拦截器
 * Created by 风象南(yuboon) on 2018-11-18
 */
public class DbLogInterceptor extends AoomsAbstractInterceptor {

    private static final String DB_LOGGER = "dbLogger";

    public DbLogInterceptor(String[] pathPatterns, String[] ignores) {
        super(pathPatterns, ignores);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        boolean logEnable = Aooms.self().getPropertyObject().getAoomsProperty().getGlobal().isLogEnable();
        if(logEnable){
            AuthenticationInfo authenticationInfo = SSOAuthentication.getAuthenticationInfo();
            if(authenticationInfo != null) {
                Record record = new Record();
                record.set(AoomsVar.ID, IDGenerator.getStringValue());
                record.set("user_id", authenticationInfo.getId());
                record.set("user_account", authenticationInfo.getAccount());
                record.set("user_name", authenticationInfo.getUserName());
                record.set("create_time", DateUtil.now());
                record.set("ip_address", IpHelper.getIpAddr(AoomsContext.getRequest()));
                record.set("status", "0");  //0 - 正常  1 - 错误日志
                record.set("resource_url", AoomsContext.getRequest().getRequestURI());
                record.set("cost", System.currentTimeMillis());
                request.setAttribute(DB_LOGGER ,record);
            }
        }
        return  true;
    }

    @Override
    public void finalHandle(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Record record = (Record) request.getAttribute(DB_LOGGER);
        if(record != null){
            DbLogService dbLogService = (DbLogService)SpringUtils.getApplicationContext().getBean("dbLogService");
            if(ex != null){
                StringWriter stackTrace = new StringWriter();
                ex.printStackTrace(new PrintWriter(stackTrace));
                stackTrace.flush();
                record.set("status", "1");
                record.set("trace", stackTrace.toString());
            }

            long start = record.getLong("cost");
            record.set("cost", (System.currentTimeMillis() - start) / 1000); // 单位：s
            dbLogService.saveLog(record);
        }
    }
}