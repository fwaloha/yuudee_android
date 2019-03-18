package bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenlipeng on 2018/11/7 0007.
 * describe :
 */

public class JuZiChengZu implements Serializable{
    /**
     * msg : 查询成功！
     * sentenceGroupTraining : [{"id":12,"startSlideshow":"http://yuudee.oss-cn-beijing.aliyuncs.com/1d5111989e214b23aba21abc91870f33.png","cardOneImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/24dfbe50ad45427eb4aebb59bf0c7094.png","cardOneChar":"红鸟","cardOneRecord":null,"cardTwoImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/1ebd427573f94830af07149b3a943dc9.png","cardTwoChar":"吃虫","cardTwoRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/746fb67c7a634cd996bc6fb5c12ad88f.MP3","groupChar":"红鸟吃虫","groupRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/4221ad91fa73473793b13bcdfa4d8369.MP3","createTime":1541497635000,"updateTime":null,"states":"1","list":[{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/1ebd427573f94830af07149b3a943dc9.png","cardChar":"吃虫","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/746fb67c7a634cd996bc6fb5c12ad88f.MP3"},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/24dfbe50ad45427eb4aebb59bf0c7094.png","cardChar":"红鸟","cardRecord":null}]},{"id":11,"startSlideshow":"http://yuudee.oss-cn-beijing.aliyuncs.com/7b117d178b874495892f175d70f0701e.png","cardOneImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/210c8087f9784852a2fcc4dc9d694bee.png","cardOneChar":"红鸟","cardOneRecord":null,"cardTwoImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/696232ce53cf47feaae6061f4ef2242a.png","cardTwoChar":"吃虫","cardTwoRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/8a5def17bcb1491698bf482a1c1de684.MP3","groupChar":"红鸟吃虫","groupRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/db9856fba86445ae90be7366a6ebd03f.MP3","createTime":1541497031000,"updateTime":null,"states":"1","list":[{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/210c8087f9784852a2fcc4dc9d694bee.png","cardChar":"红鸟","cardRecord":null},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/696232ce53cf47feaae6061f4ef2242a.png","cardChar":"吃虫","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/8a5def17bcb1491698bf482a1c1de684.MP3"}]},{"id":9,"startSlideshow":"http://yuudee.oss-cn-beijing.aliyuncs.com/ae0e017530ba4d6db2a2c67eaeab017a.png","cardOneImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/9e6149e7c78c486abe75bd96871a894d.png","cardOneChar":"白狗","cardOneRecord":null,"cardTwoImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/1ff3368d47924bfcb3eb24811dd29d91.png","cardTwoChar":"吃骨头","cardTwoRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/4e7f50ea2faa4695b60fdd965d522e70._男-骨头","groupChar":"白狗吃骨头","groupRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/57291760bdf1472f9a7dfd31a3953e6c._男-白狗吃骨头","createTime":1541496406000,"updateTime":null,"states":"1","list":[{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/1ff3368d47924bfcb3eb24811dd29d91.png","cardChar":"吃骨头","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/4e7f50ea2faa4695b60fdd965d522e70._男-骨头"},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/9e6149e7c78c486abe75bd96871a894d.png","cardChar":"白狗","cardRecord":null}]},{"id":10,"startSlideshow":"http://yuudee.oss-cn-beijing.aliyuncs.com/0392a226960d4283b3da8de4c1aeb294.png","cardOneImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/e65630a41d0545338c934c2a4f4fdab0.png","cardOneChar":"蓝鸟","cardOneRecord":null,"cardTwoImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/8bf2fbe556774c208ae86052c3ec06f0.png","cardTwoChar":"吃虫","cardTwoRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/d7afc7e1392f487db89882accbb14c69.MP3","groupChar":"蓝鸟吃虫","groupRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/eab3f78dbe074c4598b9777e29db65df.MP3","createTime":1541496795000,"updateTime":null,"states":"1","list":[{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/8bf2fbe556774c208ae86052c3ec06f0.png","cardChar":"吃虫","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/d7afc7e1392f487db89882accbb14c69.MP3"},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/e65630a41d0545338c934c2a4f4fdab0.png","cardChar":"蓝鸟","cardRecord":null}]},{"id":14,"startSlideshow":"http://yuudee.oss-cn-beijing.aliyuncs.com/6faeb9b9b78247c394c390bf751b8865.png","cardOneImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/f635786e9468441cb5ba1d7c623bb10c.png","cardOneChar":"白狗","cardOneRecord":null,"cardTwoImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/c9e8351622214a358f33edc8514ff08f.png","cardTwoChar":"吃骨头","cardTwoRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/e18cdc0557c6420f8a819953845ad340.MP3","groupChar":"白狗吃骨头","groupRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/49dfd03bda5e430fba3efdd243d43571.MP3","createTime":1541498665000,"updateTime":null,"states":"1","list":[{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/f635786e9468441cb5ba1d7c623bb10c.png","cardChar":"白狗","cardRecord":null},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/c9e8351622214a358f33edc8514ff08f.png","cardChar":"吃骨头","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/e18cdc0557c6420f8a819953845ad340.MP3"}]}]
     * code : 200
     * helptime : [{"id":3,"topicType":3,"sort":2,"helpTime":12,"states":"1","createTime":1541495504000,"updateTime":1541555497000},{"id":4,"topicType":3,"sort":2,"helpTime":7,"states":"1","createTime":1541495504000,"updateTime":null}]
     * sentenceGroupTest : [{"id":7,"startSlideshow":"http://yuudee.oss-cn-beijing.aliyuncs.com/8d9044fb4ee64bac93e2cdf0c4f7106a.png","cardOneImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/699ccbf5997f43fdbe601f7e6ce9441b.png","cardOneChar":"红鸟","cardOneRecord":null,"cardOneTime":10,"cardTwoImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/bea306d58a2f477ba8404d62d790fd34.png","cardTwoChar":"吃虫","cardTwoRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/0695676764764507ba9958d11db4082c.MP3","cardTwoTime":5,"groupChar":"红鸟吃虫","groupRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/e8e1004c21ad43398da2910a45c2064d.MP3","createTime":1541500165000,"updateTime":null,"states":"1","list":[{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/bea306d58a2f477ba8404d62d790fd34.png","cardChar":"吃虫","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/0695676764764507ba9958d11db4082c.MP3"},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/699ccbf5997f43fdbe601f7e6ce9441b.png","cardChar":"红鸟","cardRecord":null}]},{"id":10,"startSlideshow":"http://yuudee.oss-cn-beijing.aliyuncs.com/a5523fc442574c7492a32837b5b886ec.pnghttp://yuudee.oss-cn-beijing.aliyuncs.com/0d2676d49f0d425ca99f655067f56a76.pnghttp://yuudee.oss-cn-beijing.aliyuncs.com/073a8b0173774f7fa564e646c9915db2.png","cardOneImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/b9b08e9afef74575a8739dcae9c680a5.png","cardOneChar":"蓝鸟","cardOneRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/52b4deda2c2747f29ffa8650528d67ec.MP3","cardOneTime":10,"cardTwoImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/949749a32fa54e9aaa6529cabe9a974c.png","cardTwoChar":"吃虫","cardTwoRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/915d434784e94295b8d4b4518c64fc6d.MP3","cardTwoTime":5,"groupChar":"蓝鸟吃虫","groupRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/68b8b1b1ce8f40ee8d9a4455f0279c6f.MP3","createTime":1541500870000,"updateTime":null,"states":"1","list":[{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/949749a32fa54e9aaa6529cabe9a974c.png","cardChar":"吃虫","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/915d434784e94295b8d4b4518c64fc6d.MP3"},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/b9b08e9afef74575a8739dcae9c680a5.png","cardChar":"蓝鸟","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/52b4deda2c2747f29ffa8650528d67ec.MP3"}]},{"id":9,"startSlideshow":"http://yuudee.oss-cn-beijing.aliyuncs.com/721b508f125b47f39ca4754ec8e3d290.pnghttp://yuudee.oss-cn-beijing.aliyuncs.com/fbe1230fbb0b4206b2e945e41b4ff2dc.pnghttp://yuudee.oss-cn-beijing.aliyuncs.com/6a31fb461e904f48bfbdfc78be91c6aa.png","cardOneImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/b41ca42b76034c8cb43ee832f3db9ec1.png","cardOneChar":"白狗","cardOneRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/3e232b3bbaef4194bc23557c72b65431.MP3","cardOneTime":10,"cardTwoImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/15b1145090e24ea2b650a33e4f8dbeb4.png","cardTwoChar":"吃骨头","cardTwoRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/6cb02945ebf945bba26383056301f0bc.MP3","cardTwoTime":5,"groupChar":"白狗吃骨头","groupRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/3970f2f5b07e457e84ac8c53cfaf490e.MP3","createTime":1541500630000,"updateTime":null,"states":"1","list":[{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/b41ca42b76034c8cb43ee832f3db9ec1.png","cardChar":"白狗","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/3e232b3bbaef4194bc23557c72b65431.MP3"},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/15b1145090e24ea2b650a33e4f8dbeb4.png","cardChar":"吃骨头","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/6cb02945ebf945bba26383056301f0bc.MP3"}]}]
     */

