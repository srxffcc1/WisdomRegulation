package com.wisdomregulation.allactivity.mode;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_LibraryList_1;
import com.wisdomregulation.adapter.Adapter_LibraryList_2;
import com.wisdomregulation.adapter.Adapter_LibraryList_3;
import com.wisdomregulation.adapter.Adapter_LibraryList_4;
import com.wisdomregulation.adapter.Adapter_LibraryList_5;
import com.wisdomregulation.adapter.Adapter_LibraryList_6;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entitylibrary.Entity_LibraryDangerousCheck;
import com.wisdomregulation.data.entitylibrary.Entity_LibraryDangerousFlag;
import com.wisdomregulation.data.entitylibrary.Entity_LibraryLawDependence;
import com.wisdomregulation.data.entitylibrary.Entity_LibrarySafetyProduce;
import com.wisdomregulation.data.entitylibrary.Entity_LibrarySafetyProduceLaw;
import com.wisdomregulation.data.entitylibrary.Entity_LibraryTypicalCase;
import com.wisdomregulation.help.Help_DB;

import java.util.ArrayList;
import java.util.List;

public class Mode_Library extends Base_AyActivity{
	private TextView newtitle;
	private ListView libraryList;
	private int level;
	private Base_Entity searchtarget;
	private BaseAdapter adapter;
	private List<Base_Entity> baselist;
	private boolean isall = true;
	EditText targetsearch;
	private String keyword;
	@Override
	public void setRootView() {
		setContentView(R.layout.activity_library);
		
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		split=7;

		level = this.getIntent().getIntExtra("level", 1);
		baselist=new ArrayList<Base_Entity>();
		switch (level) {
		case 1:
			adapter=new Adapter_LibraryList_1(this, baselist);
			break;
		case 2:
			adapter=new Adapter_LibraryList_2(this, baselist);
			break;
		case 3:
			adapter=new Adapter_LibraryList_3(this, baselist);
			break;
		case 4:
			adapter=new Adapter_LibraryList_4(this, baselist);
			split=5;
			break;
		case 5:
			adapter=new Adapter_LibraryList_5(this, baselist);
			break;
		case 6:
			adapter=new Adapter_LibraryList_6(this, baselist);
			break;

		default:
			break;
		}
		switch (level) {
		case 1:
			searchtarget = new Entity_LibrarySafetyProduce().init();
			break;
		case 2:
			searchtarget = new Entity_LibraryDangerousFlag().clear();
			break;
		case 3:
			searchtarget = new Entity_LibraryDangerousCheck().clear();
			break;
		case 4:
			searchtarget = new Entity_LibraryLawDependence().clear();
			break;
		case 5:
			searchtarget = new Entity_LibraryTypicalCase().clear();
			break;
		case 6:
			searchtarget = new Entity_LibrarySafetyProduceLaw().clear();
			break;
		default:
			break;
		}
		


	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		newtitle=(TextView)findViewById(R.id.newtitle);
		libraryList=(ListView)findViewById(R.id.libraryList);
		targetsearch=(EditText)findViewById(R.id.targetsearch);
		libraryList.setAdapter(adapter);
		targetsearch.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode!=KeyEvent.KEYCODE_BACK){
					if(!Mode_Library.this.isIsdialog()){
						if(targetsearch.getText().toString().equals("")){
							pageIndex=1;
							keyword = targetsearch.getText().toString();
							switch (level) {
								case 1:
									searchtarget.clear().putlogic2value(0, "like", keyword);
									break;
								case 2:
									searchtarget.clear().putlogic2value(0, "like", keyword);
									break;
								case 3:
									searchtarget.clear().putlogic2value(0, "like", keyword);
									break;
								case 4:
									searchtarget.clear().putlogic2value(1, "like", keyword).putlogic2value(2, "like", keyword).putlogic2value(3, "like", keyword).putlogic2value(4, "like", keyword);
									break;
								case 5:
									searchtarget.clear().putlogic2value(0, "like", keyword);
									break;
								case 6:
									searchtarget.clear().putlogic2value(0, "like", keyword);
									break;

								default:
									break;
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
		switch (level) {
		case 1:
			newtitle.setText("安全生产标准库");
			break;
		case 2:
			newtitle.setText("危险化学品特征库");
			break;
		case 3:
			newtitle.setText("隐患排查标准库");
			break;
		case 4:
			newtitle.setText("执法依据库");
			break;
		case 5:
			newtitle.setText("典型事故案例库");
			break;
		case 6:
			newtitle.setText("安全生产法规库");
			break;

		default:
			break;
		}
		initView();
	}

	@Override
	public void simpleSearch(View view) {
		// TODO Auto-generated method stub
		super.simpleSearch(view);
		pageIndex=1;
		keyword = targetsearch.getText().toString();
		switch (level) {
		case 1:
			searchtarget.clear().putlogic2value(0, "like", keyword);
			break;
		case 2:
			searchtarget.clear().putlogic2value(0, "like", keyword);
			break;
		case 3:
			searchtarget.clear().putlogic2value(0, "like", keyword);
			break;
		case 4:
			searchtarget.clear().putlogic2value(1, "like", keyword).putlogic2value(2, "like", keyword).putlogic2value(3, "like", keyword).putlogic2value(4, "like", keyword);
			break;
		case 5:
			searchtarget.clear().putlogic2value(0, "like", keyword);
			break;
		case 6:
			searchtarget.clear().putlogic2value(0, "like", keyword);
			break;

		default:
			break;
		}
		
		initView();
	}
	public void initView(){
		Mode_Library.this.showWait();
		setLimt();

				new Thread(new Runnable() {
					
					@Override
					public void run() {
						org = Help_DB.create().setLimit(nowlmit).setNeedcount(true).setLogic("or").setIsregex(true)
								.setIsfull(isall).search(searchtarget);
						allpage = getAllPage(Integer.parseInt(org.get(0).toString()));
						Mode_Library.this.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								baselist.clear();
								baselist.addAll((List<Base_Entity>)org.get(1));
								for (int i = 0; i < baselist.size(); i++) {
									baselist.get(i).setKeyword(keyword);
								}
								adapter.notifyDataSetChanged();
								pageDown.setText(pageIndex + "/" + allpage+"\n总条目:"+org.get(0).toString()+" 本页:"+baselist.size());
								Mode_Library.this.dissmissWait();
								
							}
						});
					}
				}).start();

	}

}
