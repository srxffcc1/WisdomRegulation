package com.wisdomregulation.allactivity.single;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.igexin.sdk.PushManager;
import com.wisdomregulation.R;
import com.wisdomregulation.frame.CallBack;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.helporg2017.Pdf_Shark2017;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_Sdk;

public class Activity_Welcome extends AppCompatActivity {
	public ProgressDialog waitdialog;
	private boolean isdialog = false;
	public Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case R.string.finishWelecome:
				//完成准备工作开始跳转
				Activity_Welcome.this.startActivity(new Intent(Activity_Welcome.this, Activity_Login.class));
				Activity_Welcome.this.dissmissWait();
				Activity_Welcome.this.finish();
				break;
			
			default:
				break;
			}
		}

	};
	/**
	 * 防止程序因为特殊原因丢失设备参数 所以再次获得一次设备参数
	 */
	@SuppressLint("NewApi")
	public void initetc(){
		Point point = new Point();
        WindowManager manager = (WindowManager) Activity_Welcome.this
				.getSystemService(Context.WINDOW_SERVICE);
		manager.getDefaultDisplay().getRealSize(point);
	    int px=point.x;
	    int py=point.y;
	    Static_InfoApp.create().setAppScreenHigh(py).setAppScreenWidth(px);
	}
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_welcome);
		initetc();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		PushManager.getInstance().initialize(this.getApplicationContext(),null);
		checkinit();

	}


	public void initView() {

	}

	/**
	 * 检查初始化 并检查是否需要数据库升级 
	 */
	public void checkinit(){
		final SharedPreferences sp = Activity_Welcome.this
				.getSharedPreferences("releaseconfig", MODE_PRIVATE);
		new Thread(new Runnable() {

			@Override
			public void run() {
				Util_Sdk.initializeData(Activity_Welcome.this
						.getApplicationContext(),new CallBack() {
							
							@Override
							public void back(Object resultlist) {
								final String call=(String) resultlist;
								Activity_Welcome.this.runOnUiThread(new Runnable() {
									
									@Override
									public void run() {
										Activity_Welcome.this.showWait(call);
										
									}
								});
								
								
							}
						});
				Help_DB.create(
						Activity_Welcome.this.getApplicationContext(),
						""+Static_InfoApp.create().getdbname()+"");
						needpdatebase();//判断数据库升级 
				Pdf_Shark2017.create().setTTFpath(Static_InfoApp.create().getPath()+"/");
	
				
				
			}
		}).start();
	}
	/**
	 * 判断数据库升级操作主方法
	 * 
	 */
	public void needpdatebase(){
		final SharedPreferences sp = Activity_Welcome.this.getSharedPreferences("releaseconfig",
				MODE_PRIVATE);
		boolean isneedupdatebase=sp.getBoolean("isneedupdatebase", false);//判断数据库升级key
		if(isneedupdatebase){
			Activity_Welcome.this.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					showWait("升级数据库中...");
					//System.out.println("升级数据库dialog");
					
				}
			});
			
		new Thread(new Runnable() {
			
			@Override
			public void run() {
			
			Help_DB.create().updataDatabase();
			Activity_Welcome.this.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					Help_DB.create(Activity_Welcome.this, ""+Static_InfoApp.create().getdbname()+"");
					sp.edit().putBoolean("isneedupdatebase", false).commit();
					handler.sendEmptyMessage(R.string.finishWelecome);//发送完成welcome指令进行跳转到主页
					
				}
			});
			
			}
		}).start();
	}else{
		handler.sendEmptyMessageDelayed(R.string.finishWelecome, 555);//延时发送完成welcome指令
	}
	}
	/**
	 * 打开载入等待
	 * @param string
	 */
	public void showWait(String string) {
		if (waitdialog == null) {
			waitdialog = new ProgressDialog(Activity_Welcome.this);
			waitdialog.setMessage(string);
			waitdialog.setTitle(null);
			waitdialog.setCanceledOnTouchOutside(false);
			waitdialog.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					// TODO Auto-generated method stub
					return true;
				}
			});
		}
		if (isFinishing()) {

		} else {
			isdialog = true;
			waitdialog.show();
		}
	}
	/**
	 * 消失载入等待
	 */
	public void dissmissWait() {
		try {
			if (waitdialog != null) {
				
				waitdialog.dismiss();
				waitdialog = null;
				isdialog = false;				
			}
			
		} catch (Exception e) {

		}
	}


	public void setRootView() {

	}
}
