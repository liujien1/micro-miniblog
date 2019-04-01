package website.liujie.util;

import website.liujie.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * SessionUtil - Session脚手架类
 */
public class SessionUtil {

    /**
     * 从request中获取UserId的Session
     */
    public static User getUserSession(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }

    /**
     * 往request中设置Session
     */
    public static void addUserSession(HttpServletRequest request, User user) {
        request.getSession().setAttribute("user", user);
    }

    /**
     * 往session中设置属性
     */
    public static void setAttr(HttpServletRequest request, String key, Object value) {
        request.getSession().setAttribute(key, value);
    }

    /**
     * 获取session中属性
     */
    public static Object getAttr(HttpServletRequest request, String key) {
        return request.getSession().getAttribute(key);
    }

    /**
     * 从request中删除UserID的Session
     */
    public static void delUserSession(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
    }

}
