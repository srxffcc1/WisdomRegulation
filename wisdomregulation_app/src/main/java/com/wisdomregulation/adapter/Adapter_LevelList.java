package com.wisdomregulation.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.allactivity.tab.Tab_Law;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_BookList;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

public class Adapter_LevelList {
private String lawid;
private List<Base_Entity> booklistdata;
private Activity context;
private BaseAdapter adapter;
private List<String> levellistdata;
private LinearLayout content;
private Map<Integer,TextView> radiobuttonMap=new HashMap<Integer, TextView>();
private Map<Integer,Boolean> radiobuttonDataMap=new HashMap<Integer, Boolean>();
public Adapter_LevelList(Activity context,List<String> levellistdata, LinearLayout content,String lawid,List<Base_Entity> booklistdata,BaseAdapter adapter) {
	super();
	this.context = context;
	this.levellistdata = levellistdata;
	this.content=content;
	this.lawid=lawid;
	this.booklistdata=booklistdata;
	this.adapter=adapter;
	radiobuttonMap.clear();
}
	public int getCount() {
		// TODO Auto-generated method stub
		return levellistdata!=null?levellistdata.size():0;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return levellistdata.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public Map<Integer, TextView> getRadiobuttonMap() {
		return radiobuttonMap;
	}
	public void setRadiobuttonMap(Map<Integer, TextView> radiobuttonMap) {
		this.radiobuttonMap = radiobuttonMap;
	}
	public Adapter_LevelList initView(){
		content.removeAllViews();
		for (int i = 0; i < getCount(); i++) {
			View add=getView(i, content);
			if(add!=null){
				content.addView(add);
			}
		}
		return this;
	}
	public void clickIndex(int index){
		radiobuttonMap.get(index).performClick();
	}
	public View getView(final int position, ViewGroup parent) {
			View convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_level_list, null);
			convertView.setLayoutParams(new LayoutParams((int)(Static_InfoApp.create().getAppScreenWidth()/3.80),LayoutParams.MATCH_PARENT));
			TextView vhbookName=(TextView) convertView.findViewById(R.id.levelname);
			radiobuttonMap.put(position, vhbookName);
			vhbookName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				((Base_AyActivity)context).showWait();
				Static_InfoApp.create().getAllhandler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						for (Map.Entry<Integer, TextView> entry : radiobuttonMap.entrySet()){
							
							if(entry.getValue()!=null){
								entry.getValue().setTextColor(Color.parseColor("#333333"));
							}
							}
							((TextView)v).setTextColor(Color.parseColor("#FF4000"));

							switch (position) {
							case 0:
								Tab_Law.searchstringfield = "立案阶段相关文书";
								break;

							case 1:
								Tab_Law.searchstringfield = "调查取证阶段相关文书";
								break;

							case 2:
								Tab_Law.searchstringfield = "听证告知阶段相关文书";
								break;

							case 3:
								Tab_Law.searchstringfield = "审查决定阶段相关文书";
								break;
							case 4:
								Tab_Law.searchstringfield = "送达执行阶段相关文书";
								break;
							case 5:
								Tab_Law.searchstringfield = "结案归档阶段相关文书";
								break;

							default:
								Tab_Law.searchstringfield="立案阶段相关文书";
								break;
							}
							Base_Entity booklistEntity = new Entity_BookList().init().put(2, lawid)
									.put(3, Tab_Law.searchstringfield);
							booklistdata.clear();
							booklistdata.addAll(Util_MatchTip.getSearchResult(Help_DB.create()
									.search(booklistEntity)));
							adapter.notifyDataSetChanged();
							((Base_AyActivity)context).dissmissWait();
						
					}
				}, 300);
				
				
			}
		});
		vhbookName.setText(levellistdata.get(position));

		Util_MatchTip.initAllScreenText(convertView);		
		return convertView;
	}
}
