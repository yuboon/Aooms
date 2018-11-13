package net.aooms.rbac.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.kisso.SSOAuthorization;
import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.enums.TokenOrigin;
import com.baomidou.kisso.security.token.SSOToken;
import com.baomidou.kisso.starter.KissoAutoConfiguration;
import net.aooms.core.Aooms;
import net.aooms.core.AoomsVar;
import net.aooms.core.authentication.AuthenticationInfo;
import net.aooms.core.authentication.LoginService;
import net.aooms.core.module.mybatis.Db;
import net.aooms.core.module.mybatis.SqlPara;
import net.aooms.core.record.Record;
import net.aooms.core.service.GenericService;
import net.aooms.core.util.PasswordHash;
import net.aooms.core.web.AoomsContext;
import net.aooms.rbac.mapper.RbacMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户登录
 * Created by 风象南(yuboon) on 2018-11-01
 */
@Service
public class RbacLoginService extends GenericService implements LoginService {

    @Autowired
    private Db db;

    @Transactional(readOnly = true)
    public AuthenticationInfo login(String username, String password) {
        Record record = db.findObject(RbacMapper.PKG.by("UserMapper.findByAccount"),SqlPara.fromDataBoss());
        if(record == null){
            return null;
        }

        String storePassword = record.getString("password");
        if(!PasswordHash.validatePassword(password,storePassword)){
            return null;
        }

        AuthenticationInfo authenticationInfo = new AuthenticationInfo();
        BeanUtil.fillBeanWithMap(record, authenticationInfo, true, true);
        return authenticationInfo;
	}

    @Override
    public boolean logout(AuthenticationInfo authenticationInfo) {
        if(authenticationInfo != null){
            logger.info("user {}({}) logout", authenticationInfo.getUserName(),authenticationInfo.getAccount());
        }
        return true;
    }

	/*public static void main(String[] args) {
        SSOConfig ssoConfig = new SSOConfig();
        ssoConfig.setSignkey("yuboon.signkey.random.123456");
        SSOConfig.init(ssoConfig);
        SSOToken token = SSOToken.parser("eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJhZG1pbiIsImlzcyI6ImFvb21zIiwib2ciOiIxIiwiaWF0IjoxNTQxNTc3MDkxfQ.9OidxYpaHbrGBARsUI9aBF7gyk5WccqU0FQSromRupSPt89U5su_86L_RJxfshAVYWeID0ibxGIZKBxiIR-41Q",true);
        System.err.println(token);
    }*/

}