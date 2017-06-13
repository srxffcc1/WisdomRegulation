package com.wisdomregulation.allactivity.tab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_BookList;
import com.wisdomregulation.adapter.Adapter_LevelList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_BookList;
import com.wisdomregulation.data.entityother.Entity_Law;
import com.wisdomregulation.dialog.Dialog_Tool;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.helporg2017.Pdf_Shark2017;
import com.wisdomregulation.staticlib.Static_ConstantLib;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_Db;
import com.wisdomregulation.utils.Util_MatchTip;
import com.wisdomregulation.utils.Util_Print;
import com.wisdomregulation.utils.Util_String;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Tab_Law extends Base_AyActivity{
ListView bookListView;
HorizontalScrollView levellist;
LinearLayout content;
LinearLayout history1;
LinearLayout history2;

private Base_Entity bookList;

private List<Base_Entity> booklistdata;
private List<Base_Entity> booklistdatafromhistory;
private String levelname;
private Adapter_LevelList adapterh;
private Adapter_BookList adapterv;
public  static String searchstringfield;
private TmpBroadcastReceiver receiver;
private String lawid;
private List<String> levellistdata;
private String qiyeid;

private boolean ishistory;
private String jianchaid;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.tab_law);
		
	}
	@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();

		}
	@Override
		public void initData() {
			// TODO Auto-generated method stub

			booklistdata=new ArrayList<Base_Entity>();
			bookList = (Base_Entity) this.getIntent().getSerializableExtra("bookList");
			ishistory = this.getIntent().getBooleanExtra("ishistory", false);

			jianchaid = bookList.getValue(0);
			lawid=bookList.getValue(2);
			qiyeid = bookList.getValue(7);
		}
	@Override
		public void initWidget() {
			// TODO Auto-generated method stub

		bookListView=(ListView)findViewById(R.id.bookList);
		levellist=(HorizontalScrollView)findViewById(R.id.levellist);
		content=(LinearLayout)findViewById(R.id.levelcontent);
		history1=(LinearLayout)findViewById(R.id.history1);
		history2=(LinearLayout)findViewById(R.id.history2);
		if(ishistory){
			history2.setVisibility(View.VISIBLE);
		}else{
			history1.setVisibility(View.VISIBLE);
		}
			levellistdata = new ArrayList<String>();
			levellistdata.add("立          案\n阶段");
			levellistdata.add("调查取证\n阶段");
			levellistdata.add("听证告知\n阶段");
			levellistdata.add("审查决定\n阶段");
			levellistdata.add("送达执行\n阶段");
			levellistdata.add("结案归档\n阶段");
			adapterv = new Adapter_BookList(this, booklistdata,ishistory);
			bookListView.setAdapter(adapterv);
			searchstringfield="立案阶段相关文书";
			initView();
		}
	
	public void scrollLeft(View view){
			levellist.scrollBy(-((int)(Static_InfoApp.create().getAppScreenWidth()/3.80)*3), 0);
	}
	@Override
		public void initView() {
					booklistdatafromhistory=Util_MatchTip.getSearchResult(Help_DB.create().search(new Entity_BookList().init().putlogic2value(0, "=", jianchaid).putlogic2value(3, "=", "待定")));
					
					adapterh = new Adapter_LevelList(Tab_Law.this, levellistdata, content, lawid, booklistdata, adapterv).initView();
					if(searchstringfield.equals("立案阶段相关文书")){
						adapterh.clickIndex(0);
					}
					else if(searchstringfield.equals("调查取证阶段相关文书")){
						adapterh.clickIndex(1);
					}
					else if(searchstringfield.equals("听证告知阶段相关文书")){
						adapterh.clickIndex(2);
					}
					else if(searchstringfield.equals("审查决定阶段相关文书")){
						adapterh.clickIndex(3);
					}
					else if(searchstringfield.equals("送达执行阶段相关文书")){
						adapterh.clickIndex(4);
					}
					else if(searchstringfield.equals("结案归档阶段相关文书")){
						adapterh.clickIndex(5);
					}


			
		}
	public void scrollRight(View view){
			levellist.scrollBy(((int)(Static_InfoApp.create().getAppScreenWidth()/3.80)*3), 0);
	}
	public void addBook(View view) {
		receiver = new TmpBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Static_ConstantLib.AddBookNew);
		filter.addAction(Static_ConstantLib.AddBookOld);
		this.registerReceiver(receiver, filter);
		Dialog_Tool.showDialog_BookTypefromOldOrfromNew(this, booklistdata,booklistdatafromhistory);
	}
	public void cancelBook(View view) {
		
	}
	@Override
		public void finish() {
			// TODO Auto-generated method stub
			
			Base_Entity booklistEntityzip = new Entity_BookList().init().put(2, lawid)
					.put(3, "结案归档阶段相关文书");
			List<Base_Entity> check=new ArrayList<Base_Entity>();
			check.clear();
			check.addAll(Util_MatchTip.getSearchResult(Help_DB.create()
					.search(booklistEntityzip)));
			if(check.size()==0){
				super.finish();
			}else{
				Dialog_Tool.showDialog_FinishLaw(this, new Entity_Law().setId(lawid).put(12, "归档阶段"));
			}
		}
	public void prints(View view) {
		Tab_Law.this.showWait();
		receiver = new TmpBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Static_ConstantLib.PrintBook);
		this.registerReceiver(receiver, filter);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				adapterv.prints();
				
			}
		},300);

	}
	public void prints(final boolean canprint){
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if(canprint){
					Tab_Law.this.dissmissWait();
//					File file = new File(Static_InfoApp.create().getPath()+"/preview.pdf");
//					Intent i = new Intent(Intent.ACTION_VIEW);
//					i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//					i.setPackage(Static_ConstantLib.pack);
//					i.setDataAndType(Uri.fromFile(file), "application/pdf");
//					Tab_Law.this.startActivity(i);
					Util_Print.print(Static_InfoApp.create().getPath()+"/preview.pdf",Tab_Law.this,2104);
				}else{
					Tab_Law.this.dissmissWait();
					Toast.makeText(Tab_Law.this, "未选择文书", Toast.LENGTH_SHORT).show();
				}
				
			}
		}, 300);

			
		
	}
	public void delets(View view) {
		receiver = new TmpBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Static_ConstantLib.DeleteBook);
		this.registerReceiver(receiver, filter);
		final List<Base_Entity> deletelist=adapterv.getSelectList();
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
	}

	public void exports(View view) {
		
		final File export=new File(Static_InfoApp.create().getPath()+"/ZhiExport/"+"/"+lawid+"_"+Util_String.getDate());
		if(!export.exists()){
			export.mkdirs();
		}
		final List<Base_Entity> deletelist=adapterv.getSelectList();
		if(deletelist.size()>0){
			
		}
		Tab_Law.this.showWaitSecondNotAuto(deletelist.size());
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < deletelist.size(); i++) {
					final Base_Entity book= Pdf_Shark2017.getEneityBook(deletelist.get(i).getValue(4));
					book.setId(deletelist.get(i).getValue(6));
					final Base_Entity search=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().search(book));
					String fileout=export.getAbsolutePath()+"/"+deletelist.get(i).getValue(4)+".pdf";
					Tab_Law.this.addCount();
					if(Util_MatchTip.isnotnull(search)){


						Pdf_Shark2017.create().setFileout(fileout).open().printerMaster(search).close();
					
					}else{
						Pdf_Shark2017.create().setFileout(fileout).open().printerMaster(book.init()).close();
					}
					Tab_Law.this.addCount();
				}
