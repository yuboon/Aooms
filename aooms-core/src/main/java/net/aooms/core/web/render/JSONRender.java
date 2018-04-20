package net.aooms.core.web.render;

import com.alibaba.fastjson.JSON;
import net.aooms.core.dto.DTO;
import net.aooms.core.exception.AoomsExceptions;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * json渲染
 * Created by cccyb on 2018-04-20
 */
public class JSONRender extends IRender {

    @Override
    public void render(HttpServletResponse response,Object value) throws IOException {
        response.setContentType("text/plain");
        response.getWriter().write(JSON.toJSONString(DTO.me().getRet().getData()));
        this.flushAndClose(response);
    }
}