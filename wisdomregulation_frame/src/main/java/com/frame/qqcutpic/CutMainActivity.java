package com.frame.qqcutpic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.wisdomregulation.frame.R;
import com.wisdomregulation.staticlib.Static_InfoApp;

public class CutMainActivity extends Activity {
	
	private ImageView mAvator;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cut_main);
        initView();
    }

	public void initView() {
		mAvator = (ImageView) findViewById(R.id.iv_cutimg);
		mAvator.setImageBitmap(Static_InfoApp.head);
	}
	public void finish(View view){
//		super.toMainPage();
		Static_InfoApp.create().getContext().sendBroadcast(new Intent("Activity_Main.refreshHeadPicture"));
		this.finish();
	}


//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.iv_cutimg:
//			Intent intent = new Intent(CutMainActivity.this, CutPicActivity.class);
//			startActivityForResult(intent, 0);
//			break;
//
//		default:
//			break;
//		}
//	}
 
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//		switch (resultCode) {
//		case 0:
//			if(CutPicActivity.bitmap != null) {
//				mAvator.setImageBitmap(CutPicActivity.bitmap);
//			}
//			break;
//		default:
//			break;
//		}
//		
//	}
    
}
