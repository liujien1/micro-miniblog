package website.liujie.service;

import website.liujie.common.base.BaseService;
import website.liujie.entity.BloCategory;

import java.util.List;

/**
 * @Description :
 * @Copyright : Excenon. ALL Rights Reserved
 * @Company : jie
 * @Author : liujie
 * @Version : 1.0
 * @Date : 2016/9/28 0028
 */
public interface BloCategoryService extends BaseService<BloCategory>{

    /**
     * 获取文章类型列表
     * @param userid
     * @return
     */
    public List<BloCategory> getByUserid(int userid);
}
