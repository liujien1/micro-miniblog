package website.liujie.service;

import website.liujie.common.base.BaseService;
import website.liujie.entity.User;

import java.util.Map;

/**
 * @Description :
 * @Copyright : Excenon. ALL Rights Reserved
 * @Company : jie
 * @Author : liujie
 * @Version : 1.0
 * @Date : 2016/9/27 0027
 */
public interface UserService extends BaseService<User> {

    /**
     * 保存登陆用户信息
     * @param userInfo
     */
    public String saveLoginUserInfo(User userInfo);

}