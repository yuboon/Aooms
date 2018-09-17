package net.aooms.example.controller;

import net.aooms.core.web.AoomsAbstractController;
import net.aooms.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * simple crud
 * Created by 风象南(cheereebo) on 2018-09-12
 */
@RestController
public class CRUDController extends AoomsAbstractController {

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
    @RequestMapping("/insert")
    public void insert(){
        userService.insert();
    };

    /**
     * 修改
     * @return
     */
    @RequestMapping("/update")
    public void update(){
        userService.update();
    };

    /**
     * 删除
     * @return
     */
    @RequestMapping("/delete")
    public void delete(){
        userService.delete();
    };


}