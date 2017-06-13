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
import com.wisdomregulation.allactivity.single.Activity_Library_Detail;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.staticlib.Static_ConstantLib;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

import java.util.List;

public class Adapter_LibraryList_2 extends BaseAdapter{
	private Activity context;
	private List<Base_Entity> noticelistdata;
	private String editstate="0";
	public Adapter_LibraryList_2(Activity context, List<Base_Entity> noticelistdata) {
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

	public Adapter_LibraryList_2 setEditstate(String editstate) {
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
		convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_library2, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/9.6)));
		((TextView)convertView.findViewById(R.id.contentname)).setText(noticelistdata.get(position).getValue(0));
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context, Activity_Library_Detail.class).putExtra("newtitle", noticelistdata.get(position).getValue(0)).putExtra("baseentity", noticelistdata.get(position)));
				
			}
		});
		TextView noticeyear=(TextView) convertView.findViewById(R.id.noticeyear);
		Util_MatchTip.initAllScreenText(convertView,noticelistdata.get(position).getKeyword());
		Util_MatchTip.initAllScreenText(noticeyear,Static_ConstantLib.TEXT_SMALL);
		return convertView;
	}

}
