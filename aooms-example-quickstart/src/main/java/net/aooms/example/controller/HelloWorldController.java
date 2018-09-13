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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * HelloWorld
 * Created by 风象南(cheereebo) on 2018-09-12
 */
@RestController
public class HelloWorldController extends AoomsAbstractController {


    /**
     * 基础访问
     */
    @GetMapping("/hello")
    public void hello(){
        String str = "hello world !";
        this.renderText(str);
    };

    /**
     * 获取基本参数
     */
    @GetMapping("/hello2")
    public void hello2(){
        String id = getParaString("id");
        logger.info("id = {}" + id);
        this.renderText(id);
    };


    /**
     * 获取路径参数
     */
    @GetMapping("/hello/{id}")
    public void hello3(){
        String id = getPathString("id");
        logger.info("id = {}" + id);
        this.renderText(id);
    };

    /**
     * 上传文件
     */
    @PostMapping("/hello4")
    public void hello4(){
        MultipartFile multipartFile = this.getParaFile("upload");
        logger.info("fileName = {}" + multipartFile.getName());
        this.renderText("success");
    };

    /**
     * json输出
     */
    @RequestMapping("/hello5")
    public void hello5(){
        UserVo userVo = new UserVo();
        userVo.setName("zhangsan");
        setResultValue("userVo",userVo);
        this.renderJson();
    };

    /**
     * json输出
     */
    @RequestMapping("/hello6")
    public void hello6(){
        UserVo userVo = new UserVo();
        userVo.setName("zhangsan");
        this.renderJson(userVo);
    };

    /**
     * 文件下载
     */
    @GetMapping("/hello7")
    public void hello7(){
        this.renderFile("测试文件", this.getClass().getResourceAsStream("application.yml"));
    };

    /**
     * 图片输出
     * @return
     */
    @PostMapping("/hello8")
    public void hello8(){
        this.renderImage("F:/1.png","F:/default.png");
    };

    /**
     * html输出
     * @return
     */
    @PostMapping("/hello9")
    public void hello9(){
        this.renderHtml("<html><h1>标题</h1> <script>alert('hello world !');</script> </html>");
    };

    /**
     * 模版页面输出
     * @return
     */
    @PostMapping("/hello10")
    public void hello10(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("name","lisi");
        mv.setViewName("/demo.html");
        this.renderThymeleaf(mv);
    };

    /**
     * 重定向
     * @return
     */
    @PostMapping("/hello11")
    public void hello11(){
        this.redirect("https://www.oschina.net");
    };

}