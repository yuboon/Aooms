package net.aooms.core.id;

/**
 * ID生成器
 * Created by 风象南(cheereebo) on 2018/9/7
 */
public interface IDGenerator {

    IDGenerator GeneratorForUUID = new GeneratorForUUID();
    IDGenerator GeneratorForSnowflake = new GeneratorForSnowflake();
    IDGenerator GeneratorForDistribution = new GeneratorForDistribution();

    public ID createID();

    static ID getID(){
        return GeneratorForSnowflake.createID();
    };

    static ID getID(IDType idType){
        if(idType == IDType.UUID){
            return GeneratorForUUID.createID();
        }

        if(idType == IDType.DISTRIBUTION){
            return GeneratorForDistribution.createID();
        }

        return GeneratorForSnowflake.createID();
    };

    static String getStringValue(){
       return getID().stringValue();
    };

    static String getStringValue(IDType idType){
        return getID(idType).stringValue();
    };

    static Long getLongValue(){
        return getID().longValue();
    };

    static Long getLongValue(IDType idType){
        return getID(idType).longValue();
    };

}
