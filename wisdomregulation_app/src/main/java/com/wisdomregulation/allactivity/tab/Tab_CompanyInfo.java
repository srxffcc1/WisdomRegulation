package com.wisdomregulation.allactivity.tab;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_CompanyInfoDetailList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.allactivity.mode.Mode_CompanyInfo;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.dialog.Dialog_Tool;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_InfoApp;

import java.util.Map;

public class Tab_CompanyInfo extends Base_AyActivity{
	private LinearLayout companyDetailList;
	private TextView inspect;
	private TextView seehistory;
	LinearLayout specialLinearlayout;
	private TextView more;
	private int editState=0;
	private boolean ismore=false;
	private Base_Entity companyEntity;
	private Adapter_CompanyInfoDetailList adapter;

	@Override
	public void setRootView() {
		this.setContentView(R.layout.tab_activity_company);

	}

	@Override
	public void initData() {

		companyEntity = (Base_Entity)this.getIntent().getSerializableExtra("companyEntity");
		editState=this.getIntent().getIntExtra("editState", 0);

	}
	
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		companyDetailList=(LinearLayout)findViewById(R.id.companyDetailList);
		inspect=(TextView)findViewById(R.id.inspect);
		seehistory=(TextView)findViewById(R.id.seehistory);
		specialLinearlayout=(LinearLayout)findViewById(R.id.specialLinearlayout);
		more=(TextView)findViewById(R.id.more);
		more.setVisibility(View.INVISIBLE);
		switch (editState) {
		case 0:
//			more.setVisibility(View.VISIBLE);
//			seehistory.setVisibility(View.VISIBLE);
			inspect.setText("进行检查");
			break;
		case 1:
			inspect.setText("确认添加");
			break;
		case 2:
			inspect.setText("确认修改");
			break;
		case 3:
			inspect.setText("确认检索信息");
			break;
		case 4:
			specialLinearlayout.setVisibility(View.INVISIBLE);
			break;
		default:
			break;
		}
		if(companyEntity!=null){
			
		adapter = new Adapter_CompanyInfoDetailList(Tab_CompanyInfo.this, companyEntity, companyDetailList).setEditState(editState).initView();

			
		}
		
	}
	
	public void toInspect(View view){
		switch (editState) {
		case 0:
			Dialog_Tool.showDialog_InspectChoseType(Tab_CompanyInfo.this,companyEntity);
			break;
		case 1:
			saveEditResult();
			break;
		case 2:
			updateEditResult();
			break;
		case 3:
			researchEditResult();
			break;
		default:
			break;
		}
		
		
	}
	public void seeHistory(View view){
//		this.startActivity(new Intent(this, Tab_History_Check2Again.class).putExtra("companyid", companyEntity.getId()));
	}
	
	@Override
	public void toMore(View view) {
		// TODO Auto-generated method stub
		super.toMore(view);
		//屏蔽修改企业信息
//		if(!ismore){
//			
//			((TextView)view).setText("取消修改");
//			editState=2;
//			inspect.setText("确认修改");
//			seehistory.setVisibility(View.GONE);
//			adapter.setEditState(1);
//			adapter.initView();
//			adapter.setFocus(0);
//		}else{
//			((TextView)view).setText("修    改");
//			hideInputSoft();
//			editState=0;
//			seehistory.setVisibility(View.VISIBLE);
//			inspect.setText("进行检查");
//			adapter.setEditState(0);
//			adapter.initView();
//		}
//		ismore=!ismore;
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
	private void saveEditResult(){
		Map<String,EditText> viewmap=adapter.getViewmap();
		companyEntity.clear();
		for (int i = 0; i < companyEntity.size(); i++) {
			EditText textvalue=viewmap.get(companyEntity.getField(i));
			if(textvalue!=null){
				companyEntity.put(i, textvalue.getText().toString());
			}else{
				companyEntity.put(i, "");
			}
		}
		companyEntity.initId();
		new Thread(){
			public void run(){
				Help_DB.create().save2update(companyEntity);
				Tab_CompanyInfo.this.finish();
				Static_InfoApp.create().getContext().sendBroadcast(new Intent(Mode_CompanyInfo.refreshadaptertolast));
			}
		}.start();
	}
	private void researchEditResult(){
		Map<String,EditText> viewmap=adapter.getViewmap();
		companyEntity.clear();
		for (int i = 0; i < companyEntity.size(); i++) {
			EditText textvalue=viewmap.get(companyEntity.getField(i));
			if(textvalue!=null){
				companyEntity.put(i, textvalue.getText().toString());
			}else{
				companyEntity.put(i, "");
			}
		}
		Tab_CompanyInfo.this.finish();
	}
	public void updateEditResult(){
		Map<String,EditText> viewmap=adapter.getViewmap();
		for (int i = 0; i < companyEntity.size(); i++) {
			EditText textvalue=viewmap.get(companyEntity.getField(i));
			if(textvalue!=null){
				companyEntity.put(i, textvalue.getText().toString());
			}else{
				companyEntity.put(i, "");
			}
		}
				Help_DB.create().update(companyEntity);
				Tab_CompanyInfo.this.finish();
				Static_InfoApp.create().getContext().sendBroadcast(new Intent(Mode_CompanyInfo.refreshadapter));

		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();

		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

}
