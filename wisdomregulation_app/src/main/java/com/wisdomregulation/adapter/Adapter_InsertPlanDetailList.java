package com.wisdomregulation.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_Plan;
import com.wisdomregulation.pop.Pop_Tool;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.dialog.DateTimePickDialog;
import com.wisdomregulation.utils.Util_Db;
import com.wisdomregulation.utils.Util_MatchTip;

public class Adapter_InsertPlanDetailList {
	private Activity context;
	private Base_Entity detailMapData;
	private LinearLayout content;
	private int editState=0;
	private List<String> chosepeople = new ArrayList<String>();
	private Map<String,EditText> viewmap=new HashMap<String,EditText>();
	private List<EditText> edittextlist=new ArrayList<EditText>();
	private List<String> needpop = new ArrayList<String>();
	public Adapter_InsertPlanDetailList(Activity context,
			Base_Entity detailMapData, LinearLayout content) {
		super();
		this.context = context;
		this.detailMapData = detailMapData;
		this.content = content;
		
	}
	
	public Map<String, EditText> getViewmap() {
		return viewmap;
	}

	public Adapter_InsertPlanDetailList setChosepeople(List<String> chosepeople) {
		this.chosepeople = chosepeople;
		return this;
	}

	public Adapter_InsertPlanDetailList setNeedpop(List<String> needpop) {
		this.needpop = needpop;
		return this;
	}

	public Adapter_InsertPlanDetailList initView(){
		viewmap.clear();
		content.removeAllViews();
		for (int i = 0; i < getCount(); i++) {
			View add=getView(i, content);
			if(add!=null){
				if(add.getVisibility()==View.VISIBLE){
					content.addView(add);
				}
				
			}
		}
		return this;
	}
	
	public List<EditText> getEdittextlist() {
		return edittextlist;
	}

	public Adapter_InsertPlanDetailList setEditState(int editState) {
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
		View convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_planinsert_detail, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/14)));
		String fieldtext=getItem(position).toString();
		((TextView)convertView.findViewById(R.id.companyField)).setText(fieldtext);
		final EditText valueedit=((EditText)convertView.findViewById(R.id.companyValue));
		viewmap.put(detailMapData.getField(position), valueedit);
		valueedit.setText(detailMapData.getValue(position));
		switch (editState) {
		case 0:
			valueedit.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					return true;
				}
			});
			valueedit.setKeyListener(null);
			break;
		case 1:
//			valueedit.setEnabled(true);
			break;
		default:
			break;
		}
		if(fieldtext.matches("(.*)时间(.*)")||fieldtext.matches("(.*)日期(.*)")){
			valueedit.setFocusable(false);
			valueedit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					new DateTimePickDialog(context, "").show(valueedit);
					
				}
			});
		}if(fieldtext.equals(new Entity_Plan().init().getFieldChinese(6))){
			valueedit.setFocusable(false);
			if(Static_InfoApp.create().istest()){
				final List<String> debuglist=new ArrayList<String>();
				debuglist.add("测试员");
				valueedit.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Pop_Tool.pop_chose_list("无可选名单",context, valueedit,valueedit.getWidth(),(int)(Static_InfoApp.create().getAppScreenWidth()/2.8),debuglist,null,null);
						
					}
				});
			}else{
				valueedit.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Pop_Tool.pop_chose_list("无可选名单",context, valueedit,valueedit.getWidth(),(int)(Static_InfoApp.create().getAppScreenWidth()/2.8),chosepeople,null,null);
						
					}
				});
			}
			
			
			

		}if(fieldtext.equals(new Entity_Plan().init().getFieldChinese(4))){
			convertView.setVisibility(View.GONE);
		}
		if(fieldtext.equals(new Entity_Plan().init().getFieldChinese(0))){
			String quyuid=detailMapData.getValue(position).trim();
			if(!quyuid.equals("")){
				valueedit.setText(Util_Db.code2idtoStringSpecial(quyuid,3));
			}else{
				valueedit.setText("");
			}
			valueedit.setFocusable(false);
			
			
			valueedit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Pop_Tool.pop_chose_list("无可选区域",context, valueedit,valueedit.getWidth(),(int)(Static_InfoApp.create().getAppScreenWidth()/2.8),needpop,null,null);
					
				}
			});
		}
		if(fieldtext.equals(new Entity_Plan().init().getFieldChinese(5))){
			convertView.setVisibility(View.GONE);
		}
		if(fieldtext.equals(new Entity_Plan().init().getFieldChinese(6))){
			valueedit.setFocusable(false);
			convertView.setVisibility(View.GONE);

		}
		if(fieldtext.equals(new Entity_Plan().init().getFieldChinese(2))){
			valueedit.setFocusable(false);
			convertView.setVisibility(View.GONE);
		}
		if(convertView.getVisibility()==View.VISIBLE){
			edittextlist.add(valueedit);
		}
		Util_MatchTip.initAllScreenText(convertView);
		return convertView;
	}

}
