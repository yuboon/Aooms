package net.aooms.rbac.controller;

import net.aooms.core.Aooms;
import net.aooms.core.web.AoomsAbstractController;
import net.aooms.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 登陆模拟
 * Created by 风象南(yuboon) on 2018-09-12
 */
@RestController
public class LoginController extends AoomsAbstractController {

    @Autowired
    private UserService userService;

    /**
     * 登陆
     * @return
     */
    @RequestMapping("/login")
    public void login(){
        setResultValue("uuid","admin").set("token", UUID.randomUUID().toString());
    };

}