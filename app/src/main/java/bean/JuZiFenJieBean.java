package bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenlipeng on 2018/11/7 0007.
 * describe :
 */

public class JuZiFenJieBean  implements Serializable{
    /**
     * msg : 查询成功！
     * code : 200
     * helptime : [{"id":5,"topicType":4,"sort":1,"helpTime":7,"states":"1","createTime":1541495504000,"updateTime":1541556270000},{"id":6,"topicType":4,"sort":2,"helpTime":8,"states":"1","createTime":1541495504000,"updateTime":1541556270000},{"id":7,"topicType":4,"sort":3,"helpTime":9,"states":"1","createTime":1541495504000,"updateTime":1541556270000},{"id":8,"topicType":4,"sort":4,"helpTime":10,"states":"1","createTime":1541495504000,"updateTime":1541556270000}]
     * cosentenceResolveTestde : [{"id":7,"startSlideshow":"http://yuudee.oss-cn-beijing.aliyuncs.com/b5184463a52843859e420882fb8e31ad.png,http://yuudee.oss-cn-beijing.aliyuncs.com/e5289a40919c4070b5f0f93a9572a801.png,http://yuudee.oss-cn-beijing.aliyuncs.com/8e6c35b0aa4a4413b91bcb670e508a10.png,","cardOneImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/c04bf63771064f448ac0b059cb1fa899.png","cardOneChar":"男","cardOneRecord":null,"cardOneTime":3,"cardTwoImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/20cd021a327c419581174d1457f89278.png","cardTwoChar":"孩","cardTwoRecord":null,"cardTwoTime":3,"cardThreeImage":null,"cardThreeChar":"吃","cardThreeRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/ca747bfeaaa1463cad0831b4c7834fbc.MP3","cardThreeTime":3,"cardFourImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/2d3a2cee981b4e00a890bc0b2f0fb943.png","cardFourChar":"冰激凌","cardFourRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/3929d9524b4c4fba984adc35128bf9ba.MP3","cardFourTime":3,"groupChar":"男孩吃冰激凌","groupRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/edbfda1e56f945f19675bed2c7cd2893.MP3","createTime":1541586558000,"updateTime":null,"states":"1","list":[{"cardImage":null,"cardChar":"吃","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/ca747bfeaaa1463cad0831b4c7834fbc.MP3"},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/c04bf63771064f448ac0b059cb1fa899.png","cardChar":"男","cardRecord":null},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/20cd021a327c419581174d1457f89278.png","cardChar":"孩","cardRecord":null},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/2d3a2cee981b4e00a890bc0b2f0fb943.png","cardChar":"冰激凌","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/3929d9524b4c4fba984adc35128bf9ba.MP3"}]},{"id":6,"startSlideshow":"http://yuudee.oss-cn-beijing.aliyuncs.com/09cf89d6298347c9ae69d1e58b40f623.png,http://yuudee.oss-cn-beijing.aliyuncs.com/8359dccaeed242c0a47e7243479d640d.png,http://yuudee.oss-cn-beijing.aliyuncs.com/98da89c2ef5341d4aecb1b658c8f833e.png,","cardOneImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/2ab06692d88e4ab28592f8719b77c062.png","cardOneChar":"女","cardOneRecord":null,"cardOneTime":2,"cardTwoImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/98974025a8064c5eabbfa398d7f62879.png","cardTwoChar":"孩","cardTwoRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/81facdf88bbd486baf765c86858d8bdc.MP3","cardTwoTime":2,"cardThreeImage":null,"cardThreeChar":"洗","cardThreeRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/e3bbca1ae55f4e13960c8af09d9306be.MP3","cardThreeTime":2,"cardFourImage":null,"cardFourChar":"衣服","cardFourRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/6c8fe9776b2b492a812228498fa855e2.png","cardFourTime":2,"groupChar":"女孩洗衣服","groupRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/cf72071ee464446baffeab4a19ad4027.MP3","createTime":1541586136000,"updateTime":null,"states":"1","list":[{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/98974025a8064c5eabbfa398d7f62879.png","cardChar":"孩","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/81facdf88bbd486baf765c86858d8bdc.MP3"},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/2ab06692d88e4ab28592f8719b77c062.png","cardChar":"女","cardRecord":null},{"cardImage":null,"cardChar":"衣服","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/6c8fe9776b2b492a812228498fa855e2.png"},{"cardImage":null,"cardChar":"洗","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/e3bbca1ae55f4e13960c8af09d9306be.MP3"}]}]
     * sentenceResolveTraining : [{"id":11,"startSlideshow":"http://yuudee.oss-cn-beijing.aliyuncs.com/e01c1e00d42b4577ad74ef2d62f95d58.png,http://yuudee.oss-cn-beijing.aliyuncs.com/7ad78e5fe961454e8494c92f95648223.png,http://yuudee.oss-cn-beijing.aliyuncs.com/75277e2c7cb941ffbc08e4119bc0f1bb.png,","cardOneImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/29ae00de11d547fa8d7b08e32d3c9002.png","cardOneChar":"男","cardOneRecord":null,"cardTwoImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/1bab0c365aa2400b8fbae2e7e89f265b.png","cardTwoChar":"孩","cardTwoRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/60e5b31b5df3469e8d793bb0f652e2de.MP3","cardThreeImage":null,"cardThreeChar":"洗","cardThreeRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/d15a8ee8e3d7447ba03d1b45942c8bcd.MP3","cardFourImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/51447c1b0b2741cc8b28eef3a54004ee.png","cardFourChar":"衣服","cardFourRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/e3925b83020347c092228f1ca0d69417.MP3","groupChar":"男孩洗衣服","groupRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/a81ac2a893374a0bb736fe523d9a9999.MP3","createTime":1541586014000,"updateTime":null,"states":"1","list":[{"cardImage":null,"cardChar":"洗","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/d15a8ee8e3d7447ba03d1b45942c8bcd.MP3"},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/29ae00de11d547fa8d7b08e32d3c9002.png","cardChar":"男","cardRecord":null},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/1bab0c365aa2400b8fbae2e7e89f265b.png","cardChar":"孩","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/60e5b31b5df3469e8d793bb0f652e2de.MP3"},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/51447c1b0b2741cc8b28eef3a54004ee.png","cardChar":"衣服","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/e3925b83020347c092228f1ca0d69417.MP3"}]},{"id":13,"startSlideshow":"http://yuudee.oss-cn-beijing.aliyuncs.com/5dc836867913414cb3dce326170ad29f.png,http://yuudee.oss-cn-beijing.aliyuncs.com/6fa0f014b8c446b18e41b0dcecb2a35c.png,http://yuudee.oss-cn-beijing.aliyuncs.com/b7d5e27d7ac44f7fb848232a3a3c3ba5.png,","cardOneImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/5dedea52aa4c4021a6bbc91ff07a9c02.png","cardOneChar":"女","cardOneRecord":null,"cardTwoImage":null,"cardTwoChar":"孩","cardTwoRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/433fd4a3a60a44728ae1b540d6102d97.MP3","cardThreeImage":null,"cardThreeChar":"洗","cardThreeRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/0e6b2f3f40cb4ce191e08545db8d8a0d.MP3","cardFourImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/a4f9ed5a3fd94e36a03dbdb052f93634.png","cardFourChar":"衣服","cardFourRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/063a3cf5761846728c9438033ac285b3.MP3","groupChar":"女孩洗衣服","groupRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/2d5798a3bb6e446abf141124807aa28d.MP3","createTime":1541586462000,"updateTime":null,"states":"1","list":[{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/5dedea52aa4c4021a6bbc91ff07a9c02.png","cardChar":"女","cardRecord":null},{"cardImage":null,"cardChar":"孩","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/433fd4a3a60a44728ae1b540d6102d97.MP3"},{"cardImage":null,"cardChar":"洗","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/0e6b2f3f40cb4ce191e08545db8d8a0d.MP3"},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/a4f9ed5a3fd94e36a03dbdb052f93634.png","cardChar":"衣服","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/063a3cf5761846728c9438033ac285b3.MP3"}]},{"id":12,"startSlideshow":"http://yuudee.oss-cn-beijing.aliyuncs.com/915cba8490ae4b35b9e2130d7ab6433d.png,http://yuudee.oss-cn-beijing.aliyuncs.com/744c1dea011a4008911c230b9f3cce4d.png,http://yuudee.oss-cn-beijing.aliyuncs.com/34dd0561eb4a4b818774dd7896d55bf3.png,","cardOneImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/2521ee75b5204733af47d1c9885878ea.png","cardOneChar":"男","cardOneRecord":null,"cardTwoImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/ee98ca3c67844932bca76ca5e4bed22f.png","cardTwoChar":"孩","cardTwoRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/fe308eeea1de420a87353bdecf6c74a8.MP3","cardThreeImage":null,"cardThreeChar":"吃","cardThreeRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/9aa33513a8034bffa42467e9891a5ab3.MP3","cardFourImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/31ebe13778d5473698f353a1b82a182b.png","cardFourChar":"冰激凌","cardFourRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/8658c810d5bb4294981771fa19611145.MP3","groupChar":"男孩吃冰激凌","groupRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/58ee0969280647369d5906321dc35920.MP3","createTime":1541586353000,"updateTime":null,"states":"1","list":[{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/2521ee75b5204733af47d1c9885878ea.png","cardChar":"男","cardRecord":null},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/ee98ca3c67844932bca76ca5e4bed22f.png","cardChar":"孩","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/fe308eeea1de420a87353bdecf6c74a8.MP3"},{"cardImage":null,"cardChar":"吃","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/9aa33513a8034bffa42467e9891a5ab3.MP3"},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/31ebe13778d5473698f353a1b82a182b.png","cardChar":"冰激凌","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/8658c810d5bb4294981771fa19611145.MP3"}]}]
     */

