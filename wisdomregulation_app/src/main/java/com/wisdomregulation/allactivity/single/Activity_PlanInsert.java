package com.wisdomregulation.allactivity.single;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_InsertPlanDetailList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_OrgData;
import com.wisdomregulation.dialog.Dialog_Tool;
import com.wisdomregulation.frame.CallBack;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_Db;
import com.wisdomregulation.utils.Util_String;

import java.util.List;
import java.util.Map;

public class Activity_PlanInsert extends Base_AyActivity{
LinearLayout insertPlanDetail;
Base_Entity planentitydata;
private int editstate=1;
LinearLayout canhide;
TextView newtitle;
private Adapter_InsertPlanDetailList adapter;
private String planid;
	@Override
	public void setRootView() {
		// TODO Auto-generated method stub
		this.setContentView(R.layout.activity_plan_insert);
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub

		planid = Util_String.get16Uuid();
		planentitydata=(Base_Entity) this.getIntent().getSerializableExtra("entityPlan");
		editstate=this.getIntent().getIntExtra("editstate", 1);

	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		insertPlanDetail=(LinearLayout)findViewById(R.id.insertPlanDetail);
		canhide=(LinearLayout)findViewById(R.id.canhide);
		newtitle=(TextView)findViewById(R.id.newtitle);
		switch (editstate) {
			case 0:
				newtitle.setText("计划详情");
				canhide.setVisibility(View.GONE);
				break;
			case 1:

				break;

			default:
				break;
		}
		adapter = new Adapter_InsertPlanDetailList(this,planentitydata,insertPlanDetail).setEditState(editstate).initView();
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				if(Static_ConstantLib.isdebug){
//					Activity_PlanInsert.this.runOnUiThread(new Runnable() {
//						
//						@Override
//						public void run() {
//							adapter.setChosepeople(null);
//							
//						}
//					});
//				}else{
//					
//					Activity_PlanInsert.this.runOnUiThread(new Runnable() {
//						
//						@Override
//						public void run() {
//							adapter.setChosepeople(null);
//							
//						}
//					});
//				}
//				
//				
//				
//			}
//		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				final List<String> needpop=Util_Db.code2idtoStringList(new Entity_OrgData().put(0, "8c36059b44674981aad61c406ccc6897"),3);
				Activity_PlanInsert.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						adapter.setNeedpop(needpop);
						
					}
				});
				
				
			}
		}).start();
		
	}
	public void finishToSubmmit(View view){
		if(Util_String.isInsertAll(adapter.getEdittextlist())){
			Map<String,EditText> viewmap=adapter.getViewmap();
			planentitydata.clear();
			for (int i = 0; i < planentitydata.size(); i++) {
				EditText textvalue=viewmap.get(planentitydata.getField(i));
				if(textvalue!=null){
					planentitydata.put(i, textvalue.getText().toString());
				}else{
					planentitydata.put(i, "");
				}
				planentitydata.put(3, Static_InfoApp.create().getAccountId());
				planentitydata.put(5, "0");
			}
			new Thread(){
				public void run(){
					Help_DB.create().save2update(planentitydata.setId(planid));
					Activity_PlanInsert.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Static_InfoApp.create().getContext().sendBroadcast(new Intent(Activity_Plan.refresh));
							Activity_PlanInsert.this.finish();
						}
					});
				}
			}.start();
		}else{
			Dialog_Tool.showDialog_AllEdittextInsert(Activity_PlanInsert.this, null, new CallBack() {
				
				@Override
				public void back(Object resultlist) {
					Map<String,EditText> viewmap=adapter.getViewmap();
					planentitydata.clear();
					for (int i = 0; i < planentitydata.size(); i++) {
						EditText textvalue=viewmap.get(planentitydata.getField(i));
						if(textvalue!=null){
							planentitydata.put(i, textvalue.getText().toString());
						}else{
							planentitydata.put(i, "");
						}
						planentitydata.put(5, "0");
					}
					new Thread(){
						public void run(){
							Help_DB.create().save2update(planentitydata.setId(planid));
							Activity_PlanInsert.this.runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									Static_InfoApp.create().getContext().sendBroadcast(new Intent(Activity_Plan.refresh));
									Activity_PlanInsert.this.finish();
								}
							});
						}
					}.start();

					
				}
			});
		}
		
		
		
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
	
}
