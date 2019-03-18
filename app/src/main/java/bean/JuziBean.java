package bean;

import java.util.List;

/**
 * Created by chenlipeng on 2018/11/2 0002.
 * describe :
 */

public class JuziBean {


    /**
     * msg : 查询成功！
     * sentenceGroupTraining : [{"id":1,"startSlideshow":"http://img0.imgtn.bdimg.com/it/u=379094836,1964782496&fm=26&gp=0.jpg","cardOneImage":"http://img0.imgtn.bdimg.com/it/u=379094836,1964782496&fm=26&gp=0.jpg","cardOneChar":"吃","cardOneRecord":null,"cardTwoImage":"http://img0.imgtn.bdimg.com/it/u=379094836,1964782496&fm=26&gp=0.jpg","cardTwoChar":"饭","cardTwoRecord":null,"groupChar":"吃饭","groupRecord":null,"createTime":1540886848000,"updateTime":null,"states":"1"},{"id":4,"startSlideshow":"http://img3.imgtn.bdimg.com/it/u=4051176407,53859730&fm=26&gp=0.jpg","cardOneImage":"http://img3.imgtn.bdimg.com/it/u=4051176407,53859730&fm=26&gp=0.jpg","cardOneChar":"乐","cardOneRecord":null,"cardTwoImage":"http://img3.imgtn.bdimg.com/it/u=4051176407,53859730&fm=26&gp=0.jpg","cardTwoChar":"大师级","cardTwoRecord":null,"groupChar":"乐乐高","groupRecord":null,"createTime":1540886859000,"updateTime":null,"states":"1"},{"id":3,"startSlideshow":"http://img2.imgtn.bdimg.com/it/u=2882058482,781150338&fm=26&gp=0.jpg","cardOneImage":"http://img2.imgtn.bdimg.com/it/u=2882058482,781150338&fm=26&gp=0.jpg","cardOneChar":"玩","cardOneRecord":null,"cardTwoImage":"http://img2.imgtn.bdimg.com/it/u=2882058482,781150338&fm=26&gp=0.jpg","cardTwoChar":"阿萨德","cardTwoRecord":null,"groupChar":"玩玩机","groupRecord":null,"createTime":1540886855000,"updateTime":null,"states":"1"},{"id":2,"startSlideshow":"http://img3.imgtn.bdimg.com/it/u=1574473547,1692853306&fm=26&gp=0.jpg","cardOneImage":"http://img3.imgtn.bdimg.com/it/u=1574473547,1692853306&fm=26&gp=0.jpg","cardOneChar":"喝","cardOneRecord":null,"cardTwoImage":"http://img3.imgtn.bdimg.com/it/u=1574473547,1692853306&fm=26&gp=0.jpg","cardTwoChar":"狗","cardTwoRecord":null,"groupChar":"喝饮料","groupRecord":null,"createTime":1540886852000,"updateTime":null,"states":"1"}]
     * code : 200
     * msg1 : 查询失败！没有设置句子成组训练时间
     * sentenceGroupTest : [{"id":3,"startSlideshow":"http://img5.imgtn.bdimg.com/it/u=452570785,185157202&fm=26&gp=0.jpg","cardOneImage":"http://img5.imgtn.bdimg.com/it/u=452570785,185157202&fm=26&gp=0.jpg","cardOneChar":"喝","cardOneRecord":null,"cardOneTime":10,"cardTwoImage":"http://img2.imgtn.bdimg.com/it/u=1815119,4188454581&fm=26&gp=0.jpg","cardTwoChar":"水","cardTwoRecord":null,"cardTwoTime":5,"groupChar":"喝水","groupRecord":null,"createTime":1540886501000,"updateTime":null,"states":"1"},{"id":2,"startSlideshow":"http://img2.imgtn.bdimg.com/it/u=404479930,851499040&fm=26&gp=0.jpg","cardOneImage":"http://img2.imgtn.bdimg.com/it/u=404479930,851499040&fm=26&gp=0.jpg","cardOneChar":"吃","cardOneRecord":null,"cardOneTime":10,"cardTwoImage":"http://img5.imgtn.bdimg.com/it/u=2480938611,3665774346&fm=26&gp=0.jpg","cardTwoChar":"猫","cardTwoRecord":null,"cardTwoTime":5,"groupChar":"吃馍啊","groupRecord":null,"createTime":1540886442000,"updateTime":null,"states":"1"},{"id":1,"startSlideshow":"http://img0.imgtn.bdimg.com/it/u=2952735897,351667143&fm=200&gp=0.jpg","cardOneImage":"http://img3.imgtn.bdimg.com/it/u=1885235352,2816259787&fm=26&gp=0.jpg","cardOneChar":"玩","cardOneRecord":null,"cardOneTime":10,"cardTwoImage":"http://img3.imgtn.bdimg.com/it/u=682805376,2494346241&fm=26&gp=0.jpg","cardTwoChar":"米拉撒","cardTwoRecord":null,"cardTwoTime":5,"groupChar":"玩去","groupRecord":null,"createTime":1540886306000,"updateTime":null,"states":"1"}]
     */

