package com.wisdomregulation.allactivity.single;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_LibraryDetail;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;

public class Activity_Library_Detail extends Base_AyActivity {
LinearLayout libraryDetailContent;
TextView newtitle;
private Base_Entity baseentity;
	@Override
	public void setRootView() {
		setContentView(R.layout.activity_library_detail);

	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub



		baseentity = (Base_Entity) this.getIntent().getSerializableExtra("baseentity");
	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		libraryDetailContent=(LinearLayout)findViewById(R.id.libraryDetailContent);
		newtitle=(TextView)findViewById(R.id.newtitle);
		String newtitletext=this.getIntent().getStringExtra("newtitle");
		newtitle.setText(newtitletext);
		Adapter_LibraryDetail adapter=new Adapter_LibraryDetail(this, baseentity, libraryDetailContent);
		adapter.initView();
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

}
