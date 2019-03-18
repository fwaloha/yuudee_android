package bean;

import java.util.List;

/**
 * Created by chenlipeng on 2018/11/14 0014.
 * describe :
 */

public class LookLatelyBean {


    /**
     * playerModule : {"player1":"0","player2":"0","player3":"0","player4":"0"}
     * msg : 查询成功！
     * groupTraining : [{"id":212097,"userId":156,"trainingIdlist":"8,9","scene":"1","module":"1","startTime":null,"endTime":1545479462000,"stayTime":475,"pass":"0","accuracy":0.1,"createTime":1545478998000,"updateTime":1545479462000,"states":"1","valid":"0","length":2,"gold":0}]
     * code : 200
     * data : {"id":194,"userId":156,"learningTime":224,"module":"3","childModule":"2","passNumber":2,"states":"1","createTime":1545390528000,"updateTime":null,"player":0,"rate":0.66,"count":0,"isPass":"0","rate1":0.66,"again":null}
     * againModule : {"module4":"0","module1":"0","module3":"0","module2":"0"}
     * list : {"sentence":"1","verb":"2","noun":"1","group":"2"}
     */

    private PlayerModuleBean playerModule;
    private String msg;
    private int code;
    private DataBean data;
    private AgainModuleBean againModule;
    private ListBean list;
    private List<GroupTrainingBean> groupTraining;

    public PlayerModuleBean getPlayerModule() {
        return playerModule;
    }

    public void setPlayerModule(PlayerModuleBean playerModule) {
        this.playerModule = playerModule;
    }

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public AgainModuleBean getAgainModule() {
        return againModule;
    }

    public void setAgainModule(AgainModuleBean againModule) {
        this.againModule = againModule;
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

    public static class PlayerModuleBean {
        /**
         * player1 : 0
         * player2 : 0
         * player3 : 0
         * player4 : 0
         */

        private String player1;
        private String player2;
        private String player3;
        private String player4;

        public String getPlayer1() {
            return player1;
        }

        public void setPlayer1(String player1) {
            this.player1 = player1;
        }

        public String getPlayer2() {
            return player2;
        }

        public void setPlayer2(String player2) {
            this.player2 = player2;
        }

        public String getPlayer3() {
            return player3;
        }

        public void setPlayer3(String player3) {
            this.player3 = player3;
        }

        public String getPlayer4() {
            return player4;
        }

        public void setPlayer4(String player4) {
            this.player4 = player4;
        }
    }

    public static class DataBean {
        /**
         * id : 194
         * userId : 156
         * learningTime : 224
         * module : 3
         * childModule : 2
         * passNumber : 2
         * states : 1
         * createTime : 1545390528000
         * updateTime : null
         * player : 0
         * rate : 0.66
         * count : 0
         * isPass : 0
         * rate1 : 0.66
         * again : null
         */

        private int id;
        private int userId;
        private int learningTime;
        private String module;
        private String childModule;
        private int passNumber;
        private String states;
        private long createTime;
        private Object updateTime;
        private int player;
        private double rate;
        private int count;
        private String isPass;
        private double rate1;
        private Object again;

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

        public int getLearningTime() {
            return learningTime;
        }

        public void setLearningTime(int learningTime) {
            this.learningTime = learningTime;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public String getChildModule() {
            return childModule;
        }

        public void setChildModule(String childModule) {
            this.childModule = childModule;
        }

        public int getPassNumber() {
            return passNumber;
        }

        public void setPassNumber(int passNumber) {
            this.passNumber = passNumber;
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

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public int getPlayer() {
            return player;
        }

        public void setPlayer(int player) {
            this.player = player;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getIsPass() {
            return isPass;
        }

        public void setIsPass(String isPass) {
            this.isPass = isPass;
        }

        public double getRate1() {
            return rate1;
        }

        public void setRate1(double rate1) {
            this.rate1 = rate1;
        }

        public Object getAgain() {
            return again;
        }

        public void setAgain(Object again) {
            this.again = again;
        }
    }

    public static class AgainModuleBean {
        /**
         * module4 : 0
         * module1 : 0
         * module3 : 0
         * module2 : 0
         */

        private String module4;
        private String module1;
        private String module3;
        private String module2;

        public String getModule4() {
            return module4;
        }

        public void setModule4(String module4) {
            this.module4 = module4;
        }

        public String getModule1() {
            return module1;
        }

        public void setModule1(String module1) {
            this.module1 = module1;
        }

        public String getModule3() {
            return module3;
        }

        public void setModule3(String module3) {
            this.module3 = module3;
        }

        public String getModule2() {
            return module2;
        }

        public void setModule2(String module2) {
            this.module2 = module2;
        }
    }

    public static class ListBean {
        /**
         * sentence : 1
         * verb : 2
         * noun : 1
         * group : 2
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
         * id : 212097
         * userId : 156
         * trainingIdlist : 8,9
         * scene : 1
         * module : 1
         * startTime : null
         * endTime : 1545479462000
         * stayTime : 475
         * pass : 0
         * accuracy : 0.1
         * createTime : 1545478998000
         * updateTime : 1545479462000
         * states : 1
         * valid : 0
         * length : 2
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
