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
import com.wisdomregulation.allactivity.tab.Tab_CheckAgain;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_Company;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_CheckAgainList extends BaseAdapter{
	private Activity context;
	private List<Base_Entity> lawAgainListData;
	private Map<Integer,String> qiyenamemap=new HashMap<Integer,String>();
	private Map<Integer,Boolean> qiyecanclick=new HashMap<Integer,Boolean>();
	public Adapter_CheckAgainList(Activity context, List<Base_Entity> lawAgainDataList) {
		super();
		this.context = context;
		this.lawAgainListData = lawAgainDataList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lawAgainListData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lawAgainListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public void notifyDataSetChanged() {
		qiyenamemap.clear();
		super.notifyDataSetChanged();
		
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_checkagain_list, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/10.5)));
		final TextView qiyename=((TextView)convertView.findViewById(R.id.lawItem));
		if(qiyenamemap.get(position)==null){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					//System.out.println("搜索条件："+lawAgainListData.get(position).getValue(5));
					final String qiyenametest=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().search(new Entity_Company().put(37, lawAgainListData.get(position).getValue(5)))).getValue(0);
					
					context.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {

							
							if(qiyenametest.equals("")||qiyenametest.equals(" ")){
								qiyename.setText("此企业已经被删除");
								qiyenamemap.put(position, "此企业已经被删除");
								qiyecanclick.put(position, false);
							}else{
								qiyename.setText(qiyenametest);
								qiyenamemap.put(position, qiyenametest);
								qiyecanclick.put(position, true);
							}
							
							
							
						}
					});
					
				}
			}).start();
		}else{
			final String qiyenametest=qiyenamemap.get(position);
			qiyename.setText(qiyenametest);
		}

				
		Util_MatchTip.initAllScreenText(convertView);
		convertView.findViewById(R.id.startagain).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(qiyecanclick.get(position)!=null&&qiyecanclick.get(position)){
					context.startActivity(new Intent(context, Tab_CheckAgain.class).putExtra("againcheck", lawAgainListData.get(position)));
				}else{
					Static_InfoApp.create().showToast("企业数据不正确，可能在后台被删除");
				}
				
				
			}
		});
		return convertView;
	}

}
