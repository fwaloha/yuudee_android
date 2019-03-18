package bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenlipeng on 2018/11/5 0005.
 * describe :
 */

public class MingciBean implements Serializable {


    /**
     * msg : 查询成功！
     * code : 200
     * nounTraining : [{"id":25,"wireImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/340aafac2c424b65b5a47e73af21659c.jpg","wireChar":"小黄鸭","wireRecord":null,"colorPenChar":"黄","colorPenRecord":null,"groupImage":null,"groupRecord":null,"createTime":1541385857000,"updateTime":null,"states":"1","groupWord":"不可爱的小黄鸭"},{"id":4,"wireImage":"http://img0.imgtn.bdimg.com/it/u=2584254964,2701376004&fm=26&gp=0.jpg","wireChar":"猫","wireRecord":null,"colorPenChar":"红","colorPenRecord":null,"groupImage":"http://img1.imgtn.bdimg.com/it/u=2221958662,530121837&fm=26&gp=0.jpg","groupRecord":null,"createTime":1540888114000,"updateTime":null,"states":"1","groupWord":""},{"id":1,"wireImage":"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2984365902,1905923950&fm=170&s=397C6686D02691076A0F8C270300F05B&w=320&h=240&img.JPEG","wireChar":"猫","wireRecord":"www.vip.com","colorPenChar":"黑","colorPenRecord":"www.sina.com.cn","groupImage":"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2984365902,1905923950&fm=170&s=397C6686D02691076A0F8C270300F05B&w=320&h=240&img.JPEG","groupRecord":"www.baidu.com","createTime":1540798237000,"updateTime":null,"states":"1","groupWord":""},{"id":3,"wireImage":"http://img0.imgtn.bdimg.com/it/u=2584254964,2701376004&fm=26&gp=0.jpg","wireChar":"猫","wireRecord":null,"colorPenChar":"黄","colorPenRecord":null,"groupImage":"http://img1.imgtn.bdimg.com/it/u=2221958662,530121837&fm=26&gp=0.jpg","groupRecord":null,"createTime":1540888112000,"updateTime":null,"states":"1","groupWord":""},{"id":2,"wireImage":"http://img0.imgtn.bdimg.com/it/u=2584254964,2701376004&fm=26&gp=0.jpg","wireChar":"猫","wireRecord":"","colorPenChar":"白","colorPenRecord":null,"groupImage":"http://img1.imgtn.bdimg.com/it/u=2221958662,530121837&fm=26&gp=0.jpg","groupRecord":null,"createTime":1540888109000,"updateTime":null,"states":"1","groupWord":""},{"id":26,"wireImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/e30e403568ad4c5aa4931f09a712b4fe.jpg","wireChar":"线框图文字","wireRecord":null,"colorPenChar":"颜色笔文","colorPenRecord":null,"groupImage":"http://yuudee.oss-cn-beijing.aliyuncs.com/fa01cd69f52c497695b08bdea37a3ef2.jpg","groupRecord":null,"createTime":1541468917000,"updateTime":null,"states":"1","groupWord":"组合文"}]
     * time : {"id":1,"topicType":1,"sort":1,"helpTime":5,"states":"1","createTime":1540882195000,"updateTime":null}
     * nounTest : [{"id":2,"cardColorImage":"http://img3.imgtn.bdimg.com/it/u=1067139836,1089300925&fm=26&gp=0.jpg","cardColorChar":"黄","cardColorRecord":null,"fristAssistTime":10,"cardWireImage":"http://img1.imgtn.bdimg.com/it/u=3519731816,3637041645&fm=26&gp=0.jpg","cardWireChar":"香蕉","cardWireRecord":null,"secondAssistTime":5,"groupImage":"http://img2.imgtn.bdimg.com/it/u=3929791648,2593030610&fm=26&gp=0.jpg","groupChar":"黄橡胶","groupRecord":null,"createTime":1540888347000,"updateTime":null,"states":"1","list":[{"cardColorImage":"http://img1.imgtn.bdimg.com/it/u=3519731816,3637041645&fm=26&gp=0.jpg","cardColorChar":"香蕉","cardColorRecord":null,"assistTime":5},{"cardColorImage":"http://img3.imgtn.bdimg.com/it/u=1067139836,1089300925&fm=26&gp=0.jpg","cardColorChar":"黄","cardColorRecord":null,"assistTime":10}]},{"id":1,"cardColorImage":"http://img3.imgtn.bdimg.com/it/u=1067139836,1089300925&fm=26&gp=0.jpg","cardColorChar":"红","cardColorRecord":null,"fristAssistTime":10,"cardWireImage":"http://img1.imgtn.bdimg.com/it/u=3519731816,3637041645&fm=26&gp=0.jpg","cardWireChar":"苹果","cardWireRecord":null,"secondAssistTime":5,"groupImage":"http://img2.imgtn.bdimg.com/it/u=3929791648,2593030610&fm=26&gp=0.jpg","groupChar":"红苹果","groupRecord":null,"createTime":1540888343000,"updateTime":null,"states":"1","list":[{"cardColorImage":"http://img3.imgtn.bdimg.com/it/u=1067139836,1089300925&fm=26&gp=0.jpg","cardColorChar":"红","cardColorRecord":null,"assistTime":10},{"cardColorImage":"http://img1.imgtn.bdimg.com/it/u=3519731816,3637041645&fm=26&gp=0.jpg","cardColorChar":"苹果","cardColorRecord":null,"assistTime":5}]},{"id":3,"cardColorImage":"http://img3.imgtn.bdimg.com/it/u=1067139836,1089300925&fm=26&gp=0.jpg","cardColorChar":"蓝","cardColorRecord":null,"fristAssistTime":10,"cardWireImage":"http://img1.imgtn.bdimg.com/it/u=3519731816,3637041645&fm=26&gp=0.jpg","cardWireChar":"莓","cardWireRecord":null,"secondAssistTime":5,"groupImage":"http://img2.imgtn.bdimg.com/it/u=3929791648,2593030610&fm=26&gp=0.jpg","groupChar":"蓝莓","groupRecord":null,"createTime":1540888349000,"updateTime":null,"states":"1","list":[{"cardColorImage":"http://img1.imgtn.bdimg.com/it/u=3519731816,3637041645&fm=26&gp=0.jpg","cardColorChar":"莓","cardColorRecord":null,"assistTime":5},{"cardColorImage":"http://img3.imgtn.bdimg.com/it/u=1067139836,1089300925&fm=26&gp=0.jpg","cardColorChar":"蓝","cardColorRecord":null,"assistTime":10}]}]
     * nounSense : [{"id":1,"cardAdjectiveImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1277792631,1813565208&fm=27&gp=0.jpg","cardAdjectiveChar":"黑","cardAdjectiveRecord":"www.baidu.com","fristAssistTime":5,"cardNounImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1277792631,1813565208&fm=27&gp=0.jpg","cardNounChar":"兄","cardNounRecord":"www.sina.com.cn","secondAssistTime":5,"groupImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1277792631,1813565208&fm=27&gp=0.jpg","groupChar":"黑兄","groupRecord":"515","disturbType":"3","idioType":"1","createTime":1540880748000,"updateTime":null,"states":"1","list":[{"cardAdjectiveImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1277792631,1813565208&fm=27&gp=0.jpg","cardAdjectiveChar":"黑","cardAdjectiveRecord":"www.baidu.com","assistTime":5,"isAdj":"1","isSuccess":"1"},{"cardAdjectiveImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1277792631,1813565208&fm=27&gp=0.jpg","cardAdjectiveChar":"兄","cardAdjectiveRecord":"www.sina.com.cn","assistTime":5,"isAdj":"0","isSuccess":"1"},{"cardAdjectiveImage":"http://img4.imgtn.bdimg.com/it/u=3367912032,1519020758&fm=26&gp=0.jpg","cardAdjectiveChar":"白","cardAdjectiveRecord":null,"assistTime":5,"isAdj":"1","isSuccess":"0"},{"cardAdjectiveImage":"http://img3.imgtn.bdimg.com/it/u=3782078028,147246801&fm=26&gp=0.jpg","cardAdjectiveChar":"葫芦","cardAdjectiveRecord":null,"assistTime":5,"isAdj":"0","isSuccess":"0"}]},{"id":2,"cardAdjectiveImage":"http://img4.imgtn.bdimg.com/it/u=3367912032,1519020758&fm=26&gp=0.jpg","cardAdjectiveChar":"白","cardAdjectiveRecord":null,"fristAssistTime":5,"cardNounImage":"http://img3.imgtn.bdimg.com/it/u=3782078028,147246801&fm=26&gp=0.jpg","cardNounChar":"葫芦","cardNounRecord":null,"secondAssistTime":5,"groupImage":"http://img4.imgtn.bdimg.com/it/u=758011588,1061007660&fm=26&gp=0.jpg","groupChar":"白葫芦","groupRecord":null,"disturbType":"3","idioType":"2","createTime":1540888620000,"updateTime":null,"states":"1","list":[{"cardAdjectiveImage":"http://img4.imgtn.bdimg.com/it/u=3367912032,1519020758&fm=26&gp=0.jpg","cardAdjectiveChar":"白","cardAdjectiveRecord":null,"assistTime":5,"isAdj":"1","isSuccess":"1"},{"cardAdjectiveImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1277792631,1813565208&fm=27&gp=0.jpg","cardAdjectiveChar":"兄","cardAdjectiveRecord":"www.sina.com.cn","assistTime":5,"isAdj":"0","isSuccess":"0"},{"cardAdjectiveImage":"http://img3.imgtn.bdimg.com/it/u=3782078028,147246801&fm=26&gp=0.jpg","cardAdjectiveChar":"葫芦","cardAdjectiveRecord":null,"assistTime":5,"isAdj":"0","isSuccess":"1"},{"cardAdjectiveImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1277792631,1813565208&fm=27&gp=0.jpg","cardAdjectiveChar":"黑","cardAdjectiveRecord":"www.baidu.com","assistTime":5,"isAdj":"1","isSuccess":"0"}]},{"id":4,"cardAdjectiveImage":"http://img4.imgtn.bdimg.com/it/u=3367912032,1519020758&fm=26&gp=0.jpg","cardAdjectiveChar":"黄","cardAdjectiveRecord":null,"fristAssistTime":5,"cardNounImage":"http://img3.imgtn.bdimg.com/it/u=3782078028,147246801&fm=26&gp=0.jpg","cardNounChar":"虫","cardNounRecord":null,"secondAssistTime":5,"groupImage":"http://img4.imgtn.bdimg.com/it/u=758011588,1061007660&fm=26&gp=0.jpg","groupChar":"黄虫","groupRecord":null,"disturbType":"3","idioType":"3","createTime":1540888626000,"updateTime":null,"states":"1","list":[{"cardAdjectiveImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1277792631,1813565208&fm=27&gp=0.jpg","cardAdjectiveChar":"兄","cardAdjectiveRecord":"www.sina.com.cn","assistTime":5,"isAdj":"0","isSuccess":"0"},{"cardAdjectiveImage":"http://img3.imgtn.bdimg.com/it/u=3782078028,147246801&fm=26&gp=0.jpg","cardAdjectiveChar":"虫","cardAdjectiveRecord":null,"assistTime":5,"isAdj":"0","isSuccess":"1"},{"cardAdjectiveImage":"http://img4.imgtn.bdimg.com/it/u=3367912032,1519020758&fm=26&gp=0.jpg","cardAdjectiveChar":"黄","cardAdjectiveRecord":null,"assistTime":5,"isAdj":"1","isSuccess":"1"},{"cardAdjectiveImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1277792631,1813565208&fm=27&gp=0.jpg","cardAdjectiveChar":"黑","cardAdjectiveRecord":"www.baidu.com","assistTime":5,"isAdj":"1","isSuccess":"0"}]},{"id":3,"cardAdjectiveImage":"http://img4.imgtn.bdimg.com/it/u=3367912032,1519020758&fm=26&gp=0.jpg","cardAdjectiveChar":"灰","cardAdjectiveRecord":null,"fristAssistTime":5,"cardNounImage":"http://img3.imgtn.bdimg.com/it/u=3782078028,147246801&fm=26&gp=0.jpg","cardNounChar":"胸","cardNounRecord":null,"secondAssistTime":5,"groupImage":"http://img4.imgtn.bdimg.com/it/u=758011588,1061007660&fm=26&gp=0.jpg","groupChar":"灰胸","groupRecord":null,"disturbType":"3","idioType":"3","createTime":1540888623000,"updateTime":null,"states":"1","list":[{"cardAdjectiveImage":"http://img4.imgtn.bdimg.com/it/u=3367912032,1519020758&fm=26&gp=0.jpg","cardAdjectiveChar":"灰","cardAdjectiveRecord":null,"assistTime":5,"isAdj":"1","isSuccess":"1"},{"cardAdjectiveImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1277792631,1813565208&fm=27&gp=0.jpg","cardAdjectiveChar":"兄","cardAdjectiveRecord":"www.sina.com.cn","assistTime":5,"isAdj":"0","isSuccess":"0"},{"cardAdjectiveImage":"http://img3.imgtn.bdimg.com/it/u=3782078028,147246801&fm=26&gp=0.jpg","cardAdjectiveChar":"胸","cardAdjectiveRecord":null,"assistTime":5,"isAdj":"0","isSuccess":"1"},{"cardAdjectiveImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1277792631,1813565208&fm=27&gp=0.jpg","cardAdjectiveChar":"黑","cardAdjectiveRecord":"www.baidu.com","assistTime":5,"isAdj":"1","isSuccess":"0"}]}]
     */

