package bean;

public class ProductIntroductionBean {

    /**
     * id : 8
     * type : 1
     * title : 三生三世
     * content : <p>未全额区委区为轻微是</p>
     * versionsId : 1
     * states : 1
     * createTime : 1541040231000
     * updateTime : 1541041449000
     */

    private int id;
    private String type;
    private String title;
    private String content;
    private int versionsId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getVersionsId() {
        return versionsId;
    }

    public void setVersionsId(int versionsId) {
        this.versionsId = versionsId;
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
