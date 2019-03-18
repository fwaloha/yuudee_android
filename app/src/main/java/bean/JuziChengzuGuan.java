package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JuziChengzuGuan implements Serializable {

//    public String[] one = new String[15];
//    public String[] two = new String[15];
//    public String[] three = new String[15];

    public List<String> one = new ArrayList<>();
    public List<String> two = new ArrayList<>();
    public List<String> three = new ArrayList<>();
    public int currentPosition = 0;
    public int dangqian_pintu_xiabiao = 1;

    public JuziChengzuGuan() {
//        dangqian_pintu_xiabiao = 5;
//        for (int i = 0; i < 9; i++) {
//            one.add("1");
//        }
//        for (int i = 0; i < 9; i++) {
//            two.add("1");
//        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
