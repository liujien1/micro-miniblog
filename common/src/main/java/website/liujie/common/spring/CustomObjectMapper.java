package website.liujie.common.spring;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description	: 
 * @Copyright	: Excenon. ALL Rights Reserved
 * @Company		: jie 
 * @author		: Cosmo
 * @version		: 1.0
 * @Date		: 2015年11月28日 上午11:24:21
 */
public class CustomObjectMapper extends ObjectMapper {
	
	private static final long serialVersionUID = 1L;
	
	private static String pattern = "yyyy-MM-dd HH:mm:ss";
	
	public CustomObjectMapper(){
		//according to the Serializer factory to register the date format pattern
		this.setDateFormat(new SimpleDateFormat(pattern));
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

	/**  
	 * @param pattern the pattern to set  
	 */
	public void setPattern(String patternE) {
		pattern = patternE;
	}
	
	//here method set the filter the property form bean 
	//so the SimpleBeanPropertyFilter will be create and just create one instance for the simple filter
	public static CustomObjectMapper getFilterObjectMapperInstance(Class<?> clz,String[] filterPropertyNames){
		SimpleFilterProvider filters = new SimpleFilterProvider().addFilter(clz.getName(), SimpleBeanPropertyFilter.serializeAllExcept(filterPropertyNames));
		//fixed the bug 	at com.fasterxml.jackson.databind.filter.TestOther.main(TestOther.java:43)
		//Caused by: java.lang.IllegalArgumentException: No filter configured with id 'com.fasterxml.jackson.databind.filter.Gender' (type java.lang.String)
		filters.setFailOnUnknownId(false);
		CustomObjectMapper om = new CustomObjectMapper();
		om.setFilters(filters);
		om.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
			private static final long serialVersionUID = 944241454906882230L;
			@Override
			public Object findFilterId(Annotated ac) {
				Object id = super.findFilterId(ac);
				if(ac instanceof AnnotatedMethod){
					return null;
				}
				// but use simple class name if not
			    if (id == null) {
			       id = ac.getName();
			    }
			    return id;
			}
		});
		return om;
	}
	
	@Deprecated
	private static boolean isNotVariableJavaType(Field f){
		if(f==null){
			return true;
		}
		boolean flag = true;
		Class<?> cls =f.getType();
		if(cls == String.class){
			flag = false;
		}else if(cls == char.class || cls == Character.class){
			flag = false;
		}else if(cls == int.class || cls == Integer.class){
			flag = false;
		}else if(cls == short.class || cls == Short.class){
			flag = false;
		}else if(cls == long.class || cls == Long.class){
			flag = false;
		}else if(cls == float.class || cls == Float.class){
			flag = false;
		}else if(cls == double.class || cls == Float.class){
			flag = false;
		}else if(cls == byte.class || cls == Byte.class){
			flag = false;
		}else if(cls == boolean.class || cls == Boolean.class){
			flag = false;
		}else if(cls == Date.class){
			flag = false;
		}else{
			flag = true;
		}
		return flag;
	}
	
	@Deprecated
	public static CustomObjectMapper getFilterObjectMapperInstance2(Object obj, String[] filterPropertyNames){
		SimpleFilterProvider f = new SimpleFilterProvider();
		f.addFilter(obj.getClass().getName(), SimpleBeanPropertyFilter.serializeAllExcept(filterPropertyNames));
		//FilterProvider filters = new SimpleFilterProvider().addFilter(obj.getClass().getName(), SimpleBeanPropertyFilter.serializeAllExcept(filterPropertyNames));
		Field[] fields = obj.getClass().getDeclaredFields();
		for(Field field : fields){
			if(isNotVariableJavaType(field)){				
				f.addFilter(field.getType().getName(), SimpleBeanPropertyFilter.serializeAllExcept(filterPropertyNames));
			}
		}
		
		CustomObjectMapper om = new CustomObjectMapper();
		om.setFilters(f);
		om.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
			private static final long serialVersionUID = 944241454906882230L;
			@Override
			public Object findFilterId(Annotated ac) {
				Object id = super.findFilterId(ac);
				// but use simple class name if not
			    if (id == null) {
			       id = ac.getName();
			    }
			    return id;
			}
		});
		return om;
	}
	
}
