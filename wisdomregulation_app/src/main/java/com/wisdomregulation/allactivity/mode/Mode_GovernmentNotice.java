package com.wisdomregulation.allactivity.mode;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_GovernmentNotice;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_Notice;
import com.wisdomregulation.help.Help_DB;

import java.util.ArrayList;
import java.util.List;

public class Mode_GovernmentNotice extends Base_AyActivity {
	private ListView noticeList;
	private Adapter_GovernmentNotice adapter;
	private List<Base_Entity> contactslistdata;
	private boolean isall = true;
	private Base_Entity searchtarget;
	EditText targetsearch;
	private String keyword;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_notice_list);

	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub

		searchtarget = new Entity_Notice().clear();
		contactslistdata = new ArrayList<Base_Entity>();
		adapter = new Adapter_GovernmentNotice(this, contactslistdata);

		targetsearch.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode!=KeyEvent.KEYCODE_BACK){
					if(!Mode_GovernmentNotice.this.isIsdialog()){
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
		noticeList=(ListView)findViewById(R.id.noticeList);
		targetsearch=(EditText)findViewById(R.id.targetsearch);
		noticeList.setAdapter(adapter);
		initView();
		
	}
public void initView(){
	Mode_GovernmentNotice.this.showWait();
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
			Mode_GovernmentNotice.this.dissmissWait();
		}
	}, 500);
}
@Override
public void simpleSearch(View view) {
	// TODO Auto-generated method stub
	super.simpleSearch(view);
	pageIndex=1;
	keyword = targetsearch.getText().toString();
	searchtarget.clear().setKeywordAll(keyword);
	initView();
}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();

}

}
