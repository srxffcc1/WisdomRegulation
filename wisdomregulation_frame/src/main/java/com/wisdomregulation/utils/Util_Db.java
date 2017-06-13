package com.wisdomregulation.utils;

import android.content.Intent;

import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_BookList;
import com.wisdomregulation.data.entityother.Entity_Check;
import com.wisdomregulation.data.entityother.Entity_CheckDetail;
import com.wisdomregulation.data.entityother.Entity_Law;
import com.wisdomregulation.data.entityother.Entity_OrgData;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.helporg2017.Pdf_Shark2017;
import com.wisdomregulation.staticlib.Static_InfoApp;

import java.util.ArrayList;
import java.util.List;

/**
 * db的关联删除
 * @author King2016s
 *
 */
public class Util_Db {
	/**
	 * 关联删除的泛类自动识别顶层
	 * @param entity
	 */
	public static void deleteFix(Base_Entity entity){
		if(entity.getClass().getSimpleName().equals("Entity_Plan")){
			com.wisdomregulation.utils.Log.v("DeleteTag", "1");
			deleteFixPlan(entity);
		}
		if(entity.getClass().getSimpleName().equals("Entity_Check")){
			com.wisdomregulation.utils.Log.v("DeleteTag", "2");
			deleteFixCheck(entity);
		}
		if(entity.getClass().getSimpleName().equals("Entity_AgainCheck")){
			com.wisdomregulation.utils.Log.v("DeleteTag", "3");
			deleteFixAgainCheck(entity);
		}
		if(entity.getClass().getSimpleName().equals("Entity_BookList")){
			com.wisdomregulation.utils.Log.v("DeleteTag", "4");
			deleteFixBookList(entity);
		}
		if(entity.getClass().getSimpleName().equals("Entity_Law")){
			com.wisdomregulation.utils.Log.v("DeleteTag", "5");
			deleteFixLaw(entity);
		}
		if(entity.getClass().getSimpleName().equals("Entity_CheckHealthResult")||entity.getClass().getSimpleName().equals("Entity_CheckSaveResult")){
			com.wisdomregulation.utils.Log.v("DeleteTag", "6");
			deleteFixCheckDetail(entity);
		}
	}
	
	/**
	 * 关联删除计划
	 * @param plan
	 */
	private static void deleteFixPlan(Base_Entity plan){
		if(plan!=null){
			Help_DB.create().delete(plan);
			List<Base_Entity> checklist=Util_MatchTip.getSearchResult(Help_DB.create().search(new Entity_Check().init().put(0,plan.getId())));
			for (int i = 0; i < checklist.size(); i++) {

				deleteFixCheck(checklist.get(i));
			}
		}
	}
	/**
	 * 关联删除检查
	 * @param check
	 */
	private static void deleteFixCheck(Base_Entity check){
		if(check!=null){
			List<Base_Entity> booklist=Util_MatchTip.getSearchResult(Help_DB.create().search(new Entity_BookList().init().put(0,check.getId())));
			for (int i = 0; i < booklist.size(); i++) {
				deleteFixBookList(booklist.get(i));
			}

			String checktype=check.getValue(2);
			Base_Entity searchresult=new Entity_CheckDetail().clear().put(19, check.getId());

			
			deleteFixCheckDetail(searchresult);
			com.wisdomregulation.utils.Log.v("DeleteTag", "22");
			Help_DB.create().delete(check);
			
		}

	}
	
	/**
	 * 关联删除检查项
	 * @param result
	 */
	private static void deleteFixCheckDetail(Base_Entity result){
		if(result!=null){
			
			List<Base_Entity> detaillist=Util_MatchTip.getSearchResult(Help_DB.create().search(result));
			for (int i = 0; i < detaillist.size(); i++) {
//				Help_DB.create().deleteFix(new Entity_Dangerous().init().put(25, detaillist.get(i).getId()));
				Help_DB.create().delete(detaillist.get(i));
			}
//			Help_DB.create().deleteFix(result);
			
		}
		


	}

