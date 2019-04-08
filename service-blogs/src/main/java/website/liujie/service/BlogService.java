package website.liujie.service;

import website.liujie.common.base.BaseService;
import website.liujie.entity.Blog;

import java.io.Serializable;
import java.util.List;

/**
 * @Description :
 * @Copyright : Excenon. ALL Rights Reserved
 * @Company : jie
 * @Author : liujie
 * @Version : 1.0
 * @Date : 2016/9/27 0027
 */

public interface BlogService extends BaseService<Blog>{

    /**
     * 获取列表
     * @param userid
     * @return
     */
    public List<Blog> getByUserid(int userid);

    /**
     * 更新阅读量
     */
    public void updateReadCount(int id, int readcount);

    /**
     * 删除文章
     * @param basePath
     * @param blogid
     */
    public void delete(String basePath, Serializable blogid);

    /**
     * 删除文章
     * @param filePath
     */
    public void deleteByFilePath(String filePath);

    /**
     * 更新文章
     * @param blog
     */
    public void updateByFilePath(Blog blog);

    /**
     * 根据更新时间更新最新的数据
     * @param blog
     */
    public void saveOrUpdate(Blog blog);
}
