package website.liujie.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import website.liujie.common.SystemConstant;
import website.liujie.common.base.BaseController;
import website.liujie.entity.User;
import website.liujie.service.UserService;
import website.liujie.util.*;
import website.liujie.util.jedis.RedisUtil;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * UserIndexService - 用户主页类
 */
@RestController
@RequestMapping
@Slf4j
public class UserIndexController extends BaseController {

    @Resource
    public RedisUtil redisUtil;

    @Resource
    private UserService userService;

    /**
     * 登录页面
     */
    @RequestMapping("/public/loginPage")
    public Object login(HttpServletRequest request, Map<String, Object> map) {

        Map<String,Object> result=new HashMap<>();
        String ip = request.getRemoteAddr();
        Integer loginCount = (Integer) redisUtil.getObject(ip + "_loginCount");
        if (null != loginCount && 0 != loginCount) {
            if (loginCount > 3){
                result.put("isShowVerifyCode",true);
            }
        }else{
            redisUtil.setObject(ip + "_loginCount",0);
        }
        return result;
    }

    /**
     * 登陆
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/public/dologin")
    public Object dologin(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password,
                          HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> result = new HashMap<>();
        String verifyCode = request.getParameter("verifyCode");

        String uuid = CookieUtil.getCookieValue(request,"uuid");
        if(uuid.equals("")){
            result.put("code","0003");
            result.put("msg", "非法请求！");
            return result;
        }

        String ip = request.getRemoteAddr();
        Integer loginCount = (Integer) redisUtil.getObject(ip + "_loginCount");
        Date loginTime = (Date) redisUtil.getObject(ip + "_loginTime");

        if(null!=loginCount) {
            if (loginCount > 3 && loginCount <= 8) {
                String saveVerifyCode = redisUtil.get(uuid);
                if (!(ObjectUtil.isNotEmpty(verifyCode) && ObjectUtil.isNotEmpty(saveVerifyCode) && verifyCode.equalsIgnoreCase(saveVerifyCode))) {
                    result.put("code", "0001");
                    result.put("msg", "验证码错误！");
                    return result;
                }
            } else if (loginCount > 8) {
                if (loginTime.after(DateUtil.getDateMoveByMinute(Calendar.getInstance().getTime(), -15))) {
                    result.put("code", "0004");
                    result.put("msg", "请求已锁定，请15分钟后再试！");
                    return JSONObject.toJSONString(result);
                } else {
                    loginCount = 0;
                    redisUtil.setObject(ip + "_loginCount", 0);
                }
            }
        }

        User query = new User();
        query.setName(username);
        query.setPassword(MD5Coder.getMD5String(SystemConstant.PASSWORD_PRE + password));
        User user = userService.query(query);

        if (ObjectUtil.isNotEmpty(user)) {
            User userInfo=new User();
            userInfo.setName(user.getName());
            userInfo.setId(user.getId());
            String token=userService.saveLoginUserInfo(userInfo);
            result.put("userInfo",userInfo);
            result.put("token",token);
            result.put("code", "0000");
            log.info(userInfo.getId()+":登陆成功！");
            redisUtil.setObject(ip + "_loginCount", 0);

        } else {
            result.put("code","0002");
            result.put("msg", "用户名或密码不存在！");
            redisUtil.setObject(ip + "_loginCount",loginCount+1);
        }

        return result;
    }


    /**
     * 退出登陆
     */
    @RequestMapping("/private/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response) {

        String token = CookieUtil.getCookieValue(request,"token");
        redisUtil.del(token);
        CookieUtil.removeCookie(response,new Cookie("userInfo",null));
        CookieUtil.removeCookie(response,new Cookie("token",null));
        log.info(token+":注销登录！");
        return "SUCCESS";
    }



}
