package com.wisdomregulation.test;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.fileselector.activity.FileHomeActivity;
import com.frame.zxing.CaptureActivity;
import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.allactivity.single.Activity_DownLoad2;
import com.wisdomregulation.allactivity.single.Activity_Login;
import com.wisdomregulation.allactivity.single.Activity_Main;
import com.wisdomregulation.allactivity.single.Activity_ModelDetail;
import com.wisdomregulation.bn.BNDemoLocation;
import com.wisdomregulation.bn.BNOverLayDemo;
import com.wisdomregulation.data.entityother.Entity_OrgData;
import com.wisdomregulation.dialog.DateTimePickDialog;
import com.wisdomregulation.dialog.DateTimePickDialog.ModeTimeEnu;
import com.wisdomregulation.dialog.Dialog_Tool;
import com.wisdomregulation.frame.CallBack;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_ConstantLib;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_File;
import com.wisdomregulation.utils.Util_Json;
import com.wisdomregulation.utils.Util_MatchTip;
import com.wisdomregulation.utils.Util_Sdk;
import com.wisdomregulation.utils.Util_String;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.ui.KJActivityStack;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import static org.kymjs.kjframe.http.HttpConfig.TIMEOUT;

public class TestEdit extends Base_AyActivity {
LinearLayout wantadd;
TextView locationtest;
EditText testinput;
TextView Et;
LinearLayout testpaint;
AutoCompleteTextView autotext;
private LocationManager locationManager;
private ClipboardManager mClipboard = null;
final int Menu_1 = Menu.FIRST;
final int Menu_2 = Menu.FIRST + 1;
final int Menu_3 = Menu.FIRST + 2;
public Handler handler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		switch (msg.what) {
		case R.string.changeprogress:
			int[] max2int=(int[]) msg.obj;
			showWaitSecondNotAuto("目前进度", max2int[0], max2int[1]);
			break;		
		default:
			break;
		}
	}

};
	public int getScale() {
		return 50;
	};
	@Override
	public void setRootView() {
		this.setContentView(R.layout.test_test);
		
		
	}

	@Override
	public void initData() {

	}

	// public void print3(View view){
	// new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// Base_Entity base1=new Entity_Book0();
	// Base_Entity base2=new Entity_Book1();
	// Base_Entity base3=new Entity_Book2();
	// Help_Pdf.create().setFileout(Static_ConstantLib.exportpath+"/"+"test2.pdf").open().printerMaster(base1).printerMaster(base2).printerMaster(base3).close();
	//
	// }
	// }).start();
	//
	// }
	@Override
		public void initWidget() {
			// TODO Auto-generated method stub
		wantadd=(LinearLayout)findViewById(R.id.wantadd);
		locationtest=(TextView)findViewById(R.id.showpick);
		testinput=(EditText)findViewById(R.id.testinput);
		testpaint=(LinearLayout)findViewById(R.id.testpaint);
		autotext=(AutoCompleteTextView)findViewById(R.id.autotext);

			ListView lists=new ListView(this);
			
			TSTextView tt=new TSTextView(this);
			wantadd.addView(tt);
			locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
			Et = (TextView)findViewById(R.id.canlongclick);
			testinput.setInputType(InputType.TYPE_CLASS_NUMBER);
			registerForContextMenu(Et);
			TextView t1=new TextView(this);
			t1.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						Util_MatchTip.getViewTextSize(50));
			t1.setText("测试大下的");
			float width=t1.getPaint().measureText(t1.getText().toString());
			TextView t2=new TextView(this);
			t2.setBackgroundColor(Color.parseColor("#000000"));
			t2.setLayoutParams(new LinearLayout.LayoutParams((int)width, 2));
			testpaint.addView(t1);
			testpaint.addView(t2);
			
		}
	public void showpicter(View view) {
		new DateTimePickDialog(this, "").setTimeMode(ModeTimeEnu.Before).show((EditText)view);
	}
	public void showrtsp(View view){
		this.startActivity(new Intent(this,VideoViewDemo.class));
	}
	public void tel(View view) {
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ "13404217659"));
		startActivity(intent);
		//Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ "13404217659"));
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(intent);
		

	}
    public void overlay(View view){
    	this.startActivity(new Intent(this,BNOverLayDemo.class));
    }
	public void tosms(View view) {
		Uri smsToUri = Uri.parse("smsto:10086");
		Intent mIntent = new Intent(android.content.Intent.ACTION_SENDTO,
				smsToUri);
		mIntent.putExtra("sms_body", "");
		startActivity(mIntent);
	}
	public void showfile(View view){
		FileHomeActivity.actionStart(this, Environment.getDataDirectory().getAbsolutePath(), 100, "files");
	}
	public void map(View view){
		startActivity(new Intent(this,BNDemoLocation.class));
	}
	public void undermore(View view){
		startActivity(new Intent(this,UnderMoreActivity.class));
	}
	public void showlocation(View view){


	}
	public void zxing(View view){
		this.startActivity(new Intent(this,CaptureActivity.class));
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
    //创建ContextMenu菜单的回调方法
    public void onCreateContextMenu(ContextMenu m, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(m,v,menuInfo);

        //在上下文菜单选项中添加选项内容
        //add方法的参数：add(分组id,itemid, 排序, 菜单文字)
        m.add(0, Menu_1, 0, "复制文字");
        m.add(0, Menu_2, 0, "粘贴文字");
    }
    private void copyFromEditText1() {

        // Gets a handle to the clipboard service.
        if (null == mClipboard) {
            mClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        }

        // Creates a new text clip to put on the clipboard
        ClipData clip = ClipData.newPlainText("simple text",Et.getText());

        // Set the clipboard's primary clip.
        mClipboard.setPrimaryClip(clip);
    }
    private void pasteToResult() {
        // Gets a handle to the clipboard service.
        if (null == mClipboard) {
            mClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        }

        String resultString = "";
        // 检查剪贴板是否有内容
        if (!mClipboard.hasPrimaryClip()) {
            Toast.makeText(this,
                    "Clipboard is empty", Toast.LENGTH_SHORT).show();
        }
        else {
            ClipData clipData = mClipboard.getPrimaryClip();
            int count = clipData.getItemCount();

            for (int i = 0; i < count; ++i) {

                ClipData.Item item = clipData.getItemAt(i);
                CharSequence str = item
                        .coerceToText(this);
                Log.i("mengdd", "item : " + i + ": " + str);

                resultString += str;
            }

        }
        Toast.makeText(this, resultString, Toast.LENGTH_SHORT).show();
        Et.setText(resultString);
    }


    //ContextMenu菜单选项的选项选择的回调事件
    public boolean onContextItemSelected(MenuItem item) {
        //参数为用户选择的菜单选项对象
        //根据菜单选项的id来执行相应的功能
        switch (item.getItemId()) {
            case 1:
                Toast.makeText(this, "复制文字", Toast.LENGTH_SHORT).show();
                copyFromEditText1();
                break;
            case 2:
                Toast.makeText(this, "粘贴文字", Toast.LENGTH_SHORT).show();
                pasteToResult();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
	public void uploadSingle(View view){
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Util_File.uploadFileSingleTest(new File(Environment.getExternalStorageDirectory()+"/test.apk"));
//				uploadFile(UUID.randomUUID().toString().replace("-",""),Environment.getExternalStorageDirectory()+"/test.apk");
			}
		}).start();

	}
	private static final String CHARSET = "utf-8"; // 设置编码
	public synchronized static  String uploadFile(String recordid, final String srcPath) {

		String requestURL = Static_InfoApp.create().getiphead() + "uploadphone/upload";

		Log.d("liguo", "uploadURL=====" + requestURL);
		String result = null;
		String boundry = UUID.randomUUID().toString(); // 边界标识 随机生成
		String prefix = "--", end = "\r\n";
		String contentType = "multipart/form-data"; // 内容类型
		String cookie = HttpConfig.sCookie;
		File file = new File(srcPath);
		String status = "";
		FileInputStream stream;
		long size = 0;
		try {
			stream = new FileInputStream(file);
			size = stream.available();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String name = file.getName();
		boolean illegal = false;
		if (!illegal) {
			if (true) {// 限制上传文件的大小为5M
				try {
					URL url = new URL(requestURL);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setReadTimeout(TIMEOUT);
					conn.setConnectTimeout(TIMEOUT);
					conn.setDoInput(true); // 允许输入流
					conn.setDoOutput(true); // 允许输出流
					conn.setUseCaches(false); // 不允许使用缓存
					// conn.setChunkedStreamingMode(0);//启用没有进行内部缓存的http请求正文流
					conn.setRequestMethod("POST"); // 请求方式
					conn.setRequestProperty("Charset", CHARSET); // 设置编码
					conn.setRequestProperty("connection", "keep-alive");
					conn.setRequestProperty("Cookie", "PHPSESSID=" + cookie);
					conn.setRequestProperty("Accept", "*/*");
					conn.setRequestProperty("Content-Type", contentType
							+ ";boundary=" + boundry);

					if (file != null) {
						/**
						 * 当文件不为空，把文件包装并且上传
						 */
						DataOutputStream dos = new DataOutputStream(
								conn.getOutputStream());
						StringBuffer sb = new StringBuffer();

						sb.append(prefix);
						sb.append(boundry);
						sb.append(end);
						sb.append("Content-Disposition: form-data; name=\"Filename\""
								+ end);
						sb.append(end);
						sb.append(file.getName() + end);

						sb.append(prefix);
						sb.append(boundry);
						sb.append(end);
						sb.append("Content-Disposition: form-data; name=\"recordid\""
								+ end);
						sb.append(end);
						sb.append(recordid + end);

						sb.append(prefix);
						sb.append(boundry);
						sb.append(end);
						sb.append("Content-Disposition: form-data; name=\"folder\""
								+ end);
						sb.append(end);
						sb.append("/info/" + end);

						sb.append(prefix);
						sb.append(boundry);
						sb.append(end);
						sb.append("Content-Disposition: form-data; name=\"PHPSESSID\""
								+ end);
						sb.append(end);
						sb.append(cookie + end);

						sb.append(prefix);
						sb.append(boundry);
						sb.append(end);
						sb.append("Content-Disposition: form-data; name=\"Filedata\"; filename=\""
								+ file.getName() + "\"" + end);
						sb.append("Content-Type: application/octet-stream"
								+ end);
						sb.append(end);
						dos.write(sb.toString().getBytes());

						FileInputStream is = new FileInputStream(file);
						byte[] bytes = new byte[1024];
						int len = 0;
						long sum=0;
						while ((len = is.read(bytes)) != -1) {
							System.out.println("表单写入:"+(sum+=len));
							dos.write(bytes, 0, len);
						}
						is.close();
						dos.write(end.getBytes());

						byte[] endData = (prefix + boundry + prefix + end)
								.getBytes();
						dos.write(endData);
						dos.flush();

						/**
						 * 获取响应码 200=成功 当响应成功，获取响应的流
						 */
						int res = conn.getResponseCode();
						String mes = conn.getResponseMessage();
						Log.d("testing", "res===" + res);
						if (res == 200) {
							InputStream input = conn.getInputStream();
							StringBuffer sB = new StringBuffer();
							int ss;
							while ((ss = input.read()) != -1) {
								sB.append((char) ss);

							}
							result = "{" + sB.toString();
							try {
								System.out.println("测试返回:"+result);
								JSONObject jo = new JSONObject(result);
								status = jo.getString("status");
								JSONObject jso = jo.getJSONObject("type");
								String fileName = jso.getString("filename");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {

						}
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				status = "overFile";
			}
		} else {
			status = "illegalType";
		}
		return status;
	}
	public void testSession(View view){
		new Thread(new Runnable() {
			@Override
			public void run() {
				KJHttp kjh = new KJHttp();
				HttpParams params = new HttpParams();
//				params.putHeaders("cookie", HttpConfig.sCookie);
				params.put("page","1");
				params.put("rows","10");
				kjh.post(Static_InfoApp.create().getiphead() + "mobileEnterprise/mobileEnterpriseAction!list2", params,false,new HttpCallBack() {
					@Override
					public void onSuccess(String t) {
						super.onSuccess(t);
						System.out.println("测试返回:"+t);
					}

					@Override
					public void onFailure(int errorNo, String strMsg) {
						super.onFailure(errorNo, strMsg);
					}
				});
			}
		}).start();

	}
    public void uploadfile(View view){
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Util_File.uploadFileTest(new File(Static_InfoApp.create().getPath()+"/ZhiCollect/tmp/"));
				//System.out.println("测试上传完成");
			}
		}).start();
    }
    public void resetData(View view){
    	TestEdit.this.showWait();
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				Util_File.deleteFile(Static_InfoApp.create().getPath());
				Util_Sdk.initializeSp(TestEdit.this.getApplicationContext());
				Util_Sdk.initializeData(TestEdit.this.getApplicationContext(),null);
				Help_DB.create(TestEdit.this.getApplicationContext(),""+Static_InfoApp.create().getdbname()+"");
				TestEdit.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						
						TestEdit.this.dissmissWait();
						
						TestEdit.this.startActivity(new Intent(TestEdit.this, Activity_Login.class));
						
						KJActivityStack.create().finishActivity(Activity_Main.class);
						KJActivityStack.create().finishActivity(TestEdit.class);
					}
				});
				
			}
		}).start();
    }
    public void testattach(View view){
    	Help_DB.release();
    	Help_DB.create().attach();
    	Help_DB.create(this.getApplicationContext(), ""+Static_InfoApp.create().getdbname()+"");
    	
    }
    public void testinsert(View view){
		Message messs=Message.obtain();
		messs.what=R.string.changeprogress;
		messs.obj=new int[]{0,0};
		handler.sendMessage(messs);
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				Help_DB.create().truncate(new Entity_OrgData());
				try {
					String orgstringjson=Util_String.inputStream2String(new FileInputStream(Static_InfoApp.create().getPath()+ "/ZhiDbhome/"+ "/orgdata_1.txt"));
					long oldtime=System.currentTimeMillis();
					
					Util_Json.get2insertResultSql(orgstringjson, "orgdata",new CallBack() {
						
						@Override
						public void back(Object resultlist) {
							Message mess=Message.obtain();
							mess.what=R.string.changeprogress;
							mess.obj=resultlist;
							handler.sendMessage(mess);
							
						}
					});
					
					long nowtime=System.currentTimeMillis();
					//System.out.println("插入完毕,耗时:"+((nowtime-oldtime)/1000)+"秒");
					
					TestEdit.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							
							TestEdit.this.dissmissWait();
						}
					});
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}).start();
    	
    }
    public void testruntime(View view){
    	this.startActivity(new Intent(this,Activity_ModelDetail.class));
    }
    public  void testcompany(View view){
    	KJHttp kjh = new KJHttp();
		HttpParams params = new HttpParams();
		params.put("createTime",  "0");
		params.put("updateTime",  "0");
		kjh.post(Static_InfoApp.create().getiphead()+Static_ConstantLib.companyurl, params,  new HttpCallBack() {
			
			@Override
			public void onSuccess(byte[] t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
			}

			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				//System.out.println("测试的企业返回数据:"+t);
			}

			@Override
			public void onFailure(int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(errorNo, strMsg);
			}
			
		});
    }
    public void testdatamanager(View view){

    }
    public void testvlc(View view){
    	
    }
    public void testpass(View view){
    	Dialog_Tool.showDialog_ChangePersonType3(this, null, null);
    }
	public void testb(View view){
		sendBroadcast(new Intent("com.example.opentest.test"));
	}
	public void testbs(View view){
		startActivity(new Intent("test.test").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
	}
	public void testdownload(View view){
		this.startActivity(new Intent(this,Activity_DownLoad2.class).putExtra("path", "http://speed.myzone.cn/pc_elive_1.1.rar"));
	}
	public void testYear(View view){
		new DateTimePickDialog(this,"").show(view);
	}
	public void testDay(View view){

	}
}
