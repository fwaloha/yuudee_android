package bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenlipeng on 2018/11/7 0007.
 * describe :
 */

public class DongciBean implements Serializable {

    /**
     * msg : 查询成功！
     * code : 200
     * verbTest : [{"id":10,"startSlideshow":"http://yuudee.oss-cn-beijing.aliyuncs.com/1d57215f1a7141d4bf5ae7fede86bfb8.png,http://yuudee.oss-cn-beijing.aliyuncs.com/ca4a309bbee54626b745ccf9f726dc4f.png,http://yuudee.oss-cn-beijing.aliyuncs.com/09211adb914e4c0da445f9b1d8be667b.png,","endSlideshow":null,"verbType":"1","verbImage":null,"verbChar":"吃","verbRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/f8715da047a24d9ea1ad930f6de5f451.MP3","cardImage":null,"cardChar":"冰激凌","cardRecord":null,"groupImage":null,"groupChar":"吃冰激凌","groupRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/ee4b5c8c928c4f02a6c85a123a0d7b89.MP3","createTime":1541557460000,"updateTime":null,"states":"1","cardOneTime":1,"cardTwoTime":2,"list":[{"isSuccess":null,"cardImage":null,"cardChar":"吃","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/f8715da047a24d9ea1ad930f6de5f451.MP3"},{"isSuccess":null,"cardImage":null,"cardChar":"冰激凌","cardRecord":null}]},{"id":11,"startSlideshow":"http://yuudee.oss-cn-beijing.aliyuncs.com/84721439c94b43019991c1932ce6392a.png,http://yuudee.oss-cn-beijing.aliyuncs.com/ee825118c3044d55aadafe8cecc5e3c1.png,http://yuudee.oss-cn-beijing.aliyuncs.com/b7eece71a81f4ce09e730845fa44484c.png,","endSlideshow":null,"verbType":"1","verbImage":null,"verbChar":"吃","verbRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/0d6e5f7bc7704746aad63b43f71b698f.MP3","cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/c99ceb49a7ee4079ac037be5a70052c1.png","cardChar":"虫","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/64a36f46bb4c44739258a4cf70a2121e.png","groupImage":null,"groupChar":"吃虫","groupRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/66033517a87540fa86244b615fb9d3df.MP3","createTime":1541557712000,"updateTime":null,"states":"1","cardOneTime":1,"cardTwoTime":1,"list":[{"isSuccess":null,"cardImage":null,"cardChar":"吃","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/0d6e5f7bc7704746aad63b43f71b698f.MP3"},{"isSuccess":null,"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/c99ceb49a7ee4079ac037be5a70052c1.png","cardChar":"虫","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/64a36f46bb4c44739258a4cf70a2121e.png"}]},{"id":12,"startSlideshow":"http://yuudee.oss-cn-beijing.aliyuncs.com/d415fbb65d284d42bb30b6231d7a0971.png,http://yuudee.oss-cn-beijing.aliyuncs.com/7c218678ac8c400d8bb04da77acdd37c.png,http://yuudee.oss-cn-beijing.aliyuncs.com/f7a077971a854f35a809a1aacd0b7daf.png,","endSlideshow":null,"verbType":"1","verbImage":null,"verbChar":"吃","verbRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/0291c8121d8f47ddb77b0ceb49ca022c.MP3","cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/64f1493721a349348774fd644196315a.png","cardChar":"草","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/cec98754a744410ea8939c5da4a9fabb.png","groupImage":null,"groupChar":"吃草","groupRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/1b9f1b0973a24a15b0ee2af1c079707f.MP3","createTime":1541557799000,"updateTime":null,"states":"1","cardOneTime":1,"cardTwoTime":1,"list":[{"isSuccess":null,"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/64f1493721a349348774fd644196315a.png","cardChar":"草","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/cec98754a744410ea8939c5da4a9fabb.png"},{"isSuccess":null,"cardImage":null,"cardChar":"吃","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/0291c8121d8f47ddb77b0ceb49ca022c.MP3"}]}]
     * helptime : {"id":2,"topicType":2,"sort":1,"helpTime":4,"states":"1","createTime":1540882195000,"updateTime":1541554844000}
     * verbTraining : [{"id":22,"startSlideshow":"http://yuudee.oss-cn-beijing.aliyuncs.com/7ec4e13ba67a455d8331357f70e6247f.jpg,http://yuudee.oss-cn-beijing.aliyuncs.com/1972d86e9ba64d3db7fbf15e94608a16.jpg,http://yuudee.oss-cn-beijing.aliyuncs.com/40168354007845da8777b660bdfa94b8.jpg,","endSlideshow":null,"verbType":"1","verbChar":"吃","verbRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/4e43d87a2bd24b55935e625783b18381.MP3","cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/6950c30b64354cb0aa615718b9ff65b1.png","cardChar":"菜","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/9359db5969a84e438b2d48cdf9c9d647.png","groupImage":null,"groupChar":"吃菜","groupRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/4e6e77aadbb24b9788b3d69e579ec71e.MP3","createTime":1541498230000,"updateTime":null,"states":"1","list":[{"isSuccess":"0","cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/63f5ceef02d541e0b18f29a31622d755.png","cardChar":"蛋糕","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/22f5e873ac1940ae8f2ee64162cc9ddb.png"},{"isSuccess":"1","cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/6950c30b64354cb0aa615718b9ff65b1.png","cardChar":"菜","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/9359db5969a84e438b2d48cdf9c9d647.png"}]},{"id":19,"startSlideshow":"http://yuudee.oss-cn-beijing.aliyuncs.com/3aec80af208b4e569a6b7660dc55b9a5.png,http://yuudee.oss-cn-beijing.aliyuncs.com/6acfb6c9eaad4dbe95597f67b6b43dc5.png,http://yuudee.oss-cn-beijing.aliyuncs.com/391cf2f282e34160898a5e70134b301f.png,","endSlideshow":null,"verbType":"1","verbChar":"吃","verbRecord":null,"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/821dd0cee43c49e3bddfaea8382c9f79.png","cardChar":"虫","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/cc8af34276f4405c8ce0ac9ab0f4c39a.png","groupImage":null,"groupChar":"吃虫","groupRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/0154308ea6964486a25cbe160aea663d.MP3","createTime":1541497411000,"updateTime":null,"states":"1","list":[{"isSuccess":"1","cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/821dd0cee43c49e3bddfaea8382c9f79.png","cardChar":"虫","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/cc8af34276f4405c8ce0ac9ab0f4c39a.png"},{"isSuccess":"0","cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/63f5ceef02d541e0b18f29a31622d755.png","cardChar":"蛋糕","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/22f5e873ac1940ae8f2ee64162cc9ddb.png"}]},{"id":20,"startSlideshow":"http://yuudee.oss-cn-beijing.aliyuncs.com/7dec1c77ad23487695cae45841a834e4.png,http://yuudee.oss-cn-beijing.aliyuncs.com/3b3ce6ed67f34890bdbf2352c06520e4.png,http://yuudee.oss-cn-beijing.aliyuncs.com/058d015915fb488aae28a67e36ac029a.png,","endSlideshow":null,"verbType":"1","verbChar":"吃","verbRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/fac71b916e594ab6b5e8c65a7f06c9fa.MP3","cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/63f5ceef02d541e0b18f29a31622d755.png","cardChar":"蛋糕","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/22f5e873ac1940ae8f2ee64162cc9ddb.png","groupImage":null,"groupChar":"吃蛋糕","groupRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/8ef2d4e04cdc4c40aab5e72647770b80.MP3","createTime":1541497536000,"updateTime":null,"states":"1","list":[{"isSuccess":"1","cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/63f5ceef02d541e0b18f29a31622d755.png","cardChar":"蛋糕","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/22f5e873ac1940ae8f2ee64162cc9ddb.png"},{"isSuccess":"0","cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/821dd0cee43c49e3bddfaea8382c9f79.png","cardChar":"虫","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/cc8af34276f4405c8ce0ac9ab0f4c39a.png"}]}]
     */

