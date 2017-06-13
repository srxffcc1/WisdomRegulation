package com.wisdomregulation.allactivity.mode;

import android.os.Handler;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_BookPrinterCheckList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_Company;
import com.wisdomregulation.help.Help_DB;

import java.util.ArrayList;
import java.util.List;

public class Mode_BookPrinter extends Base_AyActivity {
	private ListView bookPrinterCompanyList;
	private List<Base_Entity> baselist;
	private BaseAdapter adapter;
	private Base_Entity searchtarget;
	private boolean isall = true;

	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_book_needprintlist);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

		searchtarget = new Entity_Company().init();
		baselist = new ArrayList<Base_Entity>();
		adapter = new Adapter_BookPrinterCheckList(Mode_BookPrinter.this,
				baselist);

	}

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		bookPrinterCompanyList=(ListView)findViewById(R.id.bookPrinterCompanyList);
        bookPrinterCompanyList.setAdapter(adapter);
		initView();
	}

	public void initView() {
		setLimt();
		Mode_BookPrinter.this.showWait();
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				org = Help_DB.create().setLimit(nowlmit).setNeedcount(true).setLogic("or").setIsregex(true)
						.setIsfull(isall).search(searchtarget);
				allpage = getAllPage(Integer.parseInt(org.get(0).toString()));
				baselist.clear();
				baselist.addAll((List<Base_Entity>) org.get(1));
				adapter.notifyDataSetChanged();
				pageDown.setText(pageIndex + "/" + allpage+"\n总条目:"+org.get(0).toString()+" 本页:"+baselist.size());
				Mode_BookPrinter.this.dissmissWait();
			}
		}, 300);

	}








}
