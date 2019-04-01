package website.liujie.common.base;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @Description	: 
 * @Copyright	: Excenon. ALL Rights Reserved
 * @Company		: 深圳市华磊移动设备科技有限公司 
 * @author		: Cosmo
 * @version		: 1.0
 * @Date		: 2015年11月27日 下午7:49:54
 */
public abstract class BaseBean implements Serializable, Cloneable, Comparable<BaseBean> {

	private static final long serialVersionUID = 1L;
	
	/**主键*/
	private String id;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public int compareTo(BaseBean o) {
		return CompareToBuilder.reflectionCompare(this, o);
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}
