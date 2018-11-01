package net.aooms.core.web.waf;


import cn.hutool.core.util.StrUtil;

/**
 * 简单防SQL注入过滤
 * @Created by 风象南(yuboon) on 2018-09-30
 *
 */
public class SqlInjectionFilter extends StringFilter {

	@Override
	public String filter(String value){
		if(StrUtil.isNotBlank(value)){
			return value
					.replaceAll("'", "")
					.replaceAll("--", "")
					.replaceAll(";", "");
		}
		return value;
	}
}
