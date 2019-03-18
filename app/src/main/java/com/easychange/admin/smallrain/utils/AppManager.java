package com.easychange.admin.smallrain.utils;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

public class AppManager {
	private static Stack<Activity> activityStack;
	private static AppManager instance;

	private AppManager() {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
	}

	/**
	 * 单一实例
	 */
	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	public Stack<Activity> getActivityStack(){
		return activityStack;
	}

	/**
	 * 获取指定类名的Activity
	 * 
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Activity> T getActivity(Class<T> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				return (T) activity;
			}
		}

		return null;
	}

	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity() {
		return activityStack.lastElement();
	}

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishCurrentActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(String className){

		Class<?> cls = null;
		try {
			cls = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		for (Activity activity : activityStack) {

			if (cls!=null&&activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}

	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}
	
	/**
	 * 结束所有Activity，除了栈底的Activity之外
	 */
	public void finishAllActivityExceptRootActivity(){
		for (int i = 1, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
	}

	/**
	 * 退出应用程序
	 */
	@SuppressWarnings("deprecation")
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
		}
	}
}
