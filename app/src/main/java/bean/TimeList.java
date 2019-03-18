package bean;

/**
 * Created by chenlipeng on 2018/11/19 0019.
 * describe :
 */

public class TimeList extends Object{

//    {
//        "accuracy": 60.333333,
//            "time": "2018-11-16",
//            "countTime": 268
//    }
    /**
     * time : 2018-10-30
     * countTime : 11
     */
    private double accuracy;

    private String time;
    private int countTime;

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCountTime() {
        return countTime;
    }

    public void setCountTime(int countTime) {
        this.countTime = countTime;
    }
}
