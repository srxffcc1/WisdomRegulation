package com.wisdomregulation.allactivity.tmp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.frame.qqcutpic.ClipActivity;
import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.staticlib.Static_InfoApp;

import java.io.File;

public class Tmp_PhotoActivity extends Base_AyActivity{

	private File file;
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
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		String path = Static_InfoApp.create().getPath()+"/ZhiHead";
		
		File path1 = new File(path);
		path1.mkdirs();
		file = new File(path1,System.currentTimeMillis()+".jpg");
		Uri mOutPutFileUri = Uri.fromFile(file);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutPutFileUri);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT,5491520L);
		startActivityForResult(intent, 11);
	}
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		if(arg0==11){
			
//				if(arg2!=null){
			if(file.exists()){
				Tmp_PhotoActivity.this.startActivity(new Intent(Tmp_PhotoActivity.this,ClipActivity.class).putExtra("specialtype", 2).putExtra("path", file.getAbsolutePath()));
			}
				
//				}
//				else{
//				}
				Tmp_PhotoActivity.this.finish();
				
		}
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

}