	/**
	 * 关联删除复查
	 * @param again
	 */
	private static void deleteFixAgainCheck(Base_Entity again){
		if(again!=null){
			Help_DB.create().delete(again);
			List<Base_Entity> booklist=Util_MatchTip.getSearchResult(Help_DB.create().search(new Entity_BookList().init().put(0,again.getValue(1))));
			for (int i = 0; i < booklist.size(); i++) {
				deleteFixBookList(booklist.get(i));
			}
			Base_Entity law=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().search(new Entity_Law().init().put(3, again.getValue(1))));
			deleteFixLaw(law);
			
		}


	}
	
	/**
	 * 关联删除执法
	 * @param law
	 */
	private static void deleteFixLaw(Base_Entity law){
		if(law!=null){
			Help_DB.create().delete(law);
			List<Base_Entity> booklist=Util_MatchTip.getSearchResult(Help_DB.create().search(new Entity_BookList().init().put(2,law.getId())));
			for (int i = 0; i < booklist.size(); i++) {
				deleteFixBookList(booklist.get(i));
			}
			
		}

	}
	
	/**
	 * 关联删除文书
	 * @param booklist
	 */
	private static void deleteFixBookList(Base_Entity booklist){
		if(booklist!=null){
			Help_DB.create().delete(booklist);
			if(!booklist.getValue(6).equals("")&&!booklist.getValue(6).equals(" ")){
				Base_Entity needdeleteFixsingle= Pdf_Shark2017.getEneityBook(booklist.getValue(4)).setId(booklist.getValue(6));
				Help_DB.create().delete(needdeleteFixsingle);
			}

			
		}

	}
	/**
	 * 带删除之后发送广播的关联删除泛方法
	 * @param entity
	 * @param broadcast
	 */
	public static void deleteFix(Base_Entity entity,Intent broadcast){
		if(entity.getClass().getSimpleName().equals("Entity_Plan")){
			com.wisdomregulation.utils.Log.v("DeleteTag", "1");
			deleteFixPlan(entity,broadcast);
		}
		if(entity.getClass().getSimpleName().equals("Entity_Check")){
			com.wisdomregulation.utils.Log.v("DeleteTag", "2");
			deleteFixCheck(entity,broadcast);
		}
		if(entity.getClass().getSimpleName().equals("Entity_AgainCheck")){
			com.wisdomregulation.utils.Log.v("DeleteTag", "3");
			deleteFixAgainCheck(entity,broadcast);
		}
		if(entity.getClass().getSimpleName().equals("Entity_BookList")){
			com.wisdomregulation.utils.Log.v("DeleteTag", "4");
			deleteFixBookList(entity,broadcast);
		}
		if(entity.getClass().getSimpleName().equals("Entity_Law")){
			com.wisdomregulation.utils.Log.v("DeleteTag", "5");
			deleteFixLaw(entity,broadcast);
		}
		if(entity.getClass().getSimpleName().equals("Entity_CheckHealthResult")||entity.getClass().getSimpleName().equals("Entity_CheckSaveResult")){
			com.wisdomregulation.utils.Log.v("DeleteTag", "6");
			deleteFixCheckDetail(entity,broadcast);
		}
	}
	/**
	 * 关联删除计划带结束广播
	 * @param plan
	 * @param broadcast
	 */
	private static void deleteFixPlan(Base_Entity plan,Intent broadcast){
		if(plan!=null){
			Help_DB.create().delete(plan);
			Static_InfoApp.create().getContext().sendBroadcast(broadcast);
			List<Base_Entity> checklist=Util_MatchTip.getSearchResult(Help_DB.create().search(new Entity_Check().init().put(0,plan.getId())));
			for (int i = 0; i < checklist.size(); i++) {

				deleteFixCheck(checklist.get(i));
			}
		}
	}
	/**
	 * 关联删除检查带结束广播
	 * @param check
	 * @param broadcast
	 */
	private static void deleteFixCheck(Base_Entity check,Intent broadcast){
		if(check!=null){
			Help_DB.create().delete(check);
			Static_InfoApp.create().getContext().sendBroadcast(broadcast);
			List<Base_Entity> booklist=Util_MatchTip.getSearchResult(Help_DB.create().search(new Entity_BookList().init().put(0,check.getId())));
			for (int i = 0; i < booklist.size(); i++) {
				deleteFixBookList(booklist.get(i));
			}

			String checktype=check.getValue(2);
			Base_Entity searchresult=new Entity_CheckDetail().clear().put(19, check.getId());

			
			deleteFixCheckDetail(searchresult,broadcast);
			com.wisdomregulation.utils.Log.v("DeleteTag", "22");
			Help_DB.create().delete(check);

			
		}

	}
	
	/**
	 * 关联删除检查项具体内容带结束广播
	 * @param result
	 * @param broadcast
	 */
	private static void deleteFixCheckDetail(Base_Entity result,Intent broadcast){
		if(result!=null){
			Static_InfoApp.create().getContext().sendBroadcast(broadcast);


				List<Base_Entity> detaillist=Util_MatchTip.getSearchResult(Help_DB.create().search(result));
				for (int i = 0; i < detaillist.size(); i++) {
//					Help_DB.create().deleteFix(new Entity_Dangerous().init().put(25, detaillist.get(i).getId()));
					Help_DB.create().delete(detaillist.get(i));
				}
//				Help_DB.create().deleteFix(result);
			
			
		}
		


	}

	/**
	 * 关联删除复查项
	 * @param again
	 * @param broadcast
	 */
	private static void deleteFixAgainCheck(Base_Entity again,Intent broadcast){
		if(again!=null){
			Help_DB.create().delete(again);
			Static_InfoApp.create().getContext().sendBroadcast(broadcast);
			List<Base_Entity> booklist=Util_MatchTip.getSearchResult(Help_DB.create().search(new Entity_BookList().init().put(0,again.getValue(1))));
			for (int i = 0; i < booklist.size(); i++) {
				deleteFixBookList(booklist.get(i));
			}
			Base_Entity law=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().search(new Entity_Law().init().put(3, again.getValue(1))));
			deleteFixLaw(law);
			
		}


	}
	
	/**
	 * 关联删除执法带
	 * @param law
	 * @param broadcast
	 */
	private static void deleteFixLaw(Base_Entity law,Intent broadcast){
		if(law!=null){
			Help_DB.create().delete(law);
			Static_InfoApp.create().getContext().sendBroadcast(broadcast);
			List<Base_Entity> booklist=Util_MatchTip.getSearchResult(Help_DB.create().search(new Entity_BookList().init().put(2,law.getId())));
			for (int i = 0; i < booklist.size(); i++) {
				deleteFixBookList(booklist.get(i));
			}
			
		}

	}
	
	/**
	 * 关联删除文书
	 * @param booklist
	 * @param broadcast
	 */
	private static void deleteFixBookList(Base_Entity booklist,Intent broadcast){
		if(booklist!=null){
			Help_DB.create().delete(booklist);
			Static_InfoApp.create().getContext().sendBroadcast(broadcast);
			if(!booklist.getValue(6).equals("")&&!booklist.getValue(6).equals(" ")){
				Base_Entity needdeleteFixsingle= Pdf_Shark2017.getEneityBook(booklist.getValue(4)).setId(booklist.getValue(6));
				Help_DB.create().delete(needdeleteFixsingle);
			}

			
		}

	}
	/**
	 * 将Entity_OrgData表中的值转为需要的值
	 * @param key
	 * @param index
	 * @return
	 */
	public static String code2idtoStringSpecial(String key,int index){
		String result="";
		Base_Entity resultentity=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().setLogic("or").search(new Entity_OrgData().setId(key).put(2, key).put(3, key)));
		if(Util_MatchTip.isnotnull(resultentity)){
			result=resultentity.getValue(index);
		}else{
			result=key;
		}
		return result;
	}
	/**
	 * 将任意表中的值转为需要的拿条数据特定的值
	 * @param key
	 * @param needindex
	 * @return
	 */
	public static String code2idtoString(Object key, int needindex) {
		String result = "";
		List<Base_Entity> resultentity = Util_MatchTip.getSearchResult(Help_DB
				.create().setLogic("and").search(key));
		if (resultentity != null) {
			if(resultentity.size()==1){
				result=resultentity.get(0).getValue(needindex);
			}

		} else {
			result = "";
		}
		return result;
	}
	/**
	 * 将任意表中的值转为需要的拿条数据特定的值
	 * @param key
	 * @param needindex
	 * @return
	 */
	public static String code2idtoString(Object key, boolean idtrue) {
		String result = "";
		List<Base_Entity> resultentity = Util_MatchTip.getSearchResult(Help_DB
				.create().setLogic("and").search(key));
		if (resultentity != null) {
			if(resultentity.size()==1){
				result=resultentity.get(0).getId();
			}

		} else {
			result = "";
		}
		return result;
	}
	/**
	 * 获得一个符合查找结果的只带需要值的list 
	 * @param key
	 * @param needindex
	 * @return
	 */
	public static List<String> code2idtoStringList(Object key, int needindex) {
		List<String> result = null;
		List<Base_Entity> resultentity = Util_MatchTip.getSearchResult(Help_DB
				.create().setLogic("and").search(key));
		if (resultentity != null) {
			result = new ArrayList<String>();
			for (int i = 0; i < resultentity.size(); i++) {
				result.add(resultentity.get(i).getValue(needindex));
			}
		} else {
			result = null;
		}
		return result;
	}
	/**
	 * 获得更新的最新时间
	 * @param object
	 * @return
	 */
	public static String getTopUpdateTime(Object object){
		//System.out.println("查询最新更新时间");
		String result="";
		Base_Entity resultentity=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().setIsfull(false).setNeedcount(false).setLimit("0,1").setOrder("updatadate DESC").search(object));
		if(resultentity!=null){
			//System.out.println("更新时间结果:"+resultentity.getUpDatadate());
			result=Help_DB.getRealTime(resultentity.getUpDatadate());
		}
		if(result.equals("")){
			result="";
		}
		return result;
	}
	/**
	 * 获得创建的最新时间
	 * @param object
	 * @return
	 */
	public static String getTopCreateTime(Object object){
		//System.out.println("查询最新创建时间");
		String result="";
		Base_Entity resultentity=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().setIsfull(false).setNeedcount(false).setLimit("0,1").setOrder("createdatadate DESC").search(object));
		if(resultentity!=null){
			result=Help_DB.getRealTime(resultentity.getCreatedatadate());
		}
		if(result.equals("")){
			result="";
		}
		return result;
	}
}
