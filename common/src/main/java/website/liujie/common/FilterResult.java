package website.liujie.common;

import java.util.Collection;

/**
 * @Description	: 支持过滤对象不需要的属性返回结果集
 * @Copyright	: Excenon. ALL Rights Reserved
 * @Company		: jie 
 * @author		: Cosmo
 * @version		: 1.0
 * @Date		: 2015年11月27日 下午7:59:51
 */
public class FilterResult {

	private Collection<?> collection;
	
	private String[] filterPropertyNames;

	public FilterResult(){}
	
	public FilterResult(Collection<?> collection, String... filterPropertyNames){
		this.collection = collection;
		this.filterPropertyNames = filterPropertyNames;
	}
	
	/**  
	 * collection  
	 *  
	 * @return  the collection  
	 * @since   1.0.0  
	 */
	
	public Collection<?> getCollection() {
		return collection;
	}

	/**  
	 * @param collection the collection to set  
	 */
	public void setCollection(Collection<?> collection) {
		this.collection = collection;
	}

	/**  
	 * filterPropertyNames  
	 *  
	 * @return  the filterPropertyNames  
	 * @since   1.0.0  
	 */
	
	public String[] getFilterPropertyNames() {
		return filterPropertyNames;
	}

	/**  
	 * @param filterPropertyNames the filterPropertyNames to set  
	 */
	public void setFilterPropertyNames(String[] filterPropertyNames) {
		this.filterPropertyNames = filterPropertyNames;
	}
	
}
