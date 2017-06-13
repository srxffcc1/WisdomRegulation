package com.wisdomregulation.allactivity.mode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_CompanyInfoList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_Company;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_InfoApp;

import java.util.ArrayList;
import java.util.List;

public class Mode_CompanyInfo extends Base_AyActivity {
	private ListView companyViewList;
	private Base_Entity searchtarget;
	private boolean isregex = false;
	private List<Base_Entity> companyList;
	private SwipeRefreshLayout swpierefresh;
	private boolean needinit = true;
	private boolean isall = true;
	public static final String refreshadaptertolast = "Mode_CompanyInfoActivity.refreshadaptertolast";
	public static final String refreshadapter = "Mode_CompanyInfoActivity.refreshadapter";
	private Adapter_CompanyInfoList adapter;
	private TmpBroadcastReceiver receiver;
	private PopupWindow pop;
	EditText targetsearch;
	private String keyword;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_company_list);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

		companyList=new ArrayList<Base_Entity>();
		adapter = new Adapter_CompanyInfoList(
				Mode_CompanyInfo.this, companyList);

		String userauthor=Static_InfoApp.create().getUserAuthority();
		if(userauthor.equals("super")){
			searchtarget = new Entity_Company().clear();
		}else{
			searchtarget = new Entity_Company().clear().putHack(49).puthacklogic(49, "or").put("主管部门", Static_InfoApp.create().getAccountDept()+","+Static_InfoApp.create().getAccountNameOnly());
		}
		

	}

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		companyViewList=(ListView)findViewById(R.id.companyList);
		swpierefresh=(SwipeRefreshLayout)findViewById(R.id.swpierefresh);
		targetsearch=(EditText)findViewById(R.id.targetsearch);
		companyViewList.setAdapter(adapter);
		targetsearch.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode!=KeyEvent.KEYCODE_BACK){
					if(!Mode_CompanyInfo.this.isIsdialog()){
						if(targetsearch.getText().toString().equals("")){
							pageIndex=1;
							keyword = targetsearch.getText().toString();
							String userauthor=Static_InfoApp.create().getUserAuthority();
							if(userauthor.equals("super")){
								searchtarget = new Entity_Company().clear().put(0, keyword);
							}else{
								searchtarget.clear().putHack(49).puthacklogic(49, "or").put("主管部门", Static_InfoApp.create().getAccountDept()+","+Static_InfoApp.create().getAccountNameOnly()).put(0, keyword);
							}
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
		receiver = new TmpBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(refreshadaptertolast);
		filter.addAction(refreshadapter);
		this.registerReceiver(receiver, filter);
		initView();
		swpierefresh.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				swpierefresh.setRefreshing(false);
				initView();
				
			}
		});

	}
	
	public void initView() {
		Mode_CompanyInfo.this.showWait();
		setLimt();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						org = Help_DB.create().setLimit(nowlmit).setNeedcount(true).setLogic("and").setIsregex(true)
								.setIsfull(isall).search(searchtarget);
						allpage = getAllPage(Integer.parseInt(org.get(0).toString()));
						Mode_CompanyInfo.this.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								companyList.clear();
								companyList.addAll((List<Base_Entity>) org.get(1));
								for (int i = 0; i < companyList.size(); i++) {
									companyList.get(i).setKeyword(keyword);
								}
								Mode_CompanyInfo.this.dissmissWait();
								adapter.notifyDataSetChanged();
								pageDown.setText(pageIndex + "/" + allpage+"\n总条目:"+org.get(0).toString()+" 本页:"+companyList.size());
								
							}
						});
						
					}
				}).start();
	}
	@Override
	public void simpleSearch(View view) {
		// TODO Auto-generated method stub
		super.simpleSearch(view);
		pageIndex=1;
		keyword = targetsearch.getText().toString();
		String userauthor=Static_InfoApp.create().getUserAuthority();
		if(userauthor.equals("super")){
			searchtarget = new Entity_Company().clear().put(0, keyword);
		}else{
			searchtarget.clear().putHack(49).puthacklogic(49, "or").put("主管部门", Static_InfoApp.create().getAccountDept()+","+Static_InfoApp.create().getAccountNameOnly()).put(0, keyword);
		}
		
		initView();
	}

	@Override
	public void toMore(View view) {
//		pop = Pop_Tool.pop_normal(this, view, R.layout.pop_mode_companylist);
		//屏蔽添加企业
//		this.startActivity(new Intent(this, Tab_CompanyInfo.class).putExtra("editState", 1).putExtra("companyEntity",
//				 new Entity_Company().init()));
	}
	private void refreshtoLast() {
		Mode_CompanyInfo.this.showWait();
		new Handler().postDelayed(new Runnable() {
			public void run() {
				
				pageIndex = 1;
				setLimt();
				initlistnotrunable();
				pageIndex = allpage;
				setLimt();
				initlistnotrunable();
				Mode_CompanyInfo.this.dissmissWait();
			}


		}, 300);
	}

	private void initlistnotrunable() {
		searchtarget = new Entity_Company();
		org = Help_DB.create().setLimit(nowlmit).setNeedcount(true)
				.setIsfull(isall).search(searchtarget);
		allpage = getAllPage(Integer.parseInt(org.get(0).toString()));
		companyList = (List<Base_Entity>) org.get(1);
		for (int i = 0; i < companyList.size(); i++) {
			companyList.get(i).setKeyword(keyword);
		}
		adapter = new Adapter_CompanyInfoList(Mode_CompanyInfo.this,
				companyList);
		companyViewList.setAdapter(adapter);
		pageDown.setText(pageIndex + "/" + allpage+"\n总条目:"+org.get(0).toString()+" 本页:"+companyList.size());
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (pop != null) {
			pop.dismiss();
		}
	}



	public void delete(View view) {

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


	private class TmpBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(refreshadaptertolast)) {
				isregex = false;
				needinit = false;
				refreshtoLast();
			}
			if (intent.getAction().equals(refreshadapter)) {
				needinit = false;
				initView();
			}
//			if (intent.getAction().equals(research)) {
//				isregex = true;
//				searchtarget = (Base_Entity) intent
//						.getSerializableExtra("searchtarget");
//
//			}

		}

	}
}
