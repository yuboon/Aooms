![输入图片说明](https://gitee.com/uploads/images/2018/0205/163124_fabb62a2_385692.png "屏幕截图.png")

# Aooms —— 基于SpringCloud的微服务基础开发平台
  _极速微服务开发，像JFinal一样简单_ 

## 一、介绍
&nbsp;&nbsp;&nbsp;&nbsp;一款基于SpringCloud的微服务基础开发平台，旨在降低SpringCloud的复杂度，像使用JFinal一样简单（本人是JFinal用户，从1.9版本开始现在也一直在使用，因此部分实现思路会借鉴JFinal的一些模式，感谢@JFinal作者波总提供这么优秀的框架），包含微服务相关的完整解决方案同时附加有权限管理、报表自定义、工作流、Cms等套件，可直接使用，Aooms基于Apache Licence 2.0开源协议，关于编写此框架的一些初衷，可通过此处[诞生](https://gitee.com/cyb-javaer/Aooms/wikis/pages)了解。

## 二、核心功能

- 极简Controller
- 基于sharding-sphere的多数据源、分库分表支持
- 基于Mybatis 实现的 Db + Record 极简模式，附带物理分页实现
- 基于Consul的服务注册、发现
- 服务熔断、限流
- 服务客户端、http客户端
- 内置各种ID生成器（UUID、snowflake）
- 穿透一切的数据对象DataBoss
- 基于J2Cache的缓存
- 单点登陆(规划中)
- 权限系统(规划中)
- 报表系统(规划中)
- cms系统(规划中)
- ...........

## 三、简单Demo
### 1. Hello World


```
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
 * Created by 风象南(yuboon) on 2018-09-12
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
```


### 2. 一个简单的CRUD

```
package net.aooms.example.controller;

import net.aooms.core.web.AoomsAbstractController;
import net.aooms.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * simple crud
 * Created by 风象南(yuboon) on 2018-09-12
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
```

```
package net.aooms.example.service;

import net.aooms.core.AoomsConstants;
import net.aooms.core.id.IDGenerator;
import net.aooms.core.module.mybatis.Db;
import net.aooms.core.module.mybatis.SqlPara;
import net.aooms.core.record.RecordGroup;
import net.aooms.core.record.Record;
import net.aooms.core.service.GenericService;
import net.aooms.example.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * simple crud service
 * Created by 风象南(yuboon) on 2018-09-17
 */
@Service
public class UserService extends GenericService {

    @Autowired
    private Db db;

    public void findList() {
        PagingRecord recordGroup = db.findList("UserMapper.findList", SqlPara.fromDataBoss());

        {
            // 返回值
            this.setResultValue(AoomsConstants.Result.DATA,recordGroup);
        }
    }

    @Transactional
    public void insert() {

        // record 模式
        Record record1 = Record.NEW();
        record1.set(AoomsConstants.ID,IDGenerator.getLongValue());
        record1.set("name","lisi3");
        db.insert("user",record1);

        UserVo userVo = new UserVo();
        userVo.setId(IDGenerator.getLongValue());
        userVo.setName("wangwu");
        Record record2 = Record.NEW().fromBean(userVo);
        // fromDataBoss(prefix) 该方法可将参数 满足model.xxx 规则的参数构造成record属性
        // 例：http://xxx/insert?record.id=1&record.name=zhangsan&model.role=3&code=02,
        // 通过 fromDataBoss 将提取id,name,role三个属性的值,不会提取code值
        record2.fromDataBoss("model");  // model为参数prefix 格式：model.role , 将会通过model取到role值

        db.insert("user",record2);
    }

    @Transactional
    public void update() {
        // record 模式
        Record record = db.findByPrimaryKey("user","root");
        if(record != null){
    	    record.set("name","zhaoliu");
	    db.update("user",record);
       	}
    }

    @Transactional
    public void delete() {
        db.deleteByPrimaryKey("user","root1");
    }
}
```

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="UserMapper">

    <!-- 二级缓存 -->
    <cache type="net.aooms.core.module.mybatis.J2CacheSupport" eviction="LRU" flushInterval="60000" size="512" readOnly="true"/>

    <sql id="columns">
         id,name
    </sql>

    <select id="findList" resultType="net.aooms.core.record.Record">
        SELECT <include refid="columns" /> FROM USER
    </select>

    <select id="updateById" resultType="int">
        update user set name = '123' where id = #{id}
    </select>

    <select id="findAll" resultType="net.aooms.core.record.Record">
        select * from t_order
    </select>

</mapper>
```






## 四、相关博文链接
https://my.oschina.net/cccyb?tab=newest&catalogId=5736543
持续关注，不定期会有更新

## 五、框架的一点声明
&nbsp;&nbsp;&nbsp;&nbsp;关于框架的一点声明，框架目前处于开发阶段，会不定期更新码云上的代码同时会有系统博客同步更新，另外此项目是带有学习性质的摸索、尝试，是为了给想学习微服务的人一个学习上的帮助，大家一起学习、探讨，感受一个微服务开发平台从0到诞生的过程，因为可能有的人想学但没有方向又或者所在公司技术体系比较老，不具备微服务的学习环境，所以构建了该工程，希望能帮到一些人同时对我自己也是一次锻炼，预计2018年底会有版本发出，供大家完整的参考。
