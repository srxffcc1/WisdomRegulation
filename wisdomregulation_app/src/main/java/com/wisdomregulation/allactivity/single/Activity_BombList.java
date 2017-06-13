package com.wisdomregulation.allactivity.single;

import android.widget.ListView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_BombList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;

import java.util.ArrayList;
import java.util.List;

public class Activity_BombList extends Base_AyActivity{
	private ListView companyViewList;
	private Adapter_BombList adapter;
	private List<Base_Entity> companyList;
	@Override
	public void setRootView() {
		setContentView(R.layout.activity_bomb_list);
		
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		companyViewList=(ListView)findViewById(R.id.companyList);
		companyList=new ArrayList<Base_Entity>();
		companyList.add(new Base_Entity());
		adapter = new Adapter_BombList(
				Activity_BombList.this, companyList);
		companyViewList.setAdapter(adapter);
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

}