    private String msg;
    private int code;
    private String msg1;
    private List<SentenceGroupTrainingBean> sentenceGroupTraining;
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

    public String getMsg1() {
        return msg1;
    }

    public void setMsg1(String msg1) {
        this.msg1 = msg1;
    }

    public List<SentenceGroupTrainingBean> getSentenceGroupTraining() {
        return sentenceGroupTraining;
    }

    public void setSentenceGroupTraining(List<SentenceGroupTrainingBean> sentenceGroupTraining) {
        this.sentenceGroupTraining = sentenceGroupTraining;
    }

    public List<SentenceGroupTestBean> getSentenceGroupTest() {
        return sentenceGroupTest;
    }

    public void setSentenceGroupTest(List<SentenceGroupTestBean> sentenceGroupTest) {
        this.sentenceGroupTest = sentenceGroupTest;
    }

    public static class SentenceGroupTrainingBean {
        /**
         * id : 1
         * startSlideshow : http://img0.imgtn.bdimg.com/it/u=379094836,1964782496&fm=26&gp=0.jpg
         * cardOneImage : http://img0.imgtn.bdimg.com/it/u=379094836,1964782496&fm=26&gp=0.jpg
         * cardOneChar : 吃
         * cardOneRecord : null
         * cardTwoImage : http://img0.imgtn.bdimg.com/it/u=379094836,1964782496&fm=26&gp=0.jpg
         * cardTwoChar : 饭
         * cardTwoRecord : null
         * groupChar : 吃饭
         * groupRecord : null
         * createTime : 1540886848000
         * updateTime : null
         * states : 1
         */

        private int id;
        private String startSlideshow;
        private String cardOneImage;
        private String cardOneChar;
        private Object cardOneRecord;
        private String cardTwoImage;
        private String cardTwoChar;
        private Object cardTwoRecord;
        private String groupChar;
        private Object groupRecord;
        private long createTime;
        private Object updateTime;
        private String states;

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

        public Object getCardTwoRecord() {
            return cardTwoRecord;
        }

        public void setCardTwoRecord(Object cardTwoRecord) {
            this.cardTwoRecord = cardTwoRecord;
        }

        public String getGroupChar() {
            return groupChar;
        }

        public void setGroupChar(String groupChar) {
            this.groupChar = groupChar;
        }

        public Object getGroupRecord() {
            return groupRecord;
        }

        public void setGroupRecord(Object groupRecord) {
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
    }

    public static class SentenceGroupTestBean {
        /**
         * id : 3
         * startSlideshow : http://img5.imgtn.bdimg.com/it/u=452570785,185157202&fm=26&gp=0.jpg
         * cardOneImage : http://img5.imgtn.bdimg.com/it/u=452570785,185157202&fm=26&gp=0.jpg
         * cardOneChar : 喝
         * cardOneRecord : null
         * cardOneTime : 10
         * cardTwoImage : http://img2.imgtn.bdimg.com/it/u=1815119,4188454581&fm=26&gp=0.jpg
         * cardTwoChar : 水
         * cardTwoRecord : null
         * cardTwoTime : 5
         * groupChar : 喝水
         * groupRecord : null
         * createTime : 1540886501000
         * updateTime : null
         * states : 1
         */

        private int id;
        private String startSlideshow;
        private String cardOneImage;
        private String cardOneChar;
        private Object cardOneRecord;
        private int cardOneTime;
        private String cardTwoImage;
        private String cardTwoChar;
        private Object cardTwoRecord;
        private int cardTwoTime;
        private String groupChar;
        private Object groupRecord;
        private long createTime;
        private Object updateTime;
        private String states;

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

        public Object getCardTwoRecord() {
            return cardTwoRecord;
        }

        public void setCardTwoRecord(Object cardTwoRecord) {
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

        public Object getGroupRecord() {
            return groupRecord;
        }

        public void setGroupRecord(Object groupRecord) {
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
    }
}
