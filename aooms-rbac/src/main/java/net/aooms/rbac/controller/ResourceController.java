package net.aooms.rbac.controller;

import net.aooms.core.Aooms;
import net.aooms.core.web.AoomsAbstractController;
import net.aooms.core.web.annotation.ClearInterceptor;
import net.aooms.core.web.interceptor.LoginInterceptor;
import net.aooms.rbac.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统资源管理
 * Created by 风象南(yuboon) on 2018-10-08
 */
@RestController
@RequestMapping(Aooms.WebContext + "/rbac/resourceService")
public class ResourceController extends AoomsAbstractController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 查询
     * @return
     */
    @RequestMapping("/findList")
    public void findList(){
        resourceService.findList();
    };

    /**
     * 树查询
     * @return
     */
    @ClearInterceptor(LoginInterceptor.class)
    @RequestMapping("/findTree")
    public void findTree(){
        resourceService.findTree();
    };

    /**
     * 新增
     * @return
     */
    @PostMapping("/insert")
    public void insert(){
        resourceService.insert();
    };

    /**
     * 修改
     * @return
     */
    @PostMapping("/update")
    public void update(){
        resourceService.update();
    };

    /**
     * 修改状态
     * @return
     */
    @PostMapping("/updateStatus")
    public void updateStatus(){
        resourceService.updateStatus();
    };

    /**
     * 删除
     * @return
     */
    @PostMapping("/delete")
    public void delete(){
        resourceService.delete();
    };


}