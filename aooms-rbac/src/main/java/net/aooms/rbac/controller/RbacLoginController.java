package net.aooms.rbac.controller;

import cn.hutool.http.HttpStatus;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;
import net.aooms.core.Aooms;
import net.aooms.core.AoomsVar;
import net.aooms.core.authentication.AuthenticationInfo;
import net.aooms.core.authentication.LoginContext;
import net.aooms.core.authentication.SSOAuthentication;
import net.aooms.core.web.AoomsAbstractController;
import net.aooms.core.web.annotation.ClearInterceptor;
import net.aooms.core.web.interceptor.LoginInterceptor;
import net.aooms.core.web.interceptor.TokenInterceptor;
import net.aooms.rbac.service.RbacLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆
 * Created by 风象南(yuboon) on 2018-09-12
 */
@RestController
@RequestMapping(Aooms.WebContext + "/rbac/loginService")
public class RbacLoginController extends AoomsAbstractController {

    @Autowired
    private RbacLoginService rbacLoginService;

    /**
     * 登陆
     * @return
     */
    @RequestMapping("/login")
    @ClearInterceptor({TokenInterceptor.class,LoginInterceptor.class})
    public void login(){
        LoginContext loginContext = new LoginContext(rbacLoginService);
        AuthenticationInfo authenticationInfo = loginContext.login(getParaString("username"),getParaString("password"));
        if(authenticationInfo == null){
            this.getResult().failure(AoomsVar.NO_FOR_INT,"用户名或密码错误");
        }else{
            this.setResultValue(AoomsVar.RS_Authentication, authenticationInfo);
            SSOHelper.setCookie(getRequest(), getResponse(), SSOToken.parser(authenticationInfo.getToken(),false), false);
        }
    };

    /**
     * 注销
     * @return
     */
    @RequestMapping("/logout")
    @ClearInterceptor(LoginInterceptor.class)
    public void logout(){
        LoginContext loginContext = new LoginContext(rbacLoginService);
        loginContext.logout();
        SSOHelper.clearLogin(getRequest(),getResponse());
    };

}