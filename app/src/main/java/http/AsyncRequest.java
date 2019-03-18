package http;

/**
 * Created by chenlipeng on 2018/11/1 0001.
 * describe :
 */

public interface AsyncRequest {
    void RequestComplete(Object var1, Object var2);

    void RequestError(Object var1, int var2, String var3);
}
