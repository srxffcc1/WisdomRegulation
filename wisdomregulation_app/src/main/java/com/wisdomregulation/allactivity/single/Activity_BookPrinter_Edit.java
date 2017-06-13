package com.wisdomregulation.allactivity.single;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_ExpandDataBookList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.map.ExpandMap;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_File;
import com.wisdomregulation.utils.Util_MatchTip;
import com.wisdomregulation.utils.Util_Print;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Activity_BookPrinter_Edit extends Base_AyActivity {
ExpandableListView dataBookList;
	private Base_Entity boollist;
	private List<ExpandMap> expandlist;
	private Adapter_ExpandDataBookList adapter;
	private TmpBroadcastReceiver receiver;
	public static final String print = "Activity_BookPrinter_Edit.print";
	public boolean allchoice=false;

	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_book_printeredit_list);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

		boollist = (Base_Entity) this.getIntent().getSerializableExtra(
				"bookList");
		expandlist = new ArrayList<ExpandMap>();
		adapter = new Adapter_ExpandDataBookList(this, expandlist, dataBookList);

	}

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		dataBookList=(ExpandableListView)findViewById(R.id.dataBookList);
        dataBookList.setAdapter(adapter);
		initView();

	}
	public void initView(){
		Activity_BookPrinter_Edit.this.showWait();
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
				List<Base_Entity> baselist = Util_MatchTip.getSearchResult(Help_DB
						.create().search(boollist));
				List<Base_Entity> sortbaseList = Util_File
						.getSortEntityList(baselist);
				
				expandlist.clear();
				if (sortbaseList != null) {
					for (int i = 0; i < sortbaseList.size(); i++) {

						String datastring = new SimpleDateFormat("yyyy-MM-dd")
								.format(Long.parseLong(sortbaseList.get(i).getUpDatadate()));
						boolean has = false;
						if (expandlist.size() != 0) {
							for (int j = 0; j < expandlist.size(); j++) {
								String tmpname = expandlist.get(j).getName();
								if (tmpname.equals(datastring)) {
									has = true;
								} else {

								}
							}
						} else {
							has = false;
						}
						if (!has) {
							expandlist.add(new ExpandMap(datastring));
						}

					}
				}
				for (int i = 0; i < expandlist.size(); i++) {
					for (int j = 0; j < sortbaseList.size(); j++) {
							String tmpname = expandlist.get(i).getName();
							String datastring = new SimpleDateFormat("yyyy-MM-dd")
							.format(Long.parseLong(sortbaseList.get(j).getUpDatadate()));
							if (tmpname
									.equals(datastring)) {
								expandlist.get(i).add(
										new ExpandMap(datastring).setBaseentity(sortbaseList.get(j)));
												
							}
						}
					}
				adapter.notifyDataSetChanged();
				for (int i = 0; i < adapter.getGroupCount(); i++) {
					dataBookList.expandGroup(i);
				}
				dataBookList.setOnGroupClickListener(new OnGroupClickListener() {
					
					@Override
					public boolean onGroupClick(ExpandableListView parent, View v,
							int groupPosition, long id) {
						// TODO Auto-generated method stub
						return true;
					}
				});
				Activity_BookPrinter_Edit.this.dissmissWait();
				
				
			}
		}, 200);
	}

	public void prints(View view) {
		Activity_BookPrinter_Edit.this.showWait();
		receiver = new TmpBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(print);
		this.registerReceiver(receiver, filter);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if(allchoice){
					adapter.prints(true);
				}else{
					adapter.prints(false);
				}
				
				
			}
		},300);
	}
	public void prints(final boolean canprint){
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if(canprint){
					Activity_BookPrinter_Edit.this.dissmissWait();
					Util_Print.print(Static_InfoApp.create().getPath()+"/preview.pdf",Activity_BookPrinter_Edit.this,2102);
//					Activity_BookPrinter_Edit.this.dissmissWait();
//					File file = new File(Static_InfoApp.create().getPath()+"/preview.pdf");
//					Intent i = new Intent(Intent.ACTION_VIEW);
//					i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//					i.setPackage(Static_ConstantLib.pack);
//					i.setDataAndType(Uri.fromFile(file), "application/pdf");
//					Activity_BookPrinter_Edit.this.startActivity(i);
				}else{
					Activity_BookPrinter_Edit.this.dissmissWait();
					Activity_BookPrinter_Edit.this.showToast("未选择文书");
				}
				
			}
		}, 300);

			
		
	}
	@Override
		public void toMore(View view) {
			// TODO Auto-generated method stub
			super.toMore(view);
			adapter.checkAllParent();
			allchoice=!allchoice;
		}
	public void exports(View view) {

	}

	class TmpBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			if(intent.getAction().equals(Activity_BookPrinter_Edit.print)){
				if(intent.getBooleanExtra("canprint", false)){
					Activity_BookPrinter_Edit.this.prints(true);
				}else{
					Activity_BookPrinter_Edit.this.prints(false);
				}
				Activity_BookPrinter_Edit.this.unregisterReceiver(receiver);
			}

		}

	}
}
