package com.wisdomregulation.allactivity.single;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.staticlib.Static_InfoApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Activity_DownLoad extends Base_AyActivity {

ProgressBar downprogress;
private String path;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_download);
		
	}

	@Override
	public void initData() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		path = this.getIntent().getStringExtra("path");
		new Handler().postDelayed(new Runnable(){
			public void run(){
				new AsyncDownLoad().execute(path);
			}
		}, 500);
	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		downprogress=(ProgressBar)findViewById(R.id.downprogress);
		downprogress.setLayoutParams(new LayoutParams((int)(Static_InfoApp.create().getAppScreenWidth()*0.8), LayoutParams.MATCH_PARENT));

		
	}
	@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();

		}
	@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			return true;
		}
	public void finishDownLoad(){
		new Handler().postDelayed(new Runnable(){
			public void run(){
				Intent intent = new Intent();
		        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);    
		        intent.setAction(android.content.Intent.ACTION_VIEW);    
		        Uri uri = Uri.fromFile(new File(Static_InfoApp.create().getPath()+"/tmp.apk"));  
		        intent.setDataAndType(uri,"application/vnd.android.package-archive");   
		        Activity_DownLoad.this.startActivityForResult(intent, 27);
			}
		}, 500);

	}
	@Override
		protected void onActivityResult(int arg0, int arg1, Intent arg2) {
			// TODO Auto-generated method stub
			super.onActivityResult(arg0, arg1, arg2);
			if(arg0==27){
				Activity_DownLoad.this.finish();
			}
		}
	class AsyncDownLoad extends AsyncTask<String, Integer, Integer>{

		@Override
		protected Integer doInBackground(String... params) {
			
			try {
				URL url=new URL(params[0]);
				
				HttpURLConnection hc=(HttpURLConnection) url.openConnection();
				hc.setConnectTimeout(5000);
				hc.connect();
				int code=hc.getResponseCode();
				if(code==200){
					InputStream is = hc.getInputStream();
					int totalsize=hc.getContentLength();
					FileOutputStream fos = new FileOutputStream(Static_InfoApp.create().getPath()+"/tmp.apk");
					byte buf[] = new byte[1024];
					int len1 = 0;
					int value=0;
	                while ((len1 = is.read(buf)) > 0) {  
	                	value += len1; //total = total + len1  
	                    publishProgress((int)((value*100)/totalsize)); 
	                    fos.write(buf, 0, len1);  
	                }  
	                fos.flush();
	                fos.close();
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			return 100;
		}

		@Override
		protected void onPostExecute(Integer result) {
			
			downprogress.setProgress(result);
			finishDownLoad();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			downprogress.setProgress(values[0]);  
		}
		
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

}
