package website.liujie.common.spring;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description	: 
 * @Copyright	: Excenon. ALL Rights Reserved
 * @Company		: 深圳市华磊移动设备科技有限公司 
 * @author		: Cosmo
 * @version		: 1.0
 * @Date		: 2015年11月28日 上午11:28:07
 */
public class DateConverter implements Converter<String, Date> {

	private String pattern = "yyyy-MM-dd HH:mm:ss";
	
	/* (non-Javadoc)  
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)  
	 */
	public Date convert(String source) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
	    dateFormat.setLenient(false);    
	    try {    
	        return dateFormat.parse(source);    
	    } catch (ParseException e) {    
	        e.printStackTrace();    
	    }           
	    return null; 
	}

	/**  
	 * @param pattern the pattern to set  
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