    private String msg;
    private int code;
    private List<HelptimeBean> helptime;
    private List<CosentenceResolveTestdeBean> cosentenceResolveTestde;
    private List<SentenceResolveTrainingBean> sentenceResolveTraining;

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

    public List<HelptimeBean> getHelptime() {
        return helptime;
    }

    public void setHelptime(List<HelptimeBean> helptime) {
        this.helptime = helptime;
    }

    public List<CosentenceResolveTestdeBean> getCosentenceResolveTestde() {
        return cosentenceResolveTestde;
    }

    public void setCosentenceResolveTestde(List<CosentenceResolveTestdeBean> cosentenceResolveTestde) {
        this.cosentenceResolveTestde = cosentenceResolveTestde;
    }

    public List<SentenceResolveTrainingBean> getSentenceResolveTraining() {
        return sentenceResolveTraining;
    }

    public void setSentenceResolveTraining(List<SentenceResolveTrainingBean> sentenceResolveTraining) {
        this.sentenceResolveTraining = sentenceResolveTraining;
    }

    public static class HelptimeBean implements Serializable{
        /**
         * id : 5
         * topicType : 4
         * sort : 1
         * helpTime : 7
         * states : 1
         * createTime : 1541495504000
         * updateTime : 1541556270000
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

    public static class CosentenceResolveTestdeBean implements Serializable{
        /**
         * id : 7
         * startSlideshow : http://yuudee.oss-cn-beijing.aliyuncs.com/b5184463a52843859e420882fb8e31ad.png,http://yuudee.oss-cn-beijing.aliyuncs.com/e5289a40919c4070b5f0f93a9572a801.png,http://yuudee.oss-cn-beijing.aliyuncs.com/8e6c35b0aa4a4413b91bcb670e508a10.png,
         * cardOneImage : http://yuudee.oss-cn-beijing.aliyuncs.com/c04bf63771064f448ac0b059cb1fa899.png
         * cardOneChar : 男
         * cardOneRecord : null
         * cardOneTime : 3
         * cardTwoImage : http://yuudee.oss-cn-beijing.aliyuncs.com/20cd021a327c419581174d1457f89278.png
         * cardTwoChar : 孩
         * cardTwoRecord : null
         * cardTwoTime : 3
         * cardThreeImage : null
         * cardThreeChar : 吃
         * cardThreeRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/ca747bfeaaa1463cad0831b4c7834fbc.MP3
         * cardThreeTime : 3
         * cardFourImage : http://yuudee.oss-cn-beijing.aliyuncs.com/2d3a2cee981b4e00a890bc0b2f0fb943.png
         * cardFourChar : 冰激凌
         * cardFourRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/3929d9524b4c4fba984adc35128bf9ba.MP3
         * cardFourTime : 3
         * groupChar : 男孩吃冰激凌
         * groupRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/edbfda1e56f945f19675bed2c7cd2893.MP3
         * createTime : 1541586558000
         * updateTime : null
         * states : 1
         * list : [{"cardImage":null,"cardChar":"吃","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/ca747bfeaaa1463cad0831b4c7834fbc.MP3"},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/c04bf63771064f448ac0b059cb1fa899.png","cardChar":"男","cardRecord":null},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/20cd021a327c419581174d1457f89278.png","cardChar":"孩","cardRecord":null},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/2d3a2cee981b4e00a890bc0b2f0fb943.png","cardChar":"冰激凌","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/3929d9524b4c4fba984adc35128bf9ba.MP3"}]
         */

