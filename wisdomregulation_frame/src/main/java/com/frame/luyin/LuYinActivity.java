package com.frame.luyin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.frame.luyin.tools.GetSystemDateTime;
import com.frame.luyin.tools.SDcardTools;
import com.frame.luyin.tools.ShowDialog;
import com.frame.luyin.tools.StringTools;
import com.wisdomregulation.frame.R;
import com.wisdomregulation.staticlib.Static_InfoApp;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LuYinActivity extends Activity {
	private Button buttonStart; // 开始按钮
	private Button buttonStop; // 停止按钮
	private TextView textViewLuYinState; // 录音状态
	private ListView listViewAudio; // 显示录音文件的list
	private ArrayAdapter<String> adaperListAudio; // 列表

	private String fileAudioName; // 保存的音频文件的名字
	private MediaRecorder mediaRecorder; // 录音控制
	private String filePath; // 音频保存的文件路径
	private List<String> listAudioFileName; // 音频文件列表
	private boolean isLuYin; // 是否在录音 true 是 false否
	private File fileAudio; // 录音文件
	private File fileAudioList; //列表中的 录音文件
	File dir; //录音文件
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.luyin_main);
		// 初始化组件
		initView();
		// 初始化数据
		initData();
		// 设置组件
		setView();
		// 设置事件
		setEvent();

	}

	/* **********************************************************************
	 * 
	 * 初始化组件
	 */
	private void initView() {
		// 开始
		buttonStart = (Button) findViewById(R.id.button_start);
		// 停止
		buttonStop = (Button) findViewById(R.id.button_stop);
		// 删除
		// 录音状态
		textViewLuYinState = (TextView) findViewById(R.id.text_luyin_state);
		// 显示录音文件的列表
		listViewAudio = (ListView) findViewById(R.id.listViewAudioFile);

	}

	/* ******************************************************************
	 * 
	 * 初始化数据
	 */
	private void initData() {
		if (!SDcardTools.isHaveSDcard()) {
			Toast.makeText(LuYinActivity.this, "请插入SD卡以便存储录音",
					Toast.LENGTH_LONG).show();
			return;
		}

		
		// 要保存的文件的路径
		filePath = Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/tmp"+"/";
		// 实例化文件夹
		dir = new File(filePath);
		if (!dir.exists()) {
			// 如果文件夹不存在 则创建文件夹
			dir.mkdir();
		}
		com.wisdomregulation.utils.Log.i("test", "要保存的录音的文件名为" + fileAudioName + "路径为" + filePath);
		listAudioFileName = SDcardTools.getFileFormSDcard(dir, ".mp3");
		adaperListAudio = new ArrayAdapter<String>(LuYinActivity.this,
				android.R.layout.simple_list_item_1, listAudioFileName);
	}

	/* **************************************************************
	 * 
	 * 设置组件
	 */
	private void setView() {
		buttonStart.setEnabled(true);
		buttonStop.setEnabled(false);
		listViewAudio.setAdapter(adaperListAudio);

	}

	/* ***********************************************************************
	 * 
	 * 设置事件
	 */
	private void setEvent() {

		// 开始按钮
		buttonStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startAudio();
			}
		});

		// 停止按钮
		buttonStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				stopAudion();

			}
		});


		listViewAudio.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String fileAudioNameList=((TextView)arg1).getText().toString();
				fileAudioList = new File(filePath + "/" + fileAudioNameList);
			    	openFile(fileAudioList);
			}
		});
		//文件列表的长按删除事件
		listViewAudio.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				com.wisdomregulation.utils.Log.i("test", "长按事件执行了");
				String fileAudioNameList=((TextView)arg1).getText().toString();
				fileAudioList = new File(filePath + "/" + fileAudioNameList);
			    	if (fileAudioList != null) {
			    		fileAudio = fileAudioList;
			    		fileAudioName = fileAudioNameList;
						showDeleteAudioDialog("是否删除" + fileAudioName + "文件", "不删除",
								"删除", false);
					} else {
						ShowDialog.showTheAlertDialog(LuYinActivity.this, "该文件不存在");
					}
				return true;
			}
		});
		

		
		
		
		
	}

	/* ****************************************************************
	 * 
	 * 开始录音
	 */
	private void startAudio() {
		// 创建录音频文件
		// 这种创建方式生成的文件名是随机的
		fileAudioName = "audio" + GetSystemDateTime.now()
				+ StringTools.getRandomString(2) + ".mp3";
		mediaRecorder = new MediaRecorder();
		// 设置录音的来源为麦克风
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		mediaRecorder.setOutputFile(filePath + "/" + fileAudioName);
		try {
			mediaRecorder.prepare();
			mediaRecorder.start();
			textViewLuYinState.setText("录音中。。。");

			fileAudio = new File(filePath + "/" + fileAudioName);
			buttonStart.setEnabled(false);
			buttonStop.setEnabled(true);
			listViewAudio.setEnabled(false);
			isLuYin = true;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (null != adaperListAudio) {
			adaperListAudio.notifyDataSetChanged();
		}
	}

	/* ******************************************************
	 * 
	 * 停止录制
	 */
	private void stopAudion() {
		if (null != mediaRecorder) {
			// 停止录音
			mediaRecorder.stop();
			mediaRecorder.release();
			mediaRecorder = null;
			textViewLuYinState.setText("按开始打开录音。。。");

			// 开始键能够按下
			buttonStart.setEnabled(true);
			buttonStop.setEnabled(false);
			listViewAudio.setEnabled(true);
			// 删除键能按下
			adaperListAudio.add(fileAudioName);

		}
	}

	/*******************************************************************************************************
	 * 
	 * 是否删除录音文件
	 * 
	 * @param messageString
	 *            //对话框标题
	 * @param button1Title
	 *            //第一个按钮的内容
	 * @param button2Title
	 *            //第二个按钮的内容
	 * @param isExit
	 *            //是否是退出程序
	 */
	public void showDeleteAudioDialog(String messageString,
			String button1Title, String button2Title, final boolean isExit) {
		AlertDialog dialog = new AlertDialog.Builder(LuYinActivity.this)
				.create();
		dialog.setTitle("提示");
		dialog.setMessage(messageString);
		dialog.setButton(button1Title, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (isExit) {
					dialog.dismiss();
					LuYinActivity.super.finish();
				}
			}
		});
		dialog.setButton2(button2Title, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				fileAudio.delete();
				adaperListAudio.remove(fileAudioName);
				fileAudio = null;
				
				if (isExit) {
					dialog.dismiss();
					LuYinActivity.super.finish();
				}
			}
		});

		dialog.show();
	}
	
	
	 /*** *************************************************************************************
	  * 
	  * 打开播放录音文件的程序
	  * @param f
	  */
	  private void openFile(File f)
	  {
	    Intent intent = new Intent();
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    intent.setAction(android.content.Intent.ACTION_VIEW);
	    String type = getMIMEType(f);
	    intent.setDataAndType(Uri.fromFile(f), type);
	    startActivity(intent);
	  }
	  private String getMIMEType(File f)
	  {
	    String end = f
	        .getName()
	        .substring(f.getName().lastIndexOf(".") + 1,
	            f.getName().length()).toLowerCase();
	    String type = "";
	    if (end.equals("mp3") || end.equals("aac") || end.equals("aac")
	        || end.equals("amr") || end.equals("mpeg")
	        || end.equals("mp4"))
	    {
	      type = "audio";
	    } else if (end.equals("jpg") || end.equals("gif")
	        || end.equals("png") || end.equals("jpeg"))
	    {
	      type = "image";
	    } else
	    {
	      type = "*";
	    }
	    type += "/*";
	    return type;
	  }

	

	/**
	 * ********************************************************
	 * 
	 * 当程序停止的时候
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		if (null != mediaRecorder && isLuYin) {
			// 停止录音
			mediaRecorder.stop();
			mediaRecorder.release();
			mediaRecorder = null;

			buttonStart.setEnabled(true);
			buttonStop.setEnabled(false);
			listViewAudio.setEnabled(true);
		}
		super.onStop();
	}

	/**
	 * 
	 * 
	 *********************************************************************** 
	 * 点击退出按钮时
	 */
	@Override
	public void finish() {
		Static_InfoApp.create().getContext()
		.sendBroadcast(new Intent("Mode_EvidenceCollect.refresh"));
		if (null != mediaRecorder && isLuYin) {
			if (fileAudio != null) {
				showDeleteAudioDialog("是否保存" + fileAudioName + "文件", "保存",
						"不保存", true);
			} else {
				ShowDialog.showTheAlertDialog(LuYinActivity.this, "该文件不存在");
			}
		}else{
			super.finish();
		}
		
		
	}
}