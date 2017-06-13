package com.wisdomregulation.allactivity.single;

import android.widget.ListView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_Video;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;

import java.util.ArrayList;
import java.util.List;

public class Activity_Video extends Base_AyActivity {
	private ListView companyViewList;
	private Adapter_Video adapter;
	private List<Base_Entity> companyList;
	@Override
	public void setRootView() {
		setContentView(R.layout.activity_model_list);
		
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		companyViewList=(ListView)findViewById(R.id.lawAgainList);
		companyList=new ArrayList<Base_Entity>();
		companyList.add(new Base_Entity());
		adapter = new Adapter_Video(
				Activity_Video.this, companyList);
		companyViewList.setAdapter(adapter);
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

}
