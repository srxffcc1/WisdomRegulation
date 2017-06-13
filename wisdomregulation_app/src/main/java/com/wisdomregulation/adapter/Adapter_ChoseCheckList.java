package com.wisdomregulation.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.AbsListView.LayoutParams;

import com.wisdomregulation.R;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

public class Adapter_ChoseCheckList extends BaseAdapter{
	private Activity context;
	private List<Base_Entity> lawDetailListData;
	private String editstate="0";
	private Map<String,Boolean> viewmapcheck=new HashMap<String, Boolean>();
	public Adapter_ChoseCheckList(Activity context, List<Base_Entity> lawDetailListData) {
		super();
		this.context = context;
		this.lawDetailListData = lawDetailListData;
		viewmapcheck.clear();
	}
	


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lawDetailListData.size();
	}
	public List<Base_Entity> getchoseList(){
		List<Base_Entity> editlist=new ArrayList<Base_Entity>();
		editlist.clear();
		for (int i = 0; i < lawDetailListData.size(); i++) {
			if(viewmapcheck.get(lawDetailListData.get(i).getId())!=null){
				if(viewmapcheck.get(lawDetailListData.get(i).getId())){
					Base_Entity tmpentity=lawDetailListData.get(i);
					editlist.add(tmpentity);
				}
				
			}
		
		}
		return editlist;
	}
	public String getEditstate() {
		return editstate;
	}

	public Adapter_ChoseCheckList setEditstate(String editstate) {
		this.editstate = editstate;
		return this;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lawDetailListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_law_check_chose, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/14)));
		((TextView)convertView.findViewById(R.id.lawDetailItem)).setText(lawDetailListData.get(position).getValue(4)+"：复查不合格");
		final CheckBox chose=(CheckBox)convertView.findViewById(R.id.againchoseCheck);
		if(editstate.equals("1")){
			chose.setVisibility(View.VISIBLE);
			chose.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					viewmapcheck.put(lawDetailListData.get(position).getId(), arg1);
					
				}
			});
			
		}
		
		Util_MatchTip.initAllScreenText(convertView);
//		convertView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if(editstate.equals("0")){
//				context.startActivity(new Intent(context, Tab_Check.class).putExtra("checkEntity",(lawDetailListData.get(position))));
//				}
//			}
//		});
		return convertView;
	}

}
