package com.wisdomregulation.allactivity.tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.allactivity.single.Activity_History_AgainCheck;
import com.wisdomregulation.allactivity.single.Activity_History_Check;
import com.wisdomregulation.allactivity.single.Activity_History_Law;

public class Tab_History_Check2Again extends Base_AyActivity {
	RadioButton radio1;
	RadioButton radio2;
	RadioButton radio3;
	private FrameLayout tabMianContent;
	private int block=1;
	private String companyid;
	@Override
	public void setRootView() {
		setContentView(R.layout.tab_historycheck2again);

	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub

		companyid = this.getIntent().getStringExtra("companyid");
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

		radio1=(RadioButton)findViewById(R.id.radio1);
		radio2=(RadioButton)findViewById(R.id.radio2);
		radio3=(RadioButton)findViewById(R.id.radio3);
		tabMianContent=(FrameLayout)findViewById(R.id.tabMianContent);
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
	public void showHistoryCheck(View view){
		block=1;
		tabMianContent.removeAllViews();
		tabMianContent.addView(getIntentContentView( tabMianContent, "tabMianContent1", new Intent(this, Activity_History_Check.class).putExtra("companyid", companyid)));
	}
	public void showHistoryAgain(View view){
		block=2;
		tabMianContent.addView(getIntentContentView(tabMianContent, "tabMianContent1", new Intent(this, Activity_History_AgainCheck.class).putExtra("companyid", companyid)));
	}
	public void showHistoryLaw(View view){
		block=3;
		tabMianContent.addView(getIntentContentView(tabMianContent, "tabMianContent1", new Intent(this, Activity_History_Law.class).putExtra("companyid", companyid)));
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
}
