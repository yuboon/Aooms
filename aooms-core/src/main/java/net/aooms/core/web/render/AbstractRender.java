package net.aooms.core.web.render;

import net.aooms.core.exception.AoomsExceptions;
import net.aooms.core.web.AoomsContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * 渲染
 * Created by cccyb on 2018-04-20
 */
public abstract class AbstractRender {

    protected RenderType renderType;

    public abstract void render(HttpServletResponse response, Object value) throws Exception;

    /**
     * 清空关闭流
     * @param response
     */
    public void flushAndClose(HttpServletResponse response){
        try(Writer writer = response.getWriter();) {
            response.getWriter().flush();
        } catch (IOException ex){
            throw AoomsExceptions.create("response writer close error",ex);
        }
    };

    /**
     * springmvc提供的render方法
     *
     */
    public void springMvcRender(Object returnValue){
        try{
            // modelAndViewContainer init
            ModelAndViewContainer modelAndViewContainer = new ModelAndViewContainer();
            ServletWebRequest webRequest = new ServletWebRequest(AoomsContext.getRequest(), AoomsContext.getResponse());

            // get ApplicationContext
            ApplicationContext ac1 = WebApplicationContextUtils.getRequiredWebApplicationContext(AoomsContext.getServletContext());

            // get RequestMappingHandlerAdapter Bean
            RequestMappingHandlerAdapter requestMappingHandlerAdapter = (RequestMappingHandlerAdapter)ac1.getBean("requestMappingHandlerAdapter");

            // init handlerMethodReturnValueHandlerComposite
            HandlerMethodReturnValueHandlerComposite handlerMethodReturnValueHandlerComposite = new HandlerMethodReturnValueHandlerComposite();
            handlerMethodReturnValueHandlerComposite.addHandlers(requestMappingHandlerAdapter.getReturnValueHandlers());

            // render
            HandlerMethod method = AoomsContext.getMethodHandler();
            handlerMethodReturnValueHandlerComposite.handleReturnValue(returnValue, method.getReturnType(), modelAndViewContainer, webRequest);

        } catch (Exception ex){
            throw AoomsExceptions.create("springmvc render error",ex);
        }
    };

}