        private int id;
        private String startSlideshow;
        private String cardOneImage;
        private String cardOneChar;
        private String cardOneRecord;
        private int cardOneTime;
        private String cardTwoImage;
        private String cardTwoChar;
        private String cardTwoRecord;
        private int cardTwoTime;
        private Object cardThreeImage;
        private String cardThreeChar;
        private String cardThreeRecord;
        private int cardThreeTime;
        private String cardFourImage;
        private String cardFourChar;
        private String cardFourRecord;
        private int cardFourTime;
        private String groupChar;
        private String groupRecord;
        private long createTime;
        private Object updateTime;
        private String states;
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

        public String getCardOneImage() {
            return cardOneImage;
        }

        public void setCardOneImage(String cardOneImage) {
            this.cardOneImage = cardOneImage;
        }

        public String getCardOneChar() {
            return cardOneChar;
        }

        public void setCardOneChar(String cardOneChar) {
            this.cardOneChar = cardOneChar;
        }

        public String getCardOneRecord() {
            return cardOneRecord;
        }

        public void setCardOneRecord(String cardOneRecord) {
            this.cardOneRecord = cardOneRecord;
        }

        public int getCardOneTime() {
            return cardOneTime;
        }

        public void setCardOneTime(int cardOneTime) {
            this.cardOneTime = cardOneTime;
        }

