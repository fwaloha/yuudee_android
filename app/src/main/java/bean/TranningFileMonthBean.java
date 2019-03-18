package bean;

import java.util.List;

public class TranningFileMonthBean {


    /**
     * schedule : 0.13
     * countTime : 100
     * resultList : [[{"timeList":[],"weekLastDay":"2018-10-08 00:00:00","weekFirstDay":"2018-10-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-10-15 00:00:00","weekFirstDay":"2018-10-08 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-10-22 00:00:00","weekFirstDay":"2018-10-15 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-10-29 00:00:00","weekFirstDay":"2018-10-22 00:00:00","accuracyList":[]},{"timeList":[{"time":"2018-10-30","countTime":11}],"weekLastDay":"2018-11-01 00:00:00","weekFirstDay":"2018-10-29 00:00:00","accuracyList":[{"accuracy":1,"time":"2018-10-30"}]}],10]
     */

    private double schedule;
    private int countTime;
    private List<List<ResultListBean>> resultList;

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

    public List<List<ResultListBean>> getResultList() {
        return resultList;
    }

    public void setResultList(List<List<ResultListBean>> resultList) {
        this.resultList = resultList;
    }

    public static class ResultListBean {
        /**
         * timeList : []
         * weekLastDay : 2018-10-08 00:00:00
         * weekFirstDay : 2018-10-01 00:00:00
         * accuracyList : []
         */

        private String weekLastDay;
        private String weekFirstDay;
        private List<TimeList> timeList;
        private List<AccuracyList> accuracyList;

        public String getWeekLastDay() {
            return weekLastDay;
        }

        public void setWeekLastDay(String weekLastDay) {
            this.weekLastDay = weekLastDay;
        }

        public String getWeekFirstDay() {
            return weekFirstDay;
        }

        public void setWeekFirstDay(String weekFirstDay) {
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
