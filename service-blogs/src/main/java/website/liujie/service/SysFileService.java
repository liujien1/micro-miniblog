package website.liujie.service;

import website.liujie.common.base.BaseService;
import website.liujie.entity.SysFile;

/**
 * @Description :
 * @Copyright : Excenon. ALL Rights Reserved
 * @Company : jie
 * @Author : liujie
 * @Version : 1.0
 * @Date : 2016/9/27 0027
 */
public interface SysFileService extends BaseService<SysFile>{

    /**
     * 更新所属文章id
     * @param pid
     * @param filesId
     */
    public void updateOfPid(Integer pid, String[] filesId);
}
