package net.aooms.rbac.controller;

import net.aooms.core.Aooms;
import net.aooms.core.web.AoomsAbstractController;
import net.aooms.rbac.service.OrgService;
import net.aooms.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 机构管理
 * Created by 风象南(yuboon) on 2018-10-08
 */
@RestController
@RequestMapping(Aooms.WebContext + "/rbac/org")
public class OrgController extends AoomsAbstractController {

    @Autowired
    private OrgService orgService;

    /**
     * 查询
     * @return
     */
    @RequestMapping("/findList")
    public void findList(){
        orgService.findList();
    }

    /**
     * 树查询
     * @return
     */
    @RequestMapping("/findTree")
    public void findTree(){
        orgService.findTree();
    };

    /**
     * 新增
     * @return
     */
    @PostMapping("/insert")
    public void insert(){
        orgService.insert();
    };

    /**
     * 修改
     * @return
     */
    @PostMapping("/update")
    public void update(){
        orgService.update();
    };

    /**
     * 测试事务
     * @return
     */
    @RequestMapping("/test")
    public void test(){
        orgService.test();
    };

    /**
     * 修改状态
     * @return
     */
    @PostMapping("/updateStatus")
    public void updateStatus(){
        orgService.updateStatus();
    };

    /**
     * 删除
     * @return
     */
    @PostMapping("/delete")
    public void delete(){
        orgService.delete();
    };


}