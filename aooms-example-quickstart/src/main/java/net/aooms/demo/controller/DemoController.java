package net.aooms.demo.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;
import com.google.common.collect.Maps;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import net.aooms.core.module.AoomsModule;
import net.aooms.core.module.mybatis.dao.GenericDao;
import net.aooms.core.module.mybatis.record.Record;
import net.aooms.core.property.PropertyObject;
import net.aooms.core.property.PropertyTest;
import net.aooms.core.web.AoomsAbstractController;
import net.aooms.core.web.annotation.ClearInterceptor;
import net.aooms.core.web.interceptor.DemoInterceptor;
import net.aooms.core.web.interceptor.KissoLoginInterceptor;
import net.aooms.demo.service.UserService;
import net.oschina.j2cache.CacheChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.beans.Transient;
import java.io.File;
import java.util.Map;

/**
 * 抽象控制器类
 * Created by cccyb on 2018-02-06
 */
@RestController
public class DemoController extends AoomsAbstractController {

    @Autowired
    private PropertyTest testProperty;

    @Autowired
    private UserService userService;

    @Autowired
    private CacheChannel cacheChannel;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 登陆
     * @return
     */
    @RequestMapping("/login")
    @ClearInterceptor({KissoLoginInterceptor.class})
    @HystrixCommand
    public void login(){
        // mybatis缓存
        // 事务、分布式事务
        // 分库分表
        // 服务注册发现
        // ID生成

        String cookieName = PropertyObject.getInstance().getKissoProperty().getConfig().getCookieName();
        System.err.println(Thread.currentThread().getName() + " cookieName:" + cookieName);

        System.err.println("testProperty:" +testProperty.getName());
        setResultValue("names","正常的");
        // 耗时加密算法
        //System.out.println(PasswordUtil.createHash("admin"));


        //String cookieName = PropertyObject.getInstance().getKissoProperty().getConfig().getCookieName();
        //System.err.println(Thread.currentThread().getName() + " cookieName:" + cookieName);

        userService.selectMap();

        //GenericDao genericDao = AoomsModule.getInstance().getGenericDao();

       // genericDao.findObject("Demo.selectListBySQL", SqlPara.SINGLETON);

        //genericDao.findObject("Demo.selectListBySQL", SqlPara.SINGLETON);
        //genericDao.findObject("Demo.selectListBySQL", SqlPara.SINGLETON);

       /* genericDao.findByPrimaryKey("user", "1534904693057-0");
        Record record = genericDao.findByPrimaryKey("user","1534904693057-0");
        System.err.println(JSON.toJSONString(record));

        Record record2 = genericDao.findByPrimaryKey("user","1534904693057-0");*/


        /*// 设置登录 COOKIE
        SSOHelper.setCookie(getRequest(), getResponse(), SSOToken.create().setIp(getRequest()).setId("放用户ID").setIssuer("kisso"), false);
        //SSOHelper.clearLogin(getRequest(), getResponse());

        GenericDao genericDao = AoomsModule.getInstance().getGenericDao();
        genericDao.findObject("Demo.selectListBySQL", SqlPara.SINGLETON);

        CacheChannel cacheChannel = AoomsModule.getInstance().getJ2Cache();
        CacheObject cacheObject = cacheChannel.get("testRegion2","name");
        if(cacheObject.getValue() != null){
            Object cacheValue = cacheObject.getValue();
            System.err.println("cacheValue:" + cacheValue);
            setResultValue("cacheValue",cacheValue);
            cacheChannel.set("testRegion2","name","23");
            setResultValue("isNew",false);
        }else{
            cacheChannel.set("testRegion2","name","23");
            setResultValue("cacheValue","");
            setResultValue("isNew",true);
        }*/

        this.renderJson();
    };

    /**
     * 登陆
     * @return
     */
    @RequestMapping("/logout")
    public void logout(){
        SSOHelper.clearLogin(getRequest(), getResponse());
        this.renderJson();
    }

    /**
     * 首页
     * @return
     */
    @RequestMapping("/index")
    public void index(){

        String msg = "暂未登录";
        SSOToken ssoToken = SSOHelper.attrToken(getRequest());
        System.err.println("$name:" + getParaString("$name"));
        if (null != ssoToken) {
            msg = "登录信息 ip=" + ssoToken.getIp();
            msg += "， id=" + ssoToken.getId();
            msg += "， issuer=" + ssoToken.getIssuer();
            msg += "， token=" + ssoToken.getToken();


        }

        this.renderText(msg);
    };

    /**
     * 获取参数
     * @return
     */
    @RequestMapping("/hello/{code}")
    @ClearInterceptor(DemoInterceptor.class)
    public void hello(){

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

        this.renderJson();
    };

    /**
     * 返回视图
     * @return
     */
    @GetMapping("/hello2")
    @ClearInterceptor({DemoInterceptor.class})
    public ModelAndView hello2(){
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
    @ClearInterceptor(KissoLoginInterceptor.class)
    public void page(){

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