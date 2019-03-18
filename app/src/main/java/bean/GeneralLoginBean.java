package bean;

/**
 * Created by chenlipeng on 2018/10/30 0030.
 * describe :
 */

public class GeneralLoginBean extends Object {


    /**
     * IsRemind : 1
     * parents : {"id":16,"nickname":null,"age":null,"sex":null,"qcellcoreId":1,"phoneNumber":"18210182462","password":null,"createTime":1541039924000,"childId":null,"updateTime":null,"status":"1","token":"Z2yIupsoZ9scMMVTlmCQ==","city":null,"phonePrefix":null}
     */

    private String IsRemind;
    private ParentsBean parents;

    public String getIsRemind() {
        return IsRemind;
    }

    public void setIsRemind(String IsRemind) {
        this.IsRemind = IsRemind;
    }

    public ParentsBean getParents() {
        return parents;
    }

    public void setParents(ParentsBean parents) {
        this.parents = parents;
    }

    public static class ParentsBean {
        /**
         * id : 16
         * nickname : null
         * age : null
         * sex : null
         * qcellcoreId : 1
         * phoneNumber : 18210182462
         * password : null
         * createTime : 1541039924000
         * childId : null
         * updateTime : null
         * status : 1
         * token : Z2yIupsoZ9scMMVTlmCQ==
         * city : null
         * phonePrefix : null
         */

        private int id;
        private Object nickname;
        private Object age;
        private Object sex;
        private int qcellcoreId;
        private String phoneNumber;
        private Object password;
        private long createTime;
        private Object childId;
        private Object updateTime;
        private String status;
        private String token;
        private Object city;
        private Object phonePrefix;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getNickname() {
            return nickname;
        }

        public void setNickname(Object nickname) {
            this.nickname = nickname;
        }

        public Object getAge() {
            return age;
        }

        public void setAge(Object age) {
            this.age = age;
        }

        public Object getSex() {
            return sex;
        }

        public void setSex(Object sex) {
            this.sex = sex;
        }

        public int getQcellcoreId() {
            return qcellcoreId;
        }

        public void setQcellcoreId(int qcellcoreId) {
            this.qcellcoreId = qcellcoreId;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public Object getChildId() {
            return childId;
        }

        public void setChildId(Object childId) {
            this.childId = childId;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public Object getPhonePrefix() {
            return phonePrefix;
        }

        public void setPhonePrefix(Object phonePrefix) {
            this.phonePrefix = phonePrefix;
        }
    }
}
