package com.wisdomregulation.allactivity.single;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_PlanList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_Plan;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_InfoApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_Plan extends Base_AyActivity{
private ListView lawList;
private List<Base_Entity> lawDataList;
private List<Object> org=new ArrayList<Object>();
private PopupWindow popupWindow;
private Adapter_PlanList adapter;
public static final String refresh="Activity_Plan.refresh";
	public static  Map<String,String> areamap=new HashMap<>();
private TmpBroadcastReceiver receiver;
private Base_Entity target1;//检查者
private Base_Entity target2;//创造者
TextView add2finishplanarea;
	@Override
	public void setRootView() {
		if(Static_InfoApp.create().isshow()){//是否为演示版本需要进行添加计划操作
			this.setContentView(R.layout.activity_paln_list_debug);
		}else{
			this.setContentView(R.layout.activity_paln_list);
		}
		
	}
	@Override
		public void initData() {
			// TODO Auto-generated method stub

//			plan = new Entity_Plan().init().putlogic2value(6, "=", Static_InfoApp.create().getAccountName());

			lawDataList=new ArrayList<Base_Entity>();
			adapter = new Adapter_PlanList(Activity_Plan.this, lawDataList);

			target1=new Entity_Plan().init().putHack(3).puthacklogic(3, "or").putlogic2value(3, "like", Static_InfoApp.create().getAccountId()+","+Static_InfoApp.create().getAccountOrg()+","+Static_InfoApp.create().getAccountDeptOnly()).putlogic2value(5, "<>", "1");
		if(Static_InfoApp.create().getUserAuthority().equals("super")){
			target2=new Entity_Plan().init().putlogic2value(5,"<>","1");
		}else{
			target2=new Entity_Plan().init().setCreated(Static_InfoApp.create().getAccountId()).putlogic2value(5, "<>", "1");
		}

		}
	public void initView(){
		Activity_Plan.this.showWait();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				org=Help_DB.create()
						.query(Help_DB.createS().justgetSqlUNION(target1,target2),target1);
						
				Activity_Plan.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						lawDataList.clear();
						lawDataList.addAll((List<Base_Entity>) org.get(1));
						adapter.notifyDataSetChanged();
						Activity_Plan.this.dissmissWait();
						
					}
				});
			}
		}).start();
				



	}
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			receiver = new TmpBroadcastReceiver();
			IntentFilter filter = new IntentFilter();
			filter.addAction(refresh);
			this.registerReceiver(receiver, filter);
		}
	@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();

			
		}
	@Override
		public void initWidget() {
			// TODO Auto-generated method stub

		lawList=(ListView)findViewById(R.id.lawList);
		add2finishplanarea=(TextView)findViewById(R.id.add2finishplanarea);
		lawList.setAdapter(adapter);
			if(Static_InfoApp.create().ismarker()){
				add2finishplanarea.setText("新增执法计划");
			}else{
				add2finishplanarea.setText("完成执法计划");
			}
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
	public void add(View view){
//		Dialog_Tool.showDialog_PlanOrOnlyCheck(this,null);
		if(Static_InfoApp.create().istest()){
			this.startActivity(new Intent(this,Activity_PlanInsert.class).putExtra("entityPlan", new Entity_Plan()));
		}else{
			
		}
		

	}
	public void delete(View view){
		
		popupWindow.dismiss();
	}
	class TmpBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent.getAction().equals(refresh)) {
				initView();
			}

		}

	}
}
