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
public class AoomsContext {

    private static ThreadLocal<Context> contextThreadLocal = new ThreadLocal<>();

    public static HttpServletRequest getRequest(){
        return contextThreadLocal.get().getRequest();
    }

    public static HttpServletResponse getResponse(){
        return contextThreadLocal.get().getResponse();
    }

    public static ServletContext getServletContext(){
        return getRequest().getServletContext();
    }

    public static HandlerMethod getMethodHandler(){
        return contextThreadLocal.get().getHandlerMethod();
    }

    public static void init(Context context){
        contextThreadLocal.set(context);
    }

    public static void remove(){
        contextThreadLocal.remove();
    }

    public static Context get(){
        Context context = contextThreadLocal.get();
        Objects.requireNonNull(context,"The AoomsContext object is not initialized.");
        return context;
    }

    public static class Context{

        HttpServletRequest request;
        HttpServletResponse response;
        HandlerMethod handlerMethod;

        public Context(HttpServletRequest request, HttpServletResponse response,Object method) {
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