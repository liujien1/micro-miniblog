package website.liujie.common.base;

import org.apache.commons.lang.StringUtils;
import website.liujie.common.CheckResult;
import website.liujie.common.page.PageModel;
import website.liujie.entity.User;
import website.liujie.util.CookieUtil;
import website.liujie.util.MethodUtil;
import website.liujie.util.ObjectUtil;
import website.liujie.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description :
 * @Copyright : Excenon. ALL Rights Reserved
 * @Company : jie
 * @Author : liujie
 * @Version : 1.0
 * @Date : 2016/9/27 0027
 */
public abstract class BaseController<T extends BaseBean>{

    /**
     * buildSuccessResult (将构造成功返回的结果,通过正确消息和需要返回的数据对象)
     * @param msg;objs
     * @author Cosmo
     * @return CheckResult
     * @exception
     * @since  1.0.0
     */
    public CheckResult buildSuccessResult(String msg, Object... objs) {
        if (StringUtils.isEmpty(msg)) {
            return null;
        }
        CheckResult cr = new CheckResult(msg, objs);
        return cr;
    }

    /**
     * buildFailResult (将构造错误返回的结果,通过错误码和错误消息需要返回的数据对象)
     * @param errorCode;errorMessage
     * @author Cosmo
     * @return CheckResult
     * @exception
     * @since  1.0.0
     */
    public CheckResult buildFailResult(int errorCode, String errorMessage) {
        if (errorCode == 0 || StringUtils.isEmpty(errorMessage)) {
            return null;
        }
        CheckResult cr = new CheckResult(errorCode, errorMessage);
        return cr;
    }

    public CheckResult buildFailResult(String msg,Object... objs) {
        if (StringUtils.isEmpty(msg)) {
            return null;
        }
        CheckResult cr = new CheckResult(msg,true, objs);
        return cr;
    }

    public User getCurrentUser(HttpServletRequest request){
        User user = SessionUtil.getUserSession(request);
        return user;
    }

    public PageModel<T> initPageObject(HttpServletRequest request,T t){
        PageModel<T> page=new PageModel<>();
        page.setBeanParams(t);
        String pageIndex=request.getParameter("pageIndex");
        if(ObjectUtil.isNotEmpty(pageIndex)){
            page.setPageIndex(Integer.valueOf(pageIndex));
        }
        return page;
    }

    /**
     * 是否登陆
     * @param request
     * @return
     */
    public boolean isLogin(HttpServletRequest request){
        String token= CookieUtil.getCookieValue(request,"token");

        //已登录
        if(null!=token) {
            String userid = MethodUtil.getUserIdByToken(token);
            //用户不正确
            if (StringUtils.isEmpty(userid)) {
                return false;
            }
            //未登录
        }else{
            return false;
        }
        return true;
    }
}
