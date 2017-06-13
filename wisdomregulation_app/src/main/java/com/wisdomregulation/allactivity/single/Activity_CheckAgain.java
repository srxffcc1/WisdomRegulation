package com.wisdomregulation.allactivity.single;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_CheckAgainList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_Check;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_InfoApp;

import java.util.ArrayList;
import java.util.List;

public class Activity_CheckAgain extends Base_AyActivity {
	private ListView lawAgainList;
	private List<Base_Entity> lawAgainDataList;
	private PopupWindow popupWindow;
	private Base_Entity againCheckEntity;
	private PopupWindow pop;
	public static final String refresh="Activity_CheckAgain.finish";
	private TmpBroadcastReceiver receiver;
	private Adapter_CheckAgainList adapter;

	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_again_list);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

		lawAgainDataList=new ArrayList<Base_Entity>();
		adapter = new Adapter_CheckAgainList(this,
				lawAgainDataList);

		initView();
		
		
	}
	
	public void initView(){
		lawAgainDataList.clear();
		againCheckEntity = new Entity_Check().init().setCreated(Static_InfoApp.create().getAccountId()).putlogic2value(16,"=", "1").putlogic2value(10,"<>", "1");
		List org = Help_DB.create().search(againCheckEntity);
		lawAgainDataList.addAll((List<Base_Entity>) org.get(1));
		adapter.notifyDataSetChanged();

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initView();
	}
	@Override
	public void initWidget() {
		lawAgainList=(ListView)findViewById(R.id.lawAgainList);
        lawAgainList.setAdapter(adapter);
	}
	class TmpBroadcastReceiver extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(refresh)) {
				Activity_CheckAgain.this.initView();
				try {
					Activity_CheckAgain.this.unregisterReceiver(receiver);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
		      }
			
		}
		
	}
}
