package com.wisdomregulation.allactivity.base;

import android.annotation.SuppressLint;
import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.single.Activity_Main;
import com.wisdomregulation.dialog.Dialog_Tool;
import com.wisdomregulation.helporg2017.Pdf_Shark2017;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;
import com.wisdomregulation.utils.Util_String;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.KJActivityStack;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.SystemTool;

import java.util.ArrayList;
import java.util.List;

public abstract class Base_AyActivity extends KJActivity implements
		OnGlobalLayoutListener {
	public static final int MFOCUS_AFTER_DESCENDANTS = 0x40000;
	private PopupWindow pop;
	public ProgressDialog waitdialog;
	public ProgressDialog waitdialog2;
	public TextView title;
	private int count;
	private SharedPreferences sp;
	private int oldscreenhigh = 0;
	private View decorView;
	private int isTabChild = 0;
	private boolean isdialog = false;
	public LocalActivityManager mLocalActivityManager;
	public int allpage;
	public int pageIndex = 1;
	public int split = 7;
	public String nowlmit="0,1";
	public TextView pageDown;
	public List<Toast> toastlist;
	public List org;
	public int scale=0;
	public Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}
		
	};
	private boolean cantoast=true;
	/**
	 * @return 是否已经在dialog载入
	 */
	public boolean isIsdialog() {
		return isdialog;
	}
	
	public int getScale() {
		return scale;
	}
	
	/**
	 * @return 可以继承重写此方法 使得base判断为需要横屏还是竖屏 返回true为竖直 返回false为横
	 */
	public boolean isportrait() {
		return true;
	}
	
	/**
	 * 是否需要对视图进行适配 可能会修改到全局view
	 * 
	 * @return
	 */
	public boolean needresize() {
		return true;
	}

	/**
	 * @return 获得之前的屏幕尺寸以此来知道是否变化 用来动态适配
	 */
	public int getOldscreenhigh() {
		return oldscreenhigh;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mLocalActivityManager = new LocalActivityManager(this, true);
		mLocalActivityManager.dispatchCreate(savedInstanceState);
		//	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// mLocalActivityManager为将activity抽取成view做准备
		super.onCreate(savedInstanceState);

		// 判断是否要横屏还是竖屏
		Pdf_Shark2017.create().setTTFpath(Static_InfoApp.create().getPath()+"/");
		pageDown = (TextView) this.findViewById(R.id.pageDown);
		if (isportrait()) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		toastlist=new ArrayList<Toast>();
		this.getWindow().getDecorView()
				.setOnTouchListener(new OnTouchListener() {

					public boolean onTouch(View arg0, MotionEvent arg1) {
						// 点击空白隐藏键盘
						InputMethodManager imm = (InputMethodManager) Base_AyActivity.this
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						try {
							return imm.hideSoftInputFromWindow(
									Base_AyActivity.this.getCurrentFocus()
											.getWindowToken(), 0);
						} catch (Exception e) {
							// TODO Auto-generated catch block

						}
						return true;
					}
				});

		isTabChild = this.getIntent().getIntExtra("isTabChild", 0);// 判断是否属于内部activity
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		title = (TextView) this.findViewById(R.id.newtitle);
		decorView = getWindow().getDecorView();
		 if(needresize()){
		 decorView.getViewTreeObserver().addOnGlobalLayoutListener(this);//对视图树进行监听
		 }
		// TextView more = (TextView) this.findViewById(R.id.more);
		// if (more != null) {
		// Util_MatchTip.initAllScreenText(more, 50);
		// }
		// Static_InfoApp.create().getAllhandler().postDelayed(new Runnable() {
		// public void run() {
		// initDataFromHandle();
		// }
		// }, 500);
		
	}

	/**
	 * 出现一个载入的dialog用来阻止用户乱按
	 */
	public void showWait() {



				if (waitdialog == null) {
					waitdialog = new ProgressDialog(Base_AyActivity.this);
					waitdialog.setMessage("正在加载...");
					waitdialog.setTitle(null);
					waitdialog.setCanceledOnTouchOutside(false);
					waitdialog.setOnKeyListener(new OnKeyListener() {

						@Override
						public boolean onKey(DialogInterface dialog,
								int keyCode, KeyEvent event) {
							// TODO Auto-generated method stub
							return true;
						}
					});
				}
				if ((waitdialog != null && waitdialog.isShowing())
						|| isFinishing()) {
					
				} else {
					
					isdialog = true;
					Base_AyActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
					waitdialog.show();
				}});
				}

			
		

	}

	/**
	 * 出现一个带字的dialog载入
	 * 
	 * @param string
	 */
	public void showWait(String string) {
		if (waitdialog == null) {
			waitdialog = new ProgressDialog(Base_AyActivity.this);
			waitdialog.setMessage(string);
			waitdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			if(0==0){
				waitdialog.isIndeterminate();
				waitdialog.setIndeterminate(true);
			}
			waitdialog.setMax(0);
			waitdialog.setTitle(null);
			waitdialog.setProgress(0);
			waitdialog.setCanceledOnTouchOutside(false);
			waitdialog.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
									 KeyEvent event) {
					// TODO Auto-generated method stub
					return true;
				}
			});
			waitdialog.setCancelable(false);

