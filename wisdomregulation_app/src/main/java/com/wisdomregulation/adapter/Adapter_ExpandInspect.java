package com.wisdomregulation.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.single.Activity_CheckOptionDetail;
import com.wisdomregulation.map.ExpandMap;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;
import com.wisdomregulation.utils.Util_String;

import java.util.List;

public class Adapter_ExpandInspect extends BaseExpandableListAdapter{
private Activity context;
private List<ExpandMap> expandMap;
private ExpandableListView expandlistview;
private boolean ishistory;
private String checkid;
private String qiyecode;
private List<String> issave;

	public Adapter_ExpandInspect(Activity context,
			List<ExpandMap> expandMap,ExpandableListView expandlistview,boolean ishistory) {
	super();
	this.context = context;
	this.expandMap = expandMap;
	this.expandlistview=expandlistview;
	this.ishistory=ishistory;
}

	public String getCheckid() {
		return checkid;
	}

	public Adapter_ExpandInspect setIssave(List<String> issave) {
		this.issave = issave;
		return this;
	}

	public Adapter_ExpandInspect setCheckid(String checkid) {
		this.checkid = checkid;
		return this;
	}

	public String getQiyecode() {
		return qiyecode;
	}

	public Adapter_ExpandInspect setQiyecode(String qiyecode) {
		this.qiyecode = qiyecode;
		return this;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return expandMap==null?0:expandMap.size();
		
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

	@Override
	public View getGroupView(final int groupPosition, final boolean isExpanded,
			View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_check_parent, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/14.1)));
		TextView text=((TextView)convertView.findViewById(R.id.inspectOption));
		TextView textsave=(TextView) convertView.findViewById(R.id.issave);
		if(issave.size()>0){
			int needshowcount=0;
			for (int i = 0; i < expandMap.get(groupPosition).size(); i++) {
				String tmpid=expandMap.get(groupPosition).get(i).getId();
				if(Util_String.isInList(issave, tmpid)){
					needshowcount++;
				}
			}
			if(needshowcount==expandMap.get(groupPosition).size()){
				textsave.setText("完成");
				textsave.setTextColor(Color.parseColor("#49AF1A"));
			}else if(needshowcount==0){
				textsave.setText("未检查");
				textsave.setTextColor(Color.parseColor("#F70000"));
			}else{
				textsave.setText(""+needshowcount+"/"+expandMap.get(groupPosition).size()+"");
				textsave.setTextColor(Color.parseColor("#F70000"));
			}
			
			
		}else{
			textsave.setText("未检查");
			textsave.setTextColor(Color.parseColor("#F70000"));
		}
		ImageView topdownimage=((ImageView)convertView.findViewById(R.id.topdownimage));
		text.setText(expandMap.get(groupPosition).getName());
		if(isExpanded){
			topdownimage.setImageResource(R.drawable.dangerous_03_2);
		}else{
			topdownimage.setImageResource(R.drawable.dangerous_03);
		}
		Util_MatchTip.initAllScreenText(convertView);
		return convertView;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_check_child, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/14)));
		((TextView)convertView.findViewById(R.id.inspectOptionChild)).setText(expandMap.get(groupPosition).get(childPosition).getName());
		convertView.findViewById(R.id.inspectOptionChild).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				context.startActivity(new Intent(context, Activity_CheckOptionDetail.class)
				.putExtra("ishistory", ishistory)
				.putExtra("title", expandMap.get(groupPosition).get(childPosition).getName())
				.putExtra("planid", checkid)
				.putExtra("qiyecode", qiyecode)
				.putExtra("linkid", expandMap.get(groupPosition).get(childPosition).getId())
				
				);
			}
		});
		LinearLayout completeline=(LinearLayout) convertView.findViewById(R.id.completeline);
		String tmpid=expandMap.get(groupPosition).get(childPosition).getId();
		if(Util_String.isInList(issave, tmpid)){
			completeline.setBackgroundColor(Color.parseColor("#49AF1A"));
		}else{
			completeline.setBackgroundColor(Color.parseColor("#F70000"));
		}
		Util_MatchTip.initAllScreenText(convertView);
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
