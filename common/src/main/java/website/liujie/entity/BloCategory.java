package website.liujie.entity;

import website.liujie.common.base.BaseBean;

/**
* @Description	: 博客类型
* @Copyright	: Excenon. ALL Rights Reserved
* @Company		: 深圳市华磊移动设备科技有限公司
* @Author		: liujie
* @Version		: 1.0
* @Date			: 2016-09-28 11:35:53
*/
public class BloCategory extends BaseBean {

	/**  */
	private String name;
	/**  */
	private Integer userid;

	private Integer num;

	/**
	 *方法: 取得Name
	 *@return: java.lang.String  Name
	 */
	public String getName() {
		return name;
	}

	/**
	*方法: 设置Name
	*@return: void  Name
	*/
	public void setName(String name) {
		this.name = name;
	}
	/**
	 *方法: 取得Userid
	 *@return: java.lang.Integer  Userid
	 */
	public Integer getUserid() {
		return userid;
	}

	/**
	*方法: 设置Userid
	*@return: void  Userid
	*/
	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
}
