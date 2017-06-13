package com.wisdomregulation.allactivity.single;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.allactivity.tab.Tab_Law;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_BookList;

public class Activity_LawDetail extends Base_AyActivity {
private TextView lawvalue1;
private TextView lawvalue2;
private TextView lawvalue3;
private TextView lawvalue4;
private TextView lawvalue5;
private Base_Entity lawEntity;
private Base_Entity bookList;
private boolean ishistory;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_law_detail);

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	@Override
	public void toMore(View view) {
		// TODO Auto-generated method stub
		super.toMore(view);
		Activity_LawDetail.this.startActivity(new Intent(Activity_LawDetail.this, Tab_Law.class)
		.putExtra("ishistory", ishistory)
		.putExtra("bookList", bookList));
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub

		lawEntity = (Base_Entity) this.getIntent().getSerializableExtra("lawEntity");
		ishistory=this.getIntent().getBooleanExtra("ishistory", false);
		bookList = new Entity_BookList().put(2, lawEntity.getId()).put(0, lawEntity.getValue(3)).put(7, lawEntity.getValue(10));
		
	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		lawvalue1=(TextView)findViewById(R.id.lawvalue1);
		lawvalue2=(TextView)findViewById(R.id.lawvalue2);
		lawvalue3=(TextView)findViewById(R.id.lawvalue3);
		lawvalue4=(TextView)findViewById(R.id.lawvalue4);
		lawvalue5=(TextView)findViewById(R.id.lawvalue5);
		lawvalue1.setText(lawEntity.getValue(0));
		lawvalue2.setText(lawEntity.getValue(9));
		lawvalue3.setText(lawEntity.getValue(13));
		lawvalue4.setText(lawEntity.getValue(14));
		lawvalue5.setText(lawEntity.getValue(12));
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
}
