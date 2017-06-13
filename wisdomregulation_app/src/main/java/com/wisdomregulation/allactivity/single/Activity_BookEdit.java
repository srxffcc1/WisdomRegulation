package com.wisdomregulation.allactivity.single;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_BookList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_BookList;
import com.wisdomregulation.dialog.Dialog_Tool;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.helporg2017.Pdf_Shark2017;
import com.wisdomregulation.pop.Pop_Tool;
import com.wisdomregulation.staticlib.Static_ConstantLib;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_Db;
import com.wisdomregulation.utils.Util_MatchTip;
import com.wisdomregulation.utils.Util_Print;
import com.wisdomregulation.utils.Util_String;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Activity_BookEdit extends Base_AyActivity {
	private PopupWindow popupWindow;
	private ListView bookList;
	private LinearLayout history1;
	private LinearLayout history2;
	private Base_Entity booklistEntity;
	private List<Base_Entity> booklistdata;
	private TmpBroadcastReceiver receiver;
	private Adapter_BookList adapter;
	private int moremodel = 1;
	private PopupWindow pop;
	private String jianchaid;
	private String qiyeid;
	private String againchaid;
	private String companyname;
	private boolean ishistory;
	

	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_book_edit_list);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		

	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub

		booklistEntity = (Base_Entity) this.getIntent().getSerializableExtra(
				"bookList");
		ishistory = this.getIntent().getBooleanExtra("ishistory", false);

		companyname = this.getIntent().getStringExtra("companyname");
		jianchaid = booklistEntity.getValue(0);
		againchaid=	booklistEntity.getValue(1);
		qiyeid = booklistEntity.getValue(7);
		booklistdata = new ArrayList<Base_Entity>();
		adapter = new Adapter_BookList(this, booklistdata,ishistory);

		
	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		bookList=(ListView)findViewById(R.id.bookList);
		history1=(LinearLayout)findViewById(R.id.history1);
		history2=(LinearLayout)findViewById(R.id.history2);
        bookList.setAdapter(adapter);
		if(ishistory){
			history2.setVisibility(View.VISIBLE);
		}else{
			history1.setVisibility(View.VISIBLE);
		}

		initView();
	}

	@Override
	public void initView() {
		Activity_BookEdit.this.showWait();
		new Handler().postDelayed(new Runnable() {
			public void run() {
				if (!jianchaid.equals(" ")&&!jianchaid.equals("") && jianchaid != null) {
					List org = Help_DB.create().search(
							booklistEntity.init().put(0, jianchaid));
					booklistdata.clear();
					booklistdata.addAll((List<Base_Entity>) org.get(1));
					adapter.notifyDataSetChanged();
					
				}
				Activity_BookEdit.this.dissmissWait();
			}
		},300);

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

	public void addBook(View view) {
//		if (moremodel == 1) {
		
		receiver = new TmpBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Static_ConstantLib.AddBookNew);
		this.registerReceiver(receiver, filter);
		Dialog_Tool.showDialog_BookTypeNew(Activity_BookEdit.this,
					booklistdata);
		
	}

	@Override
	public void toMore(View view) {
		pop = Pop_Tool.pop_normal(this, view, R.layout.pop_book_prints);
	}

	public void cancelBook(View view) {
		
	}

	public void prints(View view) {
		Activity_BookEdit.this.showWait();
		receiver = new TmpBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Static_ConstantLib.PrintBook);
		this.registerReceiver(receiver, filter);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				adapter.prints();
				
			}
		},300);
		
		
	}
	public void prints(final boolean canprint){
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if(canprint){
					Activity_BookEdit.this.dissmissWait();
					Util_Print.print(Static_InfoApp.create().getPath()+"/preview.pdf",Activity_BookEdit.this,2103);
//					File file = new File(Static_InfoApp.create().getPath()+"/preview.pdf");
//					Intent i = new Intent(Intent.ACTION_VIEW);
//					i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//					i.setPackage(Static_ConstantLib.pack);
//					i.setDataAndType(Uri.fromFile(file), "application/pdf");
//					Activity_BookEdit.this.startActivity(i);
				}else{
					Activity_BookEdit.this.dissmissWait();
					Activity_BookEdit.this.showToast("未选择文书");
				}
				
			}
		}, 300);

			
		
	}
	public void delets(View view) {
		receiver = new TmpBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Static_ConstantLib.DeleteBook);
		this.registerReceiver(receiver, filter);
		Activity_BookEdit.this.showWait();
		final List<Base_Entity> deletelist = adapter.getSelectList();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < deletelist.size(); i++) {
					final int x = i;

					Util_Db.deleteFix(deletelist.get(x));

				}
				Static_InfoApp.create().getContext().sendBroadcast(new Intent(
						Static_ConstantLib.DeleteBook));
				

			}
		}).start();
