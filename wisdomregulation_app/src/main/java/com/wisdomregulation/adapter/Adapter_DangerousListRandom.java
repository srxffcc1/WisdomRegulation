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
import com.wisdomregulation.data.entityother.Entity_CheckDetail;
import com.wisdomregulation.data.entityother.Entity_Company;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_Db;
import com.wisdomregulation.utils.Util_MatchTip;

import java.util.List;

public class Adapter_DangerousListRandom extends BaseAdapter{
	private Activity context;
	private List<Base_Entity> dangerouslistdata;
	private String editstate="0";
	public Adapter_DangerousListRandom(Activity context, List<Base_Entity> lawList) {
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

	public Adapter_DangerousListRandom setEditstate(String editstate) {
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
		convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_dangerous_list, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/11.3)));
		((TextView)convertView.findViewById(R.id.lawItem)).setText(Util_Db.code2idtoString(new Entity_Company().put(37, dangerouslistdata.get(position).getValue(25)),0));
		String zhenggaistatus=dangerouslistdata.get(position).getValue(38);
		if(zhenggaistatus.equals("1")){
			((TextView)convertView.findViewById(R.id.lawItemStatus)).setText("整改完成");
		}else{
			((TextView)convertView.findViewById(R.id.lawItemStatus)).setText("未整改");
		}
		final Base_Entity linkentity;
			linkentity=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().search(new Entity_CheckDetail().init().setId(dangerouslistdata.get(position).getValue(25))));

		Util_MatchTip.initAllScreenText(convertView);
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context, Activity_DangerousOption.class).putExtra("linkEntity", linkentity).putExtra("dangerousEntity", dangerouslistdata.get(position)));

				
				
			}
		});
		return convertView;
	}

}
