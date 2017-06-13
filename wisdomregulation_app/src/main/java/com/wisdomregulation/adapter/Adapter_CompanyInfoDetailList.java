package com.wisdomregulation.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.bn.BNDemoLocation;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_Db;
import com.wisdomregulation.utils.Util_MatchTip;

import java.util.HashMap;
import java.util.Map;

public class Adapter_CompanyInfoDetailList {
	private Activity context;
	private Base_Entity detailMapData;
	private LinearLayout content;
	private int editState=0;
	private Map<String,EditText> viewmap=new HashMap<String,EditText>();
	private Map<Integer,View> convertviewmap=new HashMap<Integer,View>();
	public Adapter_CompanyInfoDetailList(Activity context,
			Base_Entity detailMapData, LinearLayout content) {
		super();
		this.context = context;
		this.detailMapData = detailMapData;
		this.content = content;
	}
	
	public Map<String, EditText> getViewmap() {
		return viewmap;
	}

	public Adapter_CompanyInfoDetailList initView(){
		((Base_AyActivity)context).showWait();
		content.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				convertviewmap.clear();
				viewmap.clear();
				content.removeAllViews();
				for (int i = 0; i < getCount(); i++) {
					View add=getView(i, content);
					if(add!=null){
						if(add.getVisibility()==View.VISIBLE){
							content.addView(getView3(content));
						}
						if(add.getVisibility()==View.VISIBLE){
							content.addView(add);
						}
						if(add.getVisibility()==View.VISIBLE){
							content.addView(getView3(content));
							content.addView(getView2(content));
						}
					}
				}
				for (int i = 0; i < getCount(); i++) {
					initData(i);
				}
				((Base_AyActivity)context).dissmissWait();
			}
		}, 100);
		
		return this;
	}
	public void initData(final int position){
		View convertView=convertviewmap.get(position);
		if(convertView!=null&&convertView.getVisibility()==View.VISIBLE){
			final EditText valueedit=((EditText)convertView.findViewById(R.id.companyValue));
			
			new Thread(new Runnable() {
			
			@Override
			public void run() {
				final String valuename=Util_Db.code2idtoStringSpecial(detailMapData.getValue(position),3);
				context.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						valueedit.setText(valuename);
						
					}
				});
				
			}
		}).start();
		}

	}
	public Adapter_CompanyInfoDetailList setEditState(int editState) {
		this.editState = editState;
		return this;
	}
	public Adapter_CompanyInfoDetailList setFocus(int position){
		(viewmap.get(detailMapData.getField(position))).setFocusable(true);   
		(viewmap.get(detailMapData.getField(position))).setFocusableInTouchMode(true);   
		(viewmap.get(detailMapData.getField(position))).requestFocus();  
		((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
		.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
		

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
	public void setdata(final int position,View convertView){

	}
	public View getView(final int position, ViewGroup parent) {
		View convertView = null;
		if(detailMapData.getFieldChinese(position).equals("企业详细地址")){
			convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_company_detail2, null);
			convertView.findViewById(R.id.companyloc).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String longs=detailMapData.getValue(7);//经度
					String lats=detailMapData.getValue(26);//纬度
							if(longs.equals("")||longs.equals(" ")||lats.equals("")||lats.equals(" ")){
								if(Static_InfoApp.create().isshow()){
									longs="103.830918";
									lats="36.068245";
									context.startActivity(new Intent(context, BNDemoLocation.class).putExtra("tlong", Double.parseDouble(longs)).putExtra("tlat", Double.parseDouble(lats)));
								}else{
									Toast.makeText(context, "企业经纬度出现问题无法定位", Toast.LENGTH_LONG).show();
								}
//								
//								
								
							}else{
								context.startActivity(new Intent(context, BNDemoLocation.class).putExtra("tlong", Double.parseDouble(longs)).putExtra("tlat", Double.parseDouble(lats)));
							}
							
					
					
				}
			});
		}else if(detailMapData.getFieldChinese(position).equals("企业位置经度")||detailMapData.getFieldChinese(position).equals("企业位置纬度")){
			convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_company_detail, null);
			convertView.setVisibility(View.GONE);
		}else{
			convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_company_detail, null);
		}
		((TextView)convertView.findViewById(R.id.companyField)).setText(getItem(position).toString());
		final EditText valueedit=((EditText)convertView.findViewById(R.id.companyValue));
		viewmap.put(detailMapData.getField(position), valueedit);
		String valuename=detailMapData.getValue(position);
		if(detailMapData.getFieldChinese(position).equals("企业详细地址")){
			String longs=detailMapData.getValue(7);//经度
			String lats=detailMapData.getValue(26);//纬度
			if(longs.equals("")||longs.equals(" ")||lats.equals("")||lats.equals(" ")){
				if(Static_InfoApp.create().isshow()){
					longs="103.830918";
					lats="36.068245";
					valuename=longs+","+lats;
				}else{
					
				}
//				
			}else{
				valuename=longs+","+lats;
			}
			
		}

		
		switch (editState) {
		case 1:

				valueedit.setText(valuename);
				convertviewmap.put(position, convertView);
//			valueedit.setEnabled(true);
			break;
		default:
			if(valuename.equals("")||valuename.equals(" ")){
					convertView.setVisibility(View.GONE);				
			}else{
				if(detailMapData.getFieldChinese(position).equals("企业详细地址")){
					//System.out.println("SRXLOC:"+valuename);
//					valueedit.setText(valuename);
//					convertviewmap.put(position, convertView);
				}else{
					convertviewmap.put(position, convertView);
				}
				valueedit.setText(valuename);
				
			}
			valueedit.setKeyListener(null);
			break;
		}

		Util_MatchTip.initAllScreenText(convertView,detailMapData.getKeyword());
		return convertView;
	}
	public View getView2(ViewGroup parent) {
		View convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_detail_line, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)((Static_InfoApp.create().getAppScreenHigh()/960))/2));
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
