package net.aooms.core.web;

import net.aooms.core.properties.ApplicationProperties;
import net.aooms.core.properties.ServerProperties;
import net.aooms.core.properties.TestProperties;
import net.aooms.core.web.client.AoomsRestClient;
import net.aooms.core.web.client.SimpleRestTemplate;
import org.hibernate.validator.constraints.URL;
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
import java.net.URI;
import java.util.List;

/**
 * 一个完整的控制器Demo,方便框架内部调试
 * Created by cccyb on 2018-02-06
 */
@RestController
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    private TestProperties testProperties;

    @Value("${spring.application.name}")
    private String name;

    @Autowired
    private Environment environment;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SimpleRestTemplate simpleRestTemplate;

    @Autowired
    private AoomsRestClient aoomsRestClient;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value="/get")
    public String get(String id) {
        logger.error(" value from propertiesApplication ： {} ",applicationProperties.getName());
        logger.error(" server.port ： {} ", serverProperties.getPort());
        logger.error(" value from propertiesApplication ： {} ",applicationProperties.getName());

        logger.error(" value from property ： {} ", name);
        logger.error(" id value from param ： {} ", id);
        logger.error(" id value from testApplication ： {} ", testProperties.getName());

        logger.error(" id value from environment my.yml： {} ", environment.getProperty("my","test.name"));
        logger.error(" id value from environment my2.yml： {} ", environment.getProperty("my2","test.name"));

        return "get do success";
    }

    @GetMapping(value="/get2")
    public String get2(HttpServletRequest request) {
        logger.error(" id value from param ： {} ", request.getParameter("id"));
        return "get2 do success";
    }

    @GetMapping(value="/get3")
    public ModelAndView get3(HttpServletRequest request,ModelAndView mv) {
        logger.error(" id value from param ： {} ", request.getParameter("id"));
        mv.addObject("name", "张三");
        mv.setViewName("/test.html");
        return mv;
    }

    @GetMapping(value="/getRest")
    public String getRest(){
        List<String> services = discoveryClient.getServices();

        System.err.println("===>>>" + discoveryClient.description());
        System.err.println("===>>>" + services.size());

        String ret = restTemplate.getForObject("http://AOOMS/aooms/get",String.class);
        logger.error(" restTemplate Request Result is : {} " + ret);

        String ret2 = simpleRestTemplate.getForObject("http://localhost:9000/aooms/get2",String.class);
        logger.error(" simpleRestTemplate Request Url Result is : {} " + ret2);

        logger.error(" restTemplate Object Name = {}" + restTemplate.toString());

        return "get REST is success";
    }

    @GetMapping(value="/getSimple")
    public String getSimple(){
        String ret2 = simpleRestTemplate.getForObject("http://localhost:9000/get2",String.class);
        logger.error(" simpleRestTemplate Request Url Result is : {} " + ret2);
        return "get getSimple is success";
    }

    @GetMapping(value="/testClient")
    public String testClient(){
        logger.error(" isRemoteMode : {} ", aoomsRestClient.useRegistry());
        ResponseEntity<String> resp = aoomsRestClient.get("http://${serviceName}/get2");

        // ResponseEntity<String> resp = aoomsRestClient.get("http://AOOMS/aooms/get2");


        logger.error(" resp status code : {} ", resp.getStatusCode());
        logger.error(" resp status value : {} ", resp.getStatusCodeValue());
        logger.error(" resp body : {} ", resp.getBody());
        logger.error(" resp headers : {} ", resp.getHeaders());
        return "testClient is success";
    }

    public String upload(){


        return null;
    }

}