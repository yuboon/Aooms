package net.aooms.core.web;

import net.aooms.core.configuration.Vars;
import net.aooms.core.dto.DTO;
import net.aooms.core.dto.DTOPara;
import net.aooms.core.dto.DTORet;
import net.aooms.core.web.render.RenderException;
import net.aooms.core.web.render.RenderFactory;
import net.aooms.core.web.render.RenderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    /**
     * 响应结果
     * @return
     */
    public void renderJson(){
        this.renderJson(getRet().getData());
    };

    /**
     * 响应结果
     * @return
     */
    public void renderJson(Object obj){
        this.render(RenderType.JSON, obj);
    };

    /**
     * 响应结果
     * @return
     */
    public void renderPage(Object obj){

    };

    /**
     * 响应结果
     * @return
     */
    public void renderText(String text){
    };

    /**
     * 响应结果
     * @return
     */
    public void renderHtml(String html){

    };

    // 输出
    private void render(RenderType renderType,Object value){
        try {
            RenderFactory.me().getRender(renderType).render(getResponse(),value);
        } catch (IOException e) {
            throw new RenderException("render error");
        }
    }

    // 获取response
    private HttpServletResponse getResponse(){
        HttpServletResponse response = ServletContextHolder.getResponse();
        response.setCharacterEncoding(Vars.ENCODE);
        return response;
    }

}