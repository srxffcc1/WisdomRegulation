package com.wisdomregulation.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.mode.Mode_BookPrinter;
import com.wisdomregulation.allactivity.mode.Mode_CheckMain;
import com.wisdomregulation.allactivity.mode.Mode_CompanyInfo;
import com.wisdomregulation.allactivity.mode.Mode_DangerousCheckMain;
import com.wisdomregulation.allactivity.mode.Mode_EvidenceCollect;
import com.wisdomregulation.allactivity.mode.Mode_GovernmentNotice;
import com.wisdomregulation.allactivity.mode.Mode_History;
import com.wisdomregulation.allactivity.mode.Mode_Law_Main;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.test.TestEdit;
import com.wisdomregulation.utils.Util_MatchTip;

import java.util.List;

public class Adapter_MenuMainList extends BaseAdapter{
	private Activity context;
	private List<String> menuListData;
	private int splitCount;
	public Adapter_MenuMainList(Activity context, List<String> menuList,int splitCount) {
		super();
		this.context = context;
		this.menuListData = menuList;
		this.splitCount=splitCount;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return menuListData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return menuListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_main, null);
		TextView logo=(TextView)(convertView.findViewById(R.id.menuImage));
		logo.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,(int)Static_InfoApp.create().getAppScreenWidth()/3));
		logo.setText(menuListData.get(position));
//		Util_Image.scaleImage(context, logo, R.drawable.ic_launcher,splitCount);
//		logo.setImageResource(R.drawable.ic_launcher);
		logo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (position) {
				case 0:
					context.startActivity(new Intent(context, Mode_CompanyInfo.class));
					
					break;
				case 1:
					context.startActivity(new Intent(context, Mode_CheckMain.class));
					break;
				case 2:
					context.startActivity(new Intent(context, Mode_Law_Main.class));
					break;
				case 3:
					context.startActivity(new Intent(context, Mode_DangerousCheckMain.class));
					break;
				case 4:
					context.startActivity(new Intent(context, Mode_History.class));
					break;
				case 5:
					context.startActivity(new Intent(context, Mode_BookPrinter.class));
					break;
				case 6:
					context.startActivity(new Intent(context, Mode_GovernmentNotice.class));
					break;
				case 7:
					context.startActivity(new Intent(context, Mode_EvidenceCollect.class));
					break;
				case 8:
					
					break;
				case 9:
					
					break;
				case 10:
					
					break;
				case 11:
					context.startActivity(new Intent(context, TestEdit.class));
					break;
					
				default:
					break;
				}
				
			}
		});
		Util_MatchTip.initAllScreenText(convertView);
		return convertView;
	}

}