//			waitdialog = new ProgressDialog(Base_AyActivity.this);
//			waitdialog.setMessage(string);
//			waitdialog.setTitle(null);
//			waitdialog.setCanceledOnTouchOutside(false);
//			waitdialog.setOnKeyListener(new OnKeyListener() {
//
//				@Override
//				public boolean onKey(DialogInterface dialog, int keyCode,
//						KeyEvent event) {
//					// TODO Auto-generated method stub
//					return true;
//				}
//			});
//			waitdialog.isIndeterminate();
//			waitdialog.setIndeterminate(true);
//			waitdialog.setMax(0);
//			waitdialog.setProgress(0);
		}
		if (isFinishing()) {

		} else {
			isdialog = true;
			waitdialog.show();
		}
	}

	public void showWaitSecondNotAuto(final int max,final int second) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (waitdialog == null) {
					waitdialog = new ProgressDialog(Base_AyActivity.this);
					waitdialog.setMessage("载入中");
					waitdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					if(max==0){
						waitdialog.isIndeterminate();
						waitdialog.setIndeterminate(true);
					}
					waitdialog.setMax(max);
					waitdialog.setTitle(null);
					waitdialog.setProgress(second);
					waitdialog.setCanceledOnTouchOutside(false);
					waitdialog.setOnKeyListener(new OnKeyListener() {

						@Override
						public boolean onKey(DialogInterface dialog, int keyCode,
											 KeyEvent event) {
							// TODO Auto-generated method stub
							return true;
						}
					});
					waitdialog.setCancelable(false);

				}else{
					waitdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					if(max!=0){
						waitdialog.setIndeterminate(false);
					}
					waitdialog.setMax(max);
					waitdialog.setProgress(second);
				}
				if (isFinishing()) {

				} else {
					if(isdialog){

					}else{
						isdialog = true;
						waitdialog.show();
					}

				}
			}
		});

	}


	public void showWaitSecondNotAuto(final String title,final int max,final int second) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (waitdialog == null) {
					waitdialog = new ProgressDialog(Base_AyActivity.this);
					waitdialog.setMessage(title);
					waitdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					if(max==0){
						waitdialog.isIndeterminate();
						waitdialog.setIndeterminate(true);
					}
					waitdialog.setMax(max);
					waitdialog.setTitle(null);
					waitdialog.setProgress(second);
					waitdialog.setCanceledOnTouchOutside(false);
					waitdialog.setOnKeyListener(new OnKeyListener() {

						@Override
						public boolean onKey(DialogInterface dialog, int keyCode,
											 KeyEvent event) {
							// TODO Auto-generated method stub
							return true;
						}
					});
					waitdialog.setCancelable(false);

				}else{
					if(max!=0){
						waitdialog.setIndeterminate(false);
					}
					waitdialog.setMax(max);
					waitdialog.setProgress(second);
				}
				if (isFinishing()) {

				} else {
					if(isdialog){

					}else{
						isdialog = true;
						waitdialog.show();
					}

				}
			}
		});

	}
	/**
	 * 不会自动消失的一个载入dialog 依靠调用addcount方法增加进度
	 * 
	 * @param second
	 */
	public void showWaitSecondNotAuto(final int second) {
		sp = this.getSharedPreferences("releaseconfig", MODE_PRIVATE);
		if (waitdialog == null) {
			count = 0;
			waitdialog = new ProgressDialog(Base_AyActivity.this);
			waitdialog.setMessage("正在导出至sd卡ZhiExport目录...");
			waitdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			waitdialog.setMax(second + second);
			waitdialog.setTitle(null);
			waitdialog.setProgress(0);
			waitdialog.setCanceledOnTouchOutside(false);
			waitdialog.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					// TODO Auto-generated method stub
					return true;
				}
			});
			waitdialog.setCancelable(false);
		}
		if (isFinishing()) {

		} else {
			if(isdialog){
				
			}else{
				isdialog = true;
				waitdialog.show();
			}
		}
		new Thread() {
			public void run() {
				while (count < second + second) {
					// count=count+(int)(1000/second/10);
					// waitdialog.setProgress(count);
					try {
						Thread.sleep(100); // 暂停 0.1秒
					} catch (Exception e) {
						com.wisdomregulation.utils.Log.i("msg", "线程异常..");
					}
				}
				waitdialog.setProgress(second + second);
				sp.edit().putString("isfirst", "1").commit();
				dissmissWait();
			}
		}.start();
	}

	/**
	 * 辅助方法showWaitSecondNotAuto 的
	 */
	public void addCount() {
		count++;
		waitdialog.setProgress(count);
	}

