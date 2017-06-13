package com.wisdomregulation.allactivity.single;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_User;
import com.wisdomregulation.dialog.Dialog_Tool;
import com.wisdomregulation.frame.AutoCheckBox;
import com.wisdomregulation.frame.CallBack;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.helporg2017.Pdf_Shark2017;
import com.wisdomregulation.staticlib.Static_ConstantLib;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;
import com.wisdomregulation.utils.Util_Sdk;

import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.http.HttpParams;


public class Activity_Login extends Base_AyActivity {
	TextView testVersion;
	public boolean printtrue=false;
	EditText loginname;
	EditText loginpassword;
	AutoCheckBox rememberpassword;
	TextView offinelogintext;

	private String oldloginname;
	private SharedPreferences sp;
	public Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case R.string.finishLogin:
				Activity_Login.this.startActivity(new Intent(Activity_Login.this, Activity_Main.class));
				Activity_Login.this.dissmissWait();
				Activity_Login.this.finish();
				break;
			case R.string.changepersontype:				
				toChangePersonType();
				break;
			case R.string.changepersontypepass:				
				showChangePersonType();
				break;
			case R.string.changepersontypepass2:				
				showChangePersonType2();
				break;
			default:
				break;
			}
		}

	};
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
//	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
//	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	super.onCreate(savedInstanceState);
	SDKInitializer.initialize(Activity_Login.this.getApplicationContext());//百度地图初始化
	sp = Activity_Login.this.getSharedPreferences("releaseconfig",
			MODE_PRIVATE);
	oldloginname = sp.getString("accountname", "");//获得老的登陆名字 用于之后判断是否要重置登录状态 
	Pdf_Shark2017.create().setCity(Static_InfoApp.city);
}
	@Override
	public void setRootView() {
		
		setContentView(R.layout.activity_login);

		
	}

	@Override
	public void initData() {

	}

	/**
	 * 检查是否需要重置数据库同步状态 比如是否换人登陆 
	 */
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		testVersion=(TextView)findViewById(R.id.testVersion);
		loginname=(EditText)findViewById(R.id.loginname);
		loginpassword=(EditText)findViewById(R.id.loginpassword);
		rememberpassword=(AutoCheckBox)findViewById(R.id.rememberpassword);
		offinelogintext=(TextView)findViewById(R.id.offinelogintext);
		rememberpassword.setChecked(true);
		try {
			PackageManager pm = this.getPackageManager();  
			PackageInfo pi = pm.getPackageInfo(this.getPackageName(), 0);  
			String versionNamei = pi.versionName; 
			String versioncodei = pi.versionCode+"";
			testVersion.setText(versionNamei);//设置版本号而已
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 判断是否需要重置的主方法
	 * 
	 */
	public void checkNeedReset(){
		
		
		final String nowname=sp.getString("accountname", "");
		//System.out.println("登陆名字："+nowname);
		if(Static_InfoApp.create().isshow()){
			sp.edit().putString("accountid", "测试id").putString("accountdept", "管理员").putString("accountname", "测试员").putString("username", "测试员").commit();
			sp.edit().putString("autologin", "0").putString("accountname", "测试员").putString("userpassword", "测试密码").commit();
			handler.sendEmptyMessage(R.string.finishLogin);
		}else{
			if(!oldloginname.equals(nowname)){
				sp.edit().putBoolean("isrest", true).commit();
			}else{
				sp.edit().putBoolean("isrest", false).commit();
			}
			handler.sendEmptyMessage(R.string.finishLogin);

		}



		
	}
	/**
	 * 登陆方法
	 */
	public void login(){
			if(Static_InfoApp.create().isnoweb()){
				//开始离线登录比较
				Base_Entity nlist=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().search(new Entity_User()));
				if(nlist.size()!=0){
					String usernametmp=nlist.getValue(2);
					String accountidtmp=nlist.getId();
					String accountdepttmp=nlist.getValue(5);
					String accountnametmp=nlist.getValue(0);
					String accountorgtmp=nlist.getValue(3);
					String role="";
					if(usernametmp.matches("超级用户")||accountdepttmp.matches("(.*)市安委(.*)")||usernametmp.matches("(.*)市安委(.*)")){
						role="super";
					}
					sp.edit().putString("accountorg", accountorgtmp).putString("userauthor", role).putString("accountid", accountidtmp).putString("accountdept", accountdepttmp).putString("accountname", accountnametmp).putString("username", usernametmp).commit();
					handler.sendEmptyMessage(R.string.finishLogin);
				}else{
					Toast.makeText(Activity_Login.this, "离线登录失败-登录名或密码错误", Toast.LENGTH_LONG).show();
				}
				Activity_Login.this.dissmissWait();
			}else{
				HttpConfig.sCookie=null;
				KJHttp kjh = new KJHttp();
				HttpParams params = new HttpParams();
				params.put("username", loginname.getText().toString().trim());
				params.put("password", loginpassword.getText().toString().trim());
				params.put("checkType", "gov");
				kjh.post(Static_InfoApp.create().getiphead()+Static_ConstantLib.loginurl, params, false,
						new HttpCallBack() {

							@Override
							public void onSuccess(String t) {
								String jsonresult=t;
								//System.out.println("登陆返回:"+t);
								try {
									final JSONObject jsonobject=new JSONObject(jsonresult);
									boolean resultisok=jsonobject.getBoolean("status");
									if(resultisok){
										Activity_Login.this.runOnUiThread(new Runnable() {

											@Override
											public void run() {
												try {
													String usernametmp=jsonobject.getString("realName");
													String accountidtmp=jsonobject.getString("Id");
													String accountdepttmp=jsonobject.getString("departmentName");
													String accountnametmp=jsonobject.getString("userId");
													String accountorgtmp=jsonobject.getString("org");
													String role="";
													HttpConfig.sCookie=jsonobject.getString("sessionId")+"";
													if(usernametmp.matches("超级用户")||accountdepttmp.matches("(.*)市安委(.*)")||usernametmp.matches("(.*)市安委(.*)")){
														role="super";
													}
													sp.edit().putString("accountorg", accountorgtmp).putString("userauthor", role).putString("accountid", accountidtmp).putString("accountdept", accountdepttmp).putString("accountname", accountnametmp).putString("username", usernametmp).commit();
													Toast.makeText(Activity_Login.this, "登录成功正在做相关处理", Toast.LENGTH_SHORT).show();
													checkNeedReset();
												} catch (Exception e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}


											}
										});


									}else{

										Activity_Login.this.runOnUiThread(new Runnable() {

											@Override
											public void run() {
												Toast.makeText(Activity_Login.this, "登录失败请确认用户名和密码", Toast.LENGTH_LONG).show();
												canOffine();
												Activity_Login.this.dissmissWait();
											}
										});


									}
								} catch (Exception e) {
									Toast.makeText(Activity_Login.this, "服务端出错", Toast.LENGTH_LONG).show();
									canOffine();
									Activity_Login.this.dissmissWait();
									e.printStackTrace();

								}


							}

							@Override
							public void onFailure(int errorNo, final String strMsg) {
								// TODO Auto-generated method stub
								super.onFailure(errorNo, strMsg);
								Activity_Login.this.dissmissWait();
								Activity_Login.this.runOnUiThread(new Runnable() {

									@Override
									public void run() {
										Toast.makeText(Activity_Login.this, "连接出错:"+strMsg, Toast.LENGTH_LONG).show();
										canOffine();

									}
								});
							}

						});
			}



	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		checkprintapkisinstall();
		checkisautologin();

	}
	/**
	 * 检查是不是自动登陆
	 */
	private void checkisautologin() {

		String autologin=sp.getString("autologin", "");
		String username=sp.getString("accountname", "");
		String userpassword=sp.getString("userpassword", "");
		if(Static_InfoApp.create().isshow()){
			
		}else{
			if(autologin.equals("1")){
				rememberpassword.setChecked(true);
				loginname.setText(username);
				loginpassword.setText(userpassword);
				Activity_Login.this.showWait("自动登陆中");
				login();
			}else{

			}
		}
	}
	/**
	 * 检查必备的打印程序是否安装
	 */
