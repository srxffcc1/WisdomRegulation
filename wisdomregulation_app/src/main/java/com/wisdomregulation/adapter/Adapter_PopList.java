package com.wisdomregulation.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.frame.CallBack;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

public class Adapter_PopList extends BaseAdapter{
private Activity context;
private List<String> caseListData;
private PopupWindow pop;
private View editextview;
private CallBack callback;
private int select=0;
	public Adapter_PopList(Activity context, List<String> caseListData,PopupWindow pop,View editextview,CallBack callback) {
	super();
	this.context = context;
	this.caseListData = caseListData;
	this.pop=pop;
	this.editextview=editextview;
	this.callback=callback;
}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return caseListData==null?0:caseListData.size();
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
		convertView=LayoutInflater.from(context).inflate(R.layout.item_pop_list, null);
		TextView optionName=(TextView) convertView.findViewById(R.id.optionName);
		optionName.setText(caseListData.get(position));
		optionName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((TextView)editextview).setText(caseListData.get(position));
				pop.dismiss();
				if(callback!=null){
					callback.back(position);
				}
				
			}
		});
		View convertView1=convertView.findViewById(R.id.needfixl1);
		View convertView2=convertView.findViewById(R.id.needfixl2);
		View convertView3=convertView.findViewById(R.id.needfixl3);
		convertView1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/80)));
		convertView2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/80)));
		convertView3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)((Static_InfoApp.create().getAppScreenHigh()/960))/2));
		Util_MatchTip.initAllScreenText(convertView,30);
		return convertView;
	}

}
