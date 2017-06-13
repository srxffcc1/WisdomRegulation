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
import com.wisdomregulation.allactivity.tab.Tab_History_Check2Again;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

import java.util.List;

public class Adapter_HistoryCompanyList extends BaseAdapter{
	private Activity context;
	private List<Base_Entity> historycompany;
	private String editstate="0";
	public Adapter_HistoryCompanyList(Activity context, List<Base_Entity> historycompany) {
		super();
		this.context = context;
		this.historycompany = historycompany;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return historycompany.size();
	}
	
	public String getEditstate() {
		return editstate;
	}

	public Adapter_HistoryCompanyList setEditstate(String editstate) {
		this.editstate = editstate;
		return this;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return historycompany.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_historycompany_list, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/9.6)));
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context, Tab_History_Check2Again.class).putExtra("companyid", historycompany.get(position).getValue(37)));
				
			}
		});
		TextView HistorycompanyName=(TextView) convertView.findViewById(R.id.HistorycompanyName);
		TextView newtime=(TextView) convertView.findViewById(R.id.newtime);
		newtime.setText(historycompany.get(position).getValue(3).equals(" ")?"未检查":historycompany.get(position).getValue(3));
		HistorycompanyName.setText(historycompany.get(position).getValue(0));
		Util_MatchTip.initAllScreenText(convertView);
		return convertView;
	}

}
