package com.wisdomregulation.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.chart.Chart_CompanyCount;
import com.wisdomregulation.allactivity.chart.Chart_SmallDangerous;
import com.wisdomregulation.staticlib.Static_ConstantLib;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

import java.util.List;


public class Adapter_ChartMenuList {
private Activity context;
private List<String> securtityMenuListData;
private LinearLayout content;
	public Adapter_ChartMenuList(Activity context,
		List<String> securtityMenuListData,LinearLayout content) {
	super();
	this.context = context;
	this.securtityMenuListData = securtityMenuListData;
	this.content = content;
}


	public int getCount() {
		// TODO Auto-generated method stub
		return securtityMenuListData.size();
	}


	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return securtityMenuListData.get(position);
	}


	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public Adapter_ChartMenuList initView(){
		content.removeAllViews();
		for (int i = 0; i < getCount(); i++) {
			View add=getView(i, content);
			if(add!=null){
				content.addView(add);
			}
		}
		return this;
	}
	public View getView(final int position, View convertView) {
		convertView=LayoutInflater.from(context).inflate(R.layout.item_dangerous_menu, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/9.09)));
		TextView menuName=(TextView) convertView.findViewById(R.id.securityMenuName);
		final String charttype=securtityMenuListData.get(position);
		menuName.setText(charttype);
		ImageView icon=(ImageView) convertView.findViewById(R.id.dangerousicon);
		icon.setImageResource(R.drawable.dangerousicon_12);
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=null;
				if(charttype.equals("企业数量")){
					intent=new Intent(context, Chart_CompanyCount.class);
				}else if(charttype.equals("隐患数量")){
					intent=new Intent(context, Chart_SmallDangerous.class);
				}
				if(intent!=null){
					context.startActivity(intent);
				}
				
			}
		});
		Util_MatchTip.initAllScreenText(convertView,Static_ConstantLib.TEXT_NORMAL);
		
		return convertView;
	}



}
