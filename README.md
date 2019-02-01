![输入图片说明](https://images.gitee.com/uploads/images/2018/1116/124406_5d5068e6_385692.png "all (1).png")

### Aooms —— 基于SpringCloud的微服务基础开发平台
_极速微服务开发，不止像JFinal一样简单_ 

#### 一、介绍
&nbsp;&nbsp;&nbsp;&nbsp;一款基于SpringCloud的微服务基础开发平台，旨在降低SpringCloud的复杂度，像使用JFinal一样简单，但又包含整体解决方案（本人是JFinal用户，从1.9版本开始现在也一直在使用，因此部分实现思路会借鉴JFinal的一些模式，感谢@JFinal作者波总提供这么优秀的框架），包含微服务相关的完整解决方案同时附加有权限管理、报表自定义、工作流、Cms等套件，可直接使用，Aooms基于Apache Licence 2.0开源协议，关于编写此框架的一些初衷，可通过此处[诞生](https://gitee.com/cyb-javaer/Aooms/wikis/pages)了解。

---
##### 演示地址：https://www.yuboon.com/Aooms _服务器配置有限，请勿压测 X 3，重要的事情说三遍 :smiley:  :smiley:  :smiley: ！！！
##### 文档地址：待完善
##### github地址：https://github.com/yuboon/Aooms
---

#### 二、核心功能
- （1）极简Controller
- （2）基于sharding-sphere的多数据源支持
- （3）基于Mybatis 实现的 Db + Record 极简模式，附带物理分页实现
- （4）基于Consul的服务注册、发现
- （5）服务熔断、限流、降级
- （6）服务客户端、http客户端
- （7）内置各种ID生成器（UUID、snowflake）
- （8）穿透一切的数据对象DataBoss
- （9）基于J2Cache的缓存
- （10） 其他更多功能，等你发现.......

#### 二、内置集成系统
- （1）权限管理 （实现中，基本完成）
- （2）内容管理系统（规划中）
- （3）报表系统（规划中）
- （4）工作流系统（规划中）
- （5）微信公众号（规划中）
- （6）..............

#### 三、界面预览
![输入图片说明](https://images.gitee.com/uploads/images/2018/1116/130232_92a39175_385692.png "截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/1116/130245_916e85f4_385692.png "截图 (1).png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/1116/130317_dc660ad7_385692.png "截图 (2).png")

##### _界面基于D2admin实现，感谢D2admin团队_
<a href="https://github.com/d2-projects/d2-admin" target="_blank"><img src="https://raw.githubusercontent.com/FairyEver/d2-admin/master/doc/image/d2-admin@2x.png" width="200"></a>


#### 四、简单Demo
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

#### 五、相关博文链接
https://my.oschina.net/cccyb?tab=newest&catalogId=5736543
持续关注，不定期会有更新

#### 六、框架的一点声明
&nbsp;&nbsp;&nbsp;&nbsp;关于框架的一点声明，框架目前处于开发阶段，会不定期更新码云上的代码同时会有系统博客同步更新，另外此项目是带有学习性质的摸索、尝试，是为了给想学习微服务的人一个学习上的帮助，大家一起学习、探讨，感受一个微服务开发平台从0到诞生的过程，因为可能有的人想学但没有方向又或者所在公司技术体系比较老，不具备微服务的学习环境，所以构建了该工程，希望能帮到一些人同时对我自己也是一次锻炼，供大家完整的参考。
![输入图片说明](https://images.gitee.com/uploads/images/2019/0201/090347_0d1f9d70_385692.jpeg "qrcode_for_gh_f0f975381d4e_258.jpg")
