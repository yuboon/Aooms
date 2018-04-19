package net.aooms.core.web;

import net.aooms.core.dto.DTO;
import net.aooms.core.dto.DTOPara;
import net.aooms.core.dto.DTORet;
import net.aooms.core.exception.AoomsExceptions;
import net.aooms.core.properties.TestProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String hello(){
        logger.info("NAME = {}" ,testProperties.getName());

        logger.info("ID = {}" ,this.getPara().getStr("id"));

        return this.getRet().toJsonStr();
    };




}