package com.wisdomregulation.bn;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;

public class BNDemoLocation extends Base_AyActivity {
	private TextureMapView mMapView;
    BaiduMap mBaiduMap;
    BitmapDescriptor mCurrentMarker;
    LocationClient mLocClient;
    boolean isFirstLoc = true; // 是否首次定位
	private double mlat;
	private double mlong;
	private double tlat;
	private double tlong;
	public Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 111:
	            try {
					BitmapDescriptor bdA = BitmapDescriptorFactory
							.fromResource(R.drawable.icon_gcoding);
					LatLng llA = new LatLng(tlat,tlong);
					MarkerOptions ooa = new MarkerOptions().position(llA).icon(bdA)
							.zIndex(5).title("nihao");
					mBaiduMap.addOverlay(ooa);
					
					LatLngBounds.Builder builder = new LatLngBounds.Builder();
					builder.include(new LatLng(tlat, tlong));
					builder.include(new LatLng(mlat, mlong));
					mBaiduMap.setMapStatus(MapStatusUpdateFactory
					        .newLatLngBounds(builder.build()));
				} catch (Exception e) {

				}
				break;

			default:
				break;
			}
		}
		
	};
    public MyLocationListenner myListener = new MyLocationListenner();
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_bn_loc);
		
		
	}

	@Override
	public void initData() {

	}

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		mMapView = (TextureMapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		tlat=this.getIntent().getDoubleExtra("tlat", 32.049827);
		tlong=this.getIntent().getDoubleExtra("tlong", 118.790703);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        perfomOverlook();
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(3000);
        mLocClient.setLocOption(option);
        mLocClient.start();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(this.getApplicationContext());
		

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
				mMapView.onResume();

		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mMapView.onPause();
		
	}
	@Override
	protected void onDestroy() {
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();

	}
    public void perfomOverlook(){
    	try {
			int overlookAngle = 0;
			MapStatus ms = new MapStatus.Builder(mBaiduMap.getMapStatus()).overlook(overlookAngle).build();
			MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(ms);
			mBaiduMap.animateMapStatus(u);
		} catch (NumberFormatException e) {
//			Toast.makeText(this, "请输入正确的俯角", Toast.LENGTH_SHORT).show();
		}
    }
    public void reset(View view){
        mBaiduMap
        .setMyLocationConfigeration(new MyLocationConfiguration(
        		LocationMode.FOLLOWING, true, mCurrentMarker));
        new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
		    	mBaiduMap
		        .setMyLocationConfigeration(new MyLocationConfiguration(
		        		LocationMode.NORMAL, true, mCurrentMarker));
		    	BNDemoLocation.this.perfomOverlook();
				
			}
		},500);

    }
    public void normal(View view){
    	mBaiduMap
        .setMyLocationConfigeration(new MyLocationConfiguration(
        		LocationMode.NORMAL, true, mCurrentMarker));
    	
    }
    public void lead(View view){
//    	mBaiduMap
//        .setMyLocationConfigeration(new MyLocationConfiguration(
//        		LocationMode.NORMAL, true, mCurrentMarker));
    	this.startActivity(new Intent(this,BNDemoMainActivity.class).putExtra("tlong", tlong).putExtra("tlat", tlat));

    }
    public void compass(View view){
    	mBaiduMap
        .setMyLocationConfigeration(new MyLocationConfiguration(
        		LocationMode.COMPASS, true, mCurrentMarker));
    }
    public void follow(View view){
        mBaiduMap
        .setMyLocationConfigeration(new MyLocationConfiguration(
        		LocationMode.FOLLOWING, true, mCurrentMarker));
    }
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
        	
            if (location == null || mMapView == null) {
                return;
            }
            mlat=location.getLatitude();
            mlong=location.getLongitude();
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(mlat)
                    .longitude(mlong).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                handler.postDelayed(new Runnable() {
    				
    				@Override
    				public void run() {
    					handler.sendEmptyMessage(111);
    					
    				}
    			}, 300);
            }
 
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}


}

