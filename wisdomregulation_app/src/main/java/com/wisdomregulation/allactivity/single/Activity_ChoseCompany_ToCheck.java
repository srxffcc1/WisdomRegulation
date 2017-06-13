package com.wisdomregulation.allactivity.single;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_ChoseCompanyList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_Company;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_InfoApp;

import java.util.ArrayList;
import java.util.List;

public class Activity_ChoseCompany_ToCheck extends Base_AyActivity {
ListView choseCheckList;
private Base_Entity noentity;
private BaseAdapter adapter;
private List<Base_Entity> canselectlist;
public static final String finish="Activity_ChoseCompany_ToCheck.finish";
private String planid;
private String noid;
private TmpBroadcastReceiver receiver;
private Base_Entity searchtarget;
private Base_Entity searchtarget2;
private Base_Entity searchtarget3;
private Base_Entity searchtarget4;
EditText targetsearch;
private String keyword;
private String areaid1;
private String areaid2;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_company_chosefromcheck);
		
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub

		split=8;
		planid = this.getIntent().getStringExtra("planid");
		areaid1 = this.getIntent().getStringExtra("areaid1");
		areaid2 = this.getIntent().getStringExtra("areaid2");
		noentity = (Base_Entity) this.getIntent().getSerializableExtra("noidentity");
		noid = noentity.getId();
		canselectlist=new ArrayList<Base_Entity>();
		adapter=new Adapter_ChoseCompanyList(this, canselectlist).setEditstate("1");

		receiver = new TmpBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(finish);
		this.registerReceiver(receiver, filter);
		searchtarget2=new Entity_Company().clear();
		searchtarget3=new Entity_Company().clear().puthacklogic(10, "or").putHack(10).put(10, areaid1+","+areaid2);
		searchtarget4=new Entity_Company().clear().puthacklogic(22, "or").putHack(22).put(22, areaid1+","+areaid2);
		searchtarget =new Entity_Company().clear().putHack(37).putlogic2value(37,"<>",noid);

	}
	//.puthacklogic(10, "or").putHack(10).put(10, areaid1+","+areaid2)
	@Override
	public void simpleSearch(View view) {
		// TODO Auto-generated method stub
		super.simpleSearch(view);
		pageIndex=1;
		keyword = targetsearch.getText().toString();
		searchtarget2.clear().putlogic2value(0, "like", keyword);
		initView();
	}
	public void initView(){
		Activity_ChoseCompany_ToCheck.this.showWait();
		setLimt();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						if(keyword==null||keyword.equals("")){
							org = Help_DB.create().setLimit(nowlmit).setNeedcount(true).setLogic("and").searchFromResult(Help_DB.createS().justgetSqlUNION(searchtarget3,searchtarget4),searchtarget);
						}else{
							org = Help_DB.create().setLimit(nowlmit).setNeedcount(true).setLogic("and").searchFromResult(Help_DB.createS().justgetSqlUNION(searchtarget3,searchtarget4),searchtarget2,searchtarget);
						}
						Activity_ChoseCompany_ToCheck.this.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								allpage = getAllPage(Integer.parseInt(org.get(0).toString()));
								canselectlist.clear();
								canselectlist.addAll((List<Base_Entity>) org.get(1));
								if(canselectlist.size()>0){
									Activity_ChoseCompany_ToCheck.this.dissmissWait();
								}else{
									Activity_ChoseCompany_ToCheck.this.showToast("无可选企业,自动关闭");
									Activity_ChoseCompany_ToCheck.this.dissmissWait();
									Activity_ChoseCompany_ToCheck.this.finish();
								}
								adapter.notifyDataSetChanged();
								pageDown.setText(pageIndex + "/" + allpage+"\n总条目:"+org.get(0).toString()+" 本页:"+canselectlist.size());
								Activity_ChoseCompany_ToCheck.this.dissmissWait();
								
							}
						});
						
					}
				}).start();
				
	}
	@Override
		public void initWidget() {
			// TODO Auto-generated method stub
		choseCheckList=(ListView)findViewById(R.id.choseCheckList);
		targetsearch=(EditText)findViewById(R.id.targetsearch);
        choseCheckList.setAdapter(adapter);
		targetsearch.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode!=KeyEvent.KEYCODE_BACK){
					if(!Activity_ChoseCompany_ToCheck.this.isIsdialog()){
						if(targetsearch.getText().toString().equals("")){
							pageIndex=1;
							keyword = targetsearch.getText().toString();
							searchtarget2.putlogic2value(0, "like", keyword);
							initView();
							((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
									.hideSoftInputFromWindow(getCurrentFocus()
													.getWindowToken(),
											InputMethodManager.HIDE_NOT_ALWAYS);
						}
					}
				}


				return false;
			}
		});
		initView();
			
		}
	@Override
		public void toMore(View view) {
		Activity_ChoseCompany_ToCheck.this.showWait();
		((Adapter_ChoseCompanyList)adapter).setPlanid(planid).getChoseResult();
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
	private class TmpBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(finish)) {
				Activity_ChoseCompany_ToCheck.this.dissmissWait();
				Static_InfoApp.create().getContext().sendBroadcast(new Intent(Activity_PlanDetail.refresh));
				Activity_ChoseCompany_ToCheck.this.finish();
				
			}


		}

	}
}
