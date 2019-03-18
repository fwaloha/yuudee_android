package bean;

public class SplashPicBean {


    /**
     * id : 1
     * type : 1
     * image : http://yuudee.oss-cn-beijing.aliyuncs.com/5ee98df187814b70bd2d08ac4df4d0d8.png
     * title : 启动页
     * states : 1
     * createTime : 1540886820000
     * updateTime : 1542189884000
     */

    private int id;
    private String type;
    private String image;
    private String title;
    private String states;
    private long createTime;
    private long updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
