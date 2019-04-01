/*
package website.liujie.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

*/
/**
 * 请求过滤器
 * @author 刘杰
 * @date 2018-12-20
 *//*

@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/*",filterName = "loginFilter")
@Slf4j
public class RequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        log.info("【允许跨域访问】refererUrl【{}】", request.getHeader("Referer"));
        //允许跨域访问
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "0");
        response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,Authorization,SessionToken,JSESSIONID,token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("XDomainRequestAllowed","1");
        filterChain.doFilter(request, response);
    }
    public static String getRefererUrl(HttpServletRequest request){
        String returnValue = null;
        if(request != null && !StringUtils.isEmpty(request.getHeader("Referer"))) {
            returnValue = request.getHeader("Referer");
            String[] urls = returnValue.split("/");
            if(urls != null && urls.length > 3) {
                returnValue = urls[0] + "//" + urls[2];
            }
        }
        return returnValue;
    }

    @Override
    public void destroy() {

    }
}
*/
