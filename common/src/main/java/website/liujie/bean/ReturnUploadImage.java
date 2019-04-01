package website.liujie.bean;

/**
 * ueditor上传图片返回格式
 * Created by liujie on 2016/10/7.
 */
public class ReturnUploadImage {
    private String state;//上传状态SUCCESS 一定要大写
    private String url;//上传地址
    private String title;//图片名称demo.jpg
    private String original;//图片名称demo.jpg
    private Integer sysfileid;//文件id

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public Integer getSysfileid() {
        return sysfileid;
    }

    public void setSysfileid(Integer sysfileid) {
        this.sysfileid = sysfileid;
    }
}
