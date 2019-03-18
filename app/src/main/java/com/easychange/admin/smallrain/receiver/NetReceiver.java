package com.easychange.admin.smallrain.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.easychange.admin.smallrain.entity.BreakNetBean;
import com.easychange.admin.smallrain.entity.NetChangeBean;
import com.qlzx.mylibrary.util.EventBusUtil;

public class NetReceiver extends BroadcastReceiver {
    private boolean isConnected = true;

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean connected) {
		isConnected = connected;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
			isConnected = NetUtils.isNetworkConnected(context);
	        System.out.println("网络状态：" + isConnected);
	        System.out.println("wifi状态：" + NetUtils.isWifiConnected(context));
	        System.out.println("移动网络状态：" + NetUtils.isMobileConnected(context));
	        System.out.println("网络连接类型：" + NetUtils.getConnectedType(context));
	        if (isConnected) {
//	        	Toast.makeText(context, "已经连接网络", Toast.LENGTH_LONG).show();
	        } else {
	        //	Toast.makeText(context, "当前网络已断开", Toast.LENGTH_LONG).show();
				EventBusUtil.post(new BreakNetBean());
	        }
			EventBusUtil.post(new NetChangeBean(isConnected));
		}
	}
	
}
