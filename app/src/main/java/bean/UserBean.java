package bean;

public class UserBean {


    /**
     * chilName : 啊啊
     * chilSex : 1
     * chilPhoto : http://yuudee.oss-cn-beijing.aliyuncs.com/d5a4323b5b1b44d1ba4899ed551be7c3.temp
     * IsRemind : 2
     * parents : {"id":17,"nickname":null,"age":null,"sex":null,"qcellcoreId":1,"phoneNumber":"18210700961","password":null,"createTime":1541135746000,"childId":6,"updateTime":1541384676000,"status":"1","token":"RgTcPueTNLVO4lIRk5oQA==","feel":"1","city":null,"phonePrefix":null}
     */

    private String chilName;
    private String chilSex;
    private String chilPhoto;
    private String IsRemind;
    private ParentsBean parents;

    @Override
    public String toString() {
        return "UserBean{" +
                "chilName='" + chilName + '\'' +
                ", chilSex='" + chilSex + '\'' +
                ", chilPhoto='" + chilPhoto + '\'' +
                ", IsRemind='" + IsRemind + '\'' +
                ", parents=" + parents +
                '}';
    }

    public String getChilName() {
        return chilName;
    }

    public void setChilName(String chilName) {
        this.chilName = chilName;
    }

    public String getChilSex() {
        return chilSex;
    }

    public void setChilSex(String chilSex) {
        this.chilSex = chilSex;
    }

    public String getChilPhoto() {
        return chilPhoto;
    }

    public void setChilPhoto(String chilPhoto) {
        this.chilPhoto = chilPhoto;
    }

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
         * id : 17
         * nickname : null
         * age : null
         * sex : null
         * qcellcoreId : 1
         * phoneNumber : 18210700961
         * password : null
         * createTime : 1541135746000
         * childId : 6
         * updateTime : 1541384676000
         * status : 1
         * token : RgTcPueTNLVO4lIRk5oQA==
         * feel : 1
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
        private int childId;
        private long updateTime;
        private String status;
        private String token;
        private String feel;
        private Object city;
        private Object phonePrefix;

        @Override
        public String toString() {
            return "ParentsBean{" +
                    "id=" + id +
                    ", nickname=" + nickname +
                    ", age=" + age +
                    ", sex=" + sex +
                    ", qcellcoreId=" + qcellcoreId +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    ", password=" + password +
                    ", createTime=" + createTime +
                    ", childId=" + childId +
                    ", updateTime=" + updateTime +
                    ", status='" + status + '\'' +
                    ", token='" + token + '\'' +
                    ", feel='" + feel + '\'' +
                    ", city=" + city +
                    ", phonePrefix=" + phonePrefix +
                    '}';
        }

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

        public int getChildId() {
            return childId;
        }

        public void setChildId(int childId) {
            this.childId = childId;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
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

        public String getFeel() {
            return feel;
        }

        public void setFeel(String feel) {
            this.feel = feel;
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
