package com.wisdomregulation.allactivity.single;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import com.frame.photo.CameraGLSurfaceView;
import com.frame.photo.CameraInterface;
import com.frame.photo.DisplayUtil;
import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.allactivity.mode.Mode_EvidenceCollect;
import com.wisdomregulation.staticlib.Static_InfoApp;

public class Activity_PictureCollect extends Base_AyActivity {
	private CameraGLSurfaceView glSurfaceView;
	Button finishpicture;
	String savehead;
	float previewRate = -1f;
	boolean needfresh = false;
	public static final String finishsave="Activity_PictureCollect.finishsave";
	private TmpBroadcastReceiver receiver;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_picture_collect);

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

		savehead = this.getIntent().getStringExtra("savehead");
		receiver = new TmpBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(finishsave);
		this.registerReceiver(receiver, filter);

	}

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		glSurfaceView=(CameraGLSurfaceView)findViewById(R.id.camera_surfaceview);
		finishpicture=(Button)findViewById(R.id.finishpicture);
		initUI();
	}

	private void initUI() {
		LayoutParams params = glSurfaceView.getLayoutParams();
		Point p = DisplayUtil.getScreenMetrics(this);
		CameraInterface.per=CameraInterface.getInstance().getPerfectSize();
		params.width = CameraInterface.per.height;
		params.height = CameraInterface.per.width;
		previewRate = DisplayUtil.getScreenRate(this); // 默认全屏的比例预览
		glSurfaceView.setLayoutParams(params);

		// 手动设置拍照ImageButton的大小为120dip×120dip,原图片大小是64×64

	}

	public void finishPicture(View view) {
		needfresh = true;
		finishpicture.setEnabled(false);
		CameraInterface.getInstance().setSavehead(savehead).doTakePicture();
	}

	public void finishActivity(View view) {
		Activity_PictureCollect.this.finish();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		glSurfaceView.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		glSurfaceView.onPause();
	}

	@Override
	protected void onDestroy() {
		try {
			if (needfresh) {
				Static_InfoApp
						.create()
						.getContext()
						.sendBroadcast(new Intent(Mode_EvidenceCollect.refresh));
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		this.unregisterReceiver(receiver);
		super.onDestroy();

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
	private class TmpBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(finishsave)) {
				finishpicture.setEnabled(true);
			}


		}

	}
}
