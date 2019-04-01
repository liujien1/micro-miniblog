package website.liujie.dao;

import org.apache.ibatis.annotations.Mapper;
import website.liujie.common.base.BaseDao;
import website.liujie.entity.User;

/**
 * UserDao - 用户操作类
 */
//@Mybatis
@Mapper
public interface UserDao extends BaseDao<User> {

}
