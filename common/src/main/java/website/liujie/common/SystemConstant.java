package website.liujie.common;

import website.liujie.util.PropertyUtils;

/**
 * @Description	: 系统常量
 * @Copyright	: Excenon. ALL Rights Reserved
 * @Company		: 深圳市华磊移动设备科技有限公司 
 * @author		: Cosmo
 * @version		: 1.0
 * @Date		: 2015年11月27日 下午7:56:26
 */
public final class SystemConstant {
	
	/**分页常量--当前页数*/
	public static final String PAGEINDEX = "pageIndex";
	/**分页常量--每页大小*/
	public static final String PAGESIZE = "pageSize";
	
	/**三种数据库类型常量*/
	public static final String DATABASE_ORACLE = "ORACLE";
	public static final String DATABASE_MYSQL = "MYSQL";
	public static final String DATABASE_SQLSERVER = "SQLSERVER";
	/**默认使用的数据库的类型常量*/
	public static final String DEFAULT_DIALECT = DATABASE_MYSQL;
	
	/**返回客户端消息的常量*/
	public static final String DATA = "data";//数据前缀
	public static final String RESULT = "result";//返回结果前缀
	public static final String FAIL = "fail";//返回失败前缀
	public static final String MESSAGE = "message";//返回消息前缀
	public static final String ERRORCODE = "errorcode";//返回异常编码
	public static final String ERRORMESSAGE = "errormessage";//返回异常消息
	public static final String EXCEPTIONMESSAGE = "System error,please contact administrator.";//返回异常信息
	
	/** 存放在session中登录用户的user对象的标记 */
	public static final String LOGINUSERMARKER = "LOGINUSERMARKER";//用户登录标记
	public static final String ACCESS_TOKEN = "USERTOKEN";//用户TOKEN
	public static final String ENCRYPT_KEY = "ENCRYPTKEY";//用户加密标记
	public static final String SIGN_KEY = "sign";//用户签名标记
	public static final String APPID_KEY = "excenon.com";//用户APP Key
	
	/** 缓存中系统参数KEY前缀 */
	public static final String CACHE_PARAM_PROFIX = "SYS_PARAM_";
	
	/** PUB_SYSTEM_PARAM表中的参数名 **/
	public static final String TOPID_XINGHE = "TOPID_XINGHE";//总机构ID-星河
	public static final String USERTOKEN_EFF_TIME_LENGTH = "USERTOKEN_EFF_TIME_LENGTH";// token有效时长（现在业务不以这个为准，备用）
	public static final String SERVER_ENVIR = "SERVER_ENVIR"; // 当前服务器环境
	public static final String PROCESS_TIME_OUT = "PROCESS_TIME_OUT"; // 流程某些节点超时时长
	public static final String PROCESS_EVALUATE_TIME_OUT = "PROCESS_EVALUATE_TIME_OUT"; // 流程结束用户未评价超时时长
	
	/**系统用户密码MD5加密前缀*/
	public static final String PASSWORD_PRE = "liu123";//密码加密前缀

	/**
	 *系统配置
	 */
	public static final String SYS_PROPERTIES_NAME="config.properties";

	/**
	 * 上传文件类型
	 */
	public static final Integer UPLOAD_FILE_TYPE_IMG=0;//图片
	public static final Integer UPLOAD_FILE_TYPE_ATTACH=1;//附件

	//git仓库地址
	public static final String blogsFolderPath="BLOGSFOLDERPATH";



	/**
	 * 系统DES密钥相关常量
	 */
	public static final String DES_KEY_DEFAULT = getKey();

	public static final String getKey() {
		String desKey= PropertyUtils.get("DESKEY");
		return desKey;
	}
}