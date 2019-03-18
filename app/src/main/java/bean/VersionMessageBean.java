package bean;

public class VersionMessageBean {

    /**
     * img : http://yuudee.oss-cn-beijing.aliyuncs.com/d2dbe08f21564a999d1f8577157863cf.png
     * phone : 18135697075
     * versions : {"id":1,"number":"qqqq","type":"2","title":"qqqqqqqqq","download":"qqqqqqqqqqqqqq","states":"1","createTime":1541053268000,"updateTime":1539746551000}
     * name : 小雨滴
     * suggest : {"id":1,"phone":"74110","network":"www.baidu.com","weixin":"","mail":"https://www.baidu.com/","qqun":"49643485","versionsId":1,"states":"1","createTime":1541385858000,"updateTime":1541745891000}
     */

    private String img;
    private String phone;
    private VersionsBean versions;
    private String name;
    private SuggestBean suggest;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public VersionsBean getVersions() {
        return versions;
    }

    public void setVersions(VersionsBean versions) {
        this.versions = versions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SuggestBean getSuggest() {
        return suggest;
    }

    public void setSuggest(SuggestBean suggest) {
        this.suggest = suggest;
    }

    public static class VersionsBean {
        /**
         * id : 1
         * number : qqqq
         * type : 2
         * title : qqqqqqqqq
         * download : qqqqqqqqqqqqqq
         * states : 1
         * createTime : 1541053268000
         * updateTime : 1539746551000
         */

        private int id;
        private String number;
        private String type;
        private String title;
        private String download;
        private String states;
        private long createTime;
        private long updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDownload() {
            return download;
        }

        public void setDownload(String download) {
            this.download = download;
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

    public static class SuggestBean {
        /**
         * id : 1
         * phone : 74110
         * network : www.baidu.com
         * weixin :
         * mail : https://www.baidu.com/
         * qqun : 49643485
         * versionsId : 1
         * states : 1
         * createTime : 1541385858000
         * updateTime : 1541745891000
         */

        private int id;
        private String phone;
        private String network;
        private String weixin;
        private String mail;
        private String qqun;
        private int versionsId;
        private String states;
        private long createTime;
        private long updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNetwork() {
            return network;
        }

        public void setNetwork(String network) {
            this.network = network;
        }

        public String getWeixin() {
            return weixin;
        }

        public void setWeixin(String weixin) {
            this.weixin = weixin;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getQqun() {
            return qqun;
        }

        public void setQqun(String qqun) {
            this.qqun = qqun;
        }

        public int getVersionsId() {
            return versionsId;
        }

        public void setVersionsId(int versionsId) {
            this.versionsId = versionsId;
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
