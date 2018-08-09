package net.aooms.core.web;

import org.springframework.web.method.HandlerMethod;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Servlet上下文持有对象
 * Created by cccyb on 2018-04-20
 */
public class AoomsContextHolder {

    private static ThreadLocal<AoomsContext> servletContextThreadLocal = new ThreadLocal<>();

    public static HttpServletRequest getRequest(){
        return servletContextThreadLocal.get().getRequest();
    }

    public static HttpServletResponse getResponse(){
        return servletContextThreadLocal.get().getResponse();
    }

    public static ServletContext getServletContext(){
        return getRequest().getServletContext();
    }

    public static HandlerMethod getMethodHandler(){
        return servletContextThreadLocal.get().getHandlerMethod();
    }

    public static void init(AoomsContext servletContext){
        servletContextThreadLocal.set(servletContext);
    }

    public static void remove(){
        servletContextThreadLocal.remove();
    }

    public static AoomsContext get(){
        AoomsContext servletContext = servletContextThreadLocal.get();
        Objects.requireNonNull(servletContext,"The AoomsContextHolder object is not initialized.");
        return servletContext;
    }

    public static class AoomsContext{

        HttpServletRequest request;
        HttpServletResponse response;
        HandlerMethod handlerMethod;

        public AoomsContext(HttpServletRequest request, HttpServletResponse response,Object method) {
            this.request = request;
            this.response = response;
            if(method instanceof  HandlerMethod){
                this.handlerMethod = (HandlerMethod) method;
            }
        }

        public HttpServletRequest getRequest() {
            return request;
        }

        public ServletContext getServletContext() {
            return request.getServletContext();
        }

        public void setRequest(HttpServletRequest request) {
            this.request = request;
        }

        public HttpServletResponse getResponse() {
            return response;
        }

        public void setResponse(HttpServletResponse response) {
            this.response = response;
        }

        public HandlerMethod getHandlerMethod() {
            return handlerMethod;
        }

        public void setHandlerMethod(HandlerMethod handlerMethod) {
            this.handlerMethod = handlerMethod;
        }
    }
}