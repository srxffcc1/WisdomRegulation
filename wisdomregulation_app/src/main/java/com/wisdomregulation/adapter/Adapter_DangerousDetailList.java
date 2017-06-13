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

public class Adapter_DangerousDetailList extends BaseAdapter {
private Activity context;
private List<Base_Entity> dangerouslistdata;
private boolean ishistory;
	public Adapter_DangerousDetailList(Activity context,
		List<Base_Entity> dangerouslistdata,boolean ishistory) {
	super();
	this.context = context;
	this.dangerouslistdata = dangerouslistdata;
	this.ishistory = ishistory;
}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dangerouslistdata!=null?dangerouslistdata.size():0;
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
		convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_dangerous_detail__list, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/3.84)));
		

		TextView historyText=(TextView) convertView.findViewById(R.id.historyText);
		if(ishistory){
			historyText.setText("查看");
		}
		TextView yinhuanmiaoshu=(TextView) convertView.findViewById(R.id.yinhuanmiaoshu);
		yinhuanmiaoshu.setText(dangerouslistdata.get(position).getValue(16));
		TextView yinhuanjibie=(TextView) convertView.findViewById(R.id.yinhuanjibie);
		String jibie=dangerouslistdata.get(position).getValue(15);
		if(jibie.equals("0")){
			yinhuanjibie.setText("隐患级别："+"一般隐患");
		}else if(jibie.equals("1")){
			yinhuanjibie.setText("隐患级别："+"重大隐患");
		}else{
			yinhuanjibie.setText("隐患级别："+"一般隐患");
		}
		
		TextView zhenggaiqixian=(TextView) convertView.findViewById(R.id.zhenggaiqixian);
		zhenggaiqixian.setText("整改期限："+dangerouslistdata.get(position).getValue(13));
		final TextView jianchaxiang=(TextView) convertView.findViewById(R.id.jianchaxiang);
		jianchaxiang.setText("检查时间："+dangerouslistdata.get(position).getValue(26));
		TextView dubanbumen=(TextView) convertView.findViewById(R.id.dubanbumen);
		
		TextView dubanfuzeren=(TextView) convertView.findViewById(R.id.dubanfuzeren);
		
		TextView zhenggaijieguo=(TextView) convertView.findViewById(R.id.zhenggaijieguo);
		zhenggaijieguo.setText("整改结果："+(!dangerouslistdata.get(position).getValue(38).equals("1")?"未完成":"完成"));
		Util_MatchTip.initAllScreenText(convertView);
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context, Activity_DangerousOption.class).putExtra("ishistory", ishistory).putExtra("dangerousEntity", dangerouslistdata.get(position)));
				
			}
		});
		return convertView;
	}

}
