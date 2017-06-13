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
import com.wisdomregulation.allactivity.single.Activity_History_CheckOption;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

import java.text.SimpleDateFormat;
import java.util.List;

public class Adapter_HistoryBlock_Check extends BaseAdapter{
	private Activity context;
	private List<Base_Entity> noticelistdata;
	private String editstate="0";
	public Adapter_HistoryBlock_Check(Activity context, List<Base_Entity> noticelistdata) {
		super();
		this.context = context;
		this.noticelistdata = noticelistdata;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return noticelistdata.size();
	}
	
	public String getEditstate() {
		return editstate;
	}

	public Adapter_HistoryBlock_Check setEditstate(String editstate) {
		this.editstate = editstate;
		return this;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return noticelistdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_history_check, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/8.27)));
		String sdata=new SimpleDateFormat("yyyy-MM-dd").format(Long.parseLong(noticelistdata.get(position).getUpDatadate()));
		String isagain=noticelistdata.get(position).getValue(16);
		if(isagain.equals("1")){
			((TextView)convertView.findViewById(R.id.ispass)).setText("须复查");
		}else{
			((TextView)convertView.findViewById(R.id.ispass)).setText("无复查");
		}
		((TextView)convertView.findViewById(R.id.checkdata)).setText(sdata);
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context, Activity_History_CheckOption.class).putExtra("ishistory", true).putExtra("checkEntity",noticelistdata.get(position)));
				
			}
		});
		Util_MatchTip.initAllScreenText(convertView);
		return convertView;
	}

}
