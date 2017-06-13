package com.wisdomregulation.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.utils.Util_MatchTip;

public class Adapter_HistoryLawList extends BaseAdapter {
private boolean CheckFlag=true;
private Activity context;
private List<String> historyHandlerListData;

	public Adapter_HistoryLawList(Activity context,
		List<String> historyHandlerListData) {
	super();
	this.context = context;
	this.historyHandlerListData = historyHandlerListData;
}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return historyHandlerListData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return historyHandlerListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_history_list, null);
		TextView historyName=(TextView) convertView.findViewById(R.id.historyName);
		historyName.setText(historyHandlerListData.get(position));
		Util_MatchTip.initAllScreenText(convertView);
		return convertView;
	}

}
