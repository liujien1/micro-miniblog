package website.liujie.entity;

import website.liujie.common.base.BaseBean;

import java.util.Date;

/**
 * Blog - 博客类
 */
public class Blog extends BaseBean {

    public static final Integer ORIGINAL = 0; // 原创
    public static final Integer REPRINTED = 1; // 转载

    private Integer birth;      // blog来源(转载/原创)
    private String title;   // blog标题
    private String author;  // blog作者
    private String preview; // blog简介(默认为博客内容的前100字符)
    private String text;    // blog内容
    private Integer category;// blog种类
    private Date createTime;    // blog写作日期
    private Date updateTime; //修改日期
    private String birthUrl;// blog的url，转载的话就是原文地址
    private Integer readCount;  // blog阅读计数
    private Integer authorId;   // 关联的userId(User表),authorId大于0表示是原创的，否则是转载的
    private Integer ishidden;//是否隐藏

    private String categoryName;
    /**
     * 查询条数
     */
    private Integer extendSize;
    private String extendSortCol;
    private String extendSearch;
    private Boolean extendIslogin;
    private String extendTimeStr;//app端分页时间
    private String extendOpreate;//app端分页操作，-1:刷新，1：加载
    private Integer editorType;

    private Integer count;

    public Blog() {
    }

    public Blog(Integer birth, String title, String author, String preview, String text,
                Integer category, Date createTime, String birthUrl, Integer readCount, Integer authorId, Integer ishidden, Integer editorType) {
        this.birth = birth;
        this.title = title;
        this.author = author;
        this.preview = preview;
        this.text = text;
        this.category = category;
        this.createTime = createTime;
        this.birthUrl = birthUrl;
        this.readCount = readCount;
        this.authorId = authorId;
        this.ishidden = ishidden;
        this.editorType = editorType;
    }

    public Integer getBirth() {
        return birth;
    }

    public void setBirth(Integer birth) {
        this.birth = birth;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBirthUrl() {
        return birthUrl;
    }

    public void setBirthUrl(String birthUrl) {
        this.birthUrl = birthUrl;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Blog{" +
                " birth=" + birth +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", preview='" + preview + '\'' +
                ", text='" + text + '\'' +
                ", category='" + category + '\'' +
                ", createTime='" + createTime + '\'' +
                ", birthUrl='" + birthUrl + '\'' +
                ", readCount=" + readCount +
                ", authorId=" + authorId +
                '}';
    }

    public Integer getExtendSize() {
        return extendSize;
    }

    public void setExtendSize(Integer extendSize) {
        this.extendSize = extendSize;
    }

    public String getExtendSortCol() {
        return extendSortCol;
    }

    public void setExtendSortCol(String extendSortCol) {
        this.extendSortCol = extendSortCol;
    }

    public String getExtendSearch() {
        return extendSearch;
    }

    public void setExtendSearch(String extendSearch) {
        this.extendSearch = extendSearch;
    }

    public Integer getIshidden() {
        return ishidden;
    }

    public void setIshidden(Integer ishidden) {
        this.ishidden = ishidden;
    }

    public Boolean getExtendIslogin() {
        return extendIslogin;
    }

    public void setExtendIslogin(Boolean extendIslogin) {
        this.extendIslogin = extendIslogin;
    }

    public String getExtendTimeStr() {
        return extendTimeStr;
    }

    public void setExtendTimeStr(String extendTimeStr) {
        this.extendTimeStr = extendTimeStr;
    }

    public String getExtendOpreate() {
        return extendOpreate;
    }

    public void setExtendOpreate(String extendOpreate) {
        this.extendOpreate = extendOpreate;
    }

    public Integer getEditorType() {
        return editorType;
    }

    public void setEditorType(Integer editorType) {
        this.editorType = editorType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}


