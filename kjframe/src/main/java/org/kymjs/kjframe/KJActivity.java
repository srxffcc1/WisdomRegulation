/*
 * Copyright (c) 2014, 张涛.
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
package org.kymjs.kjframe;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import org.kymjs.kjframe.ui.I_BroadcastReg;
import org.kymjs.kjframe.ui.I_KJActivity;
import org.kymjs.kjframe.ui.I_SkipActivity;
import org.kymjs.kjframe.ui.KJActivityStack;
import org.kymjs.kjframe.ui.KJFragment;
import org.kymjs.kjframe.ui.SupportFragment;
import org.kymjs.kjframe.utils.KJHttpLoger;

import java.lang.ref.SoftReference;

/**
 * Activity's framework,the developer shouldn't extends it <br>
 * <b>创建时间</b> 2014-3-1 <br>
 * <b>最后修改时间</b> 2014-10-17<br>
 *
 * @author kymjs (http://www.kymjs.com)
 */
public abstract class KJActivity extends AppCompatActivity implements
		View.OnClickListener, I_BroadcastReg, I_KJActivity, I_SkipActivity {

	public static final int WHICH_MSG = 0X37210;

	public Activity aty;

	protected KJFragment currentKJFragment;
	protected SupportFragment currentSupportFragment;
	private ThreadDataCallBack callback;
	private boolean isfirst=true;
	private KJActivityHandle threadHandle = new KJActivityHandle(this);

	/**
	 * Activity状态
	 */
	public int activityState = DESTROY;

	/**
	 * 一个私有回调类，线程中初始化数据完成后的回调
	 */
	private interface ThreadDataCallBack {
		void onSuccess();
	}

	private static class KJActivityHandle extends Handler {
		private final SoftReference<KJActivity> mOuterInstance;

		KJActivityHandle(KJActivity outer) {
			mOuterInstance = new SoftReference<KJActivity>(outer);
		}

		// 当线程中初始化的数据初始化完成后，调用回调方法
		@Override
		public void handleMessage(android.os.Message msg) {
			KJActivity aty = mOuterInstance.get();
			if (msg.what == WHICH_MSG && aty != null) {
				if (aty.callback != null) {
					aty.callback.onSuccess();
				}

			}
		}
	}

	/**
	 * 如果调用了initDataFromThread()，则当数据初始化完成后将回调该方法。
	 */
	protected void threadDataInited() {
	}

	/**
	 * 在线程中初始化数据，注意不能在这里执行UI操作
	 */
	@Override
	public void initDataFromThread() {
		callback = new ThreadDataCallBack() {
			@Override
			public void onSuccess() {
				threadDataInited();
			}
		};
	}




	// 仅仅是为了代码整洁点
	private void initializer() {
		initData();
		new Thread(new Runnable() {
			@Override
			public void run() {
				initDataFromThread();
				if(threadHandle!=null){

				threadHandle.sendEmptyMessage(WHICH_MSG);
				}
			}
		}).start();
		initWidget();

		
	}

	/**
	 * listened widget's click method
	 */
	@Override
	public void widgetClick(View v) {
	}

	@Override
	public void onClick(View v) {
		widgetClick(v);
	}

	@SuppressWarnings("unchecked")
	protected <T extends View> T bindView(int id) {
		return (T) findViewById(id);
	}

	@SuppressWarnings("unchecked")
	protected <T extends View> T bindView(int id, boolean click) {
		T view = (T) findViewById(id);
		if (click) {
			view.setOnClickListener(this);
		}
		return view;
	}

	@Override
	public void registerBroadcast() {
	}

	@Override
	public void unRegisterBroadcast() {
	}

	/***************************************************************************
	 * print Activity callback methods
	 ***************************************************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		aty = this;
		KJActivityStack.create().addActivity(this);
		KJHttpLoger.state(this.getClass().getName(), "---------onCreat ");
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getSupportActionBar().hide();
		setRootView(); // 必须放在annotate之前调用


		registerBroadcast();
			initializer();

	}

	@Override
	protected void onStart() {
		super.onStart();

		
		KJHttpLoger.state(this.getClass().getName(), "---------onStart ");
	}

	@Override
	protected void onResume() {
		super.onResume();
		activityState = RESUME;
//		if(isfirst){
//			isfirst=false;
//			new Handler().postDelayed(new Runnable() {
//				
//				@Override
//				public void run() {
//					
//					
//				}
//			}, 10);
			
//		}
		
		KJHttpLoger.state(this.getClass().getName(), "---------onResume ");
	}

	@Override
	protected void onPause() {
		super.onPause();
		activityState = PAUSE;
		KJHttpLoger.state(this.getClass().getName(), "---------onPause ");
	}

	@Override
	protected void onStop() {
		super.onStop();
		activityState = STOP;
		KJHttpLoger.state(this.getClass().getName(), "---------onStop ");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		KJHttpLoger.state(this.getClass().getName(), "---------onRestart ");
	}

	@Override
	protected void onDestroy() {
		unRegisterBroadcast();
		activityState = DESTROY;
		KJHttpLoger.state(this.getClass().getName(), "---------onDestroy ");
		super.onDestroy();
		KJActivityStack.create().finishActivity(this);
		currentKJFragment = null;
		currentSupportFragment = null;
		callback = null;
		threadHandle = null;
		aty = null;
	}

	/**
	 * skip to @param(cls)，and call @param(aty's) finish() method
	 */
	@Override
	public void skipActivity(Activity aty, Class<?> cls) {
		showActivity(aty, cls);
		aty.finish();
	}

	/**
	 * skip to @param(cls)，and call @param(aty's) finish() method
	 */
	@Override
	public void skipActivity(Activity aty, Intent it) {
		showActivity(aty, it);
		aty.finish();
	}

	/**
	 * skip to @param(cls)，and call @param(aty's) finish() method
	 */
	@Override
	public void skipActivity(Activity aty, Class<?> cls, Bundle extras) {
		showActivity(aty, cls, extras);
		aty.finish();
	}

	/**
	 * show to @param(cls)，but can't finish activity
	 */
	@Override
	public void showActivity(Activity aty, Class<?> cls) {
		Intent intent = new Intent();
		intent.setClass(aty, cls);
		aty.startActivity(intent);
	}

	/**
	 * show to @param(cls)，but can't finish activity
	 */
	@Override
	public void showActivity(Activity aty, Intent it) {
		aty.startActivity(it);
	}

	/**
	 * show to @param(cls)，but can't finish activity
	 */
	@Override
	public void showActivity(Activity aty, Class<?> cls, Bundle extras) {
		Intent intent = new Intent();
		intent.putExtras(extras);
		intent.setClass(aty, cls);
		aty.startActivity(intent);
	}

	/**
	 * 用Fragment替换视图
	 *
	 * @param resView
	 *            将要被替换掉的视图
	 * @param targetFragment
	 *            用来替换的Fragment
	 */
	public void changeFragment(int resView, KJFragment targetFragment) {
		if (targetFragment.equals(currentKJFragment)) {
			return;
		}
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		if (!targetFragment.isAdded()) {
			transaction.add(resView, targetFragment, targetFragment.getClass()
					.getName());
		}
		if (targetFragment.isHidden()) {
			transaction.show(targetFragment);
			targetFragment.onChange();
		}
		if (currentKJFragment != null && currentKJFragment.isVisible()) {
			transaction.hide(currentKJFragment);
		}
		currentKJFragment = targetFragment;
		transaction.commit();
	}

	/**
	 * 用Fragment替换视图
	 *
	 * @param resView
	 *            将要被替换掉的视图
	 * @param targetFragment
	 *            用来替换的Fragment
	 */
	public void changeFragment(int resView, SupportFragment targetFragment) {
		if (targetFragment.equals(currentSupportFragment)) {
			return;
		}
		android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		if (!targetFragment.isAdded()) {
			transaction.add(resView, targetFragment, targetFragment.getClass()
					.getName());
		}
		if (targetFragment.isHidden()) {
			transaction.show(targetFragment);
			targetFragment.onChange();
		}
		if (currentSupportFragment != null
				&& currentSupportFragment.isVisible()) {
			transaction.hide(currentSupportFragment);
		}
		currentSupportFragment = targetFragment;
		transaction.commit();
	}
}
