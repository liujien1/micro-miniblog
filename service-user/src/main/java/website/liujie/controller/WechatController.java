package website.liujie.controller;

import com.alibaba.fastjson.JSON;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import website.liujie.config.CommonConfig;
import website.liujie.entity.User;
import website.liujie.service.UserService;
import website.liujie.util.AuthUtil;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统类
 * Created by liujie on 2017-3-22.
 */
@Controller
@RequestMapping
public class WechatController {


    @Resource
    private UserService userService;

    /**
     * 微信登陆
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/public/wx/doLogin")
    protected void doLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String backUrl="http://www.xyblogs.site:38152/service-user/public/wx/getUserInfo";
        /**
         *这儿一定要注意！！首尾不能有多的空格（因为直接复制往往会多出空格），其次就是参数的顺序不能变动
         **/
        String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + AuthUtil.APPID+
                "&redirect_uri=" + URLEncoder.encode(backUrl,"UTF-8")+
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=STATE#wechat_redirect";
        resp.sendRedirect(url);
    }

    /**
     * 微信回调获取用户信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/public/wx/getUserInfo")
    protected void getUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code=req.getParameter("code");
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AuthUtil.APPID+
                "&secret=" +AuthUtil.APPSECRET+
                "&code=" +code+
                "&grant_type=authorization_code";
        JSONObject jsonObject = AuthUtil.doGetJson(url);
        String openid=jsonObject.getString("openid");
        String token=jsonObject.getString("access_token");
        String infoUrl="https://api.weixin.qq.com/sns/userinfo?access_token=" +token+
                "&openid=" +openid+
                "&lang=zh_CN";
        JSONObject userInfo=AuthUtil.doGetJson(infoUrl);
        System.out.println(userInfo);
        Map<String,Object> result=saveWxUserInfo(userInfo);

        User cookUser=new User();
        cookUser.setName(userInfo.getString("nickname"));
        Cookie cookieUserInfo=new Cookie("userInfo", URLEncoder.encode(JSON.toJSONString(result.get("user"))));
        cookieUserInfo.setMaxAge(1000*60*60*2);
        cookieUserInfo.setHttpOnly(true);
        cookieUserInfo.setPath("/");
        //cookieUserInfo.setDomain("47.89.11.154");

        Cookie cookieToken=new Cookie("token", String.valueOf(result.get("token")));
        cookieToken.setMaxAge(1000*60*60*2);
        cookieToken.setHttpOnly(true);
        cookieToken.setPath("/");
        //cookieToken.setDomain("47.89.11.154");

        resp.addCookie(cookieUserInfo);
        resp.addCookie(cookieToken);
        resp.sendRedirect(CommonConfig.WEB_INDEX);
    }


    /**
     * 保存微信用户信息
     * @param userInfo
     */
    private Map<String,Object> saveWxUserInfo(JSONObject userInfo){

        Map<String,Object> result=new HashMap<>();

        User returnUser=new User();

        User queryModel=new User();
        queryModel.setName(userInfo.getString("nickname"));
        queryModel.setPassword(userInfo.getString("openid"));
        User queryResult=userService.query(queryModel);

        String token="";
        if(null==queryResult){
            userService.add(queryModel);
            User newModel=new User();
            newModel.setName(queryModel.getName());
            newModel.setPassword(queryModel.getPassword());
            returnUser=newModel;
        }else{
            returnUser=queryResult;

        }
        token=userService.saveLoginUserInfo(returnUser);

        result.put("user",returnUser);
        result.put("token",token);
        return result;
    }
}
