package website.liujie.service.impl;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.liujie.common.base.BaseServiceImpl;
import website.liujie.common.page.PageInterceptor;
import website.liujie.common.page.PageModel;
import website.liujie.dao.BloCategoryDao;
import website.liujie.entity.BloCategory;
import website.liujie.service.BloCategoryService;

import java.io.Serializable;
import java.util.List;

/**
 * @Description :
 * @Copyright : Excenon. ALL Rights Reserved
 * @Company : jie
 * @Author : liujie
 * @Version : 1.0
 * @Date : 2016/9/28 0028
 */
@Service
public class BloCategoryServiceImpl extends BaseServiceImpl<BloCategory> implements BloCategoryService {

    @Autowired
    public BloCategoryDao bloCategoryDao;


    @Override
    public List<BloCategory> getByUserid(int userid) {
        return bloCategoryDao.getByUserid(userid);
    }

    @Override
    public long add(BloCategory entity) {
         return bloCategoryDao.add(entity);
    }

    @Override
    public void update(BloCategory entity) {
        bloCategoryDao.update(entity);
    }

    @Override
    public void delete(BloCategory entity) {

    }

    @Override
    public void deleteById(Serializable id) {
        bloCategoryDao.deleteById(id);
    }

    @Override
    public BloCategory query(BloCategory entity) {
        return null;
    }

    @Override
    public BloCategory queryById(Serializable id) {
        return null;
    }

    @Override
    public List<BloCategory> queryForList(BloCategory entity) {
        return null;
    }

    @Override
    public PageModel<BloCategory> queryForPage(@Param(PageInterceptor.PAGE_KEY) PageModel p) {
        return null;
    }
}
