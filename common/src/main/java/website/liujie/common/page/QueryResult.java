package website.liujie.common.page;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import website.liujie.common.base.BaseBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description	: 
 * @Copyright	: Excenon. ALL Rights Reserved
 * @Company		: 深圳市华磊移动设备科技有限公司 
 * @author		: Cosmo
 * @version		: 1.0
 * @Date		: 2015年11月28日 上午10:46:34
 * @param <T>
 */
public class QueryResult<T extends BaseBean> {
	
	/**
	 * pagination result set
	 * data set
	 * */
	private List<?> result = new ArrayList<T>();
	
	/**
	 * total size
	 * */
	private int totalSize = 0;
	
	/**
	 * 其它参数
	 */
	private Map<String,Object> resultParam = new HashMap<String, Object>();
	
	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		if(!CollectionUtils.isEmpty(result)){
			this.result = result;
		}
	}
	
	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public Map<String, Object> getResultParam() {
		return resultParam;
	}

	public void setResultParam(Map<String, Object> resultParam) {
		if(resultParam != null) this.resultParam.putAll(resultParam);
	}
	
	public void addResoutParam(String key, Object value){
		this.resultParam.put(key, value);
	}
	
}
