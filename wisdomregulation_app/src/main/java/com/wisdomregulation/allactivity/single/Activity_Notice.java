package com.wisdomregulation.allactivity.single;

import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;

public class Activity_Notice extends Base_AyActivity {


	private TextView noticetitle;
	private TextView noticepeople;
	private TextView noticedate;
	private TextView noticecontent;
	
	private Base_Entity noticeEntity;

	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_notice_detail);

	}

	@Override
	public void initData() {

		noticeEntity = (Base_Entity) this.getIntent().getSerializableExtra("noticeEntity");
		noticetitle.setText(noticeEntity.getValue(0));
		noticepeople.setText(noticeEntity.getValue(2));
		noticedate.setText(noticeEntity.getValue(3));
		noticecontent.setText(noticeEntity.getValue(1));
	}

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		noticetitle=(TextView)findViewById(R.id.noticetitle);
		noticepeople=(TextView)findViewById(R.id.noticepeople);
		noticedate=(TextView)findViewById(R.id.noticedate);
		noticecontent=(TextView)findViewById(R.id.noticecontent);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
}
