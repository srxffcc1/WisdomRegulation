package com.wisdomregulation.staticlib;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.Toast;

import com.wisdomregulation.utils.Util_Sdcard;

import java.io.File;

/**
 * app信息记录类
 * @author King2016s
 *
 */
public class Static_InfoApp {
	public static Bitmap head;
	private int privatekey = 2;//控制默认的app写入路劲
	private double AppScreenWidth;
	private double AppScreenHigh;
	private double ViewScreenWidth;
	private double ViewScreenHigh;
	private Context context;
	private Handler allhandler;
	public static final String city="石家庄市";
	private static final Static_InfoApp mInfoApp = new Static_InfoApp();
	private Static_InfoApp() {
	}

	public Handler getAllhandler() {
		return allhandler;
	}

	public Static_InfoApp setAllhandler(Handler allhandler) {
		this.allhandler = allhandler;
		return this;
	}

	public Context getContext() {
		return context;
	}

	public Static_InfoApp setContext(Context context) {
		this.context = context;
		return this;
	}

	/**
	 * 返回的可能不带/ 最好自己加 出来
	 * @return
	 */
	public String getPath() {
		String path="";
		SharedPreferences sd=getContext().getSharedPreferences("releaseconfig", Context.MODE_PRIVATE);
		path=sd.getString("sdpath", "");
		if(!path.equals("")){
			if(new File(path+"/").exists()){
				//System.out.println("全局路径1："+path);
				return path;
			}else{
				File tmp=new File(path+"/");
				path=Util_Sdcard.getBigSdCardPath(getContext(),Static_ConstantLib.projectdir,true)+Static_ConstantLib.projectdir+"/";
				sd.edit().putString("sdpath", path);
				//System.out.println("全局路径2："+path);
				return path;
			}
			
		}
		switch (privatekey) {
		case 1:
			path = context.getFilesDir().toString()+Static_ConstantLib.projectdir+"/";
			break;
		case 2:
			path=Util_Sdcard.getBigSdCardPath(getContext())+Static_ConstantLib.projectdir+"/";
			//System.out.println("需要构造的存储目录"+path);
			File tmp=new File(path);
			tmp.mkdirs();
			if(tmp.exists()){
				
			}else{
				
				path=Util_Sdcard.getBigSdCardPath(getContext(),tmp.getParentFile())+Static_ConstantLib.projectdir+"/";
				//System.out.println("目录不存在继续构造"+path);
			}
			break;

		default:
			break;
		}
		
		String tmppath=getContext().getSharedPreferences("releaseconfig", Context.MODE_PRIVATE).getString("sdpath", "");
		if(!tmppath.equals("")){
			sd.edit().putString("sdpath", path);
		}else{
			//System.out.println("全局路径4："+path);
		}
		//System.out.println("全局路径3："+path);
		File filetest=new File(path+"/test.txt");
		if(!filetest.getParentFile().exists()){
			filetest.getParentFile().mkdirs();
		}
		return path;
	}
	/**
	 * 用于标记是离线执法的无网络登录否
	 * @return
	 */
	public String getSpecialClient(){
		String result="";
		SharedPreferences sd=getContext().getSharedPreferences("releaseconfig", Context.MODE_PRIVATE);
		result=sd.getString("Specialclient", "");
		if(result.equals("Specialclient")){
			
		}else{
			result="client";
		}
		return result;
	}
	public String getUserAuthority(){//用户权限
		String result="";
		SharedPreferences sd=getContext().getSharedPreferences("releaseconfig", Context.MODE_PRIVATE);
		result=sd.getString("userauthor", "");
		if(!result.equals("")){

		}else{
			result="";
		}
		return result;
	}
	public String getUserRole(){//用户角色 用来判断登录人员是企业还是政府
		String result="";
		SharedPreferences sd=getContext().getSharedPreferences("releaseconfig", Context.MODE_PRIVATE);
		result=sd.getString("userrole", "");
		if(!result.equals("")){

		}else{
			result="";
		}
		return result;
	}
	public String getAccountId(){
		String result="";
		SharedPreferences sd=getContext().getSharedPreferences("releaseconfig", Context.MODE_PRIVATE);
		result=sd.getString("accountid", "");
		if(!result.equals("")){
			
		}else{
			result="";
		}
		return result;
	}
	/**
	 * 将此作为参数放入
	 * @return
	 */
	public String getAccountNameOnly(){
		String result="";
		SharedPreferences sd=getContext().getSharedPreferences("releaseconfig", Context.MODE_PRIVATE);
		result=sd.getString("accountname", "");
		if(result.equals("")||result.equals("管理员")){
			result="";
		}else{
			
		}
		if(Static_InfoApp.create().isshow()){
			result="";
		}else{
			
		}
		return result;
	}
	public String getUserName(){
		String result="";
		SharedPreferences sd=getContext().getSharedPreferences("releaseconfig", Context.MODE_PRIVATE);
		result=sd.getString("username", "");
		if(!result.equals("")){

		}else{
			result="XXX";
		}
		return result;
	}
	public String getAccountOrg(){
		String result="";
		SharedPreferences sd=getContext().getSharedPreferences("releaseconfig", Context.MODE_PRIVATE);
		result=sd.getString("accountorg", "XXX");
		if(!result.equals("")){

		}else{
			result="XXX";
		}
		return result;
	}
	public String getAccountName(){
		String result="";
		SharedPreferences sd=getContext().getSharedPreferences("releaseconfig", Context.MODE_PRIVATE);
		result=sd.getString("accountname", "");
		if(!result.equals("")){

		}else{
			result="XXX";
		}
		return result;
	}
	/**
	 * 获得登陆者的主管部门id
	 * @return
	 */
	public String getAccountDept(){
		String result="";
		SharedPreferences sd=getContext().getSharedPreferences("releaseconfig", Context.MODE_PRIVATE);
		result=sd.getString("accountdept", "");
		if(result.equals("")||result.equals("管理员")){
			result="";
		}else{
			
		}
		if(result.matches("(.*)安委办")){
			result=result.replace("安委办", "安监局");
		}else{
			
		}
		return result;
	}
	public String getAccountDeptOnly(){
		String result="";
		SharedPreferences sd=getContext().getSharedPreferences("releaseconfig", Context.MODE_PRIVATE);
		result=sd.getString("accountdept", "");
		if(!result.equals("")){
			
		}else{
			result="";
		}
		return result;
	}
	public  boolean isApkDebugable() {
		try {
			ApplicationInfo info= context.getApplicationInfo();
			return (info.flags&ApplicationInfo.FLAG_DEBUGGABLE)!=0;
		} catch (Exception e) {

		}
		return false;
	}


