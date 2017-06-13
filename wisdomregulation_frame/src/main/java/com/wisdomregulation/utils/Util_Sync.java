package com.wisdomregulation.utils;

import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_InfoApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util_Sync {
	static Map<String,String> tablemap=new HashMap<String, String>();
	static Map<String,String> entity_bigplan2tb_enforcementPlan=new HashMap<String, String>();
	static Map<String,String> entity_plan2tb_enforcementArea=new HashMap<String, String>();
	static Map<String,String> entity_check2tb_enforcement=new HashMap<String, String>();
	static Map<String,String> entity_checkdetail2tb_yinhuanxinxi=new HashMap<String, String>();
	static Map<String,String> entity_law2tb_xingzhengzhifa=new HashMap<String, String>();
	static Map<String,String> entity_company2tb_enterprise=new HashMap<String, String>();
	static {

		tablemap.clear();
		tablemap.put("entity_bigplan", "tb_enforcement_plan");
		tablemap.put("entity_plan", "tb_enforcement_area");
		tablemap.put("entity_check", "tb_enforcement");
		tablemap.put("entity_checkdetail", "tb_yinhuanxinxi");
		tablemap.put("entity_law", "tb_xingzhengzhifa");
		tablemap.put("entity_company", "tb_enterprise");
		
		entity_bigplan2tb_enforcementPlan.clear();
		entity_bigplan2tb_enforcementPlan.put("fuzeren", "PLAN_FZRBH");
		entity_bigplan2tb_enforcementPlan.put("fuzedanweimingcheng", "PLAN_FZDW");
		entity_bigplan2tb_enforcementPlan.put("jianchaleibie", "PLAN_LEIB");
		entity_bigplan2tb_enforcementPlan.put("jihuazhuangtai", "PLAN_STATUS");
		entity_bigplan2tb_enforcementPlan.put("fuzerenmingcheng", "PLAN_FZR");
		entity_bigplan2tb_enforcementPlan.put("zhifajihuamingcheng", "PLAN_NAME");
		entity_bigplan2tb_enforcementPlan.put("jianchashijianzhi", "PLAN_TIMEE");
		entity_bigplan2tb_enforcementPlan.put("jianchashijianqi", "PLAN_TIMES");
		entity_bigplan2tb_enforcementPlan.put("fuzedanwei", "PLAN_FZDWBH");
		entity_bigplan2tb_enforcementPlan.put("zhifajihualeixing", "PLAN_TYPE");
		
		entity_plan2tb_enforcementArea.clear();
		entity_plan2tb_enforcementArea.put("shifouwancheng", "ISCOMPLETE");
		entity_plan2tb_enforcementArea.put("jiancharen", "CHECK_PERSON");
		entity_plan2tb_enforcementArea.put("jianchashijian", "CHECK_TIME");
		entity_plan2tb_enforcementArea.put("suoshujihuaid", "PLAN_ID");
		entity_plan2tb_enforcementArea.put("jianchaquyu", "CHECK_AREA");
		entity_plan2tb_enforcementArea.put("jianchaleixing", "TYPE");
		entity_plan2tb_enforcementArea.put("jiancharenmingzi", "CHECK_PERSONNAME");
		
		
		entity_check2tb_enforcement.clear();
		entity_check2tb_enforcement.put("shifoushijihuaneide", "isplan");
		entity_check2tb_enforcement.put("shifouwancheng", "IS_COMPLETE");
		entity_check2tb_enforcement.put("jiancharen", "CHECK_PERSON");
		entity_check2tb_enforcement.put("suoshujihua", "AREA_ID");
		entity_check2tb_enforcement.put("qiyemingcheng", "ENT_NAME");
		entity_check2tb_enforcement.put("jianchaxiangmuid", "CHECK_ITEM_ID");
		entity_check2tb_enforcement.put("jianchaxiangmumingcheng", "CHECK_ITEM_NAME");
		entity_check2tb_enforcement.put("jiancharenzhanghao", "CHECK_PERSON_ACCOUNT");
		entity_check2tb_enforcement.put("qiyeid", "ENT_ID");
		entity_check2tb_enforcement.put("fuchazhuangtai", "COMPLETE_AGAIN");
		entity_check2tb_enforcement.put("jianchariqi", "CHECK_DATE");
		entity_check2tb_enforcement.put(Util_String.getchinese2pinyin("是否要复查"), "ISNEEDAGAIN"); //增加的字段用于检测判断是否要复查
		
		entity_checkdetail2tb_yinhuanxinxi.clear();
		entity_checkdetail2tb_yinhuanxinxi.put("yinhuanzhenggaihoutupian", "ZHENGAIHOUTU");
		entity_checkdetail2tb_yinhuanxinxi.put("shangbaoshijian", "SHANGBAOTIME");
		entity_checkdetail2tb_yinhuanxinxi.put("guanlianjianchaxiang", "CHECKITEM");
		entity_checkdetail2tb_yinhuanxinxi.put("yinhuanleixing", "YINHUANLIE");
		entity_checkdetail2tb_yinhuanxinxi.put("shifoushangbao", "ISSHANGBAO");
		entity_checkdetail2tb_yinhuanxinxi.put("zhenggailuoshizijin", "ZHENGAIZIJIN");
		entity_checkdetail2tb_yinhuanxinxi.put("zhenggaiwanchengshijian", "ZHENGAITIME");
		entity_checkdetail2tb_yinhuanxinxi.put("jiancharenyuan", "CHECKPEOPLE");
		entity_checkdetail2tb_yinhuanxinxi.put("shangcifuchashijian", "UPFUCHATIME");
		entity_checkdetail2tb_yinhuanxinxi.put("zhenggaifangan", "ZHENGAIFANGAN");
		entity_checkdetail2tb_yinhuanxinxi.put("jianchashijian", "CHECKTIME");
		entity_checkdetail2tb_yinhuanxinxi.put("biaozhun", "BIAOZHUN");
		entity_checkdetail2tb_yinhuanxinxi.put("fuchamiaoshu", "ISXIAOCUYINHUAN");
		entity_checkdetail2tb_yinhuanxinxi.put("shifouzhenggaiwancheng", "ISWANCHENZHENGAI");
		entity_checkdetail2tb_yinhuanxinxi.put("yinhuanzhenggaiqiantupian", "YINHUANTU");
		entity_checkdetail2tb_yinhuanxinxi.put("suoshuqiye", "OWNENT");
		entity_checkdetail2tb_yinhuanxinxi.put("suoshujihua", "OWNPLAN");
		entity_checkdetail2tb_yinhuanxinxi.put("yinhuanjibie", "YINHUANJIEBIE");
		entity_checkdetail2tb_yinhuanxinxi.put("yinhuanmiaoshu", "YINHUANINFO");
		entity_checkdetail2tb_yinhuanxinxi.put("zhenggairen", "ZHENGAIPEOPLE");
		entity_checkdetail2tb_yinhuanxinxi.put("zhenggaicuoshi", "ZHENGAICUOSHI");
		
		entity_law2tb_xingzhengzhifa.clear();
		entity_law2tb_xingzhengzhifa.put("beijianchadanweiid", "BeiJianChaId");
		entity_law2tb_xingzhengzhifa.put("yijiaoren", "YIJIAOREN");
		entity_law2tb_xingzhengzhifa.put("jiancharen", "JianChaRen");
		entity_law2tb_xingzhengzhifa.put("fuchaqingkuang", "FUCHAQIN");
		entity_law2tb_xingzhengzhifa.put("anjianlaiyuan", "AnJianLaiY");
		entity_law2tb_xingzhengzhifa.put("dangqianjieduan", "STEP");
		entity_law2tb_xingzhengzhifa.put("anjianmingcheng", "NAME");
		entity_law2tb_xingzhengzhifa.put("jianchashijian", "JianChaShi");
		entity_law2tb_xingzhengzhifa.put("wenshuleixing", "WenShuLeiX");
		entity_law2tb_xingzhengzhifa.put("wenshumingcheng", "WenShuMing");
		entity_law2tb_xingzhengzhifa.put("jiancharenlianxifangshi", "jianChaRenContact");
		entity_law2tb_xingzhengzhifa.put("jianchaqingkuang", "JianChaQin");
		entity_law2tb_xingzhengzhifa.put("beijianchadanwei", "BeiJianCha");
		entity_law2tb_xingzhengzhifa.put("jianchajiluid", "ZhiFaJiLuI");
		
		entity_company2tb_enterprise.clear();
		entity_company2tb_enterprise.put("shengchanjingyingdizhi", "SCJYDZ");
		entity_company2tb_enterprise.put("shifouyoulishujituan", "sfylsjt");
		entity_company2tb_enterprise.put("jingyingfanwei", "JYFW");
		entity_company2tb_enterprise.put("qiyeweizhijingdu", "QYWZJD");
		entity_company2tb_enterprise.put("zhuyaofuzeren", "ZYFZR");
		entity_company2tb_enterprise.put("zhuyaofuzerengudingdianhua", "ZYFZRGDDH");
		entity_company2tb_enterprise.put("suozaiquxian", "XZQHQX");
		entity_company2tb_enterprise.put("youzhengbianma", "YZBM");
		entity_company2tb_enterprise.put("anquanfuzerendianziyouxiang", "AQFZRDZYX");
		entity_company2tb_enterprise.put("anquanfuzerenyidongdianhua", "AQFZRYDDH");
		entity_company2tb_enterprise.put("tezhongzuoyerenyuanshuliang", "TZZYRYSL");
		entity_company2tb_enterprise.put("zhuceanquangongchengshirenyuanshuliang", "ZCAQGCSRYS");
		entity_company2tb_enterprise.put("jianguanxingyexiaolei", "JGHYXL");
		entity_company2tb_enterprise.put("dianziyouxiang", "DZYX");
		entity_company2tb_enterprise.put("zhuanxiangjianguanxiaolei", "zxjghyb");
		entity_company2tb_enterprise.put("zhucedizhi", "ZCDZ");
		entity_company2tb_enterprise.put("zhuanxiangjianguandalei", "zxjghya");
		entity_company2tb_enterprise.put("zhuyaofuzerendianziyouxiang", "ZYFZRDZYX");
		entity_company2tb_enterprise.put("suozaijiedao", "XZQHJD");
		entity_company2tb_enterprise.put("qiyemingcheng", "QYMC");
		entity_company2tb_enterprise.put("xingzhengquhuasheng", "XZQHS");
		entity_company2tb_enterprise.put("lishuguanxi", "LSGX");
		entity_company2tb_enterprise.put("gongshangzhucehao", "GSZCH");
		entity_company2tb_enterprise.put("qiyeweizhiweidu", "QYWZWD");
		entity_company2tb_enterprise.put("guimoqingkuang", "GMQK");
		entity_company2tb_enterprise.put("jianguanfenlei", "JGFL");
		entity_company2tb_enterprise.put("zhuyaofuzerenyidongdianhua", "ZYFZRYDDH");
		entity_company2tb_enterprise.put("anquanfuzerengudingdianhua", "AQFZRGDDH");
		entity_company2tb_enterprise.put("xingyeleibie", "HYLB");
		entity_company2tb_enterprise.put("qiyeguimo", "QYGM");
		entity_company2tb_enterprise.put("xingyeleibiexiaolei", "hylbxl");
		entity_company2tb_enterprise.put("jingjileixingxiaolei", "jjlxxl");
		entity_company2tb_enterprise.put("fadingdaibiaoren", "FDDBR");
		entity_company2tb_enterprise.put("qiyezipingdengji", "QYZPDJ");
		entity_company2tb_enterprise.put("zuzhijigoudaima", "ZZJGDM");
		entity_company2tb_enterprise.put("zhuanzhianquanshengchanguanlirenyuanshuliang", "ZZAQSCGLRYSL");
		entity_company2tb_enterprise.put("jingjileixing", "JJLX");
		entity_company2tb_enterprise.put("xingzhengquhuashi", "XZQH");
		entity_company2tb_enterprise.put("zhucezijin（wan）", "ZCZJ");
		entity_company2tb_enterprise.put("shifoushijituan", "clique");
		entity_company2tb_enterprise.put("lianxidianhua", "LXDH");
		entity_company2tb_enterprise.put("lishujituanmingcheng", "lsjtmc");
		entity_company2tb_enterprise.put("chengliriqi", "CLRQ");
		entity_company2tb_enterprise.put("anquanfuzeren", "AQFZR");
		entity_company2tb_enterprise.put("congyerenyuanshuliang", "CYRYSL");
		entity_company2tb_enterprise.put("zhuanxiangzhilileixing", "zxzllx");

	}
	/**
	 * 本地数据库名字转为线上数据库名字
	 * @param clienttablename
	 * @return
	 */
	public static String client2serverTableName(String clienttablename){
		String servertablename="";
				servertablename=tablemap.get(clienttablename);
		if(servertablename==null){
			servertablename=clienttablename;
		}else{
			
		}
		
		return servertablename;
	}
	/**
	 * 本地数据库字段转为线上数据库字段
	 * @param servertablename
	 * @param clientfield
	 * @return
	 */
	public static String client2serverField(String servertablename,String clientfield){

		String serverfield="";
		if(servertablename.equals("entity_deletehistory")){		
			serverfield=client2serverTableName(clientfield);
		}
		else if(servertablename.equals("tb_enforcement_plan")){
			serverfield=entity_bigplan2tb_enforcementPlan.get(clientfield);

		}
		else if(servertablename.equals("tb_enforcement_area")){
			serverfield=entity_plan2tb_enforcementArea.get(clientfield);
		}
		else if(servertablename.equals("tb_enforcement")){
			serverfield=entity_check2tb_enforcement.get(clientfield);
		}
		else if(servertablename.equals("tb_yinhuanxinxi")){
			serverfield=entity_checkdetail2tb_yinhuanxinxi.get(clientfield);
		}
		else if(servertablename.equals("tb_xingzhengzhifa")){
			serverfield=entity_law2tb_xingzhengzhifa.get(clientfield);
		}
		else if(servertablename.equals("tb_enterprise")){
			serverfield=entity_company2tb_enterprise.get(clientfield);
		}
		else{
			serverfield=clientfield;
		}
		if(serverfield!=null){
			
		}else{
			serverfield=clientfield;
		}
		return serverfield;
	}
/**
 * 获得所有所有同步转为json
 * @return
 */
	
public static String getAllSyncJson(){
	String array="";
	List<String>  list=Help_DB.create().getSyncJson(null,null);
	for (int i = 0; i < list.size(); i++) {
		array=array+list.get(i)+",";
	}
	if(array.length()>1){
	array=array.substring(0, array.length()-1);
//	array="{"+"\"data\":"+"["+array+"]"+"}";

	array="["+array+"]";
	}
//	for (int i = 0; i < list.size(); i++) {
//	}
	array=array.replace("\n", "");//鉴于换行符会导致同步问题 所以去掉了 后期进行约定
	com.wisdomregulation.utils.Log.writeLogSd(array);
	return array;
}
/**
 * 获得带有client的后面的升级字段
 * @param datastate
 * @return
 */
public static List<String> getclientlist(String datastate){
	List<String> newuotablename=new ArrayList<String>();
	if(datastate.matches("(.*),(.*)")){
		String[] array=datastate.split(",");
		for (int i = 1; i < array.length; i++) {
			String tmp=new String();
			tmp=array[i].trim();
			newuotablename.add(tmp);
		}
	}
	return newuotablename;
}
/**
 * 将升级字段记录于client后面
 * @param uptablename
 * @param oldclientstring
 * @return
 */
public static String addclientstring(List<String> uptablename,String oldclientstring){
	String result=oldclientstring;
	List<String> upoldtablename=new ArrayList<String>();
	List<String> newuotablename=new ArrayList<String>();
	if(oldclientstring.matches("(.*),(.*)")){
		result="";
		String[] array=oldclientstring.split(",");
		for (int i = 1; i < array.length; i++) {
			upoldtablename.add(array[i].trim());
		}
		if(upoldtablename.size()>0){
			newuotablename=upoldtablename;
			for (int i = 0; i < uptablename.size(); i++) {
				if(!upoldtablename.contains(uptablename.get(i))){
					String tmp=new String();
					tmp=uptablename.get(i);
					newuotablename.add(tmp);
				}
			}
		}else{
			newuotablename=uptablename;
		}
		for (int i = 0; i < newuotablename.size(); i++) {
			result=result+newuotablename.get(i)+",";
		}
		if(result.length()>1){
			result=result.substring(0, result.length()-1);
		}
		
		result=Static_InfoApp.create().getSpecialClient()+","+result;
	}else{
		result="";
		for (int i = 0; i < uptablename.size(); i++) {
			result=result+uptablename.get(i)+",";
		}
		if(result.length()>0){
		result=result.substring(0, result.length()-1);
		}
		result=Static_InfoApp.create().getSpecialClient()+","+result;
	}
	return result;
}

}
