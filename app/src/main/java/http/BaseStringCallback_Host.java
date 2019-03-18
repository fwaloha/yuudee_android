package http;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.easychange.admin.smallrain.utils.GoToLoginActivityUtils;
import com.qlzx.mylibrary.util.ToastUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by wondercupid on 2018/1/5.
 */

public class BaseStringCallback_Host extends StringCallback {

    Activity mContext;

    AsyncRequest mAsyncRequest;


    public BaseStringCallback_Host(Activity context, final AsyncRequest callBackRequest) {

        mContext = context;
        mAsyncRequest = callBackRequest;
    }


    @Override
    public void onBefore(Request request, int id) {

//        if (null != mContext)
//            mContext.showProgressDialog();

//        MainTabActivity.getInstance().mHandler.sendEmptyMessage(MSG_SHOW_HTTP_BEFORE);

    }

    @Override
    public void onAfter(int id) {

//        if (null != mContext)
//            mContext.hideProgressDialog();

//        MainTabActivity.getInstance().mHandler.sendEmptyMessage(MSG_SHOW_HTTP_AFTER);
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        e.printStackTrace();

        Log.e("TAG", id + "onError" + e.getMessage());

//        if (null != mContext) {
//            mContext.hideProgressDialog();
//        }

        if (!TextUtils.isEmpty(e.getMessage()) && e.getMessage().contains("Failed to connect")) {
            if (null != mContext) {
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //此时已在主线程中，可以更新UI了
                        ToastUtil.showToast(mContext, "当前网络已断开");
//                        mContext.hideProgressDialog();
                    }
                });
            }
        } else if (!TextUtils.isEmpty(e.getMessage()) && e.getMessage().contains("failed to connect to") && e.getMessage().contains("after")) {
            if (null != mContext) {
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //此时已在主线程中，可以更新UI了
                        ToastUtil.showToast(mContext, "连接超时！");
//                        mContext.hideProgressDialog();
                    }
                });
            }
        } else {
            mAsyncRequest.RequestError(id, id, e.getMessage());
        }
    }

    @Override
    public void onResponse(String response, int id) {

        Log.e("TAG", "onResponse：complete" + response);
        try {

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                String code = jsonObject.getString("code");

                if (code.equals("205")) {
                    GoToLoginActivityUtils.tokenFailureLoginOut(mContext);
                } else if (code.equals("209")) {
                    GoToLoginActivityUtils.tokenLose(mContext);
                } else {
                    mAsyncRequest.RequestComplete(id, response);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
            mAsyncRequest.RequestError(id, id, response);
        }


        switch (id) {
            case 100:
                break;
            case 101:
                break;
        }
    }

    @Override
    public void inProgress(float progress, long total, int id) {

        Log.e("TAG", id + " ---inProgress:" + progress);

//            mProgressBar.setProgress((int) (100 * progress));
//            @SuppressLint("InflateParams") View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_pager_entrance, null);


    }
}
//onResponse：complete{"msg":"用户信息获取失败！","code":205,"nounTraining":[{"id"   token