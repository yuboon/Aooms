package net.aooms.core.module.mybatis;

/**
 * Roper条件枚举类   Roper -> Relational operator  为了保证调用时尽量简单，取Roper
 * 
 * * eq相等   ne、neq不相等，   gt大于， lt小于 gte、ge大于等于   lte、le 小于等于
 * equal/ not equal/ greater than/ less than/ less than or equal/ great than or equal/后面的就不用说了

   Smarty 中的 if 语句和 php 中的 if 语句一样灵活易用，并增加了几个特性以适宜模板引擎. if 必须于 /if 成对出现. 
       可以使用 else 和 elseif 子句. 可以使用以下条件修饰词：
       eq、ne、neq、gt、lt、lte、le、gte、ge、is even、is odd、is not even、
       is not odd、not、mod、div by、even by、odd by、==、!=、>、<、<=、>=. 
       使用这些修饰词时必须和变量或常量用空格格开.
 *
 * Created by 风象南(cheereebo) on 2018/9/29
 *
 */
public enum Roper {

	Eq(" = "), 
	Gt(" > "), 
	Lt(" < "), 
	Gte(" >= "), 
	Lte(" <= "),
	Like(" like "),
	LikeAfter(" like "),
	Or(" or ") 
	;
	
	 // 操作符
    private String oper;
	
	 // 构造方法
    private Roper(String oper) {
        this.oper = oper;
    }

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}
	
	 // 普通方法
    /*public static String getName(int index) {
        for (SqlConditionEnum c : SqlConditionEnum.values()) {
            if (c.getOper().equals(anObject)) {
                return c.name;
            }
        }
        return null;
    }*/
    
}