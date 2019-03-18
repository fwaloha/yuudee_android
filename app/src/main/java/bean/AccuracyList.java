package bean;

/**
 * Created by chenlipeng on 2018/11/19 0019.
 * describe :
 */

public class AccuracyList {
    /**
     * accuracy : 1
     * time : 2018-10-30
     */
//    {
//        "stayTime": 268,
//            "accuracy": 60.333333,
//            "time": "2018-11-16"
//    }
    private int stayTime;
    private double accuracy;
    private String time;

    public int getStayTime() {
        return stayTime;
    }

    public void setStayTime(int stayTime) {
        this.stayTime = stayTime;
    }

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
}
