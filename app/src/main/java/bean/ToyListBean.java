package bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenlipeng on 2018/11/5 0005.
 * describe :
 */

public class ToyListBean implements Serializable {


    /**
     * msg : 查询成功
     * code : 200
     * data : [{"id":105,"userId":5,"number":1,"status":"0","module":"1"},{"id":106,"userId":5,"number":2,"status":"0","module":"1"},{"id":107,"userId":5,"number":3,"status":"0","module":"1"},{"id":108,"userId":5,"number":4,"status":"0","module":"1"},{"id":109,"userId":5,"number":5,"status":"0","module":"1"},{"id":110,"userId":5,"number":6,"status":"0","module":"1"},{"id":111,"userId":5,"number":7,"status":"0","module":"1"},{"id":112,"userId":5,"number":8,"status":"0","module":"1"},{"id":113,"userId":5,"number":9,"status":"0","module":"1"}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 105
         * userId : 5
         * number : 1
         * status : 0
         * module : 1
         */

        private int id;
        private int userId;
        private int number;
        private String status;
        private String module;

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

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }
    }
}
