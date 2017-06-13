/*
 * Copyright (c) 2014,KJFrameForAndroid Open Source Project,张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kymjs.kjframe.ui;

import java.util.Stack;

import org.kymjs.kjframe.KJActivity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出<br>
 * <b>创建时间</b> 2014-2-28
 *
 * @author kymjs (https://github.com/kymjs)
 * @version 1.1
 */
final public class KJActivityStack {
	private static Stack<I_KJActivity> activityStack;
	private static final KJActivityStack instance = new KJActivityStack();

	private KJActivityStack() {
	}

	public static KJActivityStack create() {
		return instance;
	}

	public static Stack<I_KJActivity> getActivityStack() {
		return activityStack;
	}

	/**
	 * 获取当前Activity栈中元素个数
	 */
	public int getCount() {
		return activityStack.size();
	}

	public void showAllStack() {
		for (I_KJActivity activity : activityStack) {
			Log.v("KJActivityStackShow", activity.getClass().getName());
		}
	}

	/**
	 * 添加Activity到栈
	 */
	public void addActivity(I_KJActivity activity) {
		if (activityStack == null) {
			activityStack = new Stack<I_KJActivity>();
		}
		Log.v("KJActivityStack", "add");
		activityStack.add(activity);
	}

	/**
	 * 获取当前Activity（栈顶Activity）
	 */
	public Activity topActivity() {
		if (activityStack == null) {
			throw new NullPointerException(
					"Activity stack is Null,your Activity must extend KJActivity");
		}
		if (activityStack.isEmpty()) {
			return null;
		}
		I_KJActivity activity = activityStack.lastElement();
		return (Activity) activity;
	}

	/**
	 * 获取当前Activity（栈顶Activity） 没有找到则返回null
	 */
	public Activity findActivity(Class<?> cls) {
		I_KJActivity activity = null;
		for (I_KJActivity aty : activityStack) {
			if (aty.getClass().equals(cls)) {
				activity = aty;
				break;
			}
		}
		return (Activity) activity;
	}

	/**
	 * 结束当前Activity（栈顶Activity）
	 */
	public void finishActivity() {
		I_KJActivity activity = activityStack.lastElement();
		finishActivity((Activity) activity);
	}

	/**
	 * 结束指定的Activity(重载)
	 */
	public void finishActivity(Activity activity) {
		Log.v("KJActivityStackNull", activity.getClass().getName());
		if (activity != null) {

			try {
				Log.v("KJActivityStackRemove", activity.getClass().getName());
				boolean flag = activityStack.remove((I_KJActivity) activity);
				// activity.finish();//此处不用finish
				activity = null;

			} catch (Exception e) {
				// TODO Auto-generated catch block

			}
		}
	}

	/**
	 * 结束指定的Activity(重载)
	 */
	public void finishActivity(Class<?> cls) {
		for (I_KJActivity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				((Activity) activity).finish();
			}
		}
	}

	/**
	 * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
	 *
	 * @param cls
	 */
	public void finishOthersActivity(Class<?> cls) {
		for (I_KJActivity activity : activityStack) {
			if (!(activity.getClass().equals(cls))) {
				Log.v("KJActivityStackFinish", "finish1");
				try {
					((Activity) activity).finish();
				} catch (Exception e) {

				}
			}
		}
		Log.v("KJActivityStackFinishCount", activityStack.size() + "");
		for (int i = 0; i < activityStack.size(); i++) {
			if (!activityStack.get(i).getClass().getName()
					.equals(cls.getName())) {
				Log.v("KJActivityStackFinish", "finish3");
				activityStack.remove(i);
				Log.v("KJActivityStackCountS", "OK");
				i--;
			}
		}
		Log.v("KJActivityStackCount", KJActivityStack.create().getCount() + "");

	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				((Activity) activityStack.get(i)).finish();
			}
		}
		activityStack.clear();
	}

	@Deprecated
	public void AppExit(Context cxt) {
		appExit(cxt);
	}

	/**
	 * 应用程序退出
	 */
	public void appExit(Context context) {
		try {
			finishAllActivity();
			Runtime.getRuntime().exit(0);
		} catch (Exception e) {
			Runtime.getRuntime().exit(-1);
		}
	}
}