package net.aooms.core.property;

import com.baomidou.kisso.starter.KissoProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 所有属性持有者
 * Created by cccyb on 2018/8/25
 */
public class PropertyObject {

    private static PropertyObject INSTANCE;

    @Autowired
    private PropertyApplication applicationProperty;

    @Autowired
    private PropertyServer serverProperty;

    @Autowired
    private KissoProperties kissoProperty;

    public static PropertyObject getInstance(){
        return INSTANCE;
    }

    public void instance(PropertyObject propertyObject){
        INSTANCE = propertyObject;
    }

    public PropertyApplication getApplicationProperty() {
        return applicationProperty;
    }

    public PropertyServer getServerProperty() {
        return serverProperty;
    }

    public KissoProperties getKissoProperty() {
        return kissoProperty;
    }

}
