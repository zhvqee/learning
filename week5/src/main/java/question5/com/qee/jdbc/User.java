package question5.com.qee.jdbc;

import java.sql.Date;

/**
 * @ProjectName: learning
 * @Package: question5.com.qee.jdbc
 * @ClassName: User
 * @Description:
 * @Date: 2021/10/18 7:23 下午
 * @Version: 1.0
 */

public class User {

    private Long id;

    private String name;

    private String feature;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
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
}
