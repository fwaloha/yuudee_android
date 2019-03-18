package bean;

import java.util.List;

public class CommenBean {


    /**
     * schedule : 1.0
     * countTime : 21
     * resultList : [{"timeList":[],"weekLastDay":1543075200000,"weekFirstDay":1542556800000,"accuracyList":[]},{"timeList":[{"accuracy":60.333333,"time":"2018-11-16","countTime":268}],"weekLastDay":1542470400000,"weekFirstDay":1541952000000,"accuracyList":[{"stayTime":268,"accuracy":60.333333,"time":"2018-11-16"}]},{"timeList":[],"weekLastDay":1541865600000,"weekFirstDay":1541347200000,"accuracyList":[]},{"timeList":[],"weekLastDay":1541260800000,"weekFirstDay":1540742400000,"accuracyList":[]}]
     */

    private double schedule;
    private int countTime;
    private List<ResultListBean> resultList;

    public double getSchedule() {
        return schedule;
    }

    public void setSchedule(double schedule) {
        this.schedule = schedule;
    }

    public int getCountTime() {
        return countTime;
    }

    public void setCountTime(int countTime) {
        this.countTime = countTime;
    }

    public List<ResultListBean> getResultList() {
        return resultList;
    }

    public void setResultList(List<ResultListBean> resultList) {
        this.resultList = resultList;
    }

    public static class ResultListBean {
        /**
         * timeList : []
         * weekLastDay : 1543075200000
         * weekFirstDay : 1542556800000
         * accuracyList : []
         */

        private long weekLastDay;
        private long weekFirstDay;
        private List<TimeList> timeList;
        private List<AccuracyList> accuracyList;

        public long getWeekLastDay() {
            return weekLastDay;
        }

        public void setWeekLastDay(long weekLastDay) {
            this.weekLastDay = weekLastDay;
        }

        public long getWeekFirstDay() {
            return weekFirstDay;
        }

        public void setWeekFirstDay(long weekFirstDay) {
            this.weekFirstDay = weekFirstDay;
        }

        public List<TimeList> getTimeList() {
            return timeList;
        }

        public void setTimeList(List<TimeList> timeList) {
            this.timeList = timeList;
        }

        public List<AccuracyList> getAccuracyList() {
            return accuracyList;
        }

        public void setAccuracyList(List<AccuracyList> accuracyList) {
            this.accuracyList = accuracyList;
        }
    }
}
