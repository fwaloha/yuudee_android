package bean;

import java.util.List;

/**
 * Created by chenlipeng on 2018/11/19 0019.
 * describe :
 */

public class MonthData {


    /**
     * msg :
     * code : 200
     * data : {"schedule":1,"countTime":21,"resultList":[{"month":11,"list":[{"timeList":[],"weekLastDay":"2018-11-08 00:00:00","weekFirstDay":"2018-11-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-11-15 00:00:00","weekFirstDay":"2018-11-08 00:00:00","accuracyList":[]},{"timeList":[{"accuracy":60.333333,"time":"2018-11-16","countTime":268}],"weekLastDay":"2018-11-22 00:00:00","weekFirstDay":"2018-11-15 00:00:00","accuracyList":[{"stayTime":268,"accuracy":60.333333,"time":"2018-11-16"}]},{"timeList":[],"weekLastDay":"2018-11-29 00:00:00","weekFirstDay":"2018-11-22 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-12-01 00:00:00","weekFirstDay":"2018-11-29 00:00:00","accuracyList":[]}]},{"month":10,"list":[{"timeList":[],"weekLastDay":"2018-10-08 00:00:00","weekFirstDay":"2018-10-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-10-15 00:00:00","weekFirstDay":"2018-10-08 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-10-22 00:00:00","weekFirstDay":"2018-10-15 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-10-29 00:00:00","weekFirstDay":"2018-10-22 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-11-01 00:00:00","weekFirstDay":"2018-10-29 00:00:00","accuracyList":[]}]},{"month":9,"list":[{"timeList":[],"weekLastDay":"2018-09-08 00:00:00","weekFirstDay":"2018-09-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-09-15 00:00:00","weekFirstDay":"2018-09-08 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-09-22 00:00:00","weekFirstDay":"2018-09-15 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-09-29 00:00:00","weekFirstDay":"2018-09-22 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-10-01 00:00:00","weekFirstDay":"2018-09-29 00:00:00","accuracyList":[]}]},{"month":8,"list":[{"timeList":[],"weekLastDay":"2018-08-08 00:00:00","weekFirstDay":"2018-08-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-08-15 00:00:00","weekFirstDay":"2018-08-08 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-08-22 00:00:00","weekFirstDay":"2018-08-15 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-08-29 00:00:00","weekFirstDay":"2018-08-22 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-09-01 00:00:00","weekFirstDay":"2018-08-29 00:00:00","accuracyList":[]}]},{"month":7,"list":[{"timeList":[],"weekLastDay":"2018-07-08 00:00:00","weekFirstDay":"2018-07-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-07-15 00:00:00","weekFirstDay":"2018-07-08 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-07-22 00:00:00","weekFirstDay":"2018-07-15 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-07-29 00:00:00","weekFirstDay":"2018-07-22 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-08-01 00:00:00","weekFirstDay":"2018-07-29 00:00:00","accuracyList":[]}]},{"month":6,"list":[{"timeList":[],"weekLastDay":"2018-06-08 00:00:00","weekFirstDay":"2018-06-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-06-15 00:00:00","weekFirstDay":"2018-06-08 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-06-22 00:00:00","weekFirstDay":"2018-06-15 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-06-29 00:00:00","weekFirstDay":"2018-06-22 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-07-01 00:00:00","weekFirstDay":"2018-06-29 00:00:00","accuracyList":[]}]},{"month":5,"list":[{"timeList":[],"weekLastDay":"2018-05-08 00:00:00","weekFirstDay":"2018-05-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-05-15 00:00:00","weekFirstDay":"2018-05-08 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-05-22 00:00:00","weekFirstDay":"2018-05-15 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-05-29 00:00:00","weekFirstDay":"2018-05-22 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-06-01 00:00:00","weekFirstDay":"2018-05-29 00:00:00","accuracyList":[]}]},{"month":4,"list":[{"timeList":[],"weekLastDay":"2018-04-08 00:00:00","weekFirstDay":"2018-04-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-04-15 00:00:00","weekFirstDay":"2018-04-08 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-04-22 00:00:00","weekFirstDay":"2018-04-15 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-04-29 00:00:00","weekFirstDay":"2018-04-22 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-05-01 00:00:00","weekFirstDay":"2018-04-29 00:00:00","accuracyList":[]}]},{"month":3,"list":[{"timeList":[],"weekLastDay":"2018-03-08 00:00:00","weekFirstDay":"2018-03-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-03-15 00:00:00","weekFirstDay":"2018-03-08 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-03-22 00:00:00","weekFirstDay":"2018-03-15 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-03-29 00:00:00","weekFirstDay":"2018-03-22 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-04-01 00:00:00","weekFirstDay":"2018-03-29 00:00:00","accuracyList":[]}]},{"month":2,"list":[{"timeList":[],"weekLastDay":"2018-02-08 00:00:00","weekFirstDay":"2018-02-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-02-15 00:00:00","weekFirstDay":"2018-02-08 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-02-22 00:00:00","weekFirstDay":"2018-02-15 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-03-01 00:00:00","weekFirstDay":"2018-02-22 00:00:00","accuracyList":[]}]},{"month":1,"list":[{"timeList":[],"weekLastDay":"2018-01-08 00:00:00","weekFirstDay":"2018-01-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-01-15 00:00:00","weekFirstDay":"2018-01-08 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-01-22 00:00:00","weekFirstDay":"2018-01-15 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-01-29 00:00:00","weekFirstDay":"2018-01-22 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-02-01 00:00:00","weekFirstDay":"2018-01-29 00:00:00","accuracyList":[]}]}]}
     */