//	/**
//	 * 带说明的 固定秒数的载入dialog
//	 *
//	 * @param string
//	 * @param second
//	 */
//	public void showWaitSecond(String string, final int second) {
//		sp = this.getSharedPreferences("releaseconfig", MODE_PRIVATE);
//		if (waitdialog2 == null) {
//			count = 0;
//			waitdialog2 = new ProgressDialog(Base_AyActivity.this);
//			waitdialog2.setMessage(string);
//			waitdialog2.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//			waitdialog2.setMax(second * 100);
//			waitdialog2.setTitle(null);
//			waitdialog2.setProgress(0);
//			waitdialog2.setCanceledOnTouchOutside(false);
//			waitdialog2.setOnKeyListener(new OnKeyListener() {
//
//				@Override
//				public boolean onKey(DialogInterface dialog, int keyCode,
//						KeyEvent event) {
//					// TODO Auto-generated method stub
//					return true;
//				}
//			});
//			waitdialog2.setCancelable(false);
//		}
//		if (isFinishing()) {
//
//		} else {
//			isdialog = true;
//			waitdialog2.show();
//		}
//		new Thread() {
//			public void run() {
//				while (count < second * 100) {
//					count = count + (int) (second * 100 / second / 10);
//					waitdialog2.setProgress(count);
//					try {
//						Thread.sleep(100); // 暂停 0.1秒
//					} catch (Exception e) {
//						com.wisdomregulation.utils.Log.i("msg", "线程异常..");
//					}
//				}
//				waitdialog2.setProgress(second * 100);
//				sp.edit().putString("isfirst", "1").commit();
//				waitdialog2.dismiss();
//			}
//		}.start();
//	}

	/**
	 * 将dialog消失
	 */
	public void dissmissWait() {
		try {
			if (waitdialog != null) {
				
				waitdialog.dismiss();
				waitdialog = null;
				isdialog = false;
//				new Handler().postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						if(waitdialog!=null){
//
//						}
//
//					}
//				}, 50);


				
			}
			
		} catch (Exception e) {

		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		mLocalActivityManager.dispatchResume();// mLocalActivityManager为将activity抽取成view做准备
	}
	/**
	 * toast方法用来管理退出activity时能取消toast 
	 * @param contecnt
	 */
	public void toastshow(String contecnt){
		Toast roast=Toast.makeText(Base_AyActivity.this, contecnt, Toast.LENGTH_SHORT);
		toastlist.add(roast);
		
		if(cantoast){
			cantoast=false;
			//System.out.println("不可toast");
			roast.show();
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					//System.out.println("恢复toast");
					cantoast=true;
					
				}
			}, 1000);
		}
		

	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mLocalActivityManager.dispatchPause(isFinishing());// mLocalActivityManager为将activity抽取成view做准备
		if (pop != null) {
			pop.dismiss();
		}
		System.gc();// gc
		System.runFinalization();// gc
	}

	/**
	 * 进行视图适配---以视图的尺寸为主来适配
	 */
	public void ViewMatch() {
		if(getScale()!=0){
			Util_MatchTip.initAllViewText(Base_AyActivity.this.getWindow()
					.getDecorView(),getScale());
		}else{
			Util_MatchTip.initAllViewText(Base_AyActivity.this.getWindow()
					.getDecorView());
		}

	}

	/**
	 * 进行屏幕适配---以屏幕的尺寸为主来适配
	 */
	public void ScreenMatch() {
		Util_MatchTip.initAllScreenText(Base_AyActivity.this.getWindow()
				.getDecorView());
	}

	@Override
	public void onGlobalLayout() {
		int nowscreenhigh = 0;
		int nowscreenwidth = 0;
		if (isportrait()) {
			nowscreenhigh = decorView.getHeight();
			nowscreenwidth = decorView.getWidth();
		} else {
			nowscreenhigh = decorView.getWidth();
			nowscreenwidth = decorView.getHeight();
		}

		if (nowscreenhigh != oldscreenhigh) {
			if (isTabChild != 1) {


				Static_InfoApp.create().setViewScreenHigh(nowscreenhigh);
				Static_InfoApp.create().setViewScreenWidth(nowscreenwidth);
				Base_AyActivity.this.ViewMatch();
				oldscreenhigh = nowscreenhigh;
			} else {
				Base_AyActivity.this.ViewMatch();

			}

		}

	}

	/**
	 * 进入后台
	 */
	public void tobackup() {
		SystemTool.goHome(this);
	}

	/**
	 * 回退
	 * 
	 * @param view
	 */
	public void back(View view) {
		this.finish();
	}

	/**
	 * 直接进入主页
	 */
	public void toMainPage() {
		KJActivityStack.create().finishOthersActivity(Activity_Main.class);
	}

	/**
	 * 一键进主页
	 */
	public void toMainPage(View view) {
		KJActivityStack.create().finishOthersActivity(Activity_Main.class);

	}

	/**
	 * 预先实现的more方法 防止视图按钮出错
	 * 
	 * @param view
	 */
	public void toMore(View view) {

	}
	@Override
	protected void onSaveInstanceState(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(arg0);
		mLocalActivityManager.saveInstanceState();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mLocalActivityManager.dispatchStop();
	}

	@SuppressLint("NewApi")
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(waitdialog!=null){
			waitdialog.dismiss();
			waitdialog=null;
		}
		if(waitdialog2!=null){
			waitdialog2.dismiss();
			waitdialog2=null;
		}
		super.onDestroy();
		if(toastlist!=null&&toastlist.size()>0){
			for (int i = 0; i < toastlist.size(); i++) {
				toastlist.get(i).cancel();
			}
		}
		decorView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
		try {
			mLocalActivityManager.dispatchDestroy(isFinishing());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	



	@Override
	public void finish() {

			super.finish();

		

	}
	/**
	 * 关闭自己
	 */
	public void finishThis() {


			super.finish();

	}
	/**
	 * 关闭自己
	 */
	public void finishSuper() {
			super.finish();
	}
	/**
	 * 关闭所有的activity
	 */
	public void finishAll() {

		KJActivityStack.create().finishAllActivity();
	}



	/**
	 * 土司
	 * 
	 * @param body
	 */
	public void showToast(String body) {
		ViewInject.toast(body);
	}

	/**
	 * 检查网络
	 * 
	 * @return
	 */
	public String checkNet() {
		String isNet = "";
		boolean NetState = SystemTool.checkNet(this);
		if (NetState) {
			isNet = "可用";
		} else {
			isNet = "不可用";
		}
		return isNet;
	}

	/**
	 * 重写初始化方法
	 */
	public abstract  void initView();
	

	/**
	 * 抽取activity为view的方法
	 *
	 * @param tabMianContent
	 * @param mTag
	 * @param mIntent
	 * @return
	 */
	public View getIntentContentView(
			ViewGroup tabMianContent, String mTag, Intent mIntent) {
		if (mLocalActivityManager == null) {
			throw new IllegalStateException(
					"Did you forget to call 'public void setup(LocalActivityManager activityGroup)'?");
		}
		mIntent.putExtra("isTabChild", 1);
		mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		String tags=Util_String.get16Uuid();
		final Window w = mLocalActivityManager.startActivity(tags, mIntent);
		final View wd = w != null ? w.getDecorView() : null;
		if (wd.getParent() != null) {
			((ViewGroup) wd.getParent()).removeView(wd);
		}
		return wd;
	}

	public final void previousPage(View view) {
		if (pageIndex != 1) {

			pageIndex--;
			initView();
		} else {

		}

	}

	public final void nextPage(View view) {
		if (pageIndex != allpage && allpage != 0) {

			pageIndex++;
			initView();
		} else {

		}

	}

	public final void chosePage(View view) {

		Dialog_Tool.showDialog_ChosePage(this, allpage);
	}

	public final void refreshPage(int page) {
		pageIndex = page;
		initView();
	}

	public final int getAllPage(int size) {
		int tmp = size % split;
		if (tmp == 0) {
			return size / split;
		} else {
			return (int) (size / split) + 1;
		}

	}

	public final void setLimt() {
		int limit1 = (pageIndex - 1) * split;
		int limit2 = split;
		nowlmit = limit1 + "," + limit2;
	}

	/**
	 * 重写必须调用super
	 * 
	 * @param view
	 */
	public void simpleSearch(View view) {
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 重写必须调用super
	 * 
	 * @param view
	 */
	public void detailSearch(View view) {
		try {
			((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
			.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void hideInputSoft(){
		if(isFinishing()){
			
		}else{
			try {
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}


	}
}
