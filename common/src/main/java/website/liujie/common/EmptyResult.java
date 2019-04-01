package website.liujie.common;

import website.liujie.common.base.BaseBean;

/**
 * @Description	: 
 * @Copyright	: Excenon. ALL Rights Reserved
 * @Company		: 深圳市华磊移动设备科技有限公司 
 * @author		: Cosmo
 * @version		: 1.0
 * @Date		: 2015年11月27日 下午7:49:12
 */
public class EmptyResult extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	private String mesg;
	public String getMesg() {
		return mesg;
	}

	public void setMesg(String mesg) {
		this.mesg = mesg;
	}
	
}
