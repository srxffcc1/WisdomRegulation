package com.wisdomregulation.allactivity.single;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_PlanDetail;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.allactivity.mode.Mode_CheckMain;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_Check;
import com.wisdomregulation.data.entityother.Entity_Company;
import com.wisdomregulation.data.entityother.Entity_OrgData;
import com.wisdomregulation.data.entityother.Entity_Plan;
import com.wisdomregulation.dialog.Dialog_Tool;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_Finish;
import com.wisdomregulation.utils.Util_MatchTip;

import java.util.ArrayList;
import java.util.List;

public class Activity_PlanDetail extends Base_AyActivity {
	private ListView lawDetailList;
	private List<Base_Entity> lawDetaiDatalList;
	private Base_Entity check;
	private List<Object> org;
	private String idnoene="";
	public static final String refresh="Activity_CheckList.refresh";
	public static final String finish="Activity_CheckList.finish";
	private BaseAdapter adapter;
	private String planid;
	private TmpBroadcastReceiver receiver;
	TextView planadd;
	private String planstate;
	TextView more;
	private String areaid;
	private String areaid1;
	private String areaid2;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_check_list);
	}

	@Override
	public void initData() {

		receiver = new TmpBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(refresh);
		filter.addAction(finish);
		this.registerReceiver(receiver, filter);
		lawDetaiDatalList=new ArrayList<Base_Entity>();
		adapter = new Adapter_PlanDetail(
				Activity_PlanDetail.this, lawDetaiDatalList);

		planid = this.getIntent().getStringExtra("planid");
		areaid = this.getIntent().getStringExtra("areaid");
		checkPlanState();
		
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
	public void checkPlanState(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				planstate=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().search(new Entity_Plan().setId(planid))).getValue(5);
				if(planstate.equals("1")){
					Activity_PlanDetail.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							planadd.setText("退出查看");
							more.setVisibility(View.INVISIBLE);
							
						}
					});
				}else{
					Activity_PlanDetail.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							planadd.setText("新增检查企业");
							more.setVisibility(View.VISIBLE);
							
						}
					});
				}
			}
		}).start();
	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		lawDetailList=(ListView)findViewById(R.id.lawDetailList);
		planadd=(TextView)findViewById(R.id.planadd);
		more=(TextView)findViewById(R.id.more);
        lawDetailList.setAdapter(adapter);
		initView();
	}
	public void initView(){
		Activity_PlanDetail.this.showWait();
		new Handler().postDelayed(new Runnable() {
			


			@Override
			public void run() {
				check = new Entity_Check().init().put(0, planid);
				org = Help_DB.create().search(check);
				lawDetaiDatalList.clear();
				lawDetaiDatalList.addAll((List<Base_Entity>) org.get(1));
				Base_Entity area=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().setLimit("0,1").setLogic("and").searchFromResult(Help_DB.createS().justgetSqlUNION(new Entity_OrgData().setId(areaid),new Entity_OrgData().put(2, areaid)),new Entity_OrgData().put(1, "0b8415e08bf3474686e643318c0a497c")));
				areaid1 = area.getId();
				areaid2 = area.getValue(2);
				//System.out.println("查询出的代码:"+areaid1+","+areaid2);
				if(area.getValue(3).endsWith("市")){
					//System.out.println("查询出市级");
					areaid1="";
					areaid2="";
				}
				adapter.notifyDataSetChanged();
				Activity_PlanDetail.this.dissmissWait();
				idnoene="";
				for (int i = 0; i < lawDetaiDatalList.size(); i++) {
					idnoene=idnoene+lawDetaiDatalList.get(i).getValue(5)+",";
				}
				if(idnoene.length()>1){
					idnoene=idnoene.substring(0, idnoene.length()-1);
				}
				
			}
		}, 300);

	}
	@Override
	public void toMore(View view) {
		// TODO Auto-generated method stub
		super.toMore(view);
		if(planstate.equals("1")){
			
		}else{
			Activity_PlanDetail.this.showWait();
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					if(!Util_Finish.findPlanCheckIsAllFinish(new Entity_Plan().setId(planid))){
						Activity_PlanDetail.this.dissmissWait();
						Activity_PlanDetail.this.checkPlanState();
						Help_DB.create().save2update(new Entity_Plan().setId(planid).put(5, "1"));
						if(Util_Finish.findPlanIsNeedAgain(new Entity_Plan().setId(planid))){
							Activity_PlanDetail.this.runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									Dialog_Tool.showDialog_FinishPlan(Activity_PlanDetail.this, null);
									Activity_PlanDetail.this.sendBroadcast(new Intent(Activity_Plan.refresh));
									
								}
							});
							
						}else{
							
							Activity_PlanDetail.this.finish();
							Activity_PlanDetail.this.sendBroadcast(new Intent(Activity_Plan.refresh));
						}
					}else{
						Activity_PlanDetail.this.dissmissWait();
						Activity_PlanDetail.this.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								Toast.makeText(Activity_PlanDetail.this, "有检查未完成", Toast.LENGTH_LONG).show();
								
							}
						});
						
					}
					
				}
			}).start();
		}



	}
	public void addCompany(View view){
		if(planstate.equals("1")){
			this.finish();
		}else{
			Base_Entity companynone=new Entity_Company().init().setId(idnoene);
			this.startActivity(new Intent(this, Activity_ChoseCompany_ToCheck.class).putExtra("noidentity", companynone).putExtra("areaid2", areaid2).putExtra("areaid1", areaid1).putExtra("planid", planid));
		}

	}
	private class TmpBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(refresh)) {
				initView();
			}
			if (intent.getAction().equals(finish)) {
				Activity_PlanDetail.this.finish();
				Static_InfoApp.create().getContext().sendBroadcast(new Intent(Mode_CheckMain.nowcheckagain));
			}
			

		}

	}

}
