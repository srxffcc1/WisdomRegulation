package com.wisdomregulation.allactivity.single;

import android.os.Handler;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_DangerousListIsCheck;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_CheckDetail;
import com.wisdomregulation.help.Help_DB;

import java.util.ArrayList;
import java.util.List;

public class Activity_DangerousHaveCheckCompany extends Base_AyActivity {
private ListView haveCheckList;
private List<Base_Entity> notCheckListData;
private boolean isall = true;
private Base_Entity searchtarget;
private BaseAdapter adapter;

	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_dangerous_companyyeslist);

	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub

		notCheckListData=new ArrayList<Base_Entity>();
		adapter = new Adapter_DangerousListIsCheck(this, notCheckListData);

		searchtarget = new Entity_CheckDetail().init().put(45, "已审核");
	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		haveCheckList=(ListView)findViewById(R.id.haveCheckList);
        haveCheckList.setAdapter(adapter);
		initView();
	}
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		

		
		
	}
	public void initView(){
		Activity_DangerousHaveCheckCompany.this.showWait();
		setLimt();
		new Handler().postDelayed(new Runnable() {
			public void run() {
				
				
				org = Help_DB.create().setLimit(nowlmit).setNeedcount(true).setLogic("or").setIsregex(true)
						.setIsfull(isall).search(searchtarget);
				allpage = getAllPage(Integer.parseInt(org.get(0).toString()));
				notCheckListData.clear();
				notCheckListData.addAll((List<Base_Entity>) org.get(1));
				adapter.notifyDataSetChanged();
				pageDown.setText(pageIndex + "/" + allpage+"\n总条目:"+org.get(0).toString()+" 本页:"+notCheckListData.size());
				Activity_DangerousHaveCheckCompany.this.dissmissWait();
			}
		}, 500);
	}

}
