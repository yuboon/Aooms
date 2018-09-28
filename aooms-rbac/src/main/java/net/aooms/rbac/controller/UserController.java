package net.aooms.rbac.controller;

import net.aooms.core.Aooms;
import net.aooms.core.web.AoomsAbstractController;
import net.aooms.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理
 * Created by 风象南(cheereebo) on 2018-09-12
 */
@RestController
@RequestMapping(Aooms.WebContext + "/rbac/user")
public class UserController extends AoomsAbstractController {

    @Autowired
    private UserService userService;

    /**
     * 查询
     * @return
     */
    @RequestMapping("/findList")
    public void findList(){
        userService.findList();
    };

    /**
     * 新增
     * @return
     */
    @PostMapping("/insert")
    public void insert(){
        userService.insert();
    };

    /**
     * 修改
     * @return
     */
    @PostMapping("/update")
    public void update(){
        userService.update();
    };

    /**
     * 删除
     * @return
     */
    @PostMapping("/delete")
    public void delete(){
        userService.delete();
    };


}