	/**
	 *
	 * @param istest 是否测试
	 * @param isshow 是否演示
	 * @param ismarker 是否程序员
	 * @param isnoweb 是否离线
	 * @param isspecailgov 是否特殊
	 */
	public void setSetting(boolean istest,boolean isshow,boolean ismarker,boolean isnoweb,boolean isspecailgov){
		SharedPreferences sd=getContext().getSharedPreferences("releaseconfig", Context.MODE_PRIVATE);
		sd.edit()
				.putBoolean("istest",istest)
				.putBoolean("isshow",isshow)
				.putBoolean("ismarker",ismarker)
				.putBoolean("isnoweb",isnoweb)
				.putBoolean("isspecailgov",isspecailgov)
				.commit();

	}
	public boolean istest(){//线上库还是本地库标志位
		SharedPreferences sd=getContext().getSharedPreferences("releaseconfig", Context.MODE_PRIVATE);
		return sd.getBoolean("istest", false);
	}
	public boolean isshow(){//演示标志位
		SharedPreferences sd=getContext().getSharedPreferences("releaseconfig", Context.MODE_PRIVATE);
		return sd.getBoolean("isshow", false);
	}
	public boolean ismarker(){//是否程序员调试
		SharedPreferences sd=getContext().getSharedPreferences("releaseconfig", Context.MODE_PRIVATE);
		return sd.getBoolean("ismarker", false);
	}
	public boolean isnoweb(){//是否离线模式 允许对比本地人员库进行登录
		SharedPreferences sd=getContext().getSharedPreferences("releaseconfig", Context.MODE_PRIVATE);
		return sd.getBoolean("isnoweb", false);
	}
	public boolean isspecailgov(){//是否特殊政府模式-应用于一些紧急demo 设置此模式将不判断检查区域 进项全部展示
		SharedPreferences sd=getContext().getSharedPreferences("releaseconfig", Context.MODE_PRIVATE);
		return sd.getBoolean("isspecailgov", false);
	}
	public String getdbname(){
		if(istest()){
			return "debuganjian.db";
		}else{
		return "releaseanjian.db";
		}
	}
	public String getiphead(){
		//System.out.println();
		SharedPreferences sd=getContext().getSharedPreferences("releaseconfig", Context.MODE_PRIVATE);
		//System.out.println("连接的ip:"+sd.getString("ip", "http://221.192.132.39:8001/sjzzhaj/"));
		if(istest()){
			return "http://10.0.0.219:8080/sjzzhaj/";
		}else{
		return "http://221.192.132.39:8001/sjzzhaj/";
		}
	}
	public static Static_InfoApp create() {
		return mInfoApp;
	}

	public double getAppScreenWidth() {
		return AppScreenWidth;
	}

	public Static_InfoApp setAppScreenWidth(double width) {
		this.AppScreenWidth = width;
		return this;
	}
	public Static_InfoApp showToast(String string){
		Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
		return this;
	}
	public double getAppScreenHigh() {
		return AppScreenHigh;
	}

	public Static_InfoApp setAppScreenHigh(double high) {
		this.AppScreenHigh = high;
		return this;
	}

	public double getViewScreenWidth() {
		return ViewScreenWidth;
	}

	public Static_InfoApp setViewScreenWidth(double viewScreenWidth) {
		ViewScreenWidth = viewScreenWidth;
		return this;
	}

	public double getViewScreenHigh() {
		return ViewScreenHigh;
	}

	public Static_InfoApp setViewScreenHigh(double viewScreenHigh) {
		ViewScreenHigh = viewScreenHigh;
		return this;
	}

}
