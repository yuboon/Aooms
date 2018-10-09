package net.aooms.example.controller;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;
import net.aooms.core.web.AoomsAbstractController;
import net.aooms.core.web.client.AoomsHttpTemplate;
import net.aooms.core.web.client.AoomsRestTemplate;
import net.aooms.example.service.UserService;
import net.oschina.j2cache.CacheChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * backup
 * Created by 风象南(cheereebo) on 2018-09-18
 */
//@RestController
public class BackupController extends AoomsAbstractController {

    @Autowired
    private UserService userService;

    @Autowired
    private CacheChannel cacheChannel;

    @Autowired
    private AoomsRestTemplate aoomsRestTemplate;

    @Autowired
    private AoomsHttpTemplate aoomsHttpTemplate;

    /**
     * 登陆
     * @return
     */
    @RequestMapping("/login")
    public void login(){
        SSOHelper.setCookie(getRequest(), getResponse(), SSOToken.create().setIp(getRequest()).setId("放用户ID").setIssuer("kisso"), false);
    };

    /**
     * 首页
     * @return
     */
    @RequestMapping("/index")
    public void index(){
        String msg = "暂未登录";
        SSOToken ssoToken = SSOHelper.attrToken(getRequest());
        System.err.println("$name:" + getParaString("$name"));
        if (null != ssoToken) {
            msg = "登录信息 ip=" + ssoToken.getIp();
            msg += "， id=" + ssoToken.getId();
            msg += "， issuer=" + ssoToken.getIssuer();
            msg += "， token=" + ssoToken.getToken();
        }
        this.renderText(msg);
    };

    /**
     * 注销
     * @return
     */
    @RequestMapping("/logout")
    public void logout(){
        SSOHelper.clearLogin(getRequest(),getResponse());
    };


}