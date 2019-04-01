package website.liujie.service.impl;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.liujie.common.base.BaseServiceImpl;
import website.liujie.common.page.PageInterceptor;
import website.liujie.common.page.PageModel;
import website.liujie.dao.SysFileDao;
import website.liujie.entity.SysFile;
import website.liujie.service.SysFileService;
import website.liujie.util.SqlUtil;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 *  系统文件存储
 */
@Service
public class SysFileServiceImpl extends BaseServiceImpl<SysFile> implements SysFileService {

    // blogDao操作类
    @Autowired
    private SysFileDao sysFileDao;

    @Override
    public PageModel<SysFile> queryForPage(@Param(PageInterceptor.PAGE_KEY) PageModel<SysFile> p) {
        return null;
    }

    @Override
    public long add(SysFile entity) {
        return sysFileDao.add(entity);
    }

    @Override
    public void update(SysFile entity) {

    }

    @Override
    public void delete(SysFile entity) {

    }

    @Override
    public void deleteById(Serializable id) {

    }

    @Override
    public SysFile query(SysFile entity) {
        return null;
    }

    @Override
    public SysFile queryById(Serializable id) {
        return null;
    }

    @Override
    public List<SysFile> queryForList(SysFile entity) {
        return null;
    }

    @Override
    public void updateOfPid(Integer pid, String[] filesId) {
        String filesidCondition=SqlUtil.buildInSubSql(Arrays.asList(filesId));
        sysFileDao.updateOfPid(pid,filesidCondition);
    }
}
