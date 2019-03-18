package bean;

import java.util.List;

public class FileUploadBean {
    /**
     * msg : 上传成功！
     * code : 200
     * data : {"images":["http://yuudee.oss-cn-beijing.aliyuncs.com/7b66636113f64796bb6e666c3e0809bd.temp"]}
     */

    private String msg;
    private int code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<String> images;

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
