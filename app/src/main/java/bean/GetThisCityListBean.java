package bean;

import java.util.List;

public class GetThisCityListBean {

    /**
     * msg : 查询成功！
     * code : 200
     * data : {"provinceList":[{"areaid":3,"areacode":"866","areaname":"北京","level":2,"citycode":"111","center":null,"parentid":1},{"areaid":4,"areacode":"888","areaname":"上海","level":2,"citycode":"222","center":null,"parentid":1},{"areaid":5,"areacode":"777","areaname":"宁夏","level":2,"citycode":"1111","center":null,"parentid":1}],"cityList":[{"areaid":6,"areacode":"222","areaname":"银川","level":3,"citycode":"333","center":null,"parentid":5},{"areaid":7,"areacode":"333","areaname":"中卫","level":3,"citycode":"222","center":null,"parentid":5}],"countryList":[{"areaid":1,"areacode":"86","areaname":"中国","level":1,"citycode":"123","center":null,"parentid":-1},{"areaid":2,"areacode":"88","areaname":"美国","level":1,"citycode":"145","center":null,"parentid":-1}]}
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
        private List<ProvinceListBean> provinceList;
        private List<CityListBean> cityList;
        private List<CountryListBean> countryList;

        public List<ProvinceListBean> getProvinceList() {
            return provinceList;
        }

        public void setProvinceList(List<ProvinceListBean> provinceList) {
            this.provinceList = provinceList;
        }

        public List<CityListBean> getCityList() {
            return cityList;
        }

        public void setCityList(List<CityListBean> cityList) {
            this.cityList = cityList;
        }

        public List<CountryListBean> getCountryList() {
            return countryList;
        }

        public void setCountryList(List<CountryListBean> countryList) {
            this.countryList = countryList;
        }

        public static class ProvinceListBean {
            /**
             * areaid : 3
             * areacode : 866
             * areaname : 北京
             * level : 2
             * citycode : 111
             * center : null
             * parentid : 1
             */

            private int areaid;
            private String areacode;
            private String areaname;
            private int level;
            private String citycode;
            private Object center;
            private int parentid;

            public int getAreaid() {
                return areaid;
            }

            public void setAreaid(int areaid) {
                this.areaid = areaid;
            }

            public String getAreacode() {
                return areacode;
            }

            public void setAreacode(String areacode) {
                this.areacode = areacode;
            }

            public String getAreaname() {
                return areaname;
            }

            public void setAreaname(String areaname) {
                this.areaname = areaname;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getCitycode() {
                return citycode;
            }

            public void setCitycode(String citycode) {
                this.citycode = citycode;
            }

            public Object getCenter() {
                return center;
            }

            public void setCenter(Object center) {
                this.center = center;
            }

            public int getParentid() {
                return parentid;
            }

            public void setParentid(int parentid) {
                this.parentid = parentid;
            }
        }

        public static class CityListBean {
            /**
             * areaid : 6
             * areacode : 222
             * areaname : 银川
             * level : 3
             * citycode : 333
             * center : null
             * parentid : 5
             */

            private int areaid;
            private String areacode;
            private String areaname;
            private int level;
            private String citycode;
            private Object center;
            private int parentid;

            public int getAreaid() {
                return areaid;
            }

            public void setAreaid(int areaid) {
                this.areaid = areaid;
            }

            public String getAreacode() {
                return areacode;
            }

            public void setAreacode(String areacode) {
                this.areacode = areacode;
            }

            public String getAreaname() {
                return areaname;
            }

            public void setAreaname(String areaname) {
                this.areaname = areaname;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getCitycode() {
                return citycode;
            }

            public void setCitycode(String citycode) {
                this.citycode = citycode;
            }

            public Object getCenter() {
                return center;
            }

            public void setCenter(Object center) {
                this.center = center;
            }

            public int getParentid() {
                return parentid;
            }

            public void setParentid(int parentid) {
                this.parentid = parentid;
            }
        }

        public static class CountryListBean {
            /**
             * areaid : 1
             * areacode : 86
             * areaname : 中国
             * level : 1
             * citycode : 123
             * center : null
             * parentid : -1
             */

            private int areaid;
            private String areacode;
            private String areaname;
            private int level;
            private String citycode;
            private Object center;
            private int parentid;

            public int getAreaid() {
                return areaid;
            }

            public void setAreaid(int areaid) {
                this.areaid = areaid;
            }

            public String getAreacode() {
                return areacode;
            }

            public void setAreacode(String areacode) {
                this.areacode = areacode;
            }

            public String getAreaname() {
                return areaname;
            }

            public void setAreaname(String areaname) {
                this.areaname = areaname;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getCitycode() {
                return citycode;
            }

            public void setCitycode(String citycode) {
                this.citycode = citycode;
            }

            public Object getCenter() {
                return center;
            }

            public void setCenter(Object center) {
                this.center = center;
            }

            public int getParentid() {
                return parentid;
            }

            public void setParentid(int parentid) {
                this.parentid = parentid;
            }
        }
    }
}
