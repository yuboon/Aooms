package net.aooms.core.web.render;

import com.alibaba.fastjson.JSON;
import net.aooms.core.dto.DTO;
import net.aooms.core.exception.AoomsException;
import net.aooms.core.exception.AoomsExceptions;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * 渲染接口
 * Created by cccyb on 2018-04-20
 */
public abstract class IRender {

    public abstract void render(HttpServletResponse response,Object value) throws IOException;

    public void flushAndClose(HttpServletResponse response){
        try(Writer writer = response.getWriter();) {
            response.getWriter().flush();
        } catch (IOException ex){
            throw AoomsExceptions.create("response writer close error");
        }
    };

}