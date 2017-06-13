package com.wisdomregulation.allactivity.single;

import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_CompanyInfoDetailList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;

public class Activity_CompanyDetail extends Base_AyActivity{
private LinearLayout companyDetailList;
private Base_Entity detailMapData;
private PopupWindow popupWindow;
private int editState=0;
private Adapter_CompanyInfoDetailList adapter;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_company_detail);
		
	}
	@Override
	public void initData() {

		detailMapData=(Base_Entity) this.getIntent().getSerializableExtra("companyEntity");
		editState=this.getIntent().getIntExtra("editState", 0);
	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		companyDetailList=(LinearLayout)findViewById(R.id.companyDetailList);
		if(detailMapData!=null){
			adapter = new Adapter_CompanyInfoDetailList(Activity_CompanyDetail.this, detailMapData, companyDetailList).setEditState(editState).initView();			
		}

	}
	@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
		}
	@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			
		}
	@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			
		}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

}
