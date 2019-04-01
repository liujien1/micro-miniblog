package website.liujie.dao;

import org.apache.ibatis.annotations.Mapper;
import website.liujie.common.base.BaseDao;
import website.liujie.entity.BloCategory;

import java.util.List;

/**
 * BlogDao - 博客类型
 */
//@Mybatis
@Mapper
public interface BloCategoryDao extends BaseDao<BloCategory> {

    public List<BloCategory> getByUserid(int userid);
}
