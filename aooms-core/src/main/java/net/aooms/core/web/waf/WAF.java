package net.aooms.core.web.waf;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * 应用安全防火墙
 * @Created by 风象南(yuboon) on 2018-09-30
 */
public class WAF {

	private static XssFilter xssFilter = new XssFilter();
	private static SqlInjectionFilter sqlFilter = new SqlInjectionFilter();
	
	/**
	 * 字符串转义
	 * @return
	 */
	public static String escape(String str,StringFilter... filters) {
		String result = str;
		for (StringFilter stringFilter : filters) {
			result = stringFilter.filter(result);
		}
		return result;
	}

	/**
	 * 字符串转义、反转义
	 * @return
	 */
	public static String escapeXss(String str) {
		return xssFilter.filter(str);
	}
	
	public static String unescapeXss(String str) {
		return StringEscapeUtils.unescapeXml(str);
	}
	
	/**
	 * SQL注入过滤
	 * @return
	 */
	public static String escapeSql(String str) {
		return sqlFilter.filter(str);
	}
	
}
