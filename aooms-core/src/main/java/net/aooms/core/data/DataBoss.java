package net.aooms.core.data;

import net.aooms.core.exception.AoomsExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 数据对象：贯穿与框架各个层
 * Created by cccyb on 2018-04-18
 */
public class DataBoss implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(DataBoss.class);

    //
    private static final ThreadLocal<DataBoss> DATA_BOSS_THREAD_LOCAL = new ThreadLocal<>();

    private DataPara dataPara;

    private DataResult dataResult;

    /**
     * 创建
     * @return
     */
    public static DataBoss create(){
        DataBoss dto = new DataBoss(new DataPara(),new DataResult());
        // 放入线程变量
        DATA_BOSS_THREAD_LOCAL.set(dto);
        return dto;
    }

    /**
     * 创建
     * @return
     */
    public static DataBoss create(DataPara dataPara,DataResult dataResult){
        DataBoss dto = new DataBoss(dataPara,dataResult);
        // 放入线程变量
        DATA_BOSS_THREAD_LOCAL.set(dto);
        return dto;
    }

    /**
     * 获取
     * @return
     */
    public static DataBoss get(){
       DataBoss dto = DATA_BOSS_THREAD_LOCAL.get();
       if(dto == null){
           //throw AoomsExceptions.create("The DataBoss object is not initialized.");
           logger.error("The DataBoss object is not initialized.");
       }
       return dto;
    }

    /**
     * 销毁
     * @return
     */
    public static void destroy(){
        if(logger.isInfoEnabled()){
            logger.info("DataBoss from ThreadLocal remove");
        }
        DATA_BOSS_THREAD_LOCAL.remove();
    }


    public DataBoss(DataPara dataPara, DataResult dataResult) {
        this.dataPara = dataPara;
        this.dataResult = dataResult;
    }

    public DataPara getPara() {
        return dataPara;
    }

    public void setPara(DataPara dataPara) {
        this.dataPara = dataPara;
    }

    public DataResult getResult() {
        return dataResult;
    }

    public void setResult(DataResult dataResult) {
        this.dataResult = dataResult;
    }

}