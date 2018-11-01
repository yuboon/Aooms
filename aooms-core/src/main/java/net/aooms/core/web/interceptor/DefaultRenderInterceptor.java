package net.aooms.core.web.interceptor;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import net.aooms.core.AoomsConstants;
import net.aooms.core.web.AoomsAbstractController;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 默认视图渲染
 * Created by 风象南(yuboon) on 2018-04-19
 */
public class DefaultRenderInterceptor extends AoomsAbstractInterceptor {

    public DefaultRenderInterceptor(String[] pathPatterns, String[] ignores) {
        super(pathPatterns, ignores);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        return true;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler ,ModelAndView modelAndView){
        if(!isRender(request)){
            if(handler instanceof HandlerMethod){
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                Object controllerBean = handlerMethod.getBean();
                if(controllerBean instanceof AoomsAbstractController){
                    AoomsAbstractController controller = (AoomsAbstractController) controllerBean;
                    controller.renderJson();
                }
            }
        }
    }

    private boolean isRender(HttpServletRequest request){
        Object isRender = request.getAttribute(AoomsConstants.Render.IS_RENDER);
        if(isRender == null){
            return false;
        }
        return Boolean.parseBoolean(isRender.toString());
    }


}