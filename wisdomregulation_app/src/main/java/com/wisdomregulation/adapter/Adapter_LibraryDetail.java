package com.wisdomregulation.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AbsListView.LayoutParams;

import com.wisdomregulation.R;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

public class Adapter_LibraryDetail {
	private Activity context;
	private Base_Entity detailMapData;
	private LinearLayout content;
	private int editState=0;
	
	public Adapter_LibraryDetail(Activity context,
			Base_Entity detailMapData, LinearLayout content) {
		super();
		this.context = context;
		this.detailMapData = detailMapData;
		this.content = content;
	}
	
	public Base_Entity save(){
		detailMapData.clear();
		return detailMapData;
	}
	public Adapter_LibraryDetail initView(){
		content.removeAllViews();
		for (int i = 0; i < getCount(); i++) {
			content.addView(getView3(content));
			View add=getView(i, content);
			if(add!=null){
				content.addView(add);
			}
			content.addView(getView3(content));
			content.addView(getView2(content));
		}
		return this;
	}
	public Adapter_LibraryDetail setEditState(int editState) {
		this.editState = editState;
		return this;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return detailMapData.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return detailMapData.getFieldChinese(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, ViewGroup parent) {
		View convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_library_detail_content, null);
		((TextView)convertView.findViewById(R.id.librarycontent)).setText(detailMapData.getFieldChinese(position)+"："+detailMapData.getValue(position));
		Util_MatchTip.initAllScreenText(convertView,detailMapData.getKeyword());
		return convertView;
	}
	public View getView2(ViewGroup parent) {
		View convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_detail_line, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/960)));
		Util_MatchTip.initAllScreenText(convertView);
		return convertView;
	}
	public View getView3(ViewGroup parent) {
		View convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_detail_empty, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/42.6)));
		Util_MatchTip.initAllScreenText(convertView);
		return convertView;
	}

}
