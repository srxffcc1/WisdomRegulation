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
import com.wisdomregulation.allactivity.single.Activity_DangerousOption;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

import java.util.List;

public class Adapter_DangerousListIsCheck extends BaseAdapter{
	private Activity context;
	private List<Base_Entity> dangerouslistdata;
	private String editstate="0";
	public Adapter_DangerousListIsCheck(Activity context, List<Base_Entity> lawList) {
		super();
		this.context = context;
		this.dangerouslistdata = lawList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dangerouslistdata.size();
	}
	
	public String getEditstate() {
		return editstate;
	}

	public Adapter_DangerousListIsCheck setEditstate(String editstate) {
		this.editstate = editstate;
		return this;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dangerouslistdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_dangerousischeck, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/14)));
		((TextView)convertView.findViewById(R.id.securityCheckName)).setText(dangerouslistdata.get(position).getValue(27));
		Util_MatchTip.initAllScreenText(convertView);
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context, Activity_DangerousOption.class).putExtra("dangerousEntity", dangerouslistdata.get(position)));

				
				
			}
		});
		return convertView;
	}

}
