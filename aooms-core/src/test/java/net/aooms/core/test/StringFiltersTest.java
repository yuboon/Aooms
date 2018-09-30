package net.aooms.core.test;

import java.math.BigDecimal;
import java.util.Scanner;


public class StringFiltersTest {

	public static String SQL(String str) {
		return str.replaceAll(".*([';]+|(--)+).*", " ");
	}
	
	/** 
     *  
     * 防止xss跨脚本攻击（替换，根据实际情况调整） 
     */  
	
	public static void main(String[] args) {
		// System.err.println(SQL("admin''"));
		// XssFilter filter = new XssFilter();
		// System.err.println(filter.filter("<p>&lt;script&gt;alert(1);&lt;/script&gt;</p>"));
		// System.err.println(org.apache.commons.lang3.StringEscapeUtils.escapeXml11("'"));
		Scanner s = new Scanner(System.in);
		BigDecimal result = new BigDecimal(0);
		int index = 0;
		//String[] ss = new String[]{"0.3","0.3","0.1","0.2"};
		String[] ss = new String[]{"0.3","0.3","0.15","0.15","0.1"};
		while(s.hasNext()){
			String value = s.nextLine();
			if(value.equals("end")){
				System.err.println("result:" + result);
				break;
			}else{
				result = result.add(new BigDecimal(value).multiply(new BigDecimal(ss[index])));
			}
			index++;
		}
		

	}
	
	
}
