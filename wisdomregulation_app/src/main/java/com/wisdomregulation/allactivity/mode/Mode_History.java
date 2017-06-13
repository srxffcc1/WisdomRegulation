package com.wisdomregulation.allactivity.mode;

import android.widget.ListView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_HistoryCompanyList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_Company;
import com.wisdomregulation.help.Help_DB;

import java.util.ArrayList;
import java.util.List;

public class Mode_History extends Base_AyActivity {
	ListView historyCompanyList;
	private Adapter_HistoryCompanyList adapter;
	private List<Base_Entity> contactslistdata;
	private boolean isall = true;
	private Base_Entity searchtarget;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.tab_history_list);

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

		searchtarget = new Entity_Company().clear();
		contactslistdata = new ArrayList<Base_Entity>();
		adapter = new Adapter_HistoryCompanyList(this, contactslistdata);

	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		historyCompanyList=(ListView)findViewById(R.id.historyCompanyList);
		historyCompanyList.setAdapter(adapter);
		initView();
	}
	public void initView(){
		Mode_History.this.showWait();
		setLimt();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				org = Help_DB.create().setLimit(nowlmit).setNeedcount(true).setLogic("and")
						.setIsfull(isall).search(searchtarget);
				Mode_History.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						allpage = getAllPage(Integer.parseInt(org.get(0).toString()));
						contactslistdata.clear();
						contactslistdata.addAll((List<Base_Entity>) org.get(1));
						adapter.notifyDataSetChanged();
						pageDown.setText(pageIndex + "/" + allpage+"\n总条目:"+org.get(0).toString()+" 本页:"+contactslistdata.size());
						Mode_History.this.dissmissWait();
						
					}
				});

				
			}
		}).start();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}
	
}
