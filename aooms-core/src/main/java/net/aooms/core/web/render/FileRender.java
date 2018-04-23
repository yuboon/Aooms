package net.aooms.core.web.render;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件渲染
 * Created by cccyb on 2018-04-20
 */
public class FileRender extends IRender {

    private String fileName;

    public FileRender(String fileName) {
        this.renderType = RenderType.FILE;
        this.fileName = fileName;
    }

    @Override
    public void render(HttpServletResponse response, Object value) throws IOException {
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        InputStream is = (InputStream) value;
        byte[] buffer = new byte[1024]; //1KB
        int len = -1;
        while((len = is.read(buffer)) > -1){
            response.getOutputStream().write(buffer ,0 , len);
        }

        // 关闭流
        is.close();
        response.getOutputStream().close();
        //this.flushAndClose(response);
    }
}