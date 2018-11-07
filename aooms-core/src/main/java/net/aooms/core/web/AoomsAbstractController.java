package net.aooms.core.web;

import net.aooms.core.AoomsVar;
import net.aooms.core.databoss.DataBoss;
import net.aooms.core.databoss.DataPara;
import net.aooms.core.databoss.DataResult;
import net.aooms.core.util.FileUtils;
import net.aooms.core.web.render.AbstractRender;
import net.aooms.core.web.render.RenderException;
import net.aooms.core.web.render.RenderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 抽象控制器类
 * Created by 风象南(yuboon) on 2018-02-06
 */
public abstract class AoomsAbstractController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    // 熔断降级方法名
    public static final String ERROR_METHOD = "error";

    /**
     * 获取参数
     * @return
     */
    public DataPara getPara(){
        return DataBoss.self().getPara();
    };

    /**
     * 获取结果
     * @return
     */
    public DataResult getResult(){
        return DataBoss.self().getResult();
    };

    /**
     * 获取参数
     * @return
     */
    public String getParaString(String key) {
        return getPara().getString(key);
    }

    /**
     * 获取参数
     * @return
     */
    public Integer getParaInteger(String key) {
        return getPara().getInteger(key);
    }

    /**
     * 获取参数
     * @return
     */
    public String getPathString(String key) {
        return getPara().getPathVar(key);
    }

    /**
     * 获取参数
     * @return
     */
    public Integer getPathInteger(String key) {
        return getPara().getPathVar(key);
    }

    /**
     * 获取参数
     * @return
     */
    public MultipartFile getParaFile(String uploadName) {
        return getPara().getFile(uploadName);
    }

    /**
     * 获取参数
     * @return
     */
    public InputStream getParaInputStream(String uploadName) {
        return getPara().getFileInputStream(uploadName);
    }

    /**
     * 获取参数
     * @ret*/
    public Map<String,MultipartFile> getParaFiles() {
        return getPara().getFiles();
    }

    /**
     * 设置响应值
     * @return
     */
    public DataResult setResultValue(String key,Object value){
        return getResult().set(key,value);
    }

    /**
     * 响应结果
     * @return
     */
    public void renderJson(){
        this.renderJson(getResult().getData());
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
    /*public void renderJavaScript(String jsScript){
        this.doRender(RenderFactory.me().getJavaScriptRender(),jsScript);
    };*/

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
     * 图片输出
     * @return
     */
    public void renderImage(InputStream imgStream,String suffix){
        if(imgStream == null){
            throw new RenderException("imgStream is null");
        }
        this.doRender(RenderFactory.me().getImageRender(suffix),imgStream);
    };

    /**
     * 图片输出
     * @return
     */
    public void renderImage(InputStream imgStream){
        if(imgStream == null){
            throw new RenderException("imgStream is null");
        }
        this.doRender(RenderFactory.me().getImageRender(null),imgStream);
    };

    /**
     * Thymeleaf 渲染
     * @return
     */
    public void renderThymeleaf(ModelAndView mv){
        this.doRender(RenderFactory.me().getThymeleafRender(mv),null);
    };

    /**
     * Thymeleaf 渲染
     * @return
     */
    public void renderThymeleaf(String viewName){
       this.renderThymeleaf(viewName,null);
    };

    /**
     * Thymeleaf 渲染
     * @return
     */
    public void renderThymeleaf(String viewName,Map<String,Object> model){
        ModelAndView mv = new ModelAndView();
        mv.setViewName(viewName);
        if(model != null){
            mv.addAllObjects(model);
        }
        this.doRender(RenderFactory.me().getThymeleafRender(mv),null);
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
    private void doRender(AbstractRender render, Object value){
        try {
            AoomsContext.getRequest().setAttribute(AoomsVar.IS_RENDER,true);
            render.render(getResponse(),value);
        } catch (Exception e) {
            throw new RenderException(e.getMessage(),e);
        }
    }

    // Forward
    public void forward(String url){
        try {
            getRequest().getRequestDispatcher(url).forward(this.getRequest(),this.getResponse());
        } catch (ServletException | IOException e) {
            throw new RenderException(e.getMessage(),e);
        }
    }

    // Redirect
    public void redirect(String url){
        try {
            getResponse().sendRedirect(url);
        } catch (IOException e) {
            throw new RenderException(e.getMessage(),e);
        }
    }

    // 熔断后执行的方法
    public void error(Throwable e) throws Throwable {
        //System.err.println("name:" + this.getParaString("name"));
        //System.err.println("hiError.ThreadName = " + Thread.currentThread().getName());
        throw e;
        //this.redirect("/error");
        //this.getResult().failure();
    }

    // 获取response
    public HttpServletResponse getResponse(){
        HttpServletResponse response = AoomsContext.getResponse();
        response.setCharacterEncoding(AoomsVar.ENCODE);
        return response;
    }

    /**
     * 获取request
     * @return
     */
    public HttpServletRequest getRequest(){
        return AoomsContext.getRequest();
    };


    // 文件名称编码
    protected  String encodeFileName(String fileName){
        String userAgent = AoomsContext.getRequest().getHeader("User-Agent");
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
            throw new RenderException(e.getMessage(),e);
        }
        return fileName;
    }

}