//	private void checkprintapkisinstall() {
//		try {
//
//
////	        this.startService(new Intent("com.wisdomregulation.test.ZhiLocationService"));
//
//			if(!Util_Apk.appIsInstalled13(Static_InfoApp.create().getContext(), Static_ConstantLib.pack)){
//				Toast.makeText(Activity_Login.this, "安装必备打印程序", Toast.LENGTH_SHORT).show();
//				Util_Apk.appInstall13(Activity_Login.this, Static_ConstantLib.pack, Static_ConstantLib.apk);
//			}
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	/**
	 * 可否离线登陆  处于测试阶段代码未完善
	 */
	public void canOffine(){
//		offinelogintext.setVisibility(View.VISIBLE);//打开离线登陆
	}
	/**
	 * login的按钮方法
	 * @param view
	 */
	public void toLogin(View view){

		if(Static_InfoApp.create().isshow()){
			checkNeedReset();//跳过验证直接进入
		}else{
			if(loginname.getText().toString().trim().equals("")||loginpassword.getText().toString().trim().equals("")){
				Toast.makeText(Activity_Login.this, "用户名或密码不能为空", Toast.LENGTH_LONG).show();
			}else{
				if(rememberpassword.isChecked()){

					sp.edit()
							.putString("autologin", "1")
							.putString("accountname", loginname.getText().toString().trim())
							.putString("userpassword", loginpassword.getText().toString().trim())
							.commit();
					
				}else{

					sp.edit()
					.putString("autologin", "0")
					.putString("accountname", loginname.getText().toString().trim())
					.putString("userpassword", loginpassword.getText().toString().trim())
					.commit();
				}

				Activity_Login.this.showWait();
				login();
			}
		}



	}
	public void toChangePersonType(){
    	Activity_Login.this.showWait("角色更换中");
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				Util_Sdk.initializeData(Activity_Login.this.getApplicationContext(),null);
				Help_DB.create(Activity_Login.this.getApplicationContext(),""+Static_InfoApp.create().getdbname()+"");
				Activity_Login.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						
						Activity_Login.this.dissmissWait();
					}
				});
				
			}
		}).start();
		
		
		
	}
	public void showChangePersonTypePass(){
		Dialog_Tool.showDialog_ChangePersonTypePass(this, null, new CallBack() {
			
			@Override
			public void back(Object resultlist) {
				if((int)resultlist==1){
					handler.sendEmptyMessage(R.string.changepersontypepass);
				}else{
					handler.sendEmptyMessage(R.string.changepersontypepass2);
				}
				
				
			}
		});
	}
	public void showChangePersonType(View view){
		showChangePersonTypePass();
	}
	public void showChangePersonType2(){
		Dialog_Tool.showDialog_ChangePersonType2(this, null, new CallBack() {
			
			@Override
			public void back(Object resultlist) {
				handler.sendEmptyMessage(R.string.changepersontype);
				
			}
		});
	}
	public void showChangePersonType(){
		Dialog_Tool.showDialog_ChangePersonType(this, null, new CallBack() {
			
			@Override
			public void back(Object resultlist) {
				handler.sendEmptyMessage(R.string.changepersontype);
				
			}
		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			showChangePersonTypePass();
		}
		return super.onKeyDown(keyCode, event);
	}
	/**
	 * 离线登陆按钮 一般隐藏
	 * @param view
	 */
	public void tooffinelogin(View view){
		Toast.makeText(this, "已经离线登录", Toast.LENGTH_LONG).show();
	}
//	@Override
//	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(arg0, arg1, arg2);
//
//			if(arg0==22){
//						checkapkisalreadyinstall();
//			}
//		}
	/**
	 * 从安装页面返回 看 用户是否已经安装
	 */
//	private void checkapkisalreadyinstall() {
//		if(printtrue==false){
//
//			if(Util_Apk.appIsInstalled13(Static_InfoApp.create().getContext(), Static_ConstantLib.pack)){//检测是否安装 没有则继续跳安装界面 已经安装完毕 则跳测试打印
//				File file = new File(Static_InfoApp.create().getPath()+"/TestPdf/"+"test.pdf");
//				Uri uri=Uri.fromFile(file);
//				Toast.makeText(Activity_Login.this, "测试打印", Toast.LENGTH_LONG).show();
//				Intent i = new Intent(Intent.ACTION_VIEW);
//				i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//				i.setPackage(Static_ConstantLib.pack);
//				i.setDataAndType(uri, "application/pdf");
//				Activity_Login.this.startActivity(i);
//				printtrue=true;
//			}
//			}else{
//				Util_Apk.appInstall13(Activity_Login.this, Static_ConstantLib.pack, Static_ConstantLib.apk);
//
//			}
//	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
	
}
