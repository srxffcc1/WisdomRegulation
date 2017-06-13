package com.wisdomregulation.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.artifex.mupdfdemo.MuPDFActivity;
import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.single.Activity_BookPrinter_Edit;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.frame.AutoCheckBox;
import com.wisdomregulation.frame.OnCheckedChangeListener;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.helporg2017.Pdf_Shark2017;
import com.wisdomregulation.map.ExpandMap;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_ExpandDataBookList extends BaseExpandableListAdapter{
private Activity context;
private List<ExpandMap> expandMap;
private String type;
private ExpandableListView expandlistview;
private Map<Integer,AutoCheckBox> checkmapp=new HashMap<Integer, AutoCheckBox>();
private Map<String,AutoCheckBox> checkmap=new HashMap<String, AutoCheckBox>();
private Map<String,Boolean> checkmapbool=new HashMap<String, Boolean>();
private Map<Integer,Boolean> checkmapboolp=new HashMap<Integer, Boolean>();
	public Adapter_ExpandDataBookList(Activity context,
			List<ExpandMap> expandMap,ExpandableListView expandlistview) {
	super();
	this.context = context;
	this.expandMap = expandMap;
	this.expandlistview=expandlistview;	
}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return expandMap.size();
		
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return expandMap.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return expandMap.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return expandMap.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return Long.parseLong(groupPosition+""+childPosition);
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}
	public List<Base_Entity> prints(boolean isall){
		final List<Base_Entity> resultlist=new ArrayList<Base_Entity>();
		if(isall){
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					boolean canprint=false;
					Pdf_Shark2017.create().setFileout(Static_InfoApp.create().getPath()+"/preview.pdf").open();
					for (int i = 0; i < expandMap.size(); i++) {
						for (int j = 0; j < expandMap.get(i).size(); j++) {
							canprint=true;
							Base_Entity booklisttmp=expandMap.get(i).get(j).getBaseentity();
					    	Base_Entity book=Pdf_Shark2017.getEneityBook(booklisttmp.getValue(4)).setId(booklisttmp.getValue(6));
					    	Base_Entity resultbook=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().search(book));
							if (Util_MatchTip.isnotnull(resultbook)) {
								Pdf_Shark2017.create().printerMaster(resultbook);
							} else {
								Pdf_Shark2017.create().printerMaster(book.init());
							}
							
						}
					}
					Pdf_Shark2017.create().close();
					if(canprint){
						Static_InfoApp.create().getContext().sendBroadcast(new Intent(Activity_BookPrinter_Edit.print).putExtra("canprint", true));
					}else{
						Static_InfoApp.create().getContext().sendBroadcast(new Intent(Activity_BookPrinter_Edit.print).putExtra("canprint", false));
					}
					
				}
			}).start();

		}else{
			new Thread(new Runnable() {

				@Override
				public void run() {
					boolean canprint=false;
					Pdf_Shark2017.create().setFileout(Static_InfoApp.create().getPath()+"/preview.pdf").open();
					for (Map.Entry<String, Boolean> entry : checkmapbool.entrySet()) {
					if(entry.getValue()){
					canprint=true;
			    	String[] split=entry.getKey().split("@");
			    	Integer parentint=Integer.parseInt(split[0].trim());
			    	Integer clidint=Integer.parseInt(split[1].trim());
			    	Base_Entity booklisttmp=expandMap.get(parentint).get(clidint).getBaseentity();
			    	Base_Entity book=Pdf_Shark2017.getEneityBook(booklisttmp.getValue(4)).setId(booklisttmp.getValue(6));
			    	Base_Entity resultbook=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().search(book));
					if (Util_MatchTip.isnotnull(resultbook)) {
						Pdf_Shark2017.create().printerMaster(resultbook);
					} else {
						Pdf_Shark2017.create().printerMaster(book.init());
					}
					
					}

				}
					Pdf_Shark2017.create().close();
					
					if(canprint){
						Static_InfoApp.create().getContext().sendBroadcast(new Intent(Activity_BookPrinter_Edit.print).putExtra("canprint", true));
					}else{
						Static_InfoApp.create().getContext().sendBroadcast(new Intent(Activity_BookPrinter_Edit.print).putExtra("canprint", false));
					}
				}
			}).start();
		}
		return resultlist;
	}
	@Override
	public View getGroupView(final int groupPosition, final boolean isExpanded,
			View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_databook_parent, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/19)));
		((TextView)convertView.findViewById(R.id.inspectOption)).setText(expandMap.get(groupPosition).getName());
		AutoCheckBox check=(AutoCheckBox) convertView.findViewById(R.id.fullchoice);
		checkmapp.put(groupPosition, check);
		
		check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(View buttonView, boolean isChecked) {
				if(isChecked){
					checkGroupTrue(groupPosition);
				}else{
					checkGroupFalse(groupPosition);
				}
				checkmapboolp.put(groupPosition, isChecked);
				
			}
		});
		if(checkmapboolp.get(groupPosition)!=null&&checkmapboolp.get(groupPosition)){
			check.setChecked(true);
		}else{
			check.setChecked(false);
		}

		return convertView;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_book_printerlist, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/14)));
		AutoCheckBox checks=(AutoCheckBox) convertView.findViewById(R.id.gtmp2);
		checkmap.put(groupPosition+"@"+childPosition, checks);

		((TextView) convertView.findViewById(R.id.bookName)).setText(expandMap.get(groupPosition).get(childPosition).getBaseentity().getValue(4));
		convertView.findViewById(R.id.perview).setOnClickListener(new OnClickListener() {
			
		@Override
		public void onClick(View v) {
			Static_InfoApp.create().getAllhandler().post(new Runnable(){
				public void run(){
					final Base_Entity book=Pdf_Shark2017.getEneityBook(expandMap.get(groupPosition).get(childPosition).getBaseentity().getValue(4));
					book.setId(expandMap.get(groupPosition).get(childPosition).getBaseentity().getValue(6));
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
		checks.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(View buttonView, boolean isChecked) {
				checkmapbool.put(groupPosition+"@"+childPosition, isChecked);
				
			}
		});
		if(checkmapbool.get(groupPosition+"@"+childPosition)!=null&&checkmapbool.get(groupPosition+"@"+childPosition)){
			checks.setChecked(true);
		}else{
			checks.setChecked(false);
		}
		if(checkmapboolp.get(groupPosition)!=null&&checkmapboolp.get(groupPosition)){
			checks.setChecked(true);
			checks.setEnabled(false);
		}
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	public void checkGroupTrue(int groupPosition){
		for (Map.Entry<String, AutoCheckBox> entry : checkmap.entrySet()) {  
				if(entry.getKey().startsWith(groupPosition+"@")){
					entry.getValue().setChecked(true);
					entry.getValue().setEnabled(false);
				}
			}

	}
	public void checkGroupFalse(int groupPosition){
		for (Map.Entry<String, AutoCheckBox> entry : checkmap.entrySet()) {  
				if(entry.getKey().startsWith(groupPosition+"@")){
					
					entry.getValue().setEnabled(true);
				}
			}

	}
	public void checkAllParent(){
		for (Map.Entry<Integer, AutoCheckBox> entry : checkmapp.entrySet()) {  
			entry.getValue().setChecked(true);
		}
	}

}
