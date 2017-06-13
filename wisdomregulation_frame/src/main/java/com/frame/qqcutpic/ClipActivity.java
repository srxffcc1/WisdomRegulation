package com.frame.qqcutpic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.wisdomregulation.frame.R;
import com.wisdomregulation.staticlib.Static_InfoApp;

import java.io.File;

public class ClipActivity extends Activity {
	private ClipImageLayout mClipImageLayout;
	private String path;
	private ProgressDialog loadingDialog;
	private int specialtype = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cut_clipimage);
		// 这步必须要加
		specialtype = this.getIntent().getIntExtra("specialtype", 1);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		TextView backtoscan=(TextView) this.findViewById(R.id.backtoscan);
		backtoscan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ClipActivity.this.finish();
				
			}
		});
		loadingDialog = new ProgressDialog(this);
		loadingDialog.setTitle("请稍后...");
		path = getIntent().getStringExtra("path");
		if (TextUtils.isEmpty(path) || !(new File(path).exists())) {
			Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show();
			return;
		}
		Bitmap bitmap = ImageTools.convertToBitmap(path, 600, 600);
		switch (specialtype) {
		case 1:

			break;
		case 2:
			new File(path).delete();
			break;

		default:
			break;
		}
		if (bitmap == null) {
			Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show();
			return;
		}
		mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);
		mClipImageLayout.setBitmap(bitmap);
		((Button) findViewById(R.id.id_action_clip))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Static_InfoApp.head = mClipImageLayout.clip();
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								String savepath = Static_InfoApp.create().getPath()
										+ "/ZhiHead/head.jpg";
								ImageTools.savePhotoToSDCard(Static_InfoApp.head,
										savepath);
								
							}
						}).start();

						Intent intent = new Intent(ClipActivity.this,
								CutMainActivity.class);
						ClipActivity.this.startActivity(intent);

					}
				});
	}



}