//				Activity_BookEdit.this.dissmissWait();
//				Toast.makeText(Activity_BookEdit.this, "请在SD卡目录下ZhiExport下查看", Toast.LENGTH_LONG).show();
				
			}
		},300);
		

		
	}
	class TmpBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Static_ConstantLib.AddBookNew)) {
				final List<String> realChoseList = (List<String>) intent
						.getSerializableExtra("realChoseList");

						for (int i = 0; i < realChoseList.size(); i++) {
							String id=realChoseList.get(i).split("@")[0].trim();
							String type=realChoseList.get(i).split("@")[1].trim();

							Base_Entity result=new Entity_BookList();
							result	
									.put(6, id)
									.put(4, type)
									.put(3, Tab_Law.searchstringfield)
									.put(2, lawid)
									.put(7, qiyeid)
									.put(0, jianchaid);
									Help_DB.create().save2update(result);
						}

				Static_InfoApp.create().getAllhandler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						if(realChoseList.size()>0){
							Tab_Law.this.initView();
						}else{
							
						}
						
						try {
							Tab_Law.this.unregisterReceiver(receiver);
						} catch (Exception e) {
							// TODO: handle exception
						}
						
						
					}
				},300);

				
			}
			if (intent.getAction().equals(Static_ConstantLib.AddBookOld)) {	
				final List<String> realChoseList = (List<String>) intent
						.getSerializableExtra("realChoseList");


						for (int i = 0; i < realChoseList.size(); i++) {
							String id=realChoseList.get(i);
							Base_Entity result=new Entity_BookList();
							result.setId(id)
									.put(3, Tab_Law.searchstringfield)
									.put(2, lawid)
									.put(7, qiyeid)
									.put(0, jianchaid);
									Help_DB.create().save2update(result);
						}

				Static_InfoApp.create().getAllhandler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						if(realChoseList.size()>0){
							Tab_Law.this.initView();
						}else{
							
						}
						
						try {
							Tab_Law.this.unregisterReceiver(receiver);
						} catch (Exception e) {
							// TODO: handle exception
						}
						
						
					}
				},300);
				
			}
			if (intent.getAction().equals(Static_ConstantLib.DeleteBook)) {				
				
				Tab_Law.this.initView();
				try {
					Tab_Law.this.unregisterReceiver(receiver);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}if(intent.getAction().equals(Static_ConstantLib.PrintBook)){
				if(intent.getBooleanExtra("canprint", false)){
					Tab_Law.this.prints(true);
				}else{
					Tab_Law.this.prints(false);
				}
				Tab_Law.this.unregisterReceiver(receiver);
			}

		}

	}
	
}
