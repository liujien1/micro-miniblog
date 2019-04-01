package website.liujie.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import website.liujie.common.base.BaseDao;
import website.liujie.entity.SysFile;

/**
 * SysFileDao - 系统文件存储
 */
//@Mybatis
@Mapper
public interface SysFileDao extends BaseDao<SysFile> {

    /**
     * 更新所属文章id
     * @param pid
     * @param filesidCondition
     */
    public void updateOfPid(@Param("pid") Integer pid, @Param("filesidCondition") String filesidCondition);
}
