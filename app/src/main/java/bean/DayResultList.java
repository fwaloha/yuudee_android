package bean;

/**
 * Created by chenlipeng on 2018/11/19 0019.
 * describe :
 */

public class DayResultList {
    /**
     * id : 852
     * userId : 11
     * trainingIdlist : null
     * scene : 2
     * module : 1
     * startTime : null
     * endTime : null
     * stayTime : 555
     * pass : 0
     * accuracy : 0.56
     * createTime : 1542605379000
     * updateTime : 1542605382000
     * states : 1
     * valid : 1
     * length : 0
     * gold : 0
     */

    private int id;
    private int userId;
    private Object trainingIdlist;
    private String scene;
    private String module;
    private Object startTime;
    private Object endTime;
    private int stayTime;
    private String pass;
    private double accuracy;
    private long createTime;
    private long updateTime;
    private String states;
    private String valid;
    private int length;
    private int gold;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Object getTrainingIdlist() {
        return trainingIdlist;
    }

    public void setTrainingIdlist(Object trainingIdlist) {
        this.trainingIdlist = trainingIdlist;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Object getStartTime() {
        return startTime;
    }

    public void setStartTime(Object startTime) {
        this.startTime = startTime;
    }

    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }

    public int getStayTime() {
        return stayTime;
    }

    public void setStayTime(int stayTime) {
        this.stayTime = stayTime;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
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

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