    private String msg;
    private int code;
    private List<SentenceGroupTrainingBean> sentenceGroupTraining;
    private List<HelptimeBean> helptime;
    private List<SentenceGroupTestBean> sentenceGroupTest;

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

    public List<SentenceGroupTrainingBean> getSentenceGroupTraining() {
        return sentenceGroupTraining;
    }

    public void setSentenceGroupTraining(List<SentenceGroupTrainingBean> sentenceGroupTraining) {
        this.sentenceGroupTraining = sentenceGroupTraining;
    }

    public List<HelptimeBean> getHelptime() {
        return helptime;
    }

    public void setHelptime(List<HelptimeBean> helptime) {
        this.helptime = helptime;
    }

    public List<SentenceGroupTestBean> getSentenceGroupTest() {
        return sentenceGroupTest;
    }

    public void setSentenceGroupTest(List<SentenceGroupTestBean> sentenceGroupTest) {
        this.sentenceGroupTest = sentenceGroupTest;
    }

    public static class SentenceGroupTrainingBean implements Serializable{
        /**
         * id : 12
         * startSlideshow : http://yuudee.oss-cn-beijing.aliyuncs.com/1d5111989e214b23aba21abc91870f33.png
         * cardOneImage : http://yuudee.oss-cn-beijing.aliyuncs.com/24dfbe50ad45427eb4aebb59bf0c7094.png
         * cardOneChar : 红鸟
         * cardOneRecord : null
         * cardTwoImage : http://yuudee.oss-cn-beijing.aliyuncs.com/1ebd427573f94830af07149b3a943dc9.png
         * cardTwoChar : 吃虫
         * cardTwoRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/746fb67c7a634cd996bc6fb5c12ad88f.MP3
         * groupChar : 红鸟吃虫
         * groupRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/4221ad91fa73473793b13bcdfa4d8369.MP3
         * createTime : 1541497635000
         * updateTime : null
         * states : 1
         * list : [{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/1ebd427573f94830af07149b3a943dc9.png","cardChar":"吃虫","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/746fb67c7a634cd996bc6fb5c12ad88f.MP3"},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/24dfbe50ad45427eb4aebb59bf0c7094.png","cardChar":"红鸟","cardRecord":null}]
         */

