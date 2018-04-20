package net.aooms.core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Servlet上下文持有对象
 * Created by cccyb on 2018-04-20
 */
public class ServletContextHolder {

    private static ThreadLocal<ServletContext> servletContextThreadLocal = new ThreadLocal<>();

    public static HttpServletRequest getRequest(){
        return servletContextThreadLocal.get().getRequest();
    }

    public static HttpServletResponse getResponse(){
        return servletContextThreadLocal.get().getResponse();
    }

    public static void init(ServletContext servletContext){
        servletContextThreadLocal.set(servletContext);
    }

    public static void remove(){
        servletContextThreadLocal.remove();
    }

    public static ServletContext get(){
        ServletContext servletContext = servletContextThreadLocal.get();
        Objects.requireNonNull(servletContext,"The ServletContextHolder object is not initialized.");
        return servletContext;
    }

    public static class ServletContext{

        HttpServletRequest request;
        HttpServletResponse response;

        public ServletContext(HttpServletRequest request, HttpServletResponse response) {
            this.request = request;
            this.response = response;
        }

        public HttpServletRequest getRequest() {
            return request;
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
    }
}