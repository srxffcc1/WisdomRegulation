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
import com.wisdomregulation.allactivity.mode.Mode_Library;
import com.wisdomregulation.allactivity.single.Activity_DangerousCheckList;
import com.wisdomregulation.allactivity.tab.Tab_Dangerous;
import com.wisdomregulation.staticlib.Static_ConstantLib;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

import java.util.List;


public class Adapter_DangerousMenuList {
private Activity context;
private List<String> securtityMenuListData;
private LinearLayout content;
	public Adapter_DangerousMenuList(Activity context,
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

	public Adapter_DangerousMenuList initView(){
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
		menuName.setText(securtityMenuListData.get(position));
		ImageView icon=(ImageView) convertView.findViewById(R.id.dangerousicon);
		switch (position) {
		case 0:
			icon.setImageResource(R.drawable.dangerousicon_03);
			break;
		case 1:
			icon.setImageResource(R.drawable.dangerousicon_07);
			break;
		case 2:
			icon.setImageResource(R.drawable.dangerousicon_10);
			break;
		case 3:
			icon.setImageResource(R.drawable.dangerousicon_12);
			break;

		default:
			break;
		}
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(position==0){
					context.startActivity(new Intent(context, Tab_Dangerous.class));
				}
				if(position==1){
					context.startActivity(new Intent(context, Mode_Library.class).putExtra("level", 3));
				}
				if(position==2){
					context.startActivity(new Intent(context, Activity_DangerousCheckList.class));
				}
				
			}
		});
		Util_MatchTip.initAllScreenText(convertView,Static_ConstantLib.TEXT_NORMAL);
		
		return convertView;
	}



}