        public String getCardTwoImage() {
            return cardTwoImage;
        }

        public void setCardTwoImage(String cardTwoImage) {
            this.cardTwoImage = cardTwoImage;
        }

        public String getCardTwoChar() {
            return cardTwoChar;
        }

        public void setCardTwoChar(String cardTwoChar) {
            this.cardTwoChar = cardTwoChar;
        }

        public String getCardTwoRecord() {
            return cardTwoRecord;
        }

        public void setCardTwoRecord(String cardTwoRecord) {
            this.cardTwoRecord = cardTwoRecord;
        }

        public int getCardTwoTime() {
            return cardTwoTime;
        }

        public void setCardTwoTime(int cardTwoTime) {
            this.cardTwoTime = cardTwoTime;
        }

        public Object getCardThreeImage() {
            return cardThreeImage;
        }

        public void setCardThreeImage(Object cardThreeImage) {
            this.cardThreeImage = cardThreeImage;
        }

        public String getCardThreeChar() {
            return cardThreeChar;
        }

        public void setCardThreeChar(String cardThreeChar) {
            this.cardThreeChar = cardThreeChar;
        }

        public String getCardThreeRecord() {
            return cardThreeRecord;
        }

        public void setCardThreeRecord(String cardThreeRecord) {
            this.cardThreeRecord = cardThreeRecord;
        }

        public int getCardThreeTime() {
            return cardThreeTime;
        }

        public void setCardThreeTime(int cardThreeTime) {
            this.cardThreeTime = cardThreeTime;
        }

        public String getCardFourImage() {
            return cardFourImage;
        }

        public void setCardFourImage(String cardFourImage) {
            this.cardFourImage = cardFourImage;
        }

        public String getCardFourChar() {
            return cardFourChar;
        }

        public void setCardFourChar(String cardFourChar) {
            this.cardFourChar = cardFourChar;
        }

        public String getCardFourRecord() {
            return cardFourRecord;
        }

        public void setCardFourRecord(String cardFourRecord) {
            this.cardFourRecord = cardFourRecord;
        }

        public int getCardFourTime() {
            return cardFourTime;
        }

