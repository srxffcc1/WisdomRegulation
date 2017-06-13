package com.wisdomregulation.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wisdomregulation.R;
import com.wisdomregulation.data.entitybase.Base_Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_BookAddItem {
	private Activity context;
	private List<Base_Entity> detailMapData;
	private LinearLayout content;

	private boolean editState;
	private boolean isshow=true;
	private Map<String,EditText> viewmap=new HashMap<String,EditText>();

	public Adapter_BookAddItem(Activity context,
							   List<Base_Entity> detailMapData, LinearLayout content) {
		super();
		this.context = context;
		this.detailMapData = detailMapData;
		this.content = content;
	}


	public Adapter_BookAddItem initView(){
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
	public String getResult(){
		String result="";
		for (int i = 0; i < detailMapData.size(); i++) {
			result=result+getResult(detailMapData.get(i));
		}

		return result;
	}
	public String getResult(Base_Entity entity){
		String result="";
		for (int i = 0; i < entity.size(); i++) {
			result=result+entity.getValue(i)+"#";
		}
		if(result.length()>1){
			result=result.substring(0,result.length()-1)+"@";
		}
		return result;
	}
	public Adapter_BookAddItem addEntity(Base_Entity addentity){
		detailMapData.add(addentity);
		View add=getView(detailMapData.size()-1, content);
		if(add!=null){
			if(add.getVisibility()==View.VISIBLE){
				content.addView(add);
			}

		}
		return this;
	}


	public int getCount() {
		// TODO Auto-generated method stub
		return detailMapData.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return detailMapData.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public View getView(int position, ViewGroup parent) {
		View view= LayoutInflater.from(context).inflate(R.layout.item_activity_book_content4,null);
		LinearLayout linearLayout= (LinearLayout) view.findViewById(R.id.veraddtip);
		Adapter_BookDetail2 adapter_bookDetail=new Adapter_BookDetail2(context,detailMapData.get(position), linearLayout).initView();
		return view;
	}

}
