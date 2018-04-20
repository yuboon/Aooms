package net.aooms.core.dto;

import net.aooms.core.exception.AoomsExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 数据传输对象
 * Created by cccyb on 2018-04-18
 */
public class DTO implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(DTO.class);

    //
    private static final ThreadLocal<DTO> DTO = new ThreadLocal<>();

    private DTOPara dtoPara;

    private DTORet dtoRet;

    /**
     * 创建
     * @return
     */
    public static DTO create(){
        DTO dto = new DTO(new DTOPara(),new DTORet());
        // 放入线程变量
        DTO.set(dto);
        return dto;
    }

    /**
     * 获取
     * @return
     */
    public static DTO me(){
       DTO dto = DTO.get();
       if(dto == null){
           throw AoomsExceptions.create("The DTO object is not initialized.");
       }
       return dto;
    }

    /**
     * 销毁
     * @return
     */
    public static void destroy(){
        if(logger.isInfoEnabled()){
            logger.info("DTO from ThreadLocal remove");
        }
        DTO.remove();
    }


    public DTO(DTOPara dtoPara, DTORet dtoRet) {
        this.dtoPara = dtoPara;
        this.dtoRet = dtoRet;
    }

    public DTOPara getPara() {
        return dtoPara;
    }

    public void setPara(DTOPara dtoPara) {
        this.dtoPara = dtoPara;
    }

    public DTORet getRet() {
        return dtoRet;
    }

    public void setRet(DTORet dtoRet) {
        this.dtoRet = dtoRet;
    }


}