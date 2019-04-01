package website.liujie.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.liujie.common.base.BaseServiceImpl;
import website.liujie.common.page.PageInterceptor;
import website.liujie.common.page.PageModel;
import website.liujie.dao.UserDao;
import website.liujie.entity.User;
import website.liujie.service.UserService;
import website.liujie.util.CommonUtils;
import website.liujie.util.jedis.RedisUtil;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * UserService - 用户主页类
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{

    // blogDao操作类
    @Autowired
    private UserDao userDao;

    @Resource
    public RedisUtil redisUtil;

    @Override
    public long add(User entity) {
        return userDao.add(entity);
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteById(Serializable id) {

    }

    @Override
    public User query(User entity) {
        return userDao.query(entity);
    }

    @Override
    public User queryById(Serializable id) {
        return userDao.queryById(id);
    }

    @Override
    public List<User> queryForList(User entity) {
        return null;
    }

    @Override
    public PageModel<User> queryForPage(@Param(PageInterceptor.PAGE_KEY) PageModel<User> p) {
        return null;
    }

    @Override
    public String saveLoginUserInfo(User userInfo){
        String token= CommonUtils.getToken(userInfo);
        //设置token1小时过期时间
        redisUtil.setObject(token,60*60,userInfo);
        return token;
    }
}
