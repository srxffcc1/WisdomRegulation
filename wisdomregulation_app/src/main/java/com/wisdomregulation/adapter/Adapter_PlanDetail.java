package com.wisdomregulation.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.single.Activity_PlanDetail;
import com.wisdomregulation.allactivity.tab.Tab_Check;
import com.wisdomregulation.allactivity.tab.Tab_CompanyInfo;
import com.wisdomregulation.allactivity.tab.Tab_History_Check2Again;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_Company;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_Db;
import com.wisdomregulation.utils.Util_MatchTip;

import java.util.List;
import java.util.Map;

public class Adapter_PlanDetail extends BaseAdapter{
	private Activity context;
	private List<Base_Entity> checkListData;
	private String editstate="0";
	private Map<String,CheckBox> viewmap;
	private List<Base_Entity> choseResult;
	public Adapter_PlanDetail(Activity context, List<Base_Entity> lawList) {
		super();
		this.context = context;
		this.checkListData = lawList;
	}
	
	public Map<String, CheckBox> getViewmap() {
		return viewmap;
	}

	public Adapter_PlanDetail setViewmap(Map<String, CheckBox> viewmap) {
		this.viewmap = viewmap;
		return this;
	}

	public void setChoseResult(List<Base_Entity> choseResult) {
		this.choseResult = choseResult;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return checkListData.size();
	}
	
	public String getEditstate() {
		return editstate;
	}

	public Adapter_PlanDetail setEditstate(String editstate) {
		this.editstate = editstate;
		return this;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return checkListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_law_check_list, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/5.8)));
		((TextView)convertView.findViewById(R.id.lawDetailItem)).setText(checkListData.get(position).getValue(4));
		((TextView)convertView.findViewById(R.id.checkState)).setText(checkListData.get(position).getValue(6).equals("")?"未完成":(checkListData.get(position).getValue(6).equals("1")?"完成":"未完成"));
		if(checkListData.get(position).getValue(6).equals("1")){
			convertView.findViewById(R.id.gotocheck).setVisibility(View.INVISIBLE);;
		}
		Util_MatchTip.initAllScreenText(convertView);
		convertView.findViewById(R.id.topplan).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(checkListData.get(position).getValue(6).equals("1")){
					context.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							Toast.makeText(context, "已经检查过不可重复", Toast.LENGTH_SHORT).show();
							
						}
					});
				}else{
					context.startActivity(new Intent(context, Tab_Check.class).putExtra("checkEntity",(checkListData.get(position))));
				}
				

			}
		});
		convertView.findViewById(R.id.left1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Base_Entity companyEntity=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().setLogic("or").search(new Entity_Company().put(37, checkListData.get(position).getValue(5)).setId(checkListData.get(position).getValue(5))));
				context.startActivity(new Intent(context, Tab_CompanyInfo.class).putExtra("companyEntity", companyEntity).putExtra("editState", 4));
			}
		});
		convertView.findViewById(R.id.left2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context, Tab_History_Check2Again.class).putExtra("companyid", checkListData.get(position).getValue(5)));

			}
		});
		convertView.findViewById(R.id.left3).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						Util_Db.deleteFix(checkListData.get(position),new Intent(Activity_PlanDetail.refresh));
						
					}
				}).start();
				

			}
		});
		return convertView;
	}

}
