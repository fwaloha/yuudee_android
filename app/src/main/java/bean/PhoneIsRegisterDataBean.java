package bean;

/**
 * Created by chenlipeng on 2018/10/30 0030.
 * describe :
 */

public class PhoneIsRegisterDataBean {


    /**
     * msg : 该手机号已注册！
     * code : 200
     * data : {"qcellcore":{"id":1,"cityType":"热门城市","name":"中国","logo":"无","phonePrefix":"86","states":"1","createTime":1539673319000,"updateTime":1539673317000},"isRegister":true}
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
         * qcellcore : {"id":1,"cityType":"热门城市","name":"中国","logo":"无","phonePrefix":"86","states":"1","createTime":1539673319000,"updateTime":1539673317000}
         * isRegister : true
         */

        private QcellcoreBean qcellcore;
        private boolean isRegister;

        public QcellcoreBean getQcellcore() {
            return qcellcore;
        }

        public void setQcellcore(QcellcoreBean qcellcore) {
            this.qcellcore = qcellcore;
        }

        public boolean isIsRegister() {
            return isRegister;
        }

        public void setIsRegister(boolean isRegister) {
            this.isRegister = isRegister;
        }

        public static class QcellcoreBean {
            /**
             * id : 1
             * cityType : 热门城市
             * name : 中国
             * logo : 无
             * phonePrefix : 86
             * states : 1
             * createTime : 1539673319000
             * updateTime : 1539673317000
             */

            private int id;
            private String cityType;
            private String name;
            private String logo;
            private String phonePrefix;
            private String states;
            private long createTime;
            private long updateTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCityType() {
                return cityType;
            }

            public void setCityType(String cityType) {
                this.cityType = cityType;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getPhonePrefix() {
                return phonePrefix;
            }

            public void setPhonePrefix(String phonePrefix) {
                this.phonePrefix = phonePrefix;
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

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
