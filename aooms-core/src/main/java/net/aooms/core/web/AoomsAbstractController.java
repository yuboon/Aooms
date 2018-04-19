package net.aooms.core.web;

import net.aooms.core.dto.DTO;
import net.aooms.core.dto.DTOPara;
import net.aooms.core.dto.DTORet;
import net.aooms.core.properties.ApplicationProperties;
import net.aooms.core.properties.ServerProperties;
import net.aooms.core.properties.TestProperties;
import net.aooms.core.web.client.AoomsRestClient;
import net.aooms.core.web.client.SimpleRestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 抽象控制器类
 * Created by cccyb on 2018-02-06
 */
@RestController
public abstract class AoomsAbstractController {

    private static Logger logger = LoggerFactory.getLogger(AoomsAbstractController.class);

    /**
     * 获取参数
     * @return
     */
    public DTOPara getPara(){
        return DTO.me().getPara();
    };

    /**
     * 获取结果
     * @return
     */
    public DTORet getRet(){
        return DTO.me().getRet();
    };

}