package com.wisdomregulation.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.single.Activity_Model;
import com.wisdomregulation.allactivity.single.Activity_Video;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.staticlib.Static_InfoApp;

import java.util.List;

public class Adapter_Point extends BaseAdapter{
	private Activity context;
	private List<Base_Entity> pointmodelDataList;
	public Adapter_Point(Activity context, List<Base_Entity> lawList) {
		super();
		this.context = context;
		this.pointmodelDataList = lawList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pointmodelDataList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pointmodelDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_point, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/6.018)));
		convertView.findViewById(R.id.model11).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context,Activity_Model.class));
				
			}
		});
		convertView.findViewById(R.id.model12).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context,Activity_Model.class));
				
			}
		});
		convertView.findViewById(R.id.model13).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context,Activity_Model.class));
				
			}
		});
		convertView.findViewById(R.id.model14).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context,Activity_Model.class));
				
			}
		});
		convertView.findViewById(R.id.passtovideo).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context,Activity_Video.class));
				
			}
		});

		return convertView;
	}

}
