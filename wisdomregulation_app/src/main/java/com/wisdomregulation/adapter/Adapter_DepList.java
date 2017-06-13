package com.wisdomregulation.adapter;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

public class Adapter_DepList extends BaseAdapter{
private Activity context;
private List<Base_Entity> companyListData;
private TextView needyiju;
private TextView detailvalue;
private Dialog dialog;

	public Adapter_DepList(Activity context, List<Base_Entity> companyList,final TextView needyiju,Dialog dialog,TextView detailvalue) {
	super();
	this.context = context;
	this.companyListData = companyList;
	this.needyiju=needyiju;
	this.dialog=dialog;
	this.detailvalue=detailvalue;
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
			convertView=LayoutInflater.from(context).inflate(R.layout.item_dialog_deplist, null);
			convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/9.6)));
			holder.vhcompanyName=((TextView)convertView.findViewById(R.id.companyName));
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					needyiju.setText(companyListData.get(position).getValue(1));
					detailvalue.setText(companyListData.get(position).getValue(0));
					dialog.dismiss();
					
					
				}
			});
			convertView.setTag(holder);
			
		}
		holder.vhcompanyName.setText(companyListData.get(position).getValue(0));
		Util_MatchTip.initAllScreenText(convertView,companyListData.get(position).getKeyword());
		return convertView;
	}
	static class ViewHolder{
		TextView vhcompanyName;
	}

}
