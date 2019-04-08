package website.liujie.common;

/**
 * @Description	: 基本异常和错误CODE
 * @Copyright	: Excenon. ALL Rights Reserved
 * @Company		: jie 
 * @author		: Cosmo
 * @version		: 1.0
 * @Date		: 2015年11月27日 下午8:00:02
 */
public class ErrorCode {

	/**一般异常*/
	public static final int GENERIC_EXCEPTION_CODE = 1000;
	/**返回错误*/
	public final static int RESULT_ERROR_CODE = 2000;
	/**用户未登陆*/
	public final static int USER_UNLOGINE_ERROR = 3000;
	/**用户token过期*/
	public final static int TOKEN_IS_OUT_DATE = 3001;
	/**请求签名错误*/
	public final static int CLIENT_SIGN_ERROR = 3002;
	/**参数为空*/
	public final static int PARAMETER_IS_NULL = 4000;
	/** 重复提交,重复操作 */
	public static final int SUBMIT_OPERATOR_AGAIN = 4001;
	/** 当前时间在结束时间之后（当前时间大于结束时间） */
	public static final int DATE_NOWTIME_AFTER_ENDTIME = 5000;
	/** 开始时间在结束时间之后（开始时间大于结束时间） */
	public static final int DATE_STARTTIME_AFTER_ENDTIME = 5001;
	/** 操作限制 */
	public static final int OPERATING_RESTRICTIONS = 6000;
	/** 活动报名人数已满 */
	public static final int NEIACTIVITY_QUOTA_IS_FULL = 7000;
	/**输入验证码错误	 */
	public static final int INPUT_VERIFICATION_ERROR = 8000;
}
