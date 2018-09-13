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
import net.aooms.example.vo.User;
import net.aooms.example.vo.UserVo;
import net.aooms.example.service.UserService;
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
 * Demo
 * Created by cccyb on 2018-02-06
 */
@RestController
public class OtherController extends AoomsAbstractController {

    @Autowired
    private PropertyTest testProperty;

    @Autowired
    private UserService userService;

    @Autowired
    private CacheChannel cacheChannel;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AoomsRestTemplate aoomsRestTemplate;

    @Autowired
    private AoomsHttpTemplate aoomsHttpTemplate;


    /**
     * 登陆
     * @return
     */
    @RequestMapping("/login")
    @ClearInterceptor({LoginInterceptor.class})
    @HystrixCommand
    public void login(){
        // mybatis缓存      X
        // 事务、分布式事务
        // 多数据源、分库分表
        // 服务注册发现
        // ID生成

        //String body =  aoomsHttpTemplate.get("https://www.baidu.com");
        //System.err.println("body = " + body);
        DataResult dataResult = aoomsRestTemplate.get("http://AOOMS/hello/123");
        List<UserVo> users = dataResult.getBeanList("list",UserVo.class);
        PagingRecord pagingRecord = dataResult.getPagingRecord("pgRecord");
        String id = dataResult.getValue("id",String.class);

        System.err.println("id:" + id);
        System.err.println("pagingRecord:" + JSON.toJSONString(pagingRecord));
        System.err.println("users:" + JSON.toJSONString(users));
        System.err.println("result = " + JSON.toJSONString(dataResult.getData(), SerializerFeature.WriteMapNullValue));

        //ConsulClient client = new ConsulClient("127.0.0.1",8500);
        //System.err.println("kv = " + client.getKVValue("name"));


        String cookieName = PropertyObject.getInstance().getKissoProperty().getConfig().getCookieName();
        System.err.println(Thread.currentThread().getName() + " cookieName:" + cookieName);

        System.err.println("testProperty:" +testProperty.getName());
        setResultValue("names","正常的");
        // 耗时加密算法
        //System.out.println(PasswordUtil.createHash("admin"));


        //String cookieName = PropertyObject.getInstance().getKissoProperty().getConfig().getCookieName();
        //System.err.println(Thread.currentThread().getName() + " cookieName:" + cookieName);

        userService.query();
       // userService.master();

        //GenericDao genericDao = AoomsModule.getInstance().getGenericDao();

       // genericDao.findObject("Demo.selectListBySQL", SqlPara.SINGLETON);

        //genericDao.findObject("Demo.selectListBySQL", SqlPara.SINGLETON);
        //genericDao.findObject("Demo.selectListBySQL", SqlPara.SINGLETON);

       /* genericDao.findByPrimaryKey("user", "1534904693057-0");
        Record record = genericDao.findByPrimaryKey("user","1534904693057-0");
        System.err.println(JSON.toJSONString(record));

        Record record2 = genericDao.findByPrimaryKey("user","1534904693057-0");*/

        SSOHelper.setCookie(getRequest(), getResponse(), SSOToken.create().setIp(getRequest()).setId("放用户ID").setIssuer("kisso"), false);
        SSOHelper.clearLogin(getRequest(), getResponse());

        /*// 设置登录 COOKIE
        SSOHelper.setCookie(getRequest(), getResponse(), SSOToken.create().setIp(getRequest()).setId("放用户ID").setIssuer("kisso"), false);
        SSOHelper.clearLogin(getRequest(), getResponse());

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
    @ClearInterceptor({LoginInterceptor.class})
    public void hello(){

        logger.info("ID = {}" ,this.getParaString("id"));
        String s = this.getPara().getPathVar("code");
        logger.info("CODE2 = {}" , s);
       /* this.setResultValue("ids","23123")
                .set("name","张三")
                .set("id","1235")
                .set("datetime", DateUtil.now())
                .set("datet", DateUtil.formatDate(new Date()));*/

        List<User> users = Lists.newArrayList();
        User u = new User();
        u.setName("wangwu");
        u.set("name","wangwu");
        users.add(u);
        this.setResultValue("list",users);
        this.setResultValue("id","zhangsan");

        List<User> records = Lists.newArrayList();
        Record r = new Record();
        r.set("id","recordid");
        records.add(u);

        PagingRecord pgr = new PagingRecord(1,2,  records,100,true);
        this.setResultValue("pgRecord",pgr);


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
        return new ModelAndView("/example.html",this.getResult().getData());
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
    @ClearInterceptor(LoginInterceptor.class)
    public void page(){

       // System.err.println(JSON.toJSONString(recordMapper.insert(new Record())));

        //System.err.println(JSON.toJSONString(map));
        Map<String,Object> values = Maps.newHashMap();
        values.put("name","admin");
        this.renderThymeleaf("/example.html",values);
       //this.renderJson();
    };


    /**
     * 重定向
     * @return
     */
    @GetMapping("/send")
    public void send(){
        this.redirect("/example.html");
    };
}