package bean;

import java.util.List;

/**
 * Created by chenlipeng on 2018/11/14 0014.
 * describe :
 */

public class LookLatelyTwoBean {


    /**
     * msg : 查询成功！
     * groupTraining : [{"id":837,"userId":16,"trainingIdlist":"41,39,46,47,44,45,43,42","scene":"1","module":"1","startTime":null,"endTime":1542523178000,"stayTime":103,"pass":"0","accuracy":0.8,"createTime":1542523086000,"updateTime":1542523178000,"states":"1","valid":"0","length":8,"gold":0}]
     * code : 200
     * data : 0
     * list : {"sentence":"1","verb":"1","noun":"2","group":"1"}
     */

    private String msg;
    private int code;
    private int data;
    private ListBean list;
    private List<GroupTrainingBean> groupTraining;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public List<GroupTrainingBean> getGroupTraining() {
        return groupTraining;
    }

    public void setGroupTraining(List<GroupTrainingBean> groupTraining) {
        this.groupTraining = groupTraining;
    }

    public static class ListBean {
        /**
         * sentence : 1
         * verb : 1
         * noun : 2
         * group : 1
         */

        private String sentence;
        private String verb;
        private String noun;
        private String group;

        public String getSentence() {
            return sentence;
        }

        public void setSentence(String sentence) {
            this.sentence = sentence;
        }

        public String getVerb() {
            return verb;
        }

        public void setVerb(String verb) {
            this.verb = verb;
        }

        public String getNoun() {
            return noun;
        }

        public void setNoun(String noun) {
            this.noun = noun;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }
    }

    public static class GroupTrainingBean {
        /**
         * id : 837
         * userId : 16
         * trainingIdlist : 41,39,46,47,44,45,43,42
         * scene : 1
         * module : 1
         * startTime : null
         * endTime : 1542523178000
         * stayTime : 103
         * pass : 0
         * accuracy : 0.8
         * createTime : 1542523086000
         * updateTime : 1542523178000
         * states : 1
         * valid : 0
         * length : 8
         * gold : 0
         */

        private int id;
        private int userId;
        private String trainingIdlist;
        private String scene;
        private String module;
        private Object startTime;
        private long endTime;
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

        public String getTrainingIdlist() {
            return trainingIdlist;
        }

        public void setTrainingIdlist(String trainingIdlist) {
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

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
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
}