        private int id;
        private String startSlideshow;
        private String cardOneImage;
        private String cardOneChar;
        private Object cardOneRecord;
        private String cardTwoImage;
        private String cardTwoChar;
        private String cardTwoRecord;
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

        public Object getCardOneRecord() {
            return cardOneRecord;
        }

        public void setCardOneRecord(Object cardOneRecord) {
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
             * cardImage : http://yuudee.oss-cn-beijing.aliyuncs.com/1ebd427573f94830af07149b3a943dc9.png
             * cardChar : 吃虫
             * cardRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/746fb67c7a634cd996bc6fb5c12ad88f.MP3
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

    public static class HelptimeBean implements Serializable{
        /**
         * id : 3
         * topicType : 3
         * sort : 2
         * helpTime : 12
         * states : 1
         * createTime : 1541495504000
         * updateTime : 1541555497000
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

    public static class SentenceGroupTestBean implements Serializable{
        /**
         * id : 7
         * startSlideshow : http://yuudee.oss-cn-beijing.aliyuncs.com/8d9044fb4ee64bac93e2cdf0c4f7106a.png
         * cardOneImage : http://yuudee.oss-cn-beijing.aliyuncs.com/699ccbf5997f43fdbe601f7e6ce9441b.png
         * cardOneChar : 红鸟
         * cardOneRecord : null
         * cardOneTime : 10
         * cardTwoImage : http://yuudee.oss-cn-beijing.aliyuncs.com/bea306d58a2f477ba8404d62d790fd34.png
         * cardTwoChar : 吃虫
         * cardTwoRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/0695676764764507ba9958d11db4082c.MP3
         * cardTwoTime : 5
         * groupChar : 红鸟吃虫
         * groupRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/e8e1004c21ad43398da2910a45c2064d.MP3
         * createTime : 1541500165000
         * updateTime : null
         * states : 1
         * list : [{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/bea306d58a2f477ba8404d62d790fd34.png","cardChar":"吃虫","cardRecord":"http://yuudee.oss-cn-beijing.aliyuncs.com/0695676764764507ba9958d11db4082c.MP3"},{"cardImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/699ccbf5997f43fdbe601f7e6ce9441b.png","cardChar":"红鸟","cardRecord":null}]
         */

        private int id;
        private String startSlideshow;
        private String cardOneImage;
        private String cardOneChar;
        private Object cardOneRecord;
        private int cardOneTime;
        private String cardTwoImage;
        private String cardTwoChar;
        private String cardTwoRecord;
        private int cardTwoTime;
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

        public Object getCardOneRecord() {
            return cardOneRecord;
        }

        public void setCardOneRecord(Object cardOneRecord) {
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
             * cardImage : http://yuudee.oss-cn-beijing.aliyuncs.com/bea306d58a2f477ba8404d62d790fd34.png
             * cardChar : 吃虫
             * cardRecord : http://yuudee.oss-cn-beijing.aliyuncs.com/0695676764764507ba9958d11db4082c.MP3
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
