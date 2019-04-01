package website.liujie.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import website.liujie.common.base.BaseController;
import website.liujie.common.page.PageModel;
import website.liujie.entity.BloCategory;
import website.liujie.entity.Blog;
import website.liujie.entity.User;
import website.liujie.service.BloCategoryService;
import website.liujie.service.BlogService;
import website.liujie.util.CookieUtil;
import website.liujie.util.HtmlRegexpUtil;
import website.liujie.util.jedis.RedisUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * PersonalController - 个人接口
 *
 * @author liujie
 */
@RequestMapping
@RestController
public class PersonalController extends BaseController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private BloCategoryService bloCategoryService;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 获取博客列表
     *
     * @param request
     * @param entity
     * @return
     */
    @RequestMapping("/private/personal/getMyArticleList")
    public Object getMyArticleList(HttpServletRequest request, Blog entity) {
        String token = CookieUtil.getCookieValue(request, "token");
        User user = (User) redisUtil.getObject(token);

        PageModel<Blog> queryPage = initPageObject(request, entity);
        queryPage.getBeanParams().setAuthorId(Integer.valueOf(user.getId()));

        PageModel<Blog> page = blogService.queryForPage(queryPage);
        for (Blog blog : page.getResult()) {
            blog.setText("");
        }

        Map<String, Object> result = new HashMap<>();
        result.put("qContext", page);
        return result;
    }

    /**
     * 添加文章
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/private/personal/addMyArticle")
    public String addMyArticle(HttpServletRequest request, HttpServletResponse response) {

        String token = CookieUtil.getCookieValue(request, "token");
        User user = (User) redisUtil.getObject(token);

        String title = request.getParameter("title");
        String text = request.getParameter("uploadContent");
        String ishidden = request.getParameter("ishidden");
        String editorType = request.getParameter("editorType");
        String fileText = HtmlRegexpUtil.filterHtml(HtmlRegexpUtil.fiterHtmlTag(text, "img"));//过滤标签
        String preview = fileText.length() > 100 ? fileText.substring(0, 100) : fileText;
        String category = request.getParameter("category");
        Date date = Calendar.getInstance().getTime();

        Blog blog = new Blog(Blog.ORIGINAL, title, user.getName(), preview, text, Integer.parseInt(category), date, "", 0, Integer.parseInt(user.getId()), Integer.parseInt(ishidden), Integer.parseInt(editorType));
        blogService.add(blog);
        return "SUCCESS";
    }

    /**
     * 修改文章
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/private/personal/updateMyArticle")
    public String updateMyArticle(HttpServletRequest request, HttpServletResponse response) {

        String token = CookieUtil.getCookieValue(request, "token");
        User user = (User) redisUtil.getObject(token);

        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String text = request.getParameter("uploadContent");//窗口元素的name
        String ishidden = request.getParameter("ishidden");
        String editorType = request.getParameter("editorType");
        String fileText = HtmlRegexpUtil.filterHtml(HtmlRegexpUtil.fiterHtmlTag(text, "img"));//过滤标签
        String preview = fileText.length() > 100 ? fileText.substring(0, 100) : fileText;
        String category = request.getParameter("category");

        Blog blog = new Blog(Blog.ORIGINAL, title, null, preview, text, Integer.parseInt(category), null, "", null, Integer.valueOf(user.getId()), Integer.parseInt(ishidden), Integer.parseInt(editorType));
        blog.setId(id);

        blogService.update(blog);
        return "SUCCESS";
    }

    /**
     * 删除文章
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping("/private/personal/deleteMyArticle")
    @ResponseBody
    public Object deleteMyArticle(HttpServletRequest request, HttpServletResponse response, @Param("id") String id) {

        blogService.deleteById(id);
        return buildSuccessResult("删除成功！");
    }

    /**
     * 修改文章页面
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/private/personal/updateMyArticlePage")
    public Object updateMyArticlePage(@RequestParam(value = "id") int id, HttpServletRequest request) {

        String token = CookieUtil.getCookieValue(request, "token");
        User user = (User) redisUtil.getObject(token);

        Blog blog = blogService.queryById(id);

        List<BloCategory> list = bloCategoryService.getByUserid(Integer.parseInt(user.getId()));
        Map<String, Object> result = new HashMap<>(2);
        result.put("categoryList", list);
        result.put("blog", blog);
        return result;
    }

    /**
     * 获取博客分类
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/private/personal/getAllCategory")
    public Object getAllCategory(HttpServletRequest request, HttpServletResponse response) {

        String token = CookieUtil.getCookieValue(request, "token");
        User user = (User) redisUtil.getObject(token);

        List<BloCategory> list = bloCategoryService.getByUserid(Integer.parseInt(user.getId()));

        Map<String, Object> result = new HashMap<>(1);
        result.put("categoryList", list);
        return result;
    }

    /**
     * 添加分类
     *
     * @param request
     * @param response
     * @param name
     */
    @RequestMapping("/private/personal/addCategory")
    @ResponseBody
    public Object addCategory(HttpServletRequest request, HttpServletResponse response, @Param("name") String name) {

        String token = CookieUtil.getCookieValue(request, "token");
        User user = (User) redisUtil.getObject(token);
        BloCategory category = new BloCategory();
        category.setName(name);
        category.setUserid(Integer.parseInt(user.getId()));
        bloCategoryService.add(category);

        return buildSuccessResult("保存成功！");
    }

    /**
     * 修改分类
     *
     * @param request
     * @param response
     * @param category
     */
    @RequestMapping("/private/personal/updateCategory")
    @ResponseBody
    public Object updateCategory(HttpServletRequest request, HttpServletResponse response, BloCategory category) {

        bloCategoryService.update(category);
        return buildSuccessResult("保存成功！");
    }

    /**
     * 删除分类
     *
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping("/private/personal/settingCategoryDelete")
    @ResponseBody
    public Object settingCategoryDelete(HttpServletRequest request, HttpServletResponse response, @Param("id") Integer id) {

        Blog entity = new Blog();
        entity.setCategory(id);
        List list = blogService.queryForList(entity);

        if (list.size() > 0) {
            return buildFailResult("该标签下存在文章，不能删除！");
        } else {
            bloCategoryService.deleteById(id);
        }
        return buildSuccessResult("删除成功！");
    }
}
