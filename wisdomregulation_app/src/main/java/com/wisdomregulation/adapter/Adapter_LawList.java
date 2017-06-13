package com.wisdomregulation.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.single.Activity_LawDetail;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

import java.util.List;

public class Adapter_LawList extends BaseAdapter{
private Activity context;
private List<Base_Entity> caseListData;
	public Adapter_LawList(Activity context, List<Base_Entity> caseListData) {
	super();
	this.context = context;
	this.caseListData = caseListData;
}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return caseListData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return caseListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_law_list, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/9.6)));
		TextView caseName=(TextView) convertView.findViewById(R.id.caseName);
		caseName.setText(caseListData.get(position).getValue(0));
		TextView checkmanandtime=(TextView) convertView.findViewById(R.id.checkmanandtime);
		checkmanandtime.setText("检查人："+caseListData.get(position).getValue(13)+" "+"立案时间："+caseListData.get(position).getValue(14));
		Util_MatchTip.initAllScreenText(convertView);
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context, Activity_LawDetail.class).putExtra("lawEntity", caseListData.get(position)));
				
			}
		});
		return convertView;
	}

}
