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
import com.wisdomregulation.allactivity.single.Activity_BombList;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

import java.util.List;

public class Adapter_BombCompanyInfoList extends BaseAdapter{
private Activity context;
private List<Base_Entity> companyListData;

	public Adapter_BombCompanyInfoList(Activity context, List<Base_Entity> companyList) {
	super();
	this.context = context;
	this.companyListData = companyList;
}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return companyListData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return companyListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView!=null){
			holder=(ViewHolder)convertView.getTag();
		}
		else{
			holder = new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_company_bomblist, null);
			convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/9.6)));

			convertView.setTag(holder);
			
		}
		holder.vhcompanyName=((TextView)convertView.findViewById(R.id.companyName));
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context, Activity_BombList.class));

			}
		});
		holder.vhcompanyName.setText(companyListData.get(position).getValue(0));
		Util_MatchTip.initAllScreenText(convertView,companyListData.get(position).getKeyword());
		return convertView;
	}
	static class ViewHolder{
		TextView vhcompanyName;
	}

}