        public void setCardFourTime(int cardFourTime) {
            this.cardFourTime = cardFourTime;
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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            /**
             * cardImage : null
             * cardChar : 吃
             * cardRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/ca747bfeaaa1463cad0831b4c7834fbc.MP3
             */

            private String cardImage;
            private String cardChar;
            private String cardRecord;

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

    public static class SentenceResolveTrainingBean implements Serializable{
        /**
         * id : 11
         * startSlideshow : http://yuudee.oss-cn-beijing.aliyuncs.com/e01c1e00d42b4577ad74ef2d62f95d58.png,http://yuudee.oss-cn-beijing.aliyuncs.com/7ad78e5fe961454e8494c92f95648223.png,http://yuudee.oss-cn-beijing.aliyuncs.com/75277e2c7cb941ffbc08e4119bc0f1bb.png,
         * cardOneImage : http://yuudee.oss-cn-beijing.aliyuncs.com/29ae00de11d547fa8d7b08e32d3c9002.png
         * cardOneChar : 男
         * cardOneRecord : null
         * cardTwoImage : http://yuudee.oss-cn-beijing.aliyuncs.com/1bab0c365aa2400b8fbae2e7e89f265b.png
         * cardTwoChar : 孩
         * cardTwoRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/60e5b31b5df3469e8d793bb0f652e2de.MP3
         * cardThreeImage : null
         * cardThreeChar : 洗
         * cardThreeRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/d15a8ee8e3d7447ba03d1b45942c8bcd.MP3
         * cardFourImage : http://yuudee.oss-cn-beijing.aliyuncs.com/51447c1b0b2741cc8b28eef3a54004ee.png
         * cardFourChar : 衣服
         * cardFourRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/e3925b83020347c092228f1ca0d69417.MP3
         * groupChar : 男孩洗衣服
         * groupRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/a81ac2a893374a0bb736fe523d9a9999.MP3
         * createTime : 1541586014000
         * updateTime : null
         * states : 1
         * list : [{"cardImage":null,"cardChar":"洗","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/d15a8ee8e3d7447ba03d1b45942c8bcd.MP3"},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/29ae00de11d547fa8d7b08e32d3c9002.png","cardChar":"男","cardRecord":null},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/1bab0c365aa2400b8fbae2e7e89f265b.png","cardChar":"孩","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/60e5b31b5df3469e8d793bb0f652e2de.MP3"},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/51447c1b0b2741cc8b28eef3a54004ee.png","cardChar":"衣服","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/e3925b83020347c092228f1ca0d69417.MP3"}]
         */

        private int id;
        private String startSlideshow;
        private String cardOneImage;
        private String cardOneChar;
        private String cardOneRecord;
        private String cardTwoImage;
        private String cardTwoChar;
        private String cardTwoRecord;
        private Object cardThreeImage;
        private String cardThreeChar;
        private String cardThreeRecord;
        private String cardFourImage;
        private String cardFourChar;
        private String cardFourRecord;
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

        public String getCardOneImage() {
            return cardOneImage;
        }

        public void setCardOneImage(String cardOneImage) {
            this.cardOneImage = cardOneImage;
        }

        public String getCardOneChar() {
            return cardOneChar;
        }

        public void setCardOneChar(String cardOneChar) {
            this.cardOneChar = cardOneChar;
        }

        public String getCardOneRecord() {
            return cardOneRecord;
        }

        public void setCardOneRecord(String cardOneRecord) {
            this.cardOneRecord = cardOneRecord;
        }

        public String getCardTwoImage() {
            return cardTwoImage;
        }

        public void setCardTwoImage(String cardTwoImage) {
            this.cardTwoImage = cardTwoImage;
        }

        public String getCardTwoChar() {
            return cardTwoChar;
        }

        public void setCardTwoChar(String cardTwoChar) {
            this.cardTwoChar = cardTwoChar;
        }

        public String getCardTwoRecord() {
            return cardTwoRecord;
        }

        public void setCardTwoRecord(String cardTwoRecord) {
            this.cardTwoRecord = cardTwoRecord;
        }

        public Object getCardThreeImage() {
            return cardThreeImage;
        }

        public void setCardThreeImage(Object cardThreeImage) {
            this.cardThreeImage = cardThreeImage;
        }

        public String getCardThreeChar() {
            return cardThreeChar;
        }

        public void setCardThreeChar(String cardThreeChar) {
            this.cardThreeChar = cardThreeChar;
        }

        public String getCardThreeRecord() {
            return cardThreeRecord;
        }

        public void setCardThreeRecord(String cardThreeRecord) {
            this.cardThreeRecord = cardThreeRecord;
        }

        public String getCardFourImage() {
            return cardFourImage;
        }

        public void setCardFourImage(String cardFourImage) {
            this.cardFourImage = cardFourImage;
        }

        public String getCardFourChar() {
            return cardFourChar;
        }

        public void setCardFourChar(String cardFourChar) {
            this.cardFourChar = cardFourChar;
        }

        public String getCardFourRecord() {
            return cardFourRecord;
        }

        public void setCardFourRecord(String cardFourRecord) {
            this.cardFourRecord = cardFourRecord;
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

        public static class ListBeanX implements Serializable{
            /**
             * cardImage : null
             * cardChar : 洗
             * cardRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/d15a8ee8e3d7447ba03d1b45942c8bcd.MP3
             */

            private String cardImage;
            private String cardChar;
            private String cardRecord;

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