    private String msg;
    private int code;
    private TimeBean time;
    private List<NounTrainingBean> nounTraining;
    private List<NounTestBean> nounTest;
    private List<NounSenseBean> nounSense;

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

    public TimeBean getTime() {
        return time;
    }

    public void setTime(TimeBean time) {
        this.time = time;
    }

    public List<NounTrainingBean> getNounTraining() {
        return nounTraining;
    }

    public void setNounTraining(List<NounTrainingBean> nounTraining) {
        this.nounTraining = nounTraining;
    }

    public List<NounTestBean> getNounTest() {
        return nounTest;
    }

    public void setNounTest(List<NounTestBean> nounTest) {
        this.nounTest = nounTest;
    }

    public List<NounSenseBean> getNounSense() {
        return nounSense;
    }

    public void setNounSense(List<NounSenseBean> nounSense) {
        this.nounSense = nounSense;
    }

    public static class TimeBean implements  Serializable {
        /**
         * id : 1
         * topicType : 1
         * sort : 1
         * helpTime : 5
         * states : 1
         * createTime : 1540882195000
         * updateTime : null
         */

        private int id;
        private int topicType;
        private int sort;
        private int helpTime;
        private String states;
        private long createTime;
        private Object updateTime;

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

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }
    }

    public static class NounTrainingBean implements Serializable {
        /**
         * id : 25
         * wireImage : http://yuudee.oss-cn-beijing.aliyuncs.com/340aafac2c424b65b5a47e73af21659c.jpg
         * wireChar : 小黄鸭
         * wireRecord : null
         * colorPenChar : 黄
         * colorPenRecord : null
         * groupImage : null
         * groupRecord : null
         * createTime : 1541385857000
         * updateTime : null
         * states : 1
         * groupWord : 不可爱的小黄鸭
         */

        private int id;
        private String wireImage;
        private String wireChar;
        private String wireRecord;
        private String colorPenChar;
        private String colorPenRecord;
        private String groupImage;
        private String groupRecord;
        private long createTime;
        private Object updateTime;
        private String states;
        private String groupWord;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getWireImage() {
            return wireImage;
        }

        public void setWireImage(String wireImage) {
            this.wireImage = wireImage;
        }

        public String getWireChar() {
            return wireChar;
        }

        public void setWireChar(String wireChar) {
            this.wireChar = wireChar;
        }

        public String getWireRecord() {
            return wireRecord;
        }

        public void setWireRecord(String wireRecord) {
            this.wireRecord = wireRecord;
        }

        public String getColorPenChar() {
            return colorPenChar;
        }

        public void setColorPenChar(String colorPenChar) {
            this.colorPenChar = colorPenChar;
        }

        public String getColorPenRecord() {
            return colorPenRecord;
        }

        public void setColorPenRecord(String colorPenRecord) {
            this.colorPenRecord = colorPenRecord;
        }

        public String getGroupImage() {
            return groupImage;
        }

        public void setGroupImage(String groupImage) {
            this.groupImage = groupImage;
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

        public String getGroupWord() {
            return groupWord;
        }

        public void setGroupWord(String groupWord) {
            this.groupWord = groupWord;
        }
    }

    public static class NounTestBean implements Serializable {
        /**
         * id : 2
         * cardColorImage : http://img3.imgtn.bdimg.com/it/u=1067139836,1089300925&fm=26&gp=0.jpg
         * cardColorChar : 黄
         * cardColorRecord : null
         * fristAssistTime : 10
         * cardWireImage : http://img1.imgtn.bdimg.com/it/u=3519731816,3637041645&fm=26&gp=0.jpg
         * cardWireChar : 香蕉
         * cardWireRecord : null
         * secondAssistTime : 5
         * groupImage : http://img2.imgtn.bdimg.com/it/u=3929791648,2593030610&fm=26&gp=0.jpg
         * groupChar : 黄橡胶
         * groupRecord : null
         * createTime : 1540888347000
         * updateTime : null
         * states : 1
         * list : [{"cardColorImage":"http://img1.imgtn.bdimg.com/it/u=3519731816,3637041645&fm=26&gp=0.jpg","cardColorChar":"香蕉","cardColorRecord":null,"assistTime":5},{"cardColorImage":"http://img3.imgtn.bdimg.com/it/u=1067139836,1089300925&fm=26&gp=0.jpg","cardColorChar":"黄","cardColorRecord":null,"assistTime":10}]
         */

        private int id;
        private String cardColorImage;
        private String cardColorChar;
        private Object cardColorRecord;
        private int fristAssistTime;
        private String cardWireImage;
        private String cardWireChar;
        private Object cardWireRecord;
        private int secondAssistTime;
        private String groupImage;
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

        public String getCardColorImage() {
            return cardColorImage;
        }

        public void setCardColorImage(String cardColorImage) {
            this.cardColorImage = cardColorImage;
        }

        public String getCardColorChar() {
            return cardColorChar;
        }

        public void setCardColorChar(String cardColorChar) {
            this.cardColorChar = cardColorChar;
        }

        public Object getCardColorRecord() {
            return cardColorRecord;
        }

        public void setCardColorRecord(Object cardColorRecord) {
            this.cardColorRecord = cardColorRecord;
        }

        public int getFristAssistTime() {
            return fristAssistTime;
        }

        public void setFristAssistTime(int fristAssistTime) {
            this.fristAssistTime = fristAssistTime;
        }

        public String getCardWireImage() {
            return cardWireImage;
        }

        public void setCardWireImage(String cardWireImage) {
            this.cardWireImage = cardWireImage;
        }

        public String getCardWireChar() {
            return cardWireChar;
        }

        public void setCardWireChar(String cardWireChar) {
            this.cardWireChar = cardWireChar;
        }

        public Object getCardWireRecord() {
            return cardWireRecord;
        }

        public void setCardWireRecord(Object cardWireRecord) {
            this.cardWireRecord = cardWireRecord;
        }

        public int getSecondAssistTime() {
            return secondAssistTime;
        }

        public void setSecondAssistTime(int secondAssistTime) {
            this.secondAssistTime = secondAssistTime;
        }

        public String getGroupImage() {
            return groupImage;
        }

        public void setGroupImage(String groupImage) {
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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {
            /**
             * cardColorImage : http://img1.imgtn.bdimg.com/it/u=3519731816,3637041645&fm=26&gp=0.jpg
             * cardColorChar : 香蕉
             * cardColorRecord : null
             * assistTime : 5
             */

            private String cardColorImage;
            private String cardColorChar;
            private String cardColorRecord;
            private int assistTime;

            public String getCardColorImage() {
                return cardColorImage;
            }

            public void setCardColorImage(String cardColorImage) {
                this.cardColorImage = cardColorImage;
            }

            public String getCardColorChar() {
                return cardColorChar;
            }

            public void setCardColorChar(String cardColorChar) {
                this.cardColorChar = cardColorChar;
            }

            public String getCardColorRecord() {
                return cardColorRecord;
            }

            public void setCardColorRecord(String cardColorRecord) {
                this.cardColorRecord = cardColorRecord;
            }

            public int getAssistTime() {
                return assistTime;
            }

            public void setAssistTime(int assistTime) {
                this.assistTime = assistTime;
            }
        }
    }

    public static class NounSenseBean implements Serializable {
        /**
         * id : 1
         * cardAdjectiveImage : https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1277792631,1813565208&fm=27&gp=0.jpg
         * cardAdjectiveChar : 黑
         * cardAdjectiveRecord : www.baidu.com
         * fristAssistTime : 5
         * cardNounImage : https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1277792631,1813565208&fm=27&gp=0.jpg
         * cardNounChar : 兄
         * cardNounRecord : www.sina.com.cn
         * secondAssistTime : 5
         * groupImage : https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1277792631,1813565208&fm=27&gp=0.jpg
         * groupChar : 黑兄
         * groupRecord : 515
         * disturbType : 3
         * idioType : 1
         * createTime : 1540880748000
         * updateTime : null
         * states : 1
         * list : [{"cardAdjectiveImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1277792631,1813565208&fm=27&gp=0.jpg","cardAdjectiveChar":"黑","cardAdjectiveRecord":"www.baidu.com","assistTime":5,"isAdj":"1","isSuccess":"1"},{"cardAdjectiveImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1277792631,1813565208&fm=27&gp=0.jpg","cardAdjectiveChar":"兄","cardAdjectiveRecord":"www.sina.com.cn","assistTime":5,"isAdj":"0","isSuccess":"1"},{"cardAdjectiveImage":"http://img4.imgtn.bdimg.com/it/u=3367912032,1519020758&fm=26&gp=0.jpg","cardAdjectiveChar":"白","cardAdjectiveRecord":null,"assistTime":5,"isAdj":"1","isSuccess":"0"},{"cardAdjectiveImage":"http://img3.imgtn.bdimg.com/it/u=3782078028,147246801&fm=26&gp=0.jpg","cardAdjectiveChar":"葫芦","cardAdjectiveRecord":null,"assistTime":5,"isAdj":"0","isSuccess":"0"}]
         */

        private int id;
        private String cardAdjectiveImage;
        private String cardAdjectiveChar;
        private String cardAdjectiveRecord;
        private int fristAssistTime;
        private String cardNounImage;
        private String cardNounChar;
        private String cardNounRecord;
        private int secondAssistTime;
        private String groupImage;
        private String groupChar;
        private String groupRecord;
        private String disturbType;
        private String idioType;
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

        public String getCardAdjectiveImage() {
            return cardAdjectiveImage;
        }

        public void setCardAdjectiveImage(String cardAdjectiveImage) {
            this.cardAdjectiveImage = cardAdjectiveImage;
        }

        public String getCardAdjectiveChar() {
            return cardAdjectiveChar;
        }

        public void setCardAdjectiveChar(String cardAdjectiveChar) {
            this.cardAdjectiveChar = cardAdjectiveChar;
        }

        public String getCardAdjectiveRecord() {
            return cardAdjectiveRecord;
        }

        public void setCardAdjectiveRecord(String cardAdjectiveRecord) {
            this.cardAdjectiveRecord = cardAdjectiveRecord;
        }

        public int getFristAssistTime() {
            return fristAssistTime;
        }

        public void setFristAssistTime(int fristAssistTime) {
            this.fristAssistTime = fristAssistTime;
        }

        public String getCardNounImage() {
            return cardNounImage;
        }

        public void setCardNounImage(String cardNounImage) {
            this.cardNounImage = cardNounImage;
        }

        public String getCardNounChar() {
            return cardNounChar;
        }

        public void setCardNounChar(String cardNounChar) {
            this.cardNounChar = cardNounChar;
        }

        public String getCardNounRecord() {
            return cardNounRecord;
        }

        public void setCardNounRecord(String cardNounRecord) {
            this.cardNounRecord = cardNounRecord;
        }

        public int getSecondAssistTime() {
            return secondAssistTime;
        }

        public void setSecondAssistTime(int secondAssistTime) {
            this.secondAssistTime = secondAssistTime;
        }

        public String getGroupImage() {
            return groupImage;
        }

        public void setGroupImage(String groupImage) {
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

        public String getDisturbType() {
            return disturbType;
        }

        public void setDisturbType(String disturbType) {
            this.disturbType = disturbType;
        }

        public String getIdioType() {
            return idioType;
        }

        public void setIdioType(String idioType) {
            this.idioType = idioType;
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
             * cardAdjectiveImage : https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1277792631,1813565208&fm=27&gp=0.jpg
             * cardAdjectiveChar : 黑
             * cardAdjectiveRecord : www.baidu.com
             * assistTime : 5
             * isAdj : 1
             * isSuccess : 1
             */

            private String cardAdjectiveImage;
            private String cardAdjectiveChar;
            private String cardAdjectiveRecord;
            private int assistTime;
            private String isAdj;
            private String isSuccess;

            public String getCardAdjectiveImage() {
                return cardAdjectiveImage;
            }

            public void setCardAdjectiveImage(String cardAdjectiveImage) {
                this.cardAdjectiveImage = cardAdjectiveImage;
            }

            public String getCardAdjectiveChar() {
                return cardAdjectiveChar;
            }

            public void setCardAdjectiveChar(String cardAdjectiveChar) {
                this.cardAdjectiveChar = cardAdjectiveChar;
            }

            public String getCardAdjectiveRecord() {
                return cardAdjectiveRecord;
            }

            public void setCardAdjectiveRecord(String cardAdjectiveRecord) {
                this.cardAdjectiveRecord = cardAdjectiveRecord;
            }

            public int getAssistTime() {
                return assistTime;
            }

            public void setAssistTime(int assistTime) {
                this.assistTime = assistTime;
            }

            public String getIsAdj() {
                return isAdj;
            }

            public void setIsAdj(String isAdj) {
                this.isAdj = isAdj;
            }

            public String getIsSuccess() {
                return isSuccess;
            }

            public void setIsSuccess(String isSuccess) {
                this.isSuccess = isSuccess;
            }
        }
    }
}
