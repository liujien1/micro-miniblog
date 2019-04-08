package website.liujie.common;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @Description :mybatis自动扫描注解
 * @Copyright : Excenon. ALL Rights Reserved
 * @Company : jie
 * @Author : liujie
 * @Version : 1.0
 * @Date : 2016/9/28 0028
 */
@Component
@Target(ElementType.TYPE)
public @interface Mybatis {

}
