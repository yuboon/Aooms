package net.aooms.rbac.controller;

import net.aooms.core.Aooms;
import net.aooms.core.web.AoomsAbstractController;
import net.aooms.rbac.service.RoleService;
import net.aooms.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色管理
 * Created by 风象南(yuboon) on 2018-09-12
 */
@RestController
@RequestMapping(Aooms.WebContext + "/rbac/role")
public class RoleController extends AoomsAbstractController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询
     * @return
     */
    @RequestMapping("/findList")
    public void findList(){
        roleService.findList();
    };

    /**
     * 新增
     * @return
     */
    @PostMapping("/insert")
    public void insert(){
        roleService.insert();
    };

    /**
     * 修改
     * @return
     */
    @PostMapping("/update")
    public void update(){
        roleService.update();
    };

    /**
     * 修改状态
     * @return
     */
    @PostMapping("/updateStatus")
    public void updateStatus(){
        roleService.updateStatus();
    };

    /**
     * 删除
     * @return
     */
    @PostMapping("/delete")
    public void delete(){
        roleService.delete();
    };


}