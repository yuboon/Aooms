package net.aooms.core.module.mybatis;

/**
 * MappedStatementId生成
 * Created by 风象南(yuboon) on 2018/9/27
 */
public class MappedStatementId {

    public static String getStatementId(Class<? extends MapperPackage> mapperPackageClass, String method){
        return mapperPackageClass.getPackage().getName() + "." + method;
    }

}
