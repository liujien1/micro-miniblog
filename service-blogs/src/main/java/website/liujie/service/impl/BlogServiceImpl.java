package website.liujie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.liujie.common.base.BaseServiceImpl;
import website.liujie.common.page.PageModel;
import website.liujie.dao.BlogDao;
import website.liujie.dao.SysFileDao;
import website.liujie.entity.Blog;
import website.liujie.entity.SysFile;
import website.liujie.service.BlogService;
import website.liujie.util.MethodUtil;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * BlogService - 博客
 */
@Service
public class BlogServiceImpl extends BaseServiceImpl<Blog> implements BlogService{

    // blogDao操作类
    @Autowired
    private BlogDao blogDao;

    @Autowired
    private SysFileDao sysFileDao;


    @Override
    public List<Blog> getByUserid(int userid) {
        return blogDao.getByUserid(userid);
    }

    @Override
    public long add(Blog entity) {

        Date currDate=Calendar.getInstance().getTime();
        entity.setCreateTime(currDate);
        entity.setUpdateTime(currDate);
        return blogDao.add(entity);
    }

    @Override
    public List<Blog> queryForList(Blog entity) {

        List<Blog> blogs=blogDao.queryForList(entity);

        blogs.stream().forEach(s->{
            dealBlog(s);
        });
        return blogs;
    }

    @Override
    public Blog queryById(Serializable id) {
        Blog blog=blogDao.queryById(id);
        dealBlog(blog);
        return blog;
    }

    private void dealBlog(Blog blog){
        if(blog!=null && blog.getIshidden().equals(1)){
            blog.setPreview(MethodUtil.getDES(blog.getPreview(),1));
            blog.setText(MethodUtil.getDES(blog.getText(),1));
        }

        return;
    }

    @Override
    public void updateReadCount(int id,int readcount) {
        Blog entity=new Blog();
        entity.setId(String.valueOf(id));
        entity.setReadCount(readcount);
        blogDao.update(entity);
    }

    @Override
    public void delete(String basePath, Serializable blogid) {

        SysFile sysFile=new SysFile();
        sysFile.setPid(Integer.valueOf(String.valueOf(blogid)));

        //删除服务器文件
        List<SysFile> files=sysFileDao.queryForList(sysFile);
        for(SysFile file:files){
            String tempPath=basePath+File.separator+file.getPath();
            File f=new File(tempPath);
            if(f.exists()){
                f.delete();
            }
        }

        //删除数据库文件记录
        sysFileDao.delete(sysFile);

        //删除文章
        deleteById(blogid);

    }

    @Override
    public void deleteByFilePath(String filePath) {
        blogDao.deleteByFilePath(filePath);
    }

    @Override
    public void updateByFilePath(Blog blog) {
        blogDao.updateByFilePath(blog);
    }

    @Override
    public void saveOrUpdate(Blog blog) {
        blogDao.saveOrUpdate(blog);
    }

    @Override
    public PageModel<Blog> queryForPage(PageModel<Blog> queryPage){

        int count=blogDao.queryForPageCount(queryPage,queryPage.getBeanParams());
        queryPage.setTotalSize(count);

        List<Blog> blogs=blogDao.queryForPage(queryPage,queryPage.getBeanParams());

        blogs.stream().forEach(s->{
            dealBlog(s);
        });
        return super.queryForPage(queryPage,blogs);
    }

    @Override
    public void update(Blog entity) {
        entity.setUpdateTime(Calendar.getInstance().getTime());
        blogDao.update(entity);
    }

    @Override
    public void delete(Blog entity) {

    }

    @Override
    public void deleteById(Serializable id) {
        blogDao.deleteById(id);
    }

    @Override
    public Blog query(Blog entity) {
        return null;
    }

}
