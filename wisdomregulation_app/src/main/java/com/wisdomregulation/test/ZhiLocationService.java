package com.wisdomregulation.test;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class ZhiLocationService extends Service{
	private double ll;
	private double lt;
	public static final int timeload=600000;
	public final static int location=13140789;
	public Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case location:
				String m_szDevIDShort = "35" + //we make this look like a valid IMEI 
				Build.BOARD.length()%10 + 
				Build.BRAND.length()%10 + 
				Build.CPU_ABI.length()%10 + 
				Build.DEVICE.length()%10 + 
				Build.DISPLAY.length()%10 + 
				Build.HOST.length()%10 + 
				Build.ID.length()%10 + 
				Build.MANUFACTURER.length()%10 + 
				Build.MODEL.length()%10 + 
				Build.PRODUCT.length()%10 + 
				Build.TAGS.length()%10 + 
				Build.TYPE.length()%10 + 
				Build.USER.length()%10 ; //13 digits
				break;

			default:
				break;
			}
		}
		
	};
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {

		super.onCreate();
		
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public void onStart(Intent intent, int startId) {

		super.onStart(intent, startId);
        mLocClient = new LocationClient(this.getApplicationContext());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(timeload);
        mLocClient.setLocOption(option);
		mLocClient.start();
        
        
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mLocClient.stop();
	}

	public class MyLocationListenner implements BDLocationListener {



		@Override
		public void onReceiveLocation(BDLocation location) {
			ll = location.getLongitude();
			lt = location.getLatitude();
			handler.sendEmptyMessage(ZhiLocationService.location);

		}
	}

	public void onReceivePoi(BDLocation poiLocation) {
		
	}
    
}


