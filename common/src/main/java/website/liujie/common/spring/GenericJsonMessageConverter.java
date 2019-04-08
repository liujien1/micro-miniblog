package website.liujie.common.spring;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import website.liujie.common.CheckResult;
import website.liujie.common.EmptyResult;
import website.liujie.common.FilterResult;
import website.liujie.common.SystemConstant;
import website.liujie.util.LogUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Description	: 公用的json数据转换器
 * @Copyright	: Excenon. ALL Rights Reserved
 * @Company		: jie 
 * @author		: Cosmo
 * @version		: 1.0
 * @Date		: 2015年11月28日 上午11:28:34
 */
public class GenericJsonMessageConverter extends
		MappingJackson2HttpMessageConverter {

	private static final LogUtil log = LogUtil.getLogUtil(GenericJsonMessageConverter.class);
	
	private void setNullPropertyValueToObj(String propetyName,Object target){
		if(target == null || StringUtils.isEmpty(propetyName)){
			return ;
		}
		String methodName = "set"+ StringUtils.capitalize(propetyName);
		try {
			Field field = null;
			try {
				field = target.getClass().getDeclaredField(propetyName);
			} catch (NoSuchFieldException e) {
				try {
					field = target.getClass().getSuperclass().getDeclaredField(propetyName);
				} catch (NoSuchFieldException e1) {
					log.error(e1.getMessage(),e1);
					return ;
				}
			}
			Class<?> clz = field.getType();
			Method method;
			try {
				method = target.getClass().getDeclaredMethod(methodName,clz);
			} catch (NoSuchMethodException e) {
				try {
					method = target.getClass().getSuperclass().getDeclaredMethod(methodName,clz);
				} catch (NoSuchMethodException e1) {
					log.error(e1.getMessage(),e1);
					return;
				}
			}
			try {
				//here need to sperator the case to apply the value.
				//eg : String : ""
				//eg : Object : null
				//eg : Date : null..
				Object obj = null;
				if(clz == String.class){
					obj = StringUtils.EMPTY;
				}else if(clz == char.class || clz == Character.class){
					obj = null;
				}else if(clz == int.class || clz == Integer.class){
					obj = null;
				}else if(clz == short.class || clz == Short.class){
					obj = null;
				}else if(clz == long.class || clz == Long.class){
					obj = null;
				}else if(clz == float.class || clz == Float.class){
					obj = null;
				}else if(clz == double.class || clz == Float.class){
					obj = null;
				}else if(clz == byte.class || clz == Byte.class){
					obj = null;
				}else if(clz == boolean.class || clz == Boolean.class){
					obj = null;
				}else if(clz == Date.class){
					obj = null;
				}else if(clz.isEnum()){
					obj = null;
				}else{
					obj = clz.newInstance();
				}
				method.invoke(target,obj);
			} catch (InstantiationException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | SecurityException e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
	}
	
	/* (non-Javadoc)  
	 * @see org.springframework.http.converter.AbstractHttpMessageConverter#readInternal(java.lang.Class, org.springframework.http.HttpInputMessage)  
	 */
	@Override
	protected Object readInternal(Class<? extends Object> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		return super.readInternal(clazz, inputMessage);
	}
	
	/* (non-Javadoc)  
	 * @see org.springframework.http.converter.json.MappingJacksonHttpMessageConverter#writeInternal(java.lang.Object, org.springframework.http.HttpOutputMessage)  
	 */
	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		Map<String,Object> message = new HashMap<String,Object>();
		if(object == null){
			message.put(SystemConstant.RESULT, true);
			message.put(SystemConstant.MESSAGE, StringUtils.EMPTY);
		}else{
			//here is out put the message according jackson json Util 
			boolean isCheckResult = object instanceof CheckResult;
			boolean isModelMapCheckResult = object instanceof ModelMap && ((ModelMap)object).get(SystemConstant.ERRORCODE)!=null ;
			isModelMapCheckResult = isModelMapCheckResult && ((ModelMap)object).get(SystemConstant.ERRORCODE) instanceof CheckResult;
			isCheckResult = isCheckResult || isModelMapCheckResult;
			if(isCheckResult){
				//checkResult format is error or succes message and object
				CheckResult result = null;
				if(isModelMapCheckResult){
					result = (CheckResult)((ModelMap)object).get(SystemConstant.ERRORCODE);
				}else{
					result = (CheckResult)object;
				}
				if(result.isError()){
					message.put(SystemConstant.ERRORCODE, result.getErrorCode());
					//message.put(SystemConstant.MESSAGE, result.getMessage());
				}
				message.put(SystemConstant.RESULT, !result.isError());
				message.put(SystemConstant.MESSAGE, result.getMessage());
				if(!ObjectUtils.isEmpty(result.getData())){
					message.put(SystemConstant.DATA, result.getData().length == 1 ? result.getData()[0] : result.getData());
				}
			} else if (object instanceof FilterResult){
				//the filter result can filter the collection to not output some properties
				FilterResult result = (FilterResult)object;
				if(result.getFilterPropertyNames()!=null && result.getFilterPropertyNames().length>0){
					String[] filterPropertyNames = result.getFilterPropertyNames();
					Collection<?> sresult = result.getCollection();
					Iterator<?> it = sresult.iterator();
					while(it.hasNext()){
						Object obj = it.next();
						for(int i = 0; i < filterPropertyNames.length; i ++){
							setNullPropertyValueToObj(filterPropertyNames[i], obj);
						}
					}
				}
				message.put(SystemConstant.RESULT, true);
				message.put(SystemConstant.DATA, CollectionUtils.isEmpty(result.getCollection()) ? new ArrayList<Object>(): result.getCollection());
				message.put(SystemConstant.MESSAGE, "operator success");
			} else if(object instanceof EmptyResult){
				message.put(SystemConstant.RESULT, true);
				message.put(SystemConstant.MESSAGE, "operator success");
				EmptyResult er = (EmptyResult)object;
				message.put(SystemConstant.DATA, StringUtils.isNotEmpty(er.getMesg()) ? er.getMesg() : StringUtils.EMPTY);
			}else{
				//here is normal output the object by json
				message.put(SystemConstant.RESULT, true);
				if(object!=null){
					message.put(SystemConstant.DATA, object);
				}
				message.put(SystemConstant.MESSAGE, "operator success");
			}
		}
		log.debug(message);
		super.writeInternal(message, outputMessage);
	}
	
}
