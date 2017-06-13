package com.wisdomregulation.adapter;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.dialog.DateTimePickDialog;
import com.wisdomregulation.utils.Util_MatchTip;

public class Adapter_EditChoseLaw {
	private Activity context;
	private Base_Entity detailMapData;
	private LinearLayout content;
	private int editState=0;
	private Map<String,EditText> viewmap=new HashMap<String,EditText>();
	
	public Adapter_EditChoseLaw(Activity context,
			Base_Entity detailMapData, LinearLayout content) {
		super();
		this.context = context;
		this.detailMapData = detailMapData;
		this.content = content;
	}
	public void saveEntity(){
		for (int i = 0; i < detailMapData.size(); i++) {
			EditText ed=viewmap.get(detailMapData.getField(i));
			if(ed!=null){
				detailMapData.put(i, ed.getText().toString());
			}
		}
	}
	
	public Adapter_EditChoseLaw setDetailMapData(Base_Entity detailMapData) {
		this.detailMapData = detailMapData;
		return this;
	}
	public Map<String, EditText> getViewmap() {
		return viewmap;
	}

	public Adapter_EditChoseLaw initView(){
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
	public Adapter_EditChoseLaw setEditState(int editState) {
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
		View convertView=LayoutInflater.from(context).inflate(R.layout.item_dialog_edit_law, null);
		TextView fieldedit=(TextView) convertView.findViewById(R.id.companyField);
		String fieldtext=detailMapData.getFieldChinese(position);
		fieldedit.setText(fieldtext);
		final EditText valueedit=((EditText)convertView.findViewById(R.id.companyValue));
		viewmap.put(detailMapData.getField(position), valueedit);
		valueedit.setText(detailMapData.getValue(position));
		if(fieldtext.equals("案件名称")){
			
		}
		else if(fieldtext.equals("检查类型")){
			valueedit.setKeyListener(null);
		}
		else if(fieldtext.equals("被检查单位")){
			valueedit.setKeyListener(null);
		}
		else if(fieldtext.equals("当前阶段")){
			valueedit.setKeyListener(null);
		}
		else if(fieldtext.equals("立案时间")){
			valueedit.setFocusable(false);
			valueedit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					new DateTimePickDialog(context, "").show(valueedit);
					
				}
			});
		}
		else if(fieldtext.equals("检查人")){
			valueedit.setText(Static_InfoApp.create().getUserName());
		}
		else{
			convertView.setVisibility(View.GONE);
		}
		switch (editState) {
		case 0:
			valueedit.setEnabled(false);
			break;
		case 1:
			valueedit.setEnabled(true);
			break;
		default:
			break;
		}
		Util_MatchTip.initAllScreenText(convertView);
		return convertView;
	}

}
