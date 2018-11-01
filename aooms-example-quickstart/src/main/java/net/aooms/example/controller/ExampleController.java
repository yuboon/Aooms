package net.aooms.example.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import net.aooms.core.Aooms;
import net.aooms.core.databoss.DataResult;
import net.aooms.core.id.IDGenerator;
import net.aooms.core.module.mybatis.Db;
import net.aooms.core.module.mybatis.SqlPara;
import net.aooms.core.record.RecordGroup;
import net.aooms.core.record.Record;
import net.aooms.core.property.PropertyApplication;
import net.aooms.core.property.PropertyObject;
import net.aooms.core.web.AoomsAbstractController;
import net.aooms.core.web.client.AoomsHttpTemplate;
import net.aooms.core.web.client.AoomsRestTemplate;
import net.aooms.example.service.ExampleService;
import net.aooms.example.vo.UserVo;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * more example
 * Created by 风象南(yuboon) on 2018-09-18
 */
@RestController
public class ExampleController extends AoomsAbstractController {

    @Autowired
    private ExampleService exampleService;

    @Autowired
    private CacheChannel cacheChannel;

    @Autowired
    private AoomsRestTemplate aoomsRestTemplate;

    @Autowired
    private AoomsHttpTemplate aoomsHttpTemplate;

    @Autowired
    private Db db;

    /**
     * 服务熔断、降级
     * 说明：连续访问多次，触发熔断
     */
    @RequestMapping("/example")
    @HystrixCommand(fallbackMethod = ERROR_METHOD)
    public void example(){
        // 模拟服务故障，触发熔断，并降级
        aoomsRestTemplate.get("http://unknow.host/hello");
        this.renderJson();
    }

    /**
     * http请求
     * @return
     */
    @RequestMapping("/example1")
    public void example1(){
        // 模拟http请求
        String body = aoomsHttpTemplate.get("https://www.oschina.net");
        this.renderHtml(body);
    }

    /**
     * 服务请求
     * @return
     */
    @RequestMapping("/example2")
    public void example2(){
        // 模拟调用另一个服务  http://AOOMS/service2/{code}
        // 服务名：AOOMS
        //      spring.application.aooms-use-registry: false
        //          -- AOOMS 将被替换为本地地址，不会调用注册中心，满足本地化调试，而无需修改服务地址
        DataResult dataResult = aoomsRestTemplate.get("http://AOOMS/service2/params");

        // 取String值
        String id = dataResult.getValue("id",String.class);

        // 取BeanList值
        List<UserVo> userVoList = dataResult.getBeanList("userVoList",UserVo.class);

        // 取recordList值，不建议使用，建议直接使用PagingRecord代替
        List<Record> recordList = dataResult.getRecordList("recordList");

        // 取pagingRecord值
        RecordGroup recordGroup = dataResult.getRecordGroup("pgRecord");

        logger.info("id = {}", id);
        logger.info("userVoList = {}", JSON.toJSONString(userVoList));
        logger.info("recordList = {}", JSON.toJSONString(recordList));
        logger.info("recordGroup = {}", JSON.toJSONString(recordGroup));

        // 设置结果集
        this.getResult().setData(dataResult.getData());
        this.renderJson();
    }

    /**
     * 配置文件值获取
     */
    @RequestMapping("/example3")
    public void example3(){
        PropertyObject propertyObject = Aooms.self().getPropertyObject();
        // PropertyApplication 也可使用@Autowired直接注入
        PropertyApplication propertyApplication = propertyObject.getApplicationProperty();
        logger.info("appName = {}", propertyApplication.getName());
    };

    /**
     * 缓存操作
     */
    @RequestMapping("/example4")
    public void example4(){
        cacheChannel.set("testRegion","id","root");
        CacheObject cacheObject = cacheChannel.get("testRegion","id");
        logger.info("cacheValue = {}" ,cacheObject.asString());

        // 缓存操作二
        // CacheChannel channel = Aooms.self().getJ2Cache();
    };

    /**
     * 多数据源
     * 设置：spring.datasource.sharding-jdbc = false
     */
    @RequestMapping("/example5")
    public void example5(){
        // 使用主数据源
        RecordGroup recordGroup = db.use("master").findList("UserMapper.findList", SqlPara.SINGLETON);

        // 使用从数据源
        RecordGroup recordGroup2 = db.use("slave").findList("UserMapper.findList", SqlPara.SINGLETON);

        this.setResultValue("recordGroup", recordGroup);
        this.setResultValue("recordGroup2", recordGroup2);
    };

    /**
     * 多数据源,注解方式
     * 设置：spring.datasource.sharding-jdbc = false
     */
    @RequestMapping("/example6")
    public void example6(){
        exampleService.example6();
    };

    /**
     * 分表
     * 设置：spring.datasource.sharding-jdbc = true
     */
    @RequestMapping("/example7")
    public void example7(){
       Record record = Record.empty().set("order_id",IDGenerator.getLongValue()).set("user_id","12").set("status","NEW");
       // 根据application-sharding-jdbc.yml 配置的规则，order_id % 2 == 0 时放入t_order_0 表，否则存入t_order_1 表
       db.insert("t_order",record);
    };

    /**
     * 模拟另一个服务，被其他服务调用
     */
    @RequestMapping("/service2/{code}")
    public void service2(){
        // 取参
        String code = this.getPara().getPathVar("code");
        logger.info("code = {}" , code);

        // 模拟BeanList返回值
        List<UserVo> userVoList = Lists.newArrayList();
        UserVo userVo = new UserVo();
        userVo.setName("wangwu");
        userVoList.add(userVo);

        // 模拟RecordList返回值
        List<Record> records = Lists.newArrayList();
        Record r = new Record();
        r.set("id", IDGenerator.getLongValue());
        r.set("name", "liuqi");
        records.add(r);

        // 模拟标准Record集合，带分页效果
        RecordGroup recordGroup = new RecordGroup(1,2,  records,100,true);

        // 设置返回值
        this.setResultValue("userVoList",userVoList);
        this.setResultValue("recordList",records);
        this.setResultValue("id","zhangsan");
        this.setResultValue("pgRecord", recordGroup);
    };


}