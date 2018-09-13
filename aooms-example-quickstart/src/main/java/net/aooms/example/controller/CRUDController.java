package net.aooms.example.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import net.aooms.core.data.DataResult;
import net.aooms.core.module.mybatis.record.PagingRecord;
import net.aooms.core.module.mybatis.record.Record;
import net.aooms.core.property.PropertyObject;
import net.aooms.core.property.PropertyTest;
import net.aooms.core.web.AoomsAbstractController;
import net.aooms.core.web.annotation.ClearInterceptor;
import net.aooms.core.web.client.AoomsHttpTemplate;
import net.aooms.core.web.client.AoomsRestTemplate;
import net.aooms.core.web.interceptor.DemoInterceptor;
import net.aooms.core.web.interceptor.LoginInterceptor;
import net.aooms.example.service.UserService;
import net.aooms.example.vo.User;
import net.aooms.example.vo.UserVo;
import net.oschina.j2cache.CacheChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;
import java.util.Map;

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