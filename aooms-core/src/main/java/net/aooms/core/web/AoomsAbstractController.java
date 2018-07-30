package net.aooms.core.web;

import net.aooms.core.configuration.Vars;
import net.aooms.core.dto.DTO;
import net.aooms.core.dto.DTOPara;
import net.aooms.core.dto.DTORet;
import net.aooms.core.util.FileUtils;
import net.aooms.core.web.render.IRender;
import net.aooms.core.web.render.RenderException;
import net.aooms.core.web.render.RenderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 抽象控制器类
 * Created by cccyb on 2018-02-06
 */
@RestController
public abstract class AoomsAbstractController {

    private static Logger logger = LoggerFactory.getLogger(AoomsAbstractController.class);

    /**
     * 获取参数
     * @return
     */
    public DTOPara getPara(){
        return DTO.me().getPara();
    };

    /**
     * 获取结果
     * @return
     */
    public DTORet getRet(){
        return DTO.me().getRet();
    };

    /**
     * 响应结果
     * @return
     */
    public void renderJson(){
        this.renderJson(getRet().getData());
    };

    /**
     * 响应结果
     * @return
     */
    public void renderJson(Object obj){
        this.doRender(RenderFactory.me().getJSONRender(),obj);
    };

    /**
     * 响应结果
     * @return
     */
    public void renderHtml(String html){
        this.doRender(RenderFactory.me().getHtmlRender(),html);
    };

    /**
     * 响应结果
     * @return
     */
    public void renderJavaScript(String jsScript){
        this.doRender(RenderFactory.me().getJavaScriptRender(),jsScript);
    };

    /**
     * 响应结果
     * @return
     */
    public void renderText(String text){
        this.doRender(RenderFactory.me().getTextRender(),text);
    };

    /**
     * 响应结果
     * @return
     */
    public void renderImage(String imgPath){
        this.renderImage(imgPath , null);
    };

    /**
     * 响应结果
     * @return
     */
    public void renderImage(String imgPath,String defaultImg){
        InputStream is = null;
        String fileName = imgPath;
        File file = new File(imgPath);
        if(file.exists()){
            try {
                is = new FileInputStream(imgPath);
            } catch (FileNotFoundException e) {
                throw new RenderException(imgPath + " not found");
            }
        }else{
            if(defaultImg == null){
                throw new RenderException(imgPath + " not found");
            }

            try {
                fileName = defaultImg;
                is = new FileInputStream(defaultImg);
            } catch (FileNotFoundException e) {
                throw new RenderException(defaultImg + " not found");
            }
        }

        this.renderImage(is, FileUtils.getExtensionNoDot(fileName));
    };

    /**
     * 响应结果
     * @return
     */
    private void renderImage(InputStream imgStream,String suffix){
        if(imgStream == null){
            throw new RenderException("imgStream is null");
        }
        this.doRender(RenderFactory.me().getImageRender(suffix),imgStream);
    };


    /**
     * 响应结果
     * @return
     */
    public void renderPage(String page){
        try {
            ServletContextHolder.getRequest().getRequestDispatcher(page).forward(ServletContextHolder.getRequest(),ServletContextHolder.getResponse());
        } catch (Exception e) {
            throw new RenderException("page render error",e);
        }
    };

    /**
     * 输出文件
     * @return
     */
    public void renderFile(File file){
        this.renderFile(file.getName(), file);
    };

    /**
     * 输出文件
     * @return
     */
    public void renderFile(String fileName, File file){
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RenderException(file.getAbsolutePath() + " not found");
        }
        this.renderFile(fileName, is);
    };

    /**
     * 输出文件
     * @return
     */
    public void renderFile(String fileName, InputStream is){
        if(is == null){
            throw new RenderException("InputStream is null");
        }
        // 文件名称编码
        fileName = this.encodeFileName(fileName);
        this.doRender(RenderFactory.me().getFileRender(fileName), is);
    };

    // 输出
    private void doRender(IRender render, Object value){
        try {
           // RenderFactory.me().getRender(renderType).render(getResponse(),value);
            render.render(getResponse(),value);
        } catch (IOException e) {
            throw new RenderException("render error" ,e);
        }
    }

    // 获取response
    private HttpServletResponse getResponse(){
        HttpServletResponse response = ServletContextHolder.getResponse();
        response.setCharacterEncoding(Vars.ENCODE);
        return response;
    }

    // 文件名称编码
    protected  String encodeFileName(String fileName){
        String userAgent = ServletContextHolder.getRequest().getHeader("User-Agent");
        try {
            /* IE 8 至 IE 10 */  /* IE 11 */
            if (userAgent.toUpperCase().contains("MSIE") || userAgent.contains("Trident/7.0")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");

            } else if (userAgent.toUpperCase().contains("MOZILLA") || userAgent.toUpperCase().contains("CHROME")) {
                fileName = new String(fileName.getBytes(), "ISO-8859-1");

            } else {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            }
        } catch (UnsupportedEncodingException e){
            throw new RenderException("encodeFileName "+ fileName +" error",e);
        }
        return fileName;
    }

}