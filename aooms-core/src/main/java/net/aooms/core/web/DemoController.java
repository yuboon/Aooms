package net.aooms.core.web;

import cn.hutool.core.date.DateUtil;
import net.aooms.core.dto.DTO;
import net.aooms.core.dto.DTOPara;
import net.aooms.core.dto.DTORet;
import net.aooms.core.exception.AoomsExceptions;
import net.aooms.core.properties.TestProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 抽象控制器类
 * Created by cccyb on 2018-02-06
 */
@RestController
public class DemoController extends AoomsAbstractController {

    private static Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private TestProperties testProperties;

    /**
     * 获取参数
     * @return
     */
    @GetMapping("/hello")
    public void hello(){
        logger.info("NAME = {}" ,testProperties.getName());

        logger.info("ID = {}" ,this.getPara().getStr("id"));

        this.getRet()
                .set("name","张三")
                .set("id","1235")
                .set("datetime", DateUtil.now())
                .set("datet", DateUtil.formatDate(new Date()));

        this.renderJson();
    };

    /**
     * 返回视图
     * @return
     */
    @GetMapping("/hello2")
    public ModelAndView hello2(){
        logger.info("NAME = {}" ,testProperties.getName());
        this.getRet().set("name","byHello2");
        return new ModelAndView("/demo.html",this.getRet().getData());
    };

    /**
     * 上传文件
     * @return
     */
    @PostMapping("/upload")
    public void upload(){
        logger.info("ID = {}" ,this.getPara().getStr("id"));
        this.renderJson();
    };

    /**
     * 下载文件
     * @return
     */
    @GetMapping("/down")
    public void down(){
        logger.info("NAME = {}" ,testProperties.getName());
        this.getRet().set("name","byHello2");
        this.renderJson();
    };



    /**
     * 图片
     * @return
     */
    @GetMapping("/image")
    public void image(){
        logger.info("NAME = {}" ,testProperties.getName());
        this.getRet().set("name","byHello2");
        this.renderJson();
    };
}