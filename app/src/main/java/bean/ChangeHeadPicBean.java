package bean;

import java.io.Serializable;

/**
 * Created by chenlipeng on 2018/11/5 0005.
 * describe :
 */

public class ChangeHeadPicBean implements Serializable {

    public String nickName;
    public String sex;

    public ChangeHeadPicBean(String nickName, String sex) {
        this.nickName = nickName;
        this.sex = sex;
    }
}
