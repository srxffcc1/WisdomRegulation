package com.wisdomregulation.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.map.ExpandMap;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

public class Adapter_ExpandHistoryOptionList extends BaseExpandableListAdapter{
private Activity context;
private List<ExpandMap> expandMap;
private ExpandableListView expandlistview;
private boolean ishistory;
private String checkid;
private String qiyecode;
private List<String> issave;

	public Adapter_ExpandHistoryOptionList(Activity context,
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

	public Adapter_ExpandHistoryOptionList setIssave(List<String> issave) {
		this.issave = issave;
		return this;
	}

	public Adapter_ExpandHistoryOptionList setCheckid(String checkid) {
		this.checkid = checkid;
		return this;
	}

	public String getQiyecode() {
		return qiyecode;
	}

	public Adapter_ExpandHistoryOptionList setQiyecode(String qiyecode) {
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
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return expandMap.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return expandMap.get(groupPosition).getBaseentity();
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
		convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_history_check_parent, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/9.8)));
		TextView optionname=(TextView) convertView.findViewById(R.id.inspectOption);
		String optionnametext=expandMap.get(groupPosition).getBaseentity().getValue(18);
		optionname.setText(optionnametext);
		Util_MatchTip.initAllScreenText(convertView);
		return convertView;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_history_check_child, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		TextView need1=(TextView) convertView.findViewById(R.id.need1);
		TextView need2=(TextView) convertView.findViewById(R.id.need2);
		TextView need3=(TextView) convertView.findViewById(R.id.need3);
		TextView need4=(TextView) convertView.findViewById(R.id.need4);
		Base_Entity detail=expandMap.get(groupPosition).getBaseentity();
		String yinhuanjibie=detail.getValue(15);
		String yinhuanjibietext="";
		if(yinhuanjibie.equals("0")){
			yinhuanjibietext="一般隐患";
		}else if(yinhuanjibie.equals("1")){
			yinhuanjibietext="重大隐患";
		}else{
			yinhuanjibietext="无隐患";
		}
		
		need1.setText(yinhuanjibietext);
		need2.setText(detail.getValue(26));
		need3.setText(detail.getValue(5));
		need4.setText(detail.getValue(16));
		
		Util_MatchTip.initAllScreenText(convertView);
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
