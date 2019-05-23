##### 1. Hello World

```
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
```


##### 2. 用户管理示例

```
@RestController
@RequestMapping("/user")
public class UserController extends AoomsAbstractController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findList")
    public void findList(){
        userService.findList();
    };

    @RequestMapping("/insert")
    public void insert(){
        userService.insert();
    };

    @RequestMapping("/update")
    public void update(){
        userService.update();
    };

    @RequestMapping("/delete")
    public void delete(){
        userService.delete();
    };
}
```

```
@Service
public class UserService extends GenericService {

    @Autowired
    private Db db;

    public void findList() {
	this.setResultValue(AoomsVar.RS_DATA, db.findRecords("UserMapper.findList", SqlPara.SINGLETON));
    }

    @Transactional
    public void insert() {
	Record user = Record.empty().setByJsonKey("form");
        db.insert("t_user",user);
    }

    @Transactional
    public void update() {
    	Record user = Record.empty().setByJsonKey("form");
    	db.update("t_user",user);
    }

    @Transactional
    public void delete() {
    	db.deleteByPrimaryKey("t_user",getParaString("id"));
    }
	   
}
```

```
<mapper namespace="UserMapper">

    <!-- 二级缓存 -->
    <!--
    <cache type="net.aooms.core.module.mybatis.J2CacheSupport" eviction="LRU" flushInterval="60000" size="512" readOnly="true"/>
    -->

    <select id="findList" resultType="net.aooms.core.record.Record">
        SELECT t.* FROM T_USER t
    </select>

</mapper>
```
##### _收工！！！，更简单的模式，可以省略UserController， 通过内置CallServiceController统一调用_