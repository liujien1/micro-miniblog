package website.liujie.bean;

/**
 * 书籍信息
 *
 * Created by liujie on 2017-6-21.
 */
public class BookInfo {


    /**
     * 索引
     */
    private Integer index;

    /**
     * 文件名
     */
    private String name;

    /**
     * 总章节
     */
    private Integer totalChapter;

    /**
     * 当前章节
     */
    private Integer currentChapter;

    /**
     * 本地地址
     */
    private String localPath;

    /**
     * 文章内容
     */
    private String currentText;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getTotalChapter() {
        return totalChapter;
    }

    public void setTotalChapter(Integer totalChapter) {
        this.totalChapter = totalChapter;
    }

    public Integer getCurrentChapter() {
        return currentChapter;
    }

    public void setCurrentChapter(Integer currentChapter) {
        this.currentChapter = currentChapter;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getCurrentText() {
        return currentText;
    }

    public void setCurrentText(String currentText) {
        this.currentText = currentText;
    }
}
