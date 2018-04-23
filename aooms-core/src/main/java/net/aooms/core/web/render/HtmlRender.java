package net.aooms.core.web.render;

import com.alibaba.fastjson.JSON;
import net.aooms.core.dto.DTO;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * html渲染
 * Created by cccyb on 2018-04-20
 */
public class HtmlRender extends IRender {

    public HtmlRender() {
        this.renderType = RenderType.HTML;
    }

    @Override
    public void render(HttpServletResponse response, Object value) throws IOException {
        response.setContentType(renderType.getContentType());
        response.getWriter().write(String.valueOf(value));
        this.flushAndClose(response);
    }
}