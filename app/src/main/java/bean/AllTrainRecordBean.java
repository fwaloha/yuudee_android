package bean;

import java.util.List;

public class AllTrainRecordBean {

    /**
     * sumRate : 1.01
     * list : [{"id":4,"rate_all":0.99,"learning_time":20,"score":50},{"id":3,"rate_all":0.66,"learning_time":400,"score":30},{"id":2,"rate_all":0.4,"learning_time":8000,"score":25}]
     */

    private String sumRate;
    private List<ListBean> list;

    public String getSumRate() {
        return sumRate;
    }

    public void setSumRate(String sumRate) {
        this.sumRate = sumRate;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 4
         * rate_all : 0.99
         * learning_time : 20
         * score : 50
         */

        private int id;
        private String rate_all;
        private String learning_time;
        private int score;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRate_all() {
            return rate_all;
        }

        public void setRate_all(String rate_all) {
            this.rate_all = rate_all;
        }

        public String getLearning_time() {
            return learning_time;
        }

        public void setLearning_time(String learning_time) {
            this.learning_time = learning_time;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
