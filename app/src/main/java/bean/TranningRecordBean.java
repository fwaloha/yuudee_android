package bean;

import java.util.List;

public class TranningRecordBean {


    /**
     * childName : 阳平
     * statisticsList : [{"id":112,"userId":7,"learningTime":16,"module":"1","childModule":"2","passNumber":1,"states":"1","createTime":1543576151000,"updateTime":null,"rate":0.33,"count":1,"isPass":"0","rate1":0.33},{"id":null,"userId":7,"learningTime":0,"module":"2","childModule":null,"passNumber":null,"states":null,"createTime":null,"updateTime":null,"rate":null,"count":null,"isPass":null,"rate1":0},{"id":null,"userId":7,"learningTime":0,"module":"3","childModule":null,"passNumber":null,"states":null,"createTime":null,"updateTime":null,"rate":null,"count":null,"isPass":null,"rate1":0},{"id":null,"userId":7,"learningTime":0,"module":"4","childModule":null,"passNumber":null,"states":null,"createTime":null,"updateTime":null,"rate":null,"count":null,"isPass":null,"rate1":0},{"id":null,"userId":7,"learningTime":0,"module":"5","childModule":null,"passNumber":null,"states":null,"createTime":null,"updateTime":null,"rate":null,"count":null,"isPass":null,"rate1":0.0825}]
     */

    private String childName;
    private List<StatisticsListBean> statisticsList;

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public List<StatisticsListBean> getStatisticsList() {
        return statisticsList;
    }

    public void setStatisticsList(List<StatisticsListBean> statisticsList) {
        this.statisticsList = statisticsList;
    }

    public static class StatisticsListBean {
        /**
         * id : 112
         * userId : 7
         * learningTime : 16
         * module : 1
         * childModule : 2
         * passNumber : 1
         * states : 1
         * createTime : 1543576151000
         * updateTime : null
         * rate : 0.33
         * count : 1
         * isPass : 0
         * rate1 : 0.33
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
        private int count;
        private String isPass;
        private double rate1;

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
    }
}
