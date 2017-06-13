package com.wisdomregulation.allactivity.tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.allactivity.single.Activity_BookEdit;
import com.wisdomregulation.allactivity.single.Activity_CheckOptionList;
import com.wisdomregulation.allactivity.single.Activity_CompanyDetail;
import com.wisdomregulation.allactivity.single.Activity_PlanDetail;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_BookList;
import com.wisdomregulation.data.entityother.Entity_Check;
import com.wisdomregulation.data.entityother.Entity_Company;
import com.wisdomregulation.dialog.Dialog_Tool;
import com.wisdomregulation.frame.CallBack;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_Db;
import com.wisdomregulation.utils.Util_Finish;
import com.wisdomregulation.utils.Util_MatchTip;
import com.wisdomregulation.utils.Util_String;

import java.util.List;

public class Tab_Check extends Base_AyActivity {

	private PopupWindow popupWindow;
	private Base_Entity checkEntity;
	private Base_Entity companyEntity;
	private Base_Entity bookList;
	private String istmp;
	private Base_Entity save2helthEntity;
	private FrameLayout tabMianContent;
	RadioButton radio1;
	RadioButton radio2;
	RadioButton radio3;
	TextView finishInsepect;
	private int block=2;
	private String checkid;
	private String checkstate;

	private String qiyecode;
	private boolean ishistory;
	private String planid;
	private String qiyeid;
	@Override
	public void setRootView() {
		if(Static_InfoApp.create().istest()){
		this.setContentView(R.layout.tab_check_debug);
		}else{
			this.setContentView(R.layout.tab_check);
		}
		
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

		checkEntity = (Base_Entity) this.getIntent().getSerializableExtra(
				"checkEntity");
		ishistory=this.getIntent().getBooleanExtra("ishistory", false);
		planid = checkEntity.getValue(0);
		checkid = checkEntity.getId();
		qiyecode = checkEntity.getValue(5);
		
		List org = Help_DB.create().search(
				new Entity_Company().init().put(37, qiyecode));
		List<Base_Entity> tmp = (List<Base_Entity>) org.get(1);
		if (tmp.size() > 0) {
			companyEntity = tmp.get(0);
		}
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				qiyeid=Util_Db.code2idtoString(new Entity_Company().put(37, qiyecode), true);
				
			}
		}).start();
		bookList = new Entity_BookList().init().put(0, checkid).put(7, qiyecode);
		checkstate = Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().search(new Entity_Check().setId(checkid))).getValue(6);
		istmp=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().search(new Entity_Check().setId(checkid))).getValue(15);


	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		tabMianContent=(FrameLayout)findViewById(R.id.tabMianContent);
		radio1=(RadioButton)findViewById(R.id.radio1);
		radio2=(RadioButton)findViewById(R.id.radio2);
		radio3=(RadioButton)findViewById(R.id.radio3);
		finishInsepect=(TextView)findViewById(R.id.finishInsepect);
		if(!Util_MatchTip.isnotnull(checkstate)||checkstate.equals(" ")||checkstate.equals("0")||checkstate.equals("")){

		}else{
			finishInsepect.setText("退出查看");
		}
		switch (block) {
		case 1:
			radio1.performClick();
			break;
		case 2:
			radio2.performClick();
			break;
		case 3:
			radio3.performClick();
			break;

		default:
			radio1.performClick();
			break;
		}

	}

	public void showCompanyDetail(View view) {
		block = 1;
		tabMianContent.removeAllViews();
		tabMianContent.addView(getIntentContentView(
				
				tabMianContent,
				"tabMianContent1",
				new Intent(Tab_Check.this, Activity_CompanyDetail.class)
						.putExtra("ishistory", ishistory)
						.putExtra("companyEntity", companyEntity)
						.putExtra("editState", 0).putExtra("tomoreState", 0)));
	}
	public void showInspectContent(View view) {
		block=2;
		tabMianContent.removeAllViews();
		tabMianContent.addView(getIntentContentView(
				tabMianContent, "tabMianContent1", new Intent(
						Tab_Check.this, Activity_CheckOptionList.class)
		.putExtra("ishistory", ishistory)
		.putExtra("checkEntity", checkEntity)));
	}



	public void showBook(View view) {
		block=3;
		tabMianContent.removeAllViews();
		tabMianContent.addView(getIntentContentView(
				tabMianContent, "tabMianContent1", new Intent(
						Tab_Check.this, Activity_BookEdit.class)
		.putExtra("ishistory", ishistory)
		.putExtra("bookList", bookList)
		.putExtra("companyname", companyEntity.getValue(0))));
	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		Base_Entity cbase=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().search(new Entity_Check().setId(checkid)));
		//System.out.println("SRXLIN2"+checkstate);
		checkstate = (cbase==null?null:cbase.getValue(6));
		//System.out.println("临时标签："+istmp);
		if(Util_MatchTip.isnotnull(istmp)&&istmp.equals("0")){
			if(checkstate!=null&&!checkstate.equals("1")){
				//System.out.println("临时的退出删除");
//				Util_Db.deleteFix(new Entity_Check().init().setId(checkid));
				
			}
			
		}else{
			if(checkstate==null||checkstate.equals("")||checkstate.equals(" ")){
				
				Help_DB.create().save2update(new Entity_Check().init().setId(checkid).put(6, "0"));
				Static_InfoApp.create().getContext().sendBroadcast(new Intent(Activity_PlanDetail.refresh));
				
				
			}else{
				Static_InfoApp.create().getContext().sendBroadcast(new Intent(Activity_PlanDetail.refresh));
			}
			

		}
		super.finish();

		
		
	}

	public void finishInspectList(View view) {
		Dialog_Tool.showDialog_CheckFinishOr(this, new CallBack() {
	
			@Override
			public void back(Object resultlist) {
				if(!checkstate.equals("1")){
//					String targetIdList = "";
//					List<Object> sh = new ArrayList<Object>();
//					for (int i = 0; i < expandMap.size(); i++) {
//						sh.addAll(Util_MatchTip.getAllChildList(expandMap.get(i)));
//					}
//					for (int i = 0; i < sh.size(); i++) {
//						String value = ((ExpandMap) sh.get(i)).getValue();
//						if (value != null && !value.equals("")&&!value.equals(" ")) {
//							targetIdList = targetIdList + value + ",";
//						}
		//
//					}
//					if (targetIdList.length() > 1) {
//						targetIdList = targetIdList.substring(0, targetIdList.length() - 1);
//					}
					if(istmp.equals("0")){
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								Tab_Check.this.runOnUiThread(new Runnable() {
									
									@Override
									public void run() {
										
										Tab_Check.this.showWait();
										
									}
								});
								//System.out.println("临时计划id"+planid);
								//System.out.println("临时企业组织机构代码"+qiyecode);
								boolean isneed=Util_Finish.findCheckIsNeedAgain(new Entity_Check().init().setId(checkid).put(19, planid).put(25, qiyecode));
								Tab_Check.this.runOnUiThread(new Runnable() {
									
									@Override
									public void run() {
										
										Tab_Check.this.dissmissWait();
										
									}
								});
								
								if(isneed){
									Tab_Check.this.runOnUiThread(new Runnable() {
										
										@Override
										public void run() {
											
											Dialog_Tool.showDialog_FinishCheck(Tab_Check.this, null);
											
										}
									});
									
								}else{
									Tab_Check.this.runOnUiThread(new Runnable() {
										
										@Override
										public void run() {
											Tab_Check.this.dissmissWait();
											Tab_Check.this.finish();
											
										}
									});
									
								}
								
							}
						}).start();
						
					}else{
						//System.out.println("SRXLIN:"+"no");
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								Help_DB.create().save2update(new Entity_Check().init().setId(checkid).put(6, "1"));
//								Help_DB.create().save2update(new Entity_Company().init().put(37, qiyeid).put(3, Util_String.getDate()));//暂时先不用
								Tab_Check.this.runOnUiThread(new Runnable() {
									
									@Override
									public void run() {
										Tab_Check.this.finish();
										
									}
								});
								
								
							}
						}).start();
						
					}

					//System.out.println("保存企业的最新检查时间:"+qiyeid);
					Help_DB.create().save2update(new Entity_Company().setId(qiyeid).put(3, Util_String.getDate()));
				}else{
					Tab_Check.this.finishSuper();
				}
		
		}
		});


	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
}
