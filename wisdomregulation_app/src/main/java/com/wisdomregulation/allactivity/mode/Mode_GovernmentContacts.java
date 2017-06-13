package com.wisdomregulation.allactivity.mode;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_GovernmentContacts;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_Contact;
import com.wisdomregulation.help.Help_DB;

import java.util.ArrayList;
import java.util.List;

public class Mode_GovernmentContacts extends Base_AyActivity {
private ListView contactsList;
private Adapter_GovernmentContacts adapter;
private List<Base_Entity> contactslistdata;
private boolean isall = true;
private Base_Entity searchtarget;
EditText targetsearch;
private String keyword;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_contacts_list);

	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub

		contactslistdata = new ArrayList<Base_Entity>();
		adapter = new Adapter_GovernmentContacts(this, contactslistdata);

		searchtarget = new Entity_Contact().clear();
		targetsearch.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode!=KeyEvent.KEYCODE_BACK){
					if(!Mode_GovernmentContacts.this.isIsdialog()){
						if(targetsearch.getText().toString().equals("")){
							pageIndex=1;
							keyword = targetsearch.getText().toString();
							searchtarget.clear().setKeywordAll(keyword);
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
		
	}
	@Override
		public void initWidget() {
			// TODO Auto-generated method stub
		contactsList=(ListView)findViewById(R.id.contactsList);
		targetsearch=(EditText)findViewById(R.id.targetsearch);
		contactsList.setAdapter(adapter);
			initView();
			
		}
	@Override
	public void simpleSearch(View view) {
		// TODO Auto-generated method stub
		super.simpleSearch(view);
		pageIndex=1;
		keyword = targetsearch.getText().toString();
		searchtarget.clear().setKeyword(keyword);
		initView();
	}
	public void initView(){
		Mode_GovernmentContacts.this.showWait();
		setLimt();
		new Handler().postDelayed(new Runnable() {
			public void run() {
				
				
				org = Help_DB.create().setLimit(nowlmit).setNeedcount(true).setLogic("or").setIsregex(true)
						.setIsfull(isall).search(searchtarget);
				allpage = getAllPage(Integer.parseInt(org.get(0).toString()));
				contactslistdata.clear();
				contactslistdata.addAll((List<Base_Entity>) org.get(1));
				for (int i = 0; i < contactslistdata.size(); i++) {
					contactslistdata.get(i).setKeyword(keyword);
				}
				adapter.notifyDataSetChanged();
				pageDown.setText(pageIndex + "/" + allpage+"\n总条目:"+org.get(0).toString()+" 本页:"+contactslistdata.size());
				Mode_GovernmentContacts.this.dissmissWait();
			}
		}, 500);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	
}
