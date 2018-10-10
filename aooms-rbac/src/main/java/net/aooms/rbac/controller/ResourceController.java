package net.aooms.rbac.controller;

import net.aooms.core.Aooms;
import net.aooms.core.web.AoomsAbstractController;
import net.aooms.rbac.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统资源管理
 * Created by 风象南(cheereebo) on 2018-10-08
 */
@RestController
@RequestMapping(Aooms.WebContext + "/rbac/resource")
public class ResourceController extends AoomsAbstractController {

    @Autowired
    private ResourceService moduleService;

    /**
     * 查询
     * @return
     */
    @RequestMapping("/findList")
    public void findList(){
        moduleService.findList();
    };

    /**
     * 树查询
     * @return
     */
    @RequestMapping("/findTree")
    public void findTree(){
        moduleService.findTree();
    };

    /**
     * 新增
     * @return
     */
    @PostMapping("/insert")
    public void insert(){
        moduleService.insert();
    };

    /**
     * 修改
     * @return
     */
    @PostMapping("/update")
    public void update(){
        moduleService.update();
    };

    /**
     * 修改状态
     * @return
     */
    @PostMapping("/updateStatus")
    public void updateStatus(){
        moduleService.updateStatus();
    };

    /**
     * 删除
     * @return
     */
    @PostMapping("/delete")
    public void delete(){
        moduleService.delete();
    };


}