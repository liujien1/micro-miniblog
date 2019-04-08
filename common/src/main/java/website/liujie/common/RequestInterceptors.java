package website.liujie.common;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import website.liujie.entity.User;
import website.liujie.util.ObjectUtil;
import website.liujie.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description :请求拦截器
 * @Copyright : Excenon. ALL Rights Reserved
 * @Company : jie
 * @Author : liujie
 * @Version : 1.0
 * @Date : 2016/9/27 0027
 */
public class RequestInterceptors extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        User user = SessionUtil.getUserSession(httpServletRequest);
        if(ObjectUtil.isEmpty(user)){
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login");
            return true;
        }

        return super.preHandle(httpServletRequest, httpServletResponse, o);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        super.postHandle(httpServletRequest, httpServletResponse, o, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        super.afterCompletion(httpServletRequest, httpServletResponse, o, e);
    }
}
