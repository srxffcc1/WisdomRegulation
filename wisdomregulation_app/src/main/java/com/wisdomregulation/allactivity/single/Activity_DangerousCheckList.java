package com.wisdomregulation.allactivity.single;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.widget.ListView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_DangerousListRandom;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_CheckDetail;
import com.wisdomregulation.help.Help_DB;

import java.util.ArrayList;
import java.util.List;

public class Activity_DangerousCheckList extends Base_AyActivity {
	private ListView dangerousList;
	private List<Base_Entity> lawDetaiDatalList;
	private List<Base_Entity> dangerouslistdata;
	private Base_Entity searchtarget;
	private String keyword;
	private Adapter_DangerousListRandom adapter;
	public static final String refresh="Activity_DangerousCheckList.refresh";
	private TmpBroadcastReceiver receiver;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_dangerous_detail_list);

	}

	@Override
	public void initData() {

		searchtarget = new Entity_CheckDetail().init().put(42, "政府抽查").putlogic2value(38, "<>", "整改完成");
		dangerouslistdata=new ArrayList<Base_Entity>();
		adapter = new Adapter_DangerousListRandom(this, dangerouslistdata);

		initView();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		

	}

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		dangerousList=(ListView)findViewById(R.id.dangerousList);
        dangerousList.setAdapter(adapter);
		receiver = new TmpBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(refresh);
		this.registerReceiver(receiver, filter);
	}
	@Override
	protected void onDestroy() {
		try {
			this.unregisterReceiver(receiver);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		super.onDestroy();
		
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		Activity_DangerousCheckList.this.showWait();
		setLimt();
		new Handler().postDelayed(new Runnable() {
			public void run() {
				org = Help_DB.create().setLimit(nowlmit).setNeedcount(true).setIsall(true).setLogic("and")
						.setIsfull(true).search(searchtarget);
				allpage = getAllPage(Integer.parseInt(org.get(0).toString()));
				dangerouslistdata.clear();
				dangerouslistdata.addAll((List<Base_Entity>) org.get(1));
				for (int i = 0; i < dangerouslistdata.size(); i++) {
					dangerouslistdata.get(i).setKeyword(keyword);
				}
				adapter.notifyDataSetChanged();
				pageDown.setText(pageIndex + "/" + allpage+"\n总条目:"+org.get(0).toString()+" 本页:"+dangerouslistdata.size());
				Activity_DangerousCheckList.this.dissmissWait();
			}
		}, 500);
	}	
	private class TmpBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent.getAction().equals(refresh)) {
				
				initView();
			}


		}

	}

}
