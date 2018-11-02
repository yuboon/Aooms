package net.aooms.rbac.controller;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;
import net.aooms.core.Aooms;
import net.aooms.core.web.AoomsAbstractController;
import net.aooms.core.web.annotation.ClearInterceptor;
import net.aooms.core.web.interceptor.LoginInterceptor;
import net.aooms.rbac.service.LoginService;
import net.aooms.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 登陆
 * Created by 风象南(yuboon) on 2018-09-12
 */
@RestController
@RequestMapping(Aooms.WebContext + "/rbac")
@ClearInterceptor(LoginInterceptor.class)
public class LoginController extends AoomsAbstractController {

    @Autowired
    private LoginService loginService;

    /**
     * 登陆
     * @return
     */
    @RequestMapping("/login")
    public void login(){
        loginService.validateAccount();
        //setResultValue("uuid","admin").set("token", UUID.randomUUID().toString());
        //this.setResultValue("");
    };

}