package net.aooms.core.web.interceptor;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpStatus;
import net.aooms.core.AoomsVar;
import net.aooms.core.databoss.DataBoss;
import net.aooms.core.databoss.DataResult;
import net.aooms.core.exception.AoomsExceptions;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * 演示模式拦截器，不允许CUD操作,放行R操作
 * Created by 风象南(yuboon) on 2018-04-19
 */
public class DemoModeInterceptor extends AoomsAbstractInterceptor {

    public DemoModeInterceptor(String[] pathPatterns, String[] ignores) {
        super(pathPatterns, ignores);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        response.setCharacterEncoding(AoomsVar.ENCODE);
        DataResult dataResult = new DataResult();
        dataResult.failure(HttpStatus.HTTP_FORBIDDEN, "演示模式下，不允许该操作");
        try{
            response.getWriter().write(dataResult.toJsonStr());
            response.getWriter().flush();
        }catch (IOException e){
            throw AoomsExceptions.create(e.getMessage(),e);
        }

        return  false;
    }


}