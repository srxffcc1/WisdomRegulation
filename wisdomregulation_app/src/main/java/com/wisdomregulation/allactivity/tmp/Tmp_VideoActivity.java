package com.wisdomregulation.allactivity.tmp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.allactivity.mode.Mode_EvidenceCollect;
import com.wisdomregulation.staticlib.Static_InfoApp;

import java.io.File;

public class Tmp_VideoActivity extends Base_AyActivity{

	private String voicepath;
	private String savehead;
	@Override
	public void setRootView() {
		setContentView(R.layout.activity_wait);
		
	}

	@Override
	public void initData() {

	}

	@Override
	public void initWidget() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		savehead=this.getIntent().getStringExtra("savehead");
		Intent intent = new Intent();
		intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT,5491520L);
		File file = new File(savehead + "/"
				+ System.currentTimeMillis() + ".mp4");
		if (file.exists()) {
			file.delete();
		}
		Uri uri = Uri.fromFile(file);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		startActivityForResult(intent, 12);

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		if (arg2 != null) {
			if (arg0 == 12) {

				try {
					Static_InfoApp.create().getAllhandler().postDelayed(new Runnable() {
						
						@Override
						public void run() {
							Static_InfoApp.create().getContext().sendBroadcast(new Intent(Mode_EvidenceCollect.refresh));
							Tmp_VideoActivity.this.finish();
							
						}
					}, 300);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}else{

					Tmp_VideoActivity.this.finish();

			
		}
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
}
