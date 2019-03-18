package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DongciGuoGuan implements Serializable {

//    public String[] one = new String[15];
//    public String[] two = new String[15];
//    public String[] three = new String[15];

    public List<String> one = new ArrayList<>();
    public List<String> two = new ArrayList<>();
    public List<String> three = new ArrayList<>();
    public int currentPosition = 0;//当前是第几个80%了

    public int dangqian_pintu_xiabiao = 1;//没有用到了

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
