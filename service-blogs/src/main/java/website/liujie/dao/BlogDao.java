package website.liujie.dao;

import org.apache.ibatis.annotations.Mapper;
import website.liujie.common.base.BaseDao;
import website.liujie.entity.Blog;

import java.util.List;

/**
 * BlogDao - 博客操作接口
 */
//@Mybatis
@Mapper
public interface BlogDao extends BaseDao<Blog>{

    /**
     * 获取文章列表
     * @param userid
     * @return
     */
    public List<Blog> getByUserid(int userid);

    /**
     * 根据更新时间更新最新的数据
     * @param blog
     */
    public void saveOrUpdate(Blog blog);

    /**
     * 按文件路径删除
     * @param filePath
     */
    public void deleteByFilePath(String filePath);

    /**
     * 按文件路径更新
     * @param blog
     */
    public void updateByFilePath(Blog blog);
}
