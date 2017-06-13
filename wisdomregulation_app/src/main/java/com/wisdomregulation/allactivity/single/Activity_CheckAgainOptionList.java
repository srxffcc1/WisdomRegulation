package com.wisdomregulation.allactivity.single;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_DangerousDetailList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_CheckDetail;
import com.wisdomregulation.help.Help_DB;

import java.util.ArrayList;
import java.util.List;

public class Activity_CheckAgainOptionList extends Base_AyActivity {
private ListView inspectAgainList;
TextView more;
private PopupWindow popupWindow;
private Base_Entity checkAgainEntity;
private List<Base_Entity> dangerouslist;
private Base_Entity dangerousEntity;
private Adapter_DangerousDetailList adapter;
public static final String refresh="Activity_CheckAgainList.refresh";
private TmpBroadcastReceiver receiver;
private boolean ishistory;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_againcheck_listdangerous);
	}
	
	@Override
	public void initData() {
		// TODO Auto-generated method stub

		checkAgainEntity=(Base_Entity) this.getIntent().getSerializableExtra("checkAgainEntity");
		ishistory = this.getIntent().getBooleanExtra("ishistory", false);
		dangerouslist=new ArrayList<Base_Entity>();
		adapter = new Adapter_DangerousDetailList(this, dangerouslist,ishistory);

	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		inspectAgainList=(ListView)findViewById(R.id.inspectAgainList);
		more=(TextView)findViewById(R.id.more);
        inspectAgainList.setAdapter(adapter);
		receiver = new TmpBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(refresh);
		this.registerReceiver(receiver, filter);
		initView();
		
	}
	@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			try {
				this.unregisterReceiver(receiver);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();

		}
	public void initView(){
		Activity_CheckAgainOptionList.this.showWait();
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				String targetid=checkAgainEntity.getValue(0);
				String qiyecode=checkAgainEntity.getValue(5);
				dangerousEntity=new Entity_CheckDetail().init().put(25, qiyecode).put(19, targetid).putlogic2value(15, "<>", "3");//要关联计划id 再关联 企业id
				org = Help_DB.create().search(dangerousEntity);
				dangerouslist.clear();
				dangerouslist.addAll((List<Base_Entity>) org.get(1));
				adapter.notifyDataSetChanged();
				Activity_CheckAgainOptionList.this.dissmissWait();
				
			}
		}, 300);
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