//		adapter.clear();
//		if(deletelist.size()>0){
//			Static_InfoApp.create().getAllhandler().postDelayed(new Runnable() {
//				
//				@Override
//				public void run() {
//
//					Activity_BookEdit.this.initView();
//					
//				}
//			},300);
//			
//		}
		
	}

	public void exports(View view) {
		
		final File export=new File(Static_InfoApp.create().getPath()+"/ZhiExport/"+"/"+companyname+"_"+Util_String.getDate());
		if(!export.exists()){
			export.mkdirs();
		}
		final List<Base_Entity> deletelist=adapter.getSelectList();
		if(deletelist.size()>0){
			
		}
		Activity_BookEdit.this.showWaitSecondNotAuto(deletelist.size());
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < deletelist.size(); i++) {
					final Base_Entity book= Pdf_Shark2017.getEneityBook(deletelist.get(i).getValue(4));
					book.setId(deletelist.get(i).getValue(6));
					final Base_Entity search=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().search(book));
					String fileout=export.getAbsolutePath()+"/"+deletelist.get(i).getValue(4)+".pdf";
					Activity_BookEdit.this.addCount();
					if(Util_MatchTip.isnotnull(search)){


						Pdf_Shark2017.create().setFileout(fileout).open().printerMaster(search).close();
					
					}else{
						Pdf_Shark2017.create().setFileout(fileout).open().printerMaster(book.init()).close();
					}
					Activity_BookEdit.this.addCount();
				}
				Activity_BookEdit.this.dissmissWait();
				Activity_BookEdit.this.showToast("请在SD卡目录下ZhiExport下查看");
				
			}
		},300);
		

		
	}

	class TmpBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Static_ConstantLib.AddBookNew)) {
				final List<String> realChoseList = (List<String>) intent
						.getSerializableExtra("realChoseList");



				Static_InfoApp.create().getAllhandler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						for (int i = 0; i < realChoseList.size(); i++) {
							String id=realChoseList.get(i).split("@")[0].trim();
							String type=realChoseList.get(i).split("@")[1].trim();
							Base_Entity result=new Entity_BookList();
							result
							.put(6, id)
							.put(3, "待定")
							.put(4, type)
							.put(0, jianchaid)
							.put(1, againchaid)
							.put(7, qiyeid);
							Help_DB.create().save2update(result);								
						}
						Activity_BookEdit.this.initView();
						try {
							Activity_BookEdit.this.unregisterReceiver(receiver);
						} catch (Exception e) {
							// TODO: handle exception
						}
						
						
					}
				},300);

			}
			if (intent.getAction().equals(Static_ConstantLib.DeleteBook)) {				
				
				Activity_BookEdit.this.initView();
				try {
					Activity_BookEdit.this.unregisterReceiver(receiver);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}if(intent.getAction().equals(Static_ConstantLib.PrintBook)){
				if(intent.getBooleanExtra("canprint", false)){
					Activity_BookEdit.this.prints(true);
				}else{
					Activity_BookEdit.this.prints(false);
				}
				Activity_BookEdit.this.unregisterReceiver(receiver);
			}

		}

	}
}
