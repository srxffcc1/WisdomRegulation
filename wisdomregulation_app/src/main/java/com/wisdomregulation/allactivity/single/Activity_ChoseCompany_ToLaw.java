package com.wisdomregulation.allactivity.single;

import java.util.ArrayList;
import java.util.List;



import android.os.Handler;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_ChoseCheckList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_Law;
import com.wisdomregulation.dialog.Dialog_Tool;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.utils.Util_MatchTip;

public class Activity_ChoseCompany_ToLaw extends Base_AyActivity {
ListView choseCheckList;

private List<Base_Entity> searchresult;

private BaseAdapter adapter;

private Base_Entity search;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_law_chosefromcheck);
		
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub

		search = (Base_Entity) this.getIntent().getSerializableExtra("choselaw");
		searchresult=new ArrayList<Base_Entity>();
		adapter = new Adapter_ChoseCheckList(this, searchresult).setEditstate("1");

	}
	@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			
			
			
			
		}
	@Override
		public void initWidget() {
			// TODO Auto-generated method stub
		choseCheckList=(ListView)findViewById(R.id.choseCheckList);
        choseCheckList.setAdapter(adapter);
			searchresult.clear();
			searchresult.addAll(Util_MatchTip.getSearchResult(Help_DB.create().search(search)));
			if(searchresult.size()>0){
				
			}else{
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						Toast.makeText(Activity_ChoseCompany_ToLaw.this,"无可选办案企业,自动退出", 1000).show();
						Activity_ChoseCompany_ToLaw.this.finish();
					}
				}, 200);
				
			}
			adapter.notifyDataSetChanged();
			
		}
	public void finishchoselaw(View view){
		List<Base_Entity> editlist = ((Adapter_ChoseCheckList) adapter).getchoseList();
		List<Base_Entity> lawlist=new ArrayList<Base_Entity>();
		for (int i = 0; i < editlist.size(); i++) {
			Base_Entity addresult = new Entity_Law().init();
			addresult.put(3, editlist.get(i).getId())
					.put(5, editlist.get(i).getId())
					.put(4, editlist.get(i).getId())
					.put(6, editlist.get(i).getValue(2))
					.put(17, "")
					.put(9, editlist.get(i).getValue(4))
					.put(10, editlist.get(i).getValue(5))
					.put(11, "1.立案阶段");
			lawlist.add(addresult);
			// Help_DB.create().save2update(null);

		}
		
		if (lawlist != null && lawlist.size() > 0) {
			Dialog_Tool.showDialog_EditLaw(this, lawlist);
		} else {
			Toast.makeText(this, "请选择需要办案的检查", Toast.LENGTH_SHORT).show();
		}
	}
	public void cancelchoselaw(View view){
		this.finish();
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
}
