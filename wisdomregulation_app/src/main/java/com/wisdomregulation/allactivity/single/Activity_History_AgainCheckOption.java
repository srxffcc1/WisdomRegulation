package com.wisdomregulation.allactivity.single;

import android.widget.ExpandableListView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_ExpandHistoryOptionList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.map.ExpandMap;

import java.util.ArrayList;
import java.util.List;

public class Activity_History_AgainCheckOption extends Base_AyActivity{
	private ExpandableListView inspectExpandList;
	@Override
	public void setRootView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_history_check_item);
		
	}

	@Override
	public void initData() {

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		List<ExpandMap> datalist=new ArrayList<ExpandMap>();
		inspectExpandList.setAdapter(new Adapter_ExpandHistoryOptionList(this, datalist, inspectExpandList, true));
		
	}

	@Override
	public void initWidget() {
		inspectExpandList=(ExpandableListView)findViewById(R.id.inspectExpandList);
	}
}
