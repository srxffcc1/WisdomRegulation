package com.wisdomregulation.utils;

import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_Check;
import com.wisdomregulation.data.entityother.Entity_CheckDetail;
import com.wisdomregulation.help.Help_DB;

import java.util.List;

public class Util_Finish {
/**
 * 判断这个检查是否要复查
 * @param entity
 * @return
 */
public static boolean findCheckIsNeedAgain(final Object entity){
	final Base_Entity checkentity=(Base_Entity) entity;
	String qiyecode=checkentity.getValue(5);
	String checkid=checkentity.getId();
	final List<Base_Entity> dataillist=Util_MatchTip.getSearchResult(Help_DB.create().search(new Entity_CheckDetail().put(25, qiyecode).put(19, checkentity.getValue(0))));
	boolean againflag=false;
	boolean againflagall=false;
	String againtag="";
	String againcheckid="";
	againcheckid=Util_String.get16Uuid();
	
	for (int i = 0; i < dataillist.size(); i++) {
		String flagstring=dataillist.get(i).getValue(15);
		String iscomplete=dataillist.get(i).getValue(38);
		if(!flagstring.equals("3")){
			if(iscomplete.equals("1")){
				againflag=false;
			}else{
				//System.out.println("获得检查项目");
				againflagall=true;
				againflag=true;
			}

		}else{
			againflag=false;
		}
//		Base_Entity dangerous=new Entity_Dangerous().init().initId();
//		
//		dangerous
//		.put(32, dataillist.get(i).getValue(16))
//		
////		.put("隐患描述", resultdetaillist.get(i).getValue("隐患描述"))
////		.put("隐患级别", resultdetaillist.get(i).getValue("检查结果"))
////		.put("整改期限", resultdetaillist.get(i).getValue("整改期限"))
//		.put(9, "未完成")
//		.put(21, "政府抽查")
//		.put(27, checkentity.getValue(4))
//		.put(28, checkentity.getValue(5))
////		.put("隐患等级", resultdetaillist.get(i).getValue("检查结果"))
//		.put(25, dataillist.get(i).getId())
//		.put(26, dataillist.get(i).getClass().getSimpleName())
//		.put(1, againcheckid)
//		;
//		if(againflag){
//			dangerous.put(3, flagstring);
//		}else{
//			dangerous.put(3, "无隐患");
//		}
//		
//		Help_DB.create().save2update(dangerous);
		Help_DB.create().save2update(dataillist.get(i).put(20, againcheckid));
		
	}
	if(againflagall){
		
//		Base_Entity againcheck=new Entity_AgainCheck().init().setId(againcheckid);
//		againcheck.put(1, checkentity.getId())
//		.put(2, checkentity.getValue(4))
//		.put(3, checkentity.getValue(5))
//		.put(6, "不合格")
//		.put(4, checkentity.getValue(2))
//		.put(5, againtag)
//		;
		Help_DB.create().save2update(new Entity_Check().setId(checkid).put(16, "1").put(10, "0"));
	}else{
		
	}
	checkentity.init().setId(checkid).put(6, "1");
	Help_DB.create().save2update(checkentity);
	return againflagall;
}
/**
 * 判断这个检查是否可以结束 比如是不是有未完成的啊
 * @param entity
 * @return
 */
public static boolean findPlanCheckIsAllFinish(final Object entity){
	final Base_Entity planentity=(Base_Entity) entity;
	final List<Base_Entity> checklist=Util_MatchTip.getSearchResult(Help_DB.create().search(new Entity_Check().put(0, planentity.getId())));
	boolean againflagall=false;
	if(checklist.size()==0){
		againflagall=true;
	}
	for (int i = 0; i < checklist.size(); i++) {
		String tmp=checklist.get(i).getValue(6);
		if(!tmp.equals("1")){
			againflagall=true;
		}
	}
	return againflagall;
}
/**
 * 判断计划是否要复查
 * @param entity
 * @return
 */
public static boolean findPlanIsNeedAgain(final Object entity){
	final Base_Entity planentity=(Base_Entity) entity;
	final List<Base_Entity> checklist=Util_MatchTip.getSearchResult(Help_DB.create().search(new Entity_Check().put(0, planentity.getId())));
	boolean againflagall=false;
	for (int i = 0; i < checklist.size(); i++) {

		boolean tmp=findCheckIsNeedAgain(checklist.get(i));
		if(tmp){
			againflagall=true;
		}else{
			
		}
	}
	return againflagall;
}
}
