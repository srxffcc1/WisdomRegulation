package com.wisdomregulation.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.mode.Mode_EvidenceCollect;
import com.wisdomregulation.entityfile.EntityS_File;
import com.wisdomregulation.help.Help_ImageLoader;
import com.wisdomregulation.staticlib.Static_InfoApp;

import java.util.List;

public class Adapter_DangerousOptionDetailGrid {
	private Activity context;
	private GridLayout content;
	private List<EntityS_File> needshowfilelist;
	public Adapter_DangerousOptionDetailGrid(Activity context,
			List<EntityS_File> needshowfilelist, GridLayout content) {
		super();
		this.context = context;
		this.needshowfilelist = needshowfilelist;
		this.content = content;
	}

	public Adapter_DangerousOptionDetailGrid initView() {
		content.removeAllViews();
		for (int i = 0; i < getCount(); i++) {
			View add = getView(i, content);
			if (add != null) {
				content.addView(add);
			}
			

		}
		return this;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return needshowfilelist.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return needshowfilelist.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(final int position, ViewGroup parent) {
		View convertView = LayoutInflater.from(context).inflate(
				R.layout.item_activity_collect, null);
		convertView.setLayoutParams(new LayoutParams((int)Static_InfoApp.create().getAppScreenWidth()*25/27/4,(int)Static_InfoApp.create().getAppScreenWidth()*25/27/4));
		FrameLayout cansize=(FrameLayout) convertView.findViewById(R.id.cansize);
		cansize.setLayoutParams(new LayoutParams((int)Static_InfoApp.create().getAppScreenWidth()*25/27*5/21,(int)Static_InfoApp.create().getAppScreenWidth()*25/27*5/21));
		LayoutParams pra=new LayoutParams((int)Static_InfoApp.create().getAppScreenWidth()/13, (int)Static_InfoApp.create().getAppScreenWidth()/13);
		final ImageView logimage=(ImageView) convertView.findViewById(R.id.needfiximage);
		ImageView fileflag=(ImageView) convertView.findViewById(R.id.fileflag);
		TextView logo=(TextView)(convertView.findViewById(R.id.menuImage));
		logo.setText(needshowfilelist.get(position).getFilename());
		switch (needshowfilelist.get(position).getFileType()) {
		case 2:
			fileflag.setImageResource(R.drawable.flag_06);
			break;
		default:
			break;
		}
		logimage.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,(int)Static_InfoApp.create().getAppScreenWidth()*25/27*5/21));
		Static_InfoApp.create().getAllhandler().postDelayed(new Runnable(){public void run(){
				Help_ImageLoader.getInstance().load2(logimage, context, needshowfilelist.get(position).getFileallpath(), Mode_EvidenceCollect.dialogdismiss);
		}}, 300);
		   
		
		return convertView;
	}

}
