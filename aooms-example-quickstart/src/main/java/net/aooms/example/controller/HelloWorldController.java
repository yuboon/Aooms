package net.aooms.example.controller;

import net.aooms.core.web.AoomsAbstractController;
import net.aooms.example.vo.UserVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * HelloWorld
 * Created by 风象南(cheereebo) on 2018-09-12
 */
@RestController
public class HelloWorldController extends AoomsAbstractController {

    /**
     * 基础访问
     */
    @RequestMapping("/hello")
    public void hello(){
        String str = "hello world !";
        this.renderText(str);
    };

    /**
     * 获取基本参数
     */
    @RequestMapping("/hello2")
    public void hello2(){
        String id = getParaString("id");
        logger.info("id = {}" , id);
        this.renderText(id);
    };


    /**
     * 获取路径参数
     */
    @RequestMapping("/hello/{id}")
    public void hello3(){
        String id = getPathString("id");
        logger.info("id = {}" , id);
        this.renderText(id);
    };

    /**
     * 上传文件
     */
    @RequestMapping("/hello4")
    public void hello4(){
        MultipartFile multipartFile = this.getParaFile("upload");
        logger.info("fileName = {}", multipartFile.getName());
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

        // 输出json
        this.renderJson();
        // this.renderJson(); 也可省略不写，默认会使用JSONRender
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
    @RequestMapping("/hello7")
    public void hello7(){
        this.renderFile("application.yml", this.getClass().getResourceAsStream("/application.yml"));
    };

    /**
     * 图片输出
     * @return
     */
    @RequestMapping("/hello8")
    public void hello8(){
        this.renderImage("F:/1.png","F:/default.png");
    };

    /**
     * html输出
     * @return
     */
    @RequestMapping("/hello9")
    public void hello9(){
        this.renderHtml("<html><h1>标题</h1> <script>alert('hello world !');</script> </html>");
    };

    /**
     * 模版页面输出
     * @return
     */
    @RequestMapping("/hello10")
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
    @GetMapping("/hello11")
    public void hello11(){
        this.redirect("https://www.oschina.net");
    };

}