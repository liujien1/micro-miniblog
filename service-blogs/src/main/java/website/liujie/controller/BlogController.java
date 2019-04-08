package website.liujie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import website.liujie.common.base.BaseController;
import website.liujie.common.page.PageModel;
import website.liujie.entity.Blog;
import website.liujie.entity.User;
import website.liujie.service.BlogService;
import website.liujie.util.CookieUtil;
import website.liujie.util.ObjectUtil;
import website.liujie.util.jedis.RedisUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BlogService - 显示博客内容
 * @author liujie
 */
@RequestMapping
@RestController
public class BlogController extends BaseController {

    @Autowired
    private BlogService blogService;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 获取博客信息
     * @param p
     * @param request
     * @return
     */
    @RequestMapping("/public/getBlog")
    public Object getBlog(@RequestParam(value = "p") int p, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Blog blog = blogService.queryById(p);
        blog.setText("");
        map.put("blog", blog);
        return map;
    }

    /**
     * 获取博客内容
     * @param p
     * @param request
     * @return
     */
    @RequestMapping("/public/getBlogText")
    public Object getBlogText(@RequestParam(value = "p") int p, HttpServletRequest request) {

        String token= CookieUtil.getCookieValue(request,"token");

        Blog blog = blogService.queryById(p);
        if(token==null && blog.getIshidden().equals(1)) {
            return "";
        }
        if(token!=null) {
            User user = (User) redisUtil.getObject(token);

            if (null == user && blog.getIshidden().equals(1)) {
                return "";
            }
            if (blog.getIshidden().equals(1) && !String.valueOf(blog.getAuthorId()).equals(user.getId())) {
                return "";
            }
        }


        blogService.updateReadCount(Integer.parseInt(blog.getId()), blog.getReadCount() + 1);
        return blog.getText();
    }

    /**
     * 获取博客列表
     * @param request
     * @param entity
     * @return
     */
    @RequestMapping("/public/getBlogList")
    public Object getBlogList(HttpServletRequest request,Blog entity) {
        PageModel<Blog> queryPage=initPageObject(request,entity);
        if(ObjectUtil.isNotEmpty(entity.getExtendSearch())){
            request.setAttribute("extendSearch",entity.getExtendSearch());
        }

        //未登录只能查看公开文章
        queryPage.getBeanParams().setIshidden(0);
        PageModel<Blog> page=blogService.queryForPage(queryPage);
        for (Blog blog : page.getResult()) {
            blog.setText("");
        }

        Map<String,Object> result=new HashMap<>();
        result.put("qContext",page);
        return result;
    }

    /**
     * 获取阅读排行
     * @param request
     * @param entity
     * @return
     */
    @RequestMapping("/public/getReadTops")
    public Object getReadTops(HttpServletRequest request,Blog entity) {

        //阅读排行
        Blog temp=new Blog();
        temp.setExtendSize(10);
        temp.setExtendSortCol("readCount");
        temp.setIshidden(0);
        List<Blog> readTops=blogService.queryForList(temp);
        Map<String,Object> result=new HashMap<>();
        result.put("readTops",readTops);
        return result;
    }

}
