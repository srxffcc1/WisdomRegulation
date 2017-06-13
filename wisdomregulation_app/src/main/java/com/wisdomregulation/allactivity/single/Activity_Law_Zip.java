package com.wisdomregulation.allactivity.single;

import android.os.Handler;
import android.widget.ListView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_ZipList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_Law;
import com.wisdomregulation.help.Help_DB;

import java.util.ArrayList;
import java.util.List;

public class Activity_Law_Zip extends Base_AyActivity {
private ListView caseList;
private boolean isall = true;
private List<Base_Entity> caselistdata;
private Adapter_ZipList adapter;
private Base_Entity searchtarget;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_law_zip);

	}
	@Override
		public void initData() {
			// TODO Auto-generated method stub

			searchtarget = new Entity_Law().init().put(11, "6.结案归档阶段");
			caselistdata = new ArrayList<Base_Entity>();
			adapter = new Adapter_ZipList(this, caselistdata);

			
			
		}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		caseList=(ListView)findViewById(R.id.handlerMenuList);
        caseList.setAdapter(adapter);
	}
	@Override
	public void initView(){
		Activity_Law_Zip.this.showWait();
		setLimt();
		new Handler().postDelayed(new Runnable() {
			public void run() {
				
				
				org = Help_DB.create().setLimit(nowlmit).setNeedcount(true)
						.setIsfull(isall).setLogic("and").search(searchtarget);
				allpage = getAllPage(Integer.parseInt(org.get(0).toString()));
				caselistdata.clear();
				caselistdata.addAll((List<Base_Entity>) org.get(1));
				adapter.notifyDataSetChanged();
				pageDown.setText(pageIndex + "/" + allpage+"\n总条目:"+org.get(0).toString()+" 本页:"+caselistdata.size());
				Activity_Law_Zip.this.dissmissWait();
			}
		}, 500);
	}

}
