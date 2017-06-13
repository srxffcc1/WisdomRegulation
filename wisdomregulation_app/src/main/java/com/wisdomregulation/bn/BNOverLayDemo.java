package com.wisdomregulation.bn;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.SeekBar;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.wisdomregulation.R;


/**
 * 演示覆盖物的用法
 */
public class BNOverLayDemo extends Activity {

	/**
	 * MapView 是地图主控件
	 */
	private TextureMapView mMapView;
	private BaiduMap mBaiduMap;
//	private Marker mMarkerA;
//	private Marker mMarkerB;
//	private Marker mMarkerC;
//	private Marker mMarkerD;
	private InfoWindow mInfoWindow;


	// 初始化全局 bitmap 信息，不用时及时 recycle
	BitmapDescriptor bdA = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_marka);
	BitmapDescriptor bdB = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_markb);
	BitmapDescriptor bdC = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_markc);
	BitmapDescriptor bdD = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_markd);
	BitmapDescriptor bd = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_gcoding);
	BitmapDescriptor bdGround = BitmapDescriptorFactory
			.fromResource(R.drawable.ground_overlay);
	private OverlayManager overlaymanager;
	public Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}
		
	};
	private List<LatLng> latlnglist=new ArrayList<LatLng>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.test_overlay);
		mMapView = (TextureMapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(4000.0f);
		mBaiduMap.setMapStatus(msu);
		
//		mBaiduMap.setOnMapLoadedCallback(new OnMapLoadedCallback() {
//			
//			@Override
//			public void onMapLoaded() {
//				overlaymanager.zoomToSpan();
//				
//			}
//		});
//		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
//			public boolean onMarkerClick(final Marker marker) {
//				Button button = new Button(getApplicationContext());
//				button.setBackgroundResource(R.drawable.popup);
//				OnInfoWindowClickListener listener = null;
//				if (marker == mMarkerA || marker == mMarkerD) {
//					button.setText("更改位置");
//					listener = new OnInfoWindowClickListener() {
//						public void onInfoWindowClick() {
//							LatLng ll = marker.getPosition();
//							LatLng llNew = new LatLng(ll.latitude + 0.005,
//									ll.longitude + 0.005);
//							marker.setPosition(llNew);
//							mBaiduMap.hideInfoWindow();
//						}
//					};
//					LatLng ll = marker.getPosition();
//					mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll, -47, listener);
//					mBaiduMap.showInfoWindow(mInfoWindow);
//				} else if (marker == mMarkerB) {
//					button.setText("更改图标");
//					button.setOnClickListener(new OnClickListener() {
//						public void onClick(View v) {
//							marker.setIcon(bd);
//							mBaiduMap.hideInfoWindow();
//						}
//					});
//					LatLng ll = marker.getPosition();
//					mInfoWindow = new InfoWindow(button, ll, -47);
//					mBaiduMap.showInfoWindow(mInfoWindow);
//				} else if (marker == mMarkerC) {
//					button.setText("删除");
//					button.setOnClickListener(new OnClickListener() {
//						public void onClick(View v) {
//							marker.remove();
//							mBaiduMap.hideInfoWindow();
//						}
//					});
//					LatLng ll = marker.getPosition();
//					mInfoWindow = new InfoWindow(button, ll, -47);
//					mBaiduMap.showInfoWindow(mInfoWindow);
//				}
//				return true;
//			}
//		});
	}
	public void getAllMarker(){

			initOverlay(null);

	}
	public void initOverlay(List<String> usernamelist) {
		// add marker overlay
		LatLng llA = new LatLng(32.077643,118.759478);
		LatLng llB = new LatLng(32.077604,118.761358);
		LatLng llC = new LatLng(32.076423,118.768907);
		LatLng llD = new LatLng(32.076105,118.778739);

		MarkerOptions ooA = new MarkerOptions().position(llA).icon(BitmapDescriptorFactory.fromBitmap(createBitmap("hello1")))
				.zIndex(9).draggable(true).title("nihao");

//		mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
		MarkerOptions ooB = new MarkerOptions().position(llB).icon(BitmapDescriptorFactory.fromBitmap(createBitmap("hello2")))
				.zIndex(5).title("nihao");

//		mMarkerB = (Marker) (mBaiduMap.addOverlay(ooB));
		MarkerOptions ooC = new MarkerOptions().position(llC).icon(BitmapDescriptorFactory.fromBitmap(createBitmap("hello3")))
				.zIndex(7).draggable(true).title("nihao");

//		mMarkerC = (Marker) (mBaiduMap.addOverlay(ooC));
		ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
		giflist.add(bdA);
		giflist.add(bdB);
		giflist.add(bdC);
		MarkerOptions ooD = new MarkerOptions().position(llD).icon(BitmapDescriptorFactory.fromBitmap(createBitmap("hello4")))
				.zIndex(0).draggable(true).title("nihao");	

		final List<OverlayOptions> overlayOptions=new ArrayList<OverlayOptions>();
		overlayOptions.clear();
		overlayOptions.add(ooA);
		overlayOptions.add(ooB);
		overlayOptions.add(ooC);
		overlayOptions.add(ooD);
//		for (int i = 0; i < latlnglist.size(); i++) {
//			MarkerOptions sss = new MarkerOptions().position(latlnglist.get(i)).icon(BitmapDescriptorFactory.fromBitmap(createBitmap(usernamelist.get(i))))
//					.zIndex(9).draggable(true);
//			overlayOptions.add(sss);
//		}

		overlaymanager = new OverlayManager(mBaiduMap) {
			

			
			@Override
			public boolean onMarkerClick(Marker arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public List<OverlayOptions> getOverlayOptions() {
				
				return overlayOptions;
			}

			@Override
			public boolean onPolylineClick(Polyline arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		overlaymanager.addToMap();
		overlaymanager.zoomToSpan();
		if(latlnglist.size()==1){
			MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(4000.0f);
			mBaiduMap.setMapStatus(msu);
		}

		// add ground overlay
//		LatLng southwest = new LatLng(39.92235, 116.380338);
//		LatLng northeast = new LatLng(39.947246, 116.414977);
//		LatLngBounds bounds = new LatLngBounds.Builder().include(northeast)
//				.include(southwest).build();
//
//		OverlayOptions ooGround = new GroundOverlayOptions()
//				.positionFromBounds(bounds).image(bdGround).transparency(0.8f);
//		mBaiduMap.addOverlay(ooGround);
//
//		MapStatusUpdate u = MapStatusUpdateFactory
//				.newLatLng(bounds.getCenter());
//		mBaiduMap.setMapStatus(u);

//		mBaiduMap.setOnMarkerDragListener(new OnMarkerDragListener() {
//			public void onMarkerDrag(Marker marker) {
//			}
//
//			public void onMarkerDragEnd(Marker marker) {
//				Toast.makeText(
//						LeadMapActivity.this,
//						"拖拽结束，新位置：" + marker.getPosition().latitude + ", "
//								+ marker.getPosition().longitude,
//						Toast.LENGTH_LONG).show();
//			}
//
//			public void onMarkerDragStart(Marker marker) {
//			}
//		});
	}
	private Bitmap createBitmap(String letter) {
		
		Bitmap imgMarker = BitmapFactory.decodeResource(this.getResources(), R.drawable.maker2).copy(Bitmap.Config.ARGB_8888, true);
		Bitmap imgTemp = Bitmap.createBitmap(imgMarker.getWidth(), imgMarker.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(imgTemp);
        Paint paint = new Paint(); // 建立画笔
        paint.setDither(true);
        paint.setFilterBitmap(true);
        Rect src = new Rect(0, 0, imgMarker.getWidth(), imgMarker.getHeight());
        Rect dst = new Rect(0, 0, imgMarker.getWidth(), imgMarker.getHeight());
        
        canvas.drawBitmap(imgMarker, src, dst, paint);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
                        | Paint.DEV_KERN_TEXT_FLAG);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD); // 采用默认的宽度
        textPaint.setColor(Color.BLACK);
        // 调整字体在图片中的位置，此处根据分辨率来判断字体的大小了 
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int scrHeigh = dm.heightPixels;

        	
                textPaint.setTextSize(30.0f);
                canvas.drawText(String.valueOf(letter), (imgMarker.getWidth()) / 2+20,
                		(imgMarker.getHeight()) / 3, textPaint);

        canvas.save();
        canvas.restore();
        BitmapDrawable pic = new BitmapDrawable(getResources(), imgTemp);

        return pic.getBitmap();

}

	/**
	 * 清除所有Overlay
	 * 
	 * @param view
	 */
	public void clearOverlay(View view) {
		mBaiduMap.clear();
//		mMarkerA = null;
//		mMarkerB = null;
//		mMarkerC = null;
//		mMarkerD = null;
	}

    /**
     * 重新添加Overlay
     *
     * @param view
     */
    public void resetOverlay(View view) {
//        initOverlay();
    	if(latlnglist.size()>0){
    		overlaymanager.zoomToSpan();
    	}
        
    }
    private class SeekBarListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                boolean fromUser) {
            // TODO Auto-generated method stub
            float alpha = ((float)seekBar.getProgress()) / 10;
//            if (mMarkerA != null) {
//                mMarkerA.setAlpha(alpha);
//            }
//            if (mMarkerB != null) {
//                mMarkerB.setAlpha(alpha);
//            }
//            if (mMarkerC != null) {
//                mMarkerC.setAlpha(alpha);
//            }
//            if (mMarkerD != null) {
//                mMarkerD.setAlpha(alpha);
//            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub
        }
       
    }

	@Override
	protected void onPause() {
		// MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
		mMapView.onResume();
		super.onResume();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				getAllMarker();
			}
		}).start();
		
	}

	@Override
	protected void onDestroy() {
		// MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		mMapView.onDestroy();
		super.onDestroy();
		// 回收 bitmap 资源
		bdA.recycle();
		bdB.recycle();
		bdC.recycle();
		bdD.recycle();
		bd.recycle();
		bdGround.recycle();
	}


}
