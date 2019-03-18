package bean;

import java.util.List;

/**
 * Created by chenlipeng on 2018/11/19 0019.
 * describe :
 */

public class DayData {


    /**
     * code : 200
     * data : {"schedule":1,"countTime":21,"resultList":[{"str":"2018-11-20 13:14:23","studyTime":0,"dayResultList":[],"time":1542690863000},{"str":"2018-11-21 13:14:23","studyTime":0,"dayResultList":[],"time":1542777263000},{"str":"2018-11-22 13:14:23","studyTime":0,"dayResultList":[],"time":1542863663000},{"str":"2018-11-23 13:14:23","studyTime":0,"dayResultList":[],"time":1542950063000},{"str":"2018-11-24 13:14:23","studyTime":0,"dayResultList":[],"time":1543036463000}]}
     */

    private int code;
    private DataBean data;

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

    public static class DataBean {
        /**
         * schedule : 1.0
         * countTime : 21
         * resultList : [{"str":"2018-11-20 13:14:23","studyTime":0,"dayResultList":[],"time":1542690863000},{"str":"2018-11-21 13:14:23","studyTime":0,"dayResultList":[],"time":1542777263000},{"str":"2018-11-22 13:14:23","studyTime":0,"dayResultList":[],"time":1542863663000},{"str":"2018-11-23 13:14:23","studyTime":0,"dayResultList":[],"time":1542950063000},{"str":"2018-11-24 13:14:23","studyTime":0,"dayResultList":[],"time":1543036463000}]
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
             * str : 2018-11-20 13:14:23
             * studyTime : 0
             * dayResultList : []
             * time : 1542690863000
             */

            private String str;
            private float studyTime;
            private long time;
            private List<DayResultList> dayResultList;

            public String getStr() {
                return str;
            }

            public void setStr(String str) {
                this.str = str;
            }

            public float getStudyTime() {
                return studyTime;
            }

            public void setStudyTime(float studyTime) {
                this.studyTime = studyTime;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public List<DayResultList> getDayResultList() {
                return dayResultList;
            }

            public void setDayResultList(List<DayResultList> dayResultList) {
                this.dayResultList = dayResultList;
            }
        }
    }
}
