package website.liujie.common;

/**
 * @Description	: 
 * @Copyright	: Excenon. ALL Rights Reserved
 * @Company		: 深圳市华磊移动设备科技有限公司 
 * @author		: Cosmo
 * @version		: 1.0
 * @Date		: 2015年11月27日 下午8:01:14
 */
public class CheckResult {
	
	/**错误码*/
	private int errorCode;
	/**信息内容*/
	private String message;
	/**对象*/
	private Object[] data;
	/**操作是否成功*/
	private boolean isError = false;
	 
	/**
	 * 错误的构造
	 * 创建一个新的实例 CheckResult.  
	 *  
	 * @param errorCode
	 * @param errorMessage
	 */
	public CheckResult(int errorCode,String errorMessage) {
		this.errorCode = errorCode;
		this.message = errorMessage;
		this.isError = true;
	}
	
	public CheckResult(String message,Object... obj){
		this.data = obj;
		this.message = message;
	}

	public CheckResult(String message,boolean isError,Object... obj){
		this.data = obj;
		this.message = message;
		this.isError = isError;
	}

	/**  
	 * errorCode  
	 *  
	 * @return  the errorCode  
	 * @since   1.0.0  
	 */
	
	public int getErrorCode() {
		return errorCode;
	}

	/**  
	 * @param errorCode the errorCode to set  
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**  
	 * message  
	 *  
	 * @return  the message  
	 * @since   1.0.0  
	 */
	
	public String getMessage() {
		return message;
	}

	/**  
	 * @param message the message to set  
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	

	/**  
	 * data  
	 *  
	 * @return  the data  
	 * @since   1.0.0  
	 */
	
	public Object[] getData() {
		return data;
	}

	/**  
	 * @param data the data to set  
	 */
	public void setData(Object[] data) {
		this.data = data;
	}

	/**  
	 * isError  
	 *  
	 * @return  the isError  
	 * @since   1.0.0  
	 */
	
	public boolean isError() {
		return isError;
	}

	/**  
	 * @param isError the isError to set  
	 */
	public void setError(boolean isError) {
		this.isError = isError;
	}
	
	
}
