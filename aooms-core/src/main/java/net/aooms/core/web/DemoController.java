package net.aooms.core.web;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import net.aooms.core.annotation.ClearInterceptor;
import net.aooms.core.property.TestProperty;
import net.aooms.core.web.interceptor.DemoInterceptor;
import net.aooms.mybatis.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 抽象控制器类
 * Created by cccyb on 2018-02-06
 */
@RestController
public class DemoController extends AoomsAbstractController {

    private static Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private TestProperty testProperty;


    @Autowired
    private UserService userService;

    /**
     * 获取参数
     * @return
     */
    @RequestMapping("/hello/{code}")
    @ClearInterceptor(DemoInterceptor.class)
    public void hello(){
        logger.info("NAME = {}" ,testProperty.getName());

        logger.info("ID = {}" ,this.getParaString("id"));
        String s = this.getPara().getPathVar("code");
        logger.info("CODE2 = {}" , s);
       /* this.setResultValue("ids","23123")
                .set("name","张三")
                .set("id","1235")
                .set("datetime", DateUtil.now())
                .set("datet", DateUtil.formatDate(new Date()));*/

        userService.selectMap();
        this.setResultValue("idsd2","");

        getResult().printCaller();

        this.renderJson();
    };

    /**
     * 返回视图
     * @return
     */
    @GetMapping("/hello2")
    @ClearInterceptor({DemoInterceptor.class})
    public ModelAndView hello2(){
        logger.info("NAME = {}" ,testProperty.getName());
        this.getResult().set("name","byHello2");
        return new ModelAndView("/demo.html",this.getResult().getData());
    };

    /**
     * 上传文件
     * @return
     */
    @PostMapping("/upload")
    public void upload(){
        logger.info("ID = {}" ,this.getPara().getString("id"));
        logger.info("SIZE = {}" ,this.getPara().getFiles().size());
        logger.info("FILENAME FROM NAME= {}" ,this.getPara().getFile("my"));
        System.err.println(this.getPara().getFileInputStream("my"));
        this.renderJson();
    };

    /**
     * 下载文件
     * @return
     */
    @GetMapping("/down")
    public void down(){
        logger.info("NAME = {}" ,testProperty.getName());
        this.getResult().set("name","byHello2");
        this.renderFile("中文.txt",new File("F:/1.txt"));
    };

    /**
     * html
     * @return
     */
    @GetMapping("/html")
    public void html(){
        this.renderHtml("<h1>这是标题</h1><script>alert(123);</script>");
    };

    /**
     * html
     * @return
     */
    @GetMapping("/js")
    public void js(){
        this.renderJavaScript("<script>alert('奥迪功能');</script>");
    };

    /**
     * html
     * @return
     */
    @GetMapping("/text")
    public void text(){
        this.renderText("<h1>这是标题</h1><script>alert('奥迪功能');</script>");
    };

    /**
     * 图片
     * @return
     */
    @GetMapping("/image")
    public void image(){
        this.renderImage("F:/rpt.png","F:/df.jpg");
    };

    /**
     * 页面
     * @return
     */
    @GetMapping("/page")
    public void page(){
        System.err.println("email:" + testProperty.getName());

       // System.err.println(JSON.toJSONString(recordMapper.insert(new Record())));

        //System.err.println(JSON.toJSONString(map));
        Map<String,Object> values = Maps.newHashMap();
        values.put("name","admin");
        this.renderThymeleaf("/demo.html",values);
       //this.renderJson();
    };


    /**
     * 重定向
     * @return
     */
    @GetMapping("/send")
    public void send(){
        this.redirect("/demo.html");
    };
}