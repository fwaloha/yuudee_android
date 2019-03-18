package bean;

import java.util.List;

/**
 * Created by chenlipeng on 2018/10/30 0030.
 * describe :
 */

public class LocationBean {


    /**
     * title : 热门城市
     * list : [{"id":1,"cityType":"热门城市","name":"中国","logo":"无","phonePrefix":"86","states":"1","createTime":1539673319000,"updateTime":1539673317000},{"id":2,"cityType":"热门城市","name":"北京","logo":"1","phonePrefix":"861","states":"1","createTime":1540431435000,"updateTime":1540431438000}]
     */

    private String title;
    private List<ListBean> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
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
