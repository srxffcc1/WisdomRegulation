package com.wisdomregulation.staticlib;



/**
 * 静态字段类
 * @author King2016s
 *
 */
public class Static_ConstantLib {
	public static String serviceip="http://192.168.2.106:8080/ZhiHuiAnJianUpdateAPK";//线上交互地址 已经废弃 前期测试本机调试使用
	public static final int sqllengthlimit=2000;
	public static final String projectdir="/android/data/com.wisdomregulation/files/.ZhiZZ";
//	public static boolean istest=true;//给测试组测试的时候设为true
//	public static boolean isshow=true; //演示的时候设为true
//	public static boolean ismarker=false; //程序员测试的时候设为true
	
//	public static final String dbname="anjian.db";//正式版数据库
	
//	public static final String dbname="anjiandebug.db";//演示版版数据库
	public static String apk="PrinterShare_Crack";
//	public static String pack="com.dynamixsoftware.printershare.amazon";
	public static String pack="com.dynamixsoftware.printershare";
	public static final String AddBookOld = "Static_ConstantLib.AddBookOld";
	public static final String AddBookNew = "Static_ConstantLib.AddBookNew";
	public static final String PrintBook = "Static_ConstantLib.PrintBook";
	public static final String DeleteBook = "Static_ConstantLib.DeleteBook";
//	public static String iphead="http://10.0.0.95:8080/sjzzhaj/";//本地交互地址头
//	public static String iphead="http://221.192.132.39:8001/sjzzhaj/";//线上交互地址头
	

	public static String syncurl="login!sjdsjtb";//线上交互地址头   login!sjdsjtb?json参数json
	
	public static String bigplanurl="enforcementPlan/enforcementPlanAction!listAll";//bigplan表
	public static String smallplanurl="enforcementArea/enforcementAreaAction!listAll";//plan表
	public static String checkurl="enforcement/enforcementAction!listAll";//check表
	public static String checkoptionurl="yinHuanXinXi/yinHuanXinXiAction!listAll";//checkoption表
	public static String lawurl="xingZhengZhiFa/xingZhengZhiFaAction!listAll";//law表
	
	public static String companyurl="enterprise/enterpriseAction!listAll";//enterprise表 参数createTime不要一次写两个参数updateTime   无参数就是请求所有 没有参数就是所有 分开请求 第一次请求create 得到的数据直接插入 第二次请求 uodate的 得到的数据 对元数据进行一次判断性质的删除 再插入
	public static String saveproducebase="criterion/criKnowlbaseAction!listAll";//参数同上问题criterion  安全生产标准库
	public static String dangerousbase="tbMsds/tbMsdsAction!listAll";//参数同上问题?tbMsds   危险品化学特征库   
	public static String lawdependencebase="zhiFaYiJuKu/zhiFaYiJuKuAction!listAll";//参数同上问题?zhiFaYiJuKu  执法依据库
	public static String casespecialbase="accidentCase/accKnowlbaseAction!listAll";//参数同上问题?accidentCase 典型案例
	public static String savelawbase="law/lawKnowlbaseAction!listAll";//参数同上问题?law   安全生产标注库
	
	
//	public static String loginurl="login!login";//login  username= password= checkType=gov; 登陆参数
	public static String loginurl="login!loginApps.action";//login  username= password= checkType=gov; 登陆参数
	public static String upload="uploadphone/upload";//上传文件 传入id 和 文件内容
	/**
	 * 获得ip头
	 * @return
	 */
//	public static  String getiphead(){
//		if(istest){
//			return "http://10.0.0.96:8080/";
//		}else{
//		return "http://221.192.132.39:8001/sjzzhaj/";
////			return "http://10.0.0.233:8080/";
//		}
//	}
	/**
	 * 获得数据库名字
	 * @return
	 */
//	public static  String getdbname(){
//		if(istest){
//			return "debuganjian.db";
//		}else{
//		return "releaseanjian.db";
//		}
//	}
	
	
	
	public static String chartcompanycount="enterprise/enterpriseAction!calcPercen";//type=xzqhqx 企业数量
	public static String chartcompanytype="enterprise/enterpriseAction!hylb";//type=xzqhqx 企业类别
	public static String chartsmalldangerousinfo="qyzczb/qyzczbAction!getYhxx";//type=xzqhqx 隐患统计
	public static final double TEXT_NORMAL = 44;
	public static final double TEXT_NORMAL2 = 37;
	public static final double TEXT_NORMAL3 = 25;
	public static final double TEXT_BIG = 19;
	public static final double TEXT_SMALL = 57;
	public static final int anjiandbversion =1;
	public static final String companyduty = "临时_";
	public static final String planduty = "计划_";
}
