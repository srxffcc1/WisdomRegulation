package com.wisdomregulation.allactivity.mode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_BombCompanyInfoList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_Company;
import com.wisdomregulation.help.Help_DB;

import java.util.ArrayList;
import java.util.List;

public class Mode_BombMain extends Base_AyActivity {
	private ListView companyViewList;
	private Base_Entity searchtarget2;
	private boolean isregex = false;
	private List<Base_Entity> companyList;
	private boolean needinit = true;
	private boolean isall = true;
	public static final String refreshadaptertolast = "Mode_BombMain.refreshadaptertolast";
	private Adapter_BombCompanyInfoList adapter;
	private TmpBroadcastReceiver receiver;
	private PopupWindow pop;
	EditText targetsearch;
	private String keyword;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_bomb_menu);
		
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

		companyList=new ArrayList<Base_Entity>();
		adapter = new Adapter_BombCompanyInfoList(
				Mode_BombMain.this, companyList);

		searchtarget2 = new Entity_Company().clear();

	}

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		companyViewList=(ListView)findViewById(R.id.companyList);
		targetsearch=(EditText)findViewById(R.id.targetsearch);
        companyViewList.setAdapter(adapter);
        targetsearch.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode!=KeyEvent.KEYCODE_BACK){
                    if(!Mode_BombMain.this.isIsdialog()){
                        if(targetsearch.getText().toString().equals("")){
                            pageIndex=1;
                            keyword = targetsearch.getText().toString();
                            searchtarget2.clear();
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
		this.registerReceiver(receiver, filter);
		initView();

	}
	
	public void initView() {
		Mode_BombMain.this.showWait();
		setLimt();
		new Thread(new Runnable() {

			@Override
			public void run() {
				if(keyword==null||keyword.equals("")){
					org = Help_DB.create().setLimit(nowlmit).setNeedcount(true).setLogic("and").query(getJsonString(),new Entity_Company());
				}else{
					org = Help_DB.create().setLimit(nowlmit).setNeedcount(true).setLogic("and").searchFromResult(searchtarget2,getJsonString());
				}
//				org = Help_DB.create().setLimit(nowlmit).setNeedcount(true)
//						.setIsfull(isall).search(searchtarget2);
				allpage = getAllPage(Integer.parseInt(org.get(0).toString()));
				Mode_BombMain.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						companyList.clear();
						companyList.addAll((List<Base_Entity>) org.get(1));
						for (int i = 0; i < companyList.size(); i++) {
							companyList.get(i).setKeyword(keyword);
						}
						adapter.notifyDataSetChanged();
						pageDown.setText(pageIndex + "/" + allpage+"\n总条目:"+org.get(0).toString()+" 本页:"+companyList.size());
						Mode_BombMain.this.dissmissWait();

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
		searchtarget2.clear().init(keyword).putlogicAll2("like");
		initView();
	}

	@Override
	public void toMore(View view) {
////		pop = Pop_Tool.pop_normal(this, view, R.layout.pop_mode_companylist);
//		this.startActivity(new Intent(this, Tab_CompanyInfo.class).putExtra("editState", 1).putExtra("companyEntity",
//				 new Entity_Company().init()));
	}

	private void refreshtoLast() {
		Mode_BombMain.this.showWait();
		new Handler().postDelayed(new Runnable() {
			public void run() {
				
				pageIndex = 1;
				setLimt();
				initlistnotrunable();
				pageIndex = allpage;
				setLimt();
				initlistnotrunable();
				Mode_BombMain.this.dissmissWait();
			}


		}, 300);
	}

	private void initlistnotrunable() {
		searchtarget2 = new Entity_Company();
		org = Help_DB.create().setLimit(nowlmit).setNeedcount(true)
				.setIsfull(isall).search(searchtarget2);
		allpage = getAllPage(Integer.parseInt(org.get(0).toString()));
		companyList = (List<Base_Entity>) org.get(1);
		for (int i = 0; i < companyList.size(); i++) {
			companyList.get(i).setKeyword(keyword);
		}
		adapter = new Adapter_BombCompanyInfoList(Mode_BombMain.this,
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
	public String getJsonString(){
		String result="";
		String alltable=getTableDetail(new Entity_Company());
		result="select distinct "+alltable+" from entity_company join entity_bomb on entity_company.zuzhijigoudaima=entity_bomb.qiyeid ";
		return result;
	}
	public String getTableDetail(Object object){
		if (object instanceof Base_Entity) {
			Base_Entity be = (Base_Entity) object;
			String tablename=Help_DB.getTableName(be);
			String str1 = ""+tablename+".id,"+tablename+".hashid,"+tablename+".datastate,"+tablename+".updatadate,"+tablename+".createdatadate,"+tablename+".tableid,"+tablename+".isadd,"+tablename+".created,";
			String str2 = "";
			for (int i = 0; i < be.size(); i++) {
				str2 = str2+tablename+"." + be.getField(i) + ",";
			}
			str2 = str2.substring(0, str2.length() - 1);
			return "" + str1 + str2 + "";
		} else {
			return "";
		}	
	}
	private class TmpBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(refreshadaptertolast)) {
				isregex = false;
				needinit = false;
				refreshtoLast();
			}

		}

	}
}
