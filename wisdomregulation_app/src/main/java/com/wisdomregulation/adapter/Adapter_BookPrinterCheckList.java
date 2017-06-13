package com.wisdomregulation.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.single.Activity_BookPrinter_Edit;
import com.wisdomregulation.allactivity.tab.Tab_CompanyInfo;
import com.wisdomregulation.allactivity.tab.Tab_History_Check2Again;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_BookList;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

import java.util.List;

public class Adapter_BookPrinterCheckList extends BaseAdapter {
private Activity context;
private List<Base_Entity> bookPrinterCompanyListData;

	public Adapter_BookPrinterCheckList(Activity context,
			List<Base_Entity> bookPrinterCompanyListData) {
	super();
	this.context = context;
	this.bookPrinterCompanyListData = bookPrinterCompanyListData;
}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bookPrinterCompanyListData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return bookPrinterCompanyListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_book_printercompany, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/5.73)));
		TextView bookPrinterCompanName=(TextView) convertView.findViewById(R.id.bookPrinterCompanyName);
		bookPrinterCompanName.setText(bookPrinterCompanyListData.get(position).getValue(0));
		Util_MatchTip.initAllScreenText(convertView);
		convertView.findViewById(R.id.top1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Base_Entity bookList=new Entity_BookList().init().put(7, bookPrinterCompanyListData.get(position).getValue(37));
				context.startActivity(new Intent(context, Activity_BookPrinter_Edit.class).putExtra("bookList", bookList));
				
			}
		});
		convertView.findViewById(R.id.left1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				context.startActivity(new Intent(context, Tab_CompanyInfo.class).putExtra("editState", 4).putExtra("companyEntity", bookPrinterCompanyListData.get(position)));
			}
		});
		convertView.findViewById(R.id.left2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				context.startActivity(new Intent(context, Tab_History_Check2Again.class).putExtra("companyid", bookPrinterCompanyListData.get(position).getId()));
			}
		});
		return convertView;
	}

}
