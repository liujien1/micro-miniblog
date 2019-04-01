package website.liujie.entity;

import website.liujie.common.base.BaseBean;

import java.util.Date;

/**
 * @Description :系统文件
 * @Copyright : Excenon. ALL Rights Reserved
 * @Company : 深圳市华磊移动设备科技有限公司
 * @Author : liujie
 * @Version : 1.0
 * @Date : 2016/10/9 0009
 */
public class SysFile extends BaseBean {

    private String path;

    private String fullpath;

    private Long size;

    private Integer type;//0:图片

    private Integer createuser;

    private Date createtime;

    private Integer pid;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFullpath() {
        return fullpath;
    }

    public void setFullpath(String fullpath) {
        this.fullpath = fullpath;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCreateuser() {
        return createuser;
    }

    public void setCreateuser(Integer createuser) {
        this.createuser = createuser;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}
