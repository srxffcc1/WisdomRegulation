package com.wisdomregulation.allactivity.tmp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.frame.luyin.LuYinActivity;
import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.allactivity.mode.Mode_EvidenceCollect;
import com.wisdomregulation.staticlib.Static_InfoApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;

public class Tmp_VoiceActivity extends Base_AyActivity {

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
		savehead = this.getIntent().getStringExtra("savehead");
		Intent intent = new Intent();
		intent.setAction(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
		voicepath = savehead + "/" + System.currentTimeMillis() + ".mp3";
		try {
			Tmp_VoiceActivity.this.startActivityForResult(intent, 11);
		} catch (Exception e) {
			Intent intent2 = new Intent(Tmp_VoiceActivity.this,
					LuYinActivity.class);
			Tmp_VoiceActivity.this.startActivityForResult(intent2, 11);
		}

	}

	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Audio.Media.DATA };
		Cursor cursor = managedQuery(contentUri, proj, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		if (arg2 != null) {
			if (arg0 == 11) {
				Uri audioUri = arg2.getData();
				if (audioUri.toString().startsWith("file://")) {
					try {

						String path2 = voicepath;
						File file = new File(new URI(audioUri.toString()));

						File file2 = new File(path2);
						InputStream is = new FileInputStream(file);
						FileOutputStream fos = new FileOutputStream(file2);
						byte[] buffer = new byte[1024];
						int byteCount = 0;
						while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
																		// buffer字节
							fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
						}
						fos.flush();// 刷新缓冲区
						is.close();
						fos.close();
						file.delete();
						Static_InfoApp
								.create()
								.getContext()
								.sendBroadcast(
										new Intent(Mode_EvidenceCollect.refresh));
						Tmp_VoiceActivity.this.finish();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (audioUri.toString().startsWith("content://")) {
					try {
						String absolutepath = getRealPathFromURI(audioUri);
						String path2 = voicepath;
						File file = new File(absolutepath);
						File file2 = new File(path2);
						InputStream is = new FileInputStream(file);
						FileOutputStream fos = new FileOutputStream(file2);
						byte[] buffer = new byte[1024];
						int byteCount = 0;
						while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
																		// buffer字节
							fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
						}
						fos.flush();// 刷新缓冲区
						is.close();
						fos.close();
						file.delete();
						Static_InfoApp
								.create()
								.getContext()
								.sendBroadcast(
										new Intent(Mode_EvidenceCollect.refresh));
						Tmp_VoiceActivity.this.finish();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else  {
			Tmp_VoiceActivity.this.finish();
		} 

			}
		} else {

			Tmp_VoiceActivity.this.finish();

		}
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
}