    private String msg;
    private int code;
    private HelptimeBean helptime;
    private List<VerbTestBean> verbTest;
    private List<VerbTrainingBean> verbTraining;

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

    public HelptimeBean getHelptime() {
        return helptime;
    }

    public void setHelptime(HelptimeBean helptime) {
        this.helptime = helptime;
    }

    public List<VerbTestBean> getVerbTest() {
        return verbTest;
    }

    public void setVerbTest(List<VerbTestBean> verbTest) {
        this.verbTest = verbTest;
    }

    public List<VerbTrainingBean> getVerbTraining() {
        return verbTraining;
    }

    public void setVerbTraining(List<VerbTrainingBean> verbTraining) {
        this.verbTraining = verbTraining;
    }

    public static class HelptimeBean implements Serializable {
        /**
         * id : 2
         * topicType : 2
         * sort : 1
         * helpTime : 4
         * states : 1
         * createTime : 1540882195000
         * updateTime : 1541554844000
         */

        private int id;
        private int topicType;
        private int sort;
        private int helpTime;
        private String states;
        private long createTime;
        private long updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTopicType() {
            return topicType;
        }

        public void setTopicType(int topicType) {
            this.topicType = topicType;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getHelpTime() {
            return helpTime;
        }

        public void setHelpTime(int helpTime) {
            this.helpTime = helpTime;
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

    public static class VerbTestBean implements Serializable {
        /**
         * id : 10
         * startSlideshow : http://yuudee.oss-cn-beijing.aliyuncs.com/1d57215f1a7141d4bf5ae7fede86bfb8.png,http://yuudee.oss-cn-beijing.aliyuncs.com/ca4a309bbee54626b745ccf9f726dc4f.png,http://yuudee.oss-cn-beijing.aliyuncs.com/09211adb914e4c0da445f9b1d8be667b.png,
         * endSlideshow : null
         * verbType : 1
         * verbImage : null
         * verbChar : 吃
         * verbRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/f8715da047a24d9ea1ad930f6de5f451.MP3
         * cardImage : null
         * cardChar : 冰激凌
         * cardRecord : null
         * groupImage : null
         * groupChar : 吃冰激凌
         * groupRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/ee4b5c8c928c4f02a6c85a123a0d7b89.MP3
         * createTime : 1541557460000
         * updateTime : null
         * states : 1
         * cardOneTime : 1
         * cardTwoTime : 2
         * list : [{"isSuccess":null,"cardImage":null,"cardChar":"吃","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/f8715da047a24d9ea1ad930f6de5f451.MP3"},{"isSuccess":null,"cardImage":null,"cardChar":"冰激凌","cardRecord":null}]
         */

        private int id;
        private String startSlideshow;
        private Object endSlideshow;
        private String verbType;
        private Object verbImage;
        private String verbChar;
        private String verbRecord;
        private Object cardImage;
        private String cardChar;
        private String cardRecord;
        private Object groupImage;
        private String groupChar;
        private String groupRecord;
        private long createTime;
        private Object updateTime;
        private String states;
        private int cardOneTime;
        private int cardTwoTime;
        private List<ListBean> list;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStartSlideshow() {
            return startSlideshow;
        }

        public void setStartSlideshow(String startSlideshow) {
            this.startSlideshow = startSlideshow;
        }

        public Object getEndSlideshow() {
            return endSlideshow;
        }

        public void setEndSlideshow(Object endSlideshow) {
            this.endSlideshow = endSlideshow;
        }

        public String getVerbType() {
            return verbType;
        }

        public void setVerbType(String verbType) {
            this.verbType = verbType;
        }

        public Object getVerbImage() {
            return verbImage;
        }

        public void setVerbImage(Object verbImage) {
            this.verbImage = verbImage;
        }

        public String getVerbChar() {
            return verbChar;
        }

        public void setVerbChar(String verbChar) {
            this.verbChar = verbChar;
        }

        public String getVerbRecord() {
            return verbRecord;
        }

        public void setVerbRecord(String verbRecord) {
            this.verbRecord = verbRecord;
        }

        public Object getCardImage() {
            return cardImage;
        }

        public void setCardImage(Object cardImage) {
            this.cardImage = cardImage;
        }

        public String getCardChar() {
            return cardChar;
        }

        public void setCardChar(String cardChar) {
            this.cardChar = cardChar;
        }

        public String getCardRecord() {
            return cardRecord;
        }

        public void setCardRecord(String  cardRecord) {
            this.cardRecord = cardRecord;
        }

        public Object getGroupImage() {
            return groupImage;
        }

        public void setGroupImage(Object groupImage) {
            this.groupImage = groupImage;
        }

        public String getGroupChar() {
            return groupChar;
        }

        public void setGroupChar(String groupChar) {
            this.groupChar = groupChar;
        }

        public String getGroupRecord() {
            return groupRecord;
        }

        public void setGroupRecord(String groupRecord) {
            this.groupRecord = groupRecord;
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

        public String getStates() {
            return states;
        }

        public void setStates(String states) {
            this.states = states;
        }

        public int getCardOneTime() {
            return cardOneTime;
        }

        public void setCardOneTime(int cardOneTime) {
            this.cardOneTime = cardOneTime;
        }

        public int getCardTwoTime() {
            return cardTwoTime;
        }

        public void setCardTwoTime(int cardTwoTime) {
            this.cardTwoTime = cardTwoTime;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {
            /**
             * isSuccess : null
             * cardImage : null
             * cardChar : 吃
             * cardRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/f8715da047a24d9ea1ad930f6de5f451.MP3
             */

            private Object isSuccess;
            private String cardImage;
            private String cardChar;
            private String cardRecord;

            public Object getIsSuccess() {
                return isSuccess;
            }

            public void setIsSuccess(Object isSuccess) {
                this.isSuccess = isSuccess;
            }

            public String getCardImage() {
                return cardImage;
            }

            public void setCardImage(String cardImage) {
                this.cardImage = cardImage;
            }

            public String getCardChar() {
                return cardChar;
            }

            public void setCardChar(String cardChar) {
                this.cardChar = cardChar;
            }

            public String getCardRecord() {
                return cardRecord;
            }

            public void setCardRecord(String cardRecord) {
                this.cardRecord = cardRecord;
            }
        }
    }

    public static class VerbTrainingBean implements Serializable {
        /**
         * id : 22
         * startSlideshow : http://yuudee.oss-cn-beijing.aliyuncs.com/7ec4e13ba67a455d8331357f70e6247f.jpg,http://yuudee.oss-cn-beijing.aliyuncs.com/1972d86e9ba64d3db7fbf15e94608a16.jpg,http://yuudee.oss-cn-beijing.aliyuncs.com/40168354007845da8777b660bdfa94b8.jpg,
         * endSlideshow : null
         * verbType : 1
         * verbChar : 吃
         * verbRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/4e43d87a2bd24b55935e625783b18381.MP3
         * cardImage : http://yuudee.oss-cn-beijing.aliyuncs.com/6950c30b64354cb0aa615718b9ff65b1.png
         * cardChar : 菜
         * cardRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/9359db5969a84e438b2d48cdf9c9d647.png
         * groupImage : null
         * groupChar : 吃菜
         * groupRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/4e6e77aadbb24b9788b3d69e579ec71e.MP3
         * createTime : 1541498230000
         * updateTime : null
         * states : 1
         * list : [{"isSuccess":"0","cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/63f5ceef02d541e0b18f29a31622d755.png","cardChar":"蛋糕","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/22f5e873ac1940ae8f2ee64162cc9ddb.png"},{"isSuccess":"1","cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/6950c30b64354cb0aa615718b9ff65b1.png","cardChar":"菜","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/9359db5969a84e438b2d48cdf9c9d647.png"}]
         */

        private int id;
        private String startSlideshow;
        private Object endSlideshow;
        private String verbType;
        private String verbChar;
        private String verbRecord;
        private String cardImage;
        private String cardChar;
        private String cardRecord;
        private Object groupImage;
        private String groupChar;
        private String groupRecord;
        private long createTime;
        private Object updateTime;
        private String states;
        private List<ListBeanX> list;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStartSlideshow() {
            return startSlideshow;
        }

        public void setStartSlideshow(String startSlideshow) {
            this.startSlideshow = startSlideshow;
        }

        public Object getEndSlideshow() {
            return endSlideshow;
        }

        public void setEndSlideshow(Object endSlideshow) {
            this.endSlideshow = endSlideshow;
        }

        public String getVerbType() {
            return verbType;
        }

        public void setVerbType(String verbType) {
            this.verbType = verbType;
        }

        public String getVerbChar() {
            return verbChar;
        }

        public void setVerbChar(String verbChar) {
            this.verbChar = verbChar;
        }

        public String getVerbRecord() {
            return verbRecord;
        }

        public void setVerbRecord(String verbRecord) {
            this.verbRecord = verbRecord;
        }

        public String getCardImage() {
            return cardImage;
        }

        public void setCardImage(String cardImage) {
            this.cardImage = cardImage;
        }

        public String getCardChar() {
            return cardChar;
        }

        public void setCardChar(String cardChar) {
            this.cardChar = cardChar;
        }

        public String getCardRecord() {
            return cardRecord;
        }

        public void setCardRecord(String cardRecord) {
            this.cardRecord = cardRecord;
        }

        public Object getGroupImage() {
            return groupImage;
        }

        public void setGroupImage(Object groupImage) {
            this.groupImage = groupImage;
        }

        public String getGroupChar() {
            return groupChar;
        }

        public void setGroupChar(String groupChar) {
            this.groupChar = groupChar;
        }

        public String getGroupRecord() {
            return groupRecord;
        }

        public void setGroupRecord(String groupRecord) {
            this.groupRecord = groupRecord;
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

        public String getStates() {
            return states;
        }

        public void setStates(String states) {
            this.states = states;
        }

        public List<ListBeanX> getList() {
            return list;
        }

        public void setList(List<ListBeanX> list) {
            this.list = list;
        }

        public static class ListBeanX implements Serializable {
            /**
             * isSuccess : 0
             * cardImage : http://yuudee.oss-cn-beijing.aliyuncs.com/63f5ceef02d541e0b18f29a31622d755.png
             * cardChar : 蛋糕
             * cardRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/22f5e873ac1940ae8f2ee64162cc9ddb.png
             */

            private String isSuccess;
            private String cardImage;
            private String cardChar;
            private String cardRecord;

            public String getIsSuccess() {
                return isSuccess;
            }

            public void setIsSuccess(String isSuccess) {
                this.isSuccess = isSuccess;
            }

            public String getCardImage() {
                return cardImage;
            }

            public void setCardImage(String cardImage) {
                this.cardImage = cardImage;
            }

            public String getCardChar() {
                return cardChar;
            }

            public void setCardChar(String cardChar) {
                this.cardChar = cardChar;
            }

            public String getCardRecord() {
                return cardRecord;
            }

            public void setCardRecord(String cardRecord) {
                this.cardRecord = cardRecord;
            }
        }
    }
}
