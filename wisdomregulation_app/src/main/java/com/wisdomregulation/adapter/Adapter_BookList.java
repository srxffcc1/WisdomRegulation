package com.wisdomregulation.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artifex.mupdfdemo.MuPDFActivity;
import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.single.Activity_BookEdit_Detail;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.frame.AutoCheckBox;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.helporg2017.Pdf_Shark2017;
import com.wisdomregulation.staticlib.Static_ConstantLib;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_BookList extends BaseAdapter {
private Activity context;
private List<Base_Entity> booklistdata;
private boolean moreflag=false;
private Map<Integer,Boolean> checkmap=new HashMap<Integer, Boolean>();
private boolean ishistory;
public Adapter_BookList(Activity context,
		List<Base_Entity> booklistdata,boolean ishistory) {
	super();
	this.context = context;
	this.booklistdata = booklistdata;
	this.ishistory=ishistory;
	checkmap.clear();
}

	public boolean isMoreflag() {
		return moreflag;
	}
    public void clear(){
    	checkmap.clear();
    }
	public List<Base_Entity> getSelectList(){
		List<Base_Entity> baselist=new ArrayList<Base_Entity>();
		for (Map.Entry<Integer, Boolean> entry:checkmap.entrySet()) {
			if(entry.getValue()){
				baselist.add(booklistdata.get(entry.getKey()));
			}
		}

		return baselist;
	}
	@Override
		public void notifyDataSetChanged() {
			// TODO Auto-generated method stub
			super.notifyDataSetChanged();
			checkmap.clear();
		}
	public void prints(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				boolean canprint=false;
				Pdf_Shark2017.create().setFileout(Static_InfoApp.create().getPath()+"/preview.pdf").open();
				for (Map.Entry<Integer, Boolean> entry:checkmap.entrySet()) {
					if(entry.getValue()){
						canprint=true;
						Base_Entity book=Pdf_Shark2017.getEneityBook(booklistdata.get(entry.getKey()).getValue(4));
						book.setId(booklistdata.get(entry.getKey()).getValue(6));
						final Base_Entity search=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().search(book));
						if(Util_MatchTip.isnotnull(search)){
							Pdf_Shark2017.create().printerMaster(search);
						}
						else{
							Pdf_Shark2017.create().printerMaster(book.init());
						}
					}
					
				}
				Pdf_Shark2017.create().close();
				
				if(canprint){
					Static_InfoApp.create().getContext().sendBroadcast(new Intent(Static_ConstantLib.PrintBook).putExtra("canprint", true));
				}else{
					Static_InfoApp.create().getContext().sendBroadcast(new Intent(Static_ConstantLib.PrintBook).putExtra("canprint", false));
				}
				
			}
		}).start();

		
	}
	public Adapter_BookList setMoreflag(boolean moreflag) {
		this.moreflag = moreflag;
		return this;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return booklistdata!=null?booklistdata.size():0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return booklistdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView!=null){
			holder=(ViewHolder)convertView.getTag();
		}else{
			holder = new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_book_list, null);
			convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/7)));
			holder.vhbookName=(TextView) convertView.findViewById(R.id.bookName);
			holder.vcheck=(AutoCheckBox) convertView.findViewById(R.id.gtmp2);
			Util_MatchTip.initAllScreenText(convertView);
			convertView.setTag(holder);
		}
			LinearLayout history=(LinearLayout) convertView.findViewById(R.id.history);
			if(ishistory){
				history.setVisibility(View.GONE);
			}
			convertView.findViewById(R.id.perview).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Static_InfoApp.create().getAllhandler().post(new Runnable(){
					public void run(){
						final Base_Entity book=Pdf_Shark2017.getEneityBook(booklistdata.get(position).getValue(4));
						book.setId(booklistdata.get(position).getValue(6));
						final Base_Entity search=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().search(book));
						if(Util_MatchTip.isnotnull(search)){
							Pdf_Shark2017.create().setFileout(Static_InfoApp.create().getPath()+"/preview.pdf").open().printerMaster(search).close();
						
						}
						else{
							Pdf_Shark2017.create().setFileout(Static_InfoApp.create().getPath()+"/preview.pdf").open().printerMaster(book.init()).close();
						}
						Intent intent = new Intent(context,
						MuPDFActivity.class);
						intent.setAction(Intent.ACTION_VIEW);
						intent.setData(Uri.fromFile(new File(Static_InfoApp.create().getPath()+"/preview.pdf")));
						context.startActivity(intent);						
					}
				});			
			}
		});
			convertView.findViewById(R.id.passtobook).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v)
				{
					final Base_Entity book=Pdf_Shark2017.getEneityBook(booklistdata.get(position).getValue(4));
					book.setId(booklistdata.get(position).getValue(6));
					final Base_Entity search=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().search(book));
					int editState=1;//假设为新增的
					if(Util_MatchTip.isnotnull(search)){
						editState=0;//发现已经存在
					}
					context.startActivity(new Intent(context, Activity_BookEdit_Detail.class).putExtra("searchbook", book).putExtra("editState", editState));
					
				}
			});
//			if(moreflag){
//			final CheckBox more=(CheckBox) convertView.findViewById(R.id.bookmorecheck);
//			more.setVisibility(View.VISIBLE);
//			more.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//				
//				@Override
//				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//					checkmoreMap.put(position+"", more);
//				}
//			});
//			
//		}

		holder.vcheck.setOnCheckedChangeListener(new com.wisdomregulation.frame.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(View buttonView, boolean isChecked) {
				if(isChecked){
					checkmap.put(position, true);
				}else{
					checkmap.put(position, false);
				}
				
				
			}
		});
		if(checkmap.get(position)!=null&&checkmap.get(position)){
			holder.vcheck.setChecked(true);
		}else{
			checkmap.put(position, false);
			holder.vcheck.setChecked(false);
		}
		holder.vhbookName.setText(booklistdata.get(position).getValue(4));

//		
		
//		
		return convertView;
	}
	static class ViewHolder{
		TextView vhbookName;
		AutoCheckBox vcheck;
	}
}
