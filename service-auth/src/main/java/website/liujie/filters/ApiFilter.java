package website.liujie.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import website.liujie.util.CookieUtil;
import website.liujie.util.jedis.RedisUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * API过滤器
 * @author 刘杰
 * @date 2018-12-24
 */
@Slf4j
@Component
public class ApiFilter extends ZuulFilter {

    @Resource
    public RedisUtil redisUtil;

    @Override
    public String filterType() {
        return "pre";  //枚举值：pre, routing, post, error
    }

    @Override
    public int filterOrder() {
        return 0;    //优先级， 0是最高优先级即最先执行
    }

    @Override
    public boolean shouldFilter() {
        //return true;  //写逻辑，是否需要执行过滤。true会执行run函数，false不执行run函数

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest  request = requestContext.getRequest();

        //公共页面不进入过滤器
        if (request.getRequestURI().contains("/public/")){
            return false;
        }

        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("-----------------------------------ApiFilter 开始--------------------------------");

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s %s", request.getMethod(), request.getRequestURL().toString()));

        String accessToken = CookieUtil.getCookieValue(request,"token");
        String errorMsg="";
        if(accessToken == null){
            errorMsg="token is empty";
            log.error(errorMsg);
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write(errorMsg);
            }catch (Exception e){
                log.error("ApiFilter exception",e);
            }
            return null;
        }

        if(null==redisUtil.get(String.valueOf(accessToken))){
            errorMsg="token已过期";
            log.error(errorMsg);
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write(errorMsg);
            }catch (Exception e){
                log.error("ApiFilter exception",e);
            }
            return null;
        }

        return null;
    }
}