    private String msg;
    private int code;
    private DataBean data;

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

    public static class DataBean {
        /**
         * schedule : 1.0
         * countTime : 21
         * resultList : [{"month":11,"list":[{"timeList":[],"weekLastDay":"2018-11-08 00:00:00","weekFirstDay":"2018-11-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-11-15 00:00:00","weekFirstDay":"2018-11-08 00:00:00","accuracyList":[]},{"timeList":[{"accuracy":60.333333,"time":"2018-11-16","countTime":268}],"weekLastDay":"2018-11-22 00:00:00","weekFirstDay":"2018-11-15 00:00:00","accuracyList":[{"stayTime":268,"accuracy":60.333333,"time":"2018-11-16"}]},{"timeList":[],"weekLastDay":"2018-11-29 00:00:00","weekFirstDay":"2018-11-22 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-12-01 00:00:00","weekFirstDay":"2018-11-29 00:00:00","accuracyList":[]}]},{"month":10,"list":[{"timeList":[],"weekLastDay":"2018-10-08 00:00:00","weekFirstDay":"2018-10-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-10-15 00:00:00","weekFirstDay":"2018-10-08 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-10-22 00:00:00","weekFirstDay":"2018-10-15 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-10-29 00:00:00","weekFirstDay":"2018-10-22 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-11-01 00:00:00","weekFirstDay":"2018-10-29 00:00:00","accuracyList":[]}]},{"month":9,"list":[{"timeList":[],"weekLastDay":"2018-09-08 00:00:00","weekFirstDay":"2018-09-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-09-15 00:00:00","weekFirstDay":"2018-09-08 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-09-22 00:00:00","weekFirstDay":"2018-09-15 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-09-29 00:00:00","weekFirstDay":"2018-09-22 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-10-01 00:00:00","weekFirstDay":"2018-09-29 00:00:00","accuracyList":[]}]},{"month":8,"list":[{"timeList":[],"weekLastDay":"2018-08-08 00:00:00","weekFirstDay":"2018-08-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-08-15 00:00:00","weekFirstDay":"2018-08-08 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-08-22 00:00:00","weekFirstDay":"2018-08-15 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-08-29 00:00:00","weekFirstDay":"2018-08-22 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-09-01 00:00:00","weekFirstDay":"2018-08-29 00:00:00","accuracyList":[]}]},{"month":7,"list":[{"timeList":[],"weekLastDay":"2018-07-08 00:00:00","weekFirstDay":"2018-07-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-07-15 00:00:00","weekFirstDay":"2018-07-08 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-07-22 00:00:00","weekFirstDay":"2018-07-15 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-07-29 00:00:00","weekFirstDay":"2018-07-22 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-08-01 00:00:00","weekFirstDay":"2018-07-29 00:00:00","accuracyList":[]}]},{"month":6,"list":[{"timeList":[],"weekLastDay":"2018-06-08 00:00:00","weekFirstDay":"2018-06-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-06-15 00:00:00","weekFirstDay":"2018-06-08 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-06-22 00:00:00","weekFirstDay":"2018-06-15 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-06-29 00:00:00","weekFirstDay":"2018-06-22 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-07-01 00:00:00","weekFirstDay":"2018-06-29 00:00:00","accuracyList":[]}]},{"month":5,"list":[{"timeList":[],"weekLastDay":"2018-05-08 00:00:00","weekFirstDay":"2018-05-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-05-15 00:00:00","weekFirstDay":"2018-05-08 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-05-22 00:00:00","weekFirstDay":"2018-05-15 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-05-29 00:00:00","weekFirstDay":"2018-05-22 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-06-01 00:00:00","weekFirstDay":"2018-05-29 00:00:00","accuracyList":[]}]},{"month":4,"list":[{"timeList":[],"weekLastDay":"2018-04-08 00:00:00","weekFirstDay":"2018-04-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-04-15 00:00:00","weekFirstDay":"2018-04-08 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-04-22 00:00:00","weekFirstDay":"2018-04-15 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-04-29 00:00:00","weekFirstDay":"2018-04-22 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-05-01 00:00:00","weekFirstDay":"2018-04-29 00:00:00","accuracyList":[]}]},{"month":3,"list":[{"timeList":[],"weekLastDay":"2018-03-08 00:00:00","weekFirstDay":"2018-03-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-03-15 00:00:00","weekFirstDay":"2018-03-08 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-03-22 00:00:00","weekFirstDay":"2018-03-15 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-03-29 00:00:00","weekFirstDay":"2018-03-22 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-04-01 00:00:00","weekFirstDay":"2018-03-29 00:00:00","accuracyList":[]}]},{"month":2,"list":[{"timeList":[],"weekLastDay":"2018-02-08 00:00:00","weekFirstDay":"2018-02-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-02-15 00:00:00","weekFirstDay":"2018-02-08 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-02-22 00:00:00","weekFirstDay":"2018-02-15 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-03-01 00:00:00","weekFirstDay":"2018-02-22 00:00:00","accuracyList":[]}]},{"month":1,"list":[{"timeList":[],"weekLastDay":"2018-01-08 00:00:00","weekFirstDay":"2018-01-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-01-15 00:00:00","weekFirstDay":"2018-01-08 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-01-22 00:00:00","weekFirstDay":"2018-01-15 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-01-29 00:00:00","weekFirstDay":"2018-01-22 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-02-01 00:00:00","weekFirstDay":"2018-01-29 00:00:00","accuracyList":[]}]}]
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
             * month : 11
             * list : [{"timeList":[],"weekLastDay":"2018-11-08 00:00:00","weekFirstDay":"2018-11-01 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-11-15 00:00:00","weekFirstDay":"2018-11-08 00:00:00","accuracyList":[]},{"timeList":[{"accuracy":60.333333,"time":"2018-11-16","countTime":268}],"weekLastDay":"2018-11-22 00:00:00","weekFirstDay":"2018-11-15 00:00:00","accuracyList":[{"stayTime":268,"accuracy":60.333333,"time":"2018-11-16"}]},{"timeList":[],"weekLastDay":"2018-11-29 00:00:00","weekFirstDay":"2018-11-22 00:00:00","accuracyList":[]},{"timeList":[],"weekLastDay":"2018-12-01 00:00:00","weekFirstDay":"2018-11-29 00:00:00","accuracyList":[]}]
             */

            private int month;
            private List<ListBean> list;

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * timeList : []
                 * weekLastDay : 2018-11-08 00:00:00
                 * weekFirstDay : 2018-11-01 00:00:00
                 * accuracyList : []
                 */

                private String weekLastDay;
                private String weekFirstDay;
//                private List<?> timeList;
//                private List<?> accuracyList;

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
    }
}
