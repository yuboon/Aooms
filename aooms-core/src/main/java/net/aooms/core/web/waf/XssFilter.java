package net.aooms.core.web.waf;

import cn.hutool.core.util.StrUtil;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * Xss过滤
 * @Created by 风象南(yuboon) on 2018-09-30
 */
public class XssFilter extends StringFilter {
	
	private final static Whitelist user_content_filter = Whitelist.relaxed();  
	static {  
		// addAttributes (tags, keys...);
	    user_content_filter.addTags("embed","object","param","span","div");  
	    user_content_filter.addAttributes(":all", "style", "class", "id", "name");  
	    user_content_filter.addAttributes("object", "width", "height","classid","codebase");      
	    user_content_filter.addAttributes("param", "name", "value");  
	    user_content_filter.addAttributes("td",	"valign","align");  
	    user_content_filter.addAttributes("embed", "src","quality","width","height","allowFullScreen","allowScriptAccess","flashvars","name","type","pluginspage");  
	}  
	  
	/** 
	 * 对用户输入内容进行过滤 
	 * @return
	 */  
	public String filter(String value) {
	    if(StrUtil.isNotBlank(value)){
	    	return Jsoup.clean(value, user_content_filter);  
	    }
	    return value;
	}
}
