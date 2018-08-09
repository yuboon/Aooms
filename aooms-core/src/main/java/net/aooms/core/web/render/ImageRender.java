package net.aooms.core.web.render;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片渲染
 * Created by cccyb on 2018-04-20
 */
public class ImageRender extends AbstractRender {

    private String suffix;

    public ImageRender(String suffix) {
        this.renderType = RenderType.IMAGE;
        this.suffix = suffix;
    }

    @Override
    public void render(HttpServletResponse response, Object value) throws Exception {
        response.setContentType("image/" + suffix);
        // response.setHeader("Content-Encoding","gzip"); // 启用压缩


       /* age:66700
        cache-control:max-age=315360000
        content-length:3902
        content-type:image/png
        date:Mon, 23 Apr 2018 08:34:56 GMT
        expires:Wed, 19 Apr 2028 13:19:35 GMT
        last-modified:Thu, 06 Apr 2017 09:03:07 GMT
       */

        InputStream is = (InputStream) value;
        byte[] buffer = new byte[1024]; //1KB
        int len = -1;
        int size = 0;
        while((len = is.read(buffer)) > -1){
            size += len;
            response.getOutputStream().write(buffer ,0 , len);
        }

        // 关闭流
        is.close();
        response.getOutputStream().close();
        //this.flushAndClose(response);
    }
}