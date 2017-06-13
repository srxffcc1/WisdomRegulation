package com.wisdomregulation.allactivity.single;

import android.os.Handler;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_HistoryBlock_Check;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_Check;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_InfoApp;

import java.util.ArrayList;
import java.util.List;

public class Activity_History_Check extends Base_AyActivity{
	private Base_Entity searchtarget;
	private BaseAdapter adapter;
	private List<Base_Entity> baselist;
	ListView companyList;
	private boolean isall = true;
	@Override
	public void setRootView() {
		setContentView(R.layout.activity_history_check_list);
		
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub

		String id=this.getIntent().getStringExtra("companyid");
		searchtarget=new Entity_Check().init().put(5, id).put(6, "1").setCreated(Static_InfoApp.create().getAccountId());
		baselist=new ArrayList<Base_Entity>();
		adapter=new Adapter_HistoryBlock_Check(this, baselist);

	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		companyList=(ListView)findViewById(R.id.companyList);
        companyList.setAdapter(adapter);
		initView();
	}

	public void initView(){
		Activity_History_Check.this.showWait();
		setLimt();
		new Handler().postDelayed(new Runnable() {
			public void run() {
				org = Help_DB.create().setLimit(nowlmit).setNeedcount(true)
						.setIsfull(isall).search(searchtarget);
				allpage = getAllPage(Integer.parseInt(org.get(0).toString()));
				baselist.clear();
				baselist.addAll((List<Base_Entity>)org.get(1));
				adapter.notifyDataSetChanged();
				pageDown.setText(pageIndex + "/" + allpage+"\n总条目:"+org.get(0).toString()+" 本页:"+baselist.size());
				Activity_History_Check.this.dissmissWait();
			}
		}, 300);
	}
	

}
