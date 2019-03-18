package bean;

/**
 * Created by chenlipeng on 2018/11/28 0028.
 * describe :
 */
public class ChildInfoBean {
    /**
     * msg : 无法获取用户信息!
     * code : 200
     * data : {"xydChild":{"id":11,"parentsId":31,"phoneNumber":"18614233682","name":"小雨滴2","sex":"0","birthdate":1447516800000,"birthTime":null,"countiy":"中国","province":"宁夏","city":"银川","medical":"0","medicalState":"0","firstLanguage":"0","firstRests":null,"secondLanguage":"1","secondRests":null,"fatherCulture":"1","motherCulture":"2","trainingMethod":"1","trainingRests":null,"perfection":"1","createTime":1542266923000,"updateTime":null,"status":"1","photo":null,"address":"中国-宁夏-银川"},"IsRemind":"2"}
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
         * xydChild : {"id":11,"parentsId":31,"phoneNumber":"18614233682","name":"小雨滴2","sex":"0","birthdate":1447516800000,"birthTime":null,"countiy":"中国","province":"宁夏","city":"银川","medical":"0","medicalState":"0","firstLanguage":"0","firstRests":null,"secondLanguage":"1","secondRests":null,"fatherCulture":"1","motherCulture":"2","trainingMethod":"1","trainingRests":null,"perfection":"1","createTime":1542266923000,"updateTime":null,"status":"1","photo":null,"address":"中国-宁夏-银川"}
         * IsRemind : 2
         */

        private XydChildBean xydChild;
        private String IsRemind;

        public XydChildBean getXydChild() {
            return xydChild;
        }

        public void setXydChild(XydChildBean xydChild) {
            this.xydChild = xydChild;
        }

        public String getIsRemind() {
            return IsRemind;
        }

        public void setIsRemind(String IsRemind) {
            this.IsRemind = IsRemind;
        }

        public static class XydChildBean {
            /**
             * id : 11
             * parentsId : 31
             * phoneNumber : 18614233682
             * name :
             * sex : 0
             * birthdate : 1447516800000
             * birthTime : null
             * countiy : 中国
             * province : 宁夏
             * city : 银川
             * medical : 0
             * medicalState : 0
             * firstLanguage : 0
             * firstRests : null
             * secondLanguage : 1
             * secondRests : null
             * fatherCulture : 1
             * motherCulture : 2
             * trainingMethod : 1
             * trainingRests : null
             * perfection : 1
             * createTime : 1542266923000
             * updateTime : null
             * status : 1
             * photo : null
             * address : 中国-宁夏-银川
             */

            private int id;
            private int parentsId;
            private String phoneNumber;
            private String name;
            private String sex;
            private String birthdate;
            private Object birthTime;
            private String countiy;
            private String province;
            private String city;
            private int countiyId = -1;
            private int provinceId = -1;
            private int cityId = -1;
            private String medical;
            private String medicalState;
            private String firstLanguage;
            private String firstRests;
            private String secondLanguage;
            private String secondRests;
            private String fatherCulture;
            private String motherCulture;
            private String trainingMethod;
            private String trainingRests;
            private String perfection;
            private long createTime;
            private Object updateTime;
            private String status;
            private Object photo;
            private String address;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getParentsId() {
                return parentsId;
            }

            public void setParentsId(int parentsId) {
                this.parentsId = parentsId;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getBirthdate() {
                return birthdate;
            }

            public void setBirthdate(String birthdate) {
                this.birthdate = birthdate;
            }

            public Object getBirthTime() {
                return birthTime;
            }

            public void setBirthTime(Object birthTime) {
                this.birthTime = birthTime;
            }

            public String getCountiy() {
                return countiy;
            }

            public void setCountiy(String countiy) {
                this.countiy = countiy;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getMedical() {
                return medical;
            }

            public void setMedical(String medical) {
                this.medical = medical;
            }

            public String getMedicalState() {
                return medicalState;
            }

            public void setMedicalState(String medicalState) {
                this.medicalState = medicalState;
            }

            public String getFirstLanguage() {
                return firstLanguage;
            }

            public void setFirstLanguage(String firstLanguage) {
                this.firstLanguage = firstLanguage;
            }

            public String getFirstRests() {
                return firstRests;
            }

            public void setFirstRests(String firstRests) {
                this.firstRests = firstRests;
            }

            public String getSecondLanguage() {
                return secondLanguage;
            }

            public void setSecondLanguage(String secondLanguage) {
                this.secondLanguage = secondLanguage;
            }

            public String getSecondRests() {
                return secondRests;
            }

            public void setSecondRests(String secondRests) {
                this.secondRests = secondRests;
            }

            public String getFatherCulture() {
                return fatherCulture;
            }

            public void setFatherCulture(String fatherCulture) {
                this.fatherCulture = fatherCulture;
            }

            public String getMotherCulture() {
                return motherCulture;
            }

            public void setMotherCulture(String motherCulture) {
                this.motherCulture = motherCulture;
            }

            public String getTrainingMethod() {
                return trainingMethod;
            }

            public void setTrainingMethod(String trainingMethod) {
                this.trainingMethod = trainingMethod;
            }

            public String getTrainingRests() {
                return trainingRests;
            }

            public void setTrainingRests(String trainingRests) {
                this.trainingRests = trainingRests;
            }

            public String getPerfection() {
                return perfection;
            }

            public void setPerfection(String perfection) {
                this.perfection = perfection;
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public Object getPhoto() {
                return photo;
            }

            public void setPhoto(Object photo) {
                this.photo = photo;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getCountiyId() {
                return countiyId;
            }

            public void setCountiyId(int countiyId) {
                this.countiyId = countiyId;
            }

            public int getProvinceId() {
                return provinceId;
            }

            public void setProvinceId(int provinceId) {
                this.provinceId = provinceId;
            }

            public int getCityId() {
                return cityId;
            }

            public void setCityId(int cityId) {
                this.cityId = cityId;
            }
        }
    }
}
