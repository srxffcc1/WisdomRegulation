package com.wisdomregulation.allactivity.single;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.staticlib.Static_InfoApp;

/**
 * 第二种下载app的方式 利用downloadermanager
 * @author King2016s
 *
 */
public class Activity_DownLoad2 extends Base_AyActivity {
ProgressBar downprogress;
private String path;
private TmpBroadcastReceiver receiver;
private long id;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_download);
		
	}
@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

		   
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub

		path = this.getIntent().getStringExtra("path");
	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		downprogress=(ProgressBar)findViewById(R.id.downprogress);
		receiver = new TmpBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
		filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
		this.registerReceiver(receiver, filter);
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(path));
		   //设置在什么网络情况下进行下载
		   request.setAllowedNetworkTypes(Request.NETWORK_WIFI|Request.NETWORK_MOBILE);
		   //设置通知栏标题
		   request.setNotificationVisibility(Request.VISIBILITY_VISIBLE);
		   request.setTitle("下载");
		   request.setDescription("移动执法app更新下载");
		   request.setAllowedOverRoaming(false);
		   //设置文件存放目录
		   request.setDestinationInExternalFilesDir(this, Static_InfoApp.create().getPath(), "tmp.apk");
		   DownloadManager downManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
		   id = downManager.enqueue(request);

		
	}
	public void finishDownLoad(){
//		new Handler().postDelayed(new Runnable(){
//			public void run(){
//				Intent intent = new Intent();
//		        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);    
//		        intent.setAction(android.content.Intent.ACTION_VIEW);    
//		        Uri uri = Uri.fromFile(new File(Static_InfoApp.create().getPath()+"/tmp.apk"));  
//		        intent.setDataAndType(uri,"application/vnd.android.package-archive");   
//		        Activity_DownLoad2.this.startActivityForResult(intent, 27);
//			}
//		}, 500);

	}
	@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			try {
				this.unregisterReceiver(receiver);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	private class TmpBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
                long tmpid = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if(id==tmpid){
                	finishDownLoad();
                }
                
//                Toast.makeText(MainActivity.this, "编号："+id+"的下载任务已经完成！", Toast.LENGTH_SHORT).show();
            }else if(intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)){
//                Toast.makeText(MainActivity.this, "别瞎点！！！", Toast.LENGTH_SHORT).show();
            }
        }
    }
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

}
