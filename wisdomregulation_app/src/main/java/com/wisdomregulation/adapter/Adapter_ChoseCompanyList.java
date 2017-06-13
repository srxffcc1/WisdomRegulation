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
import com.wisdomregulation.allactivity.single.Activity_ChoseCompany_ToCheck;
import com.wisdomregulation.allactivity.tab.Tab_Check;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_Check;
import com.wisdomregulation.frame.AutoCheckBox;
import com.wisdomregulation.frame.AutoCheckGroup;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_ConstantLib;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;
import com.wisdomregulation.utils.Util_String;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_ChoseCompanyList extends BaseAdapter {
	private Activity context;
	private String planid;
	private List<Base_Entity> lawDetailListData;
	private String editstate = "0";
	private Map<String, Boolean> viewmap = new HashMap<String, Boolean>();
	private Map<String, Integer> viewmap2 = new HashMap<String, Integer>();

	public Adapter_ChoseCompanyList(Activity context,
			List<Base_Entity> lawDetailListData) {
		super();
		this.context = context;
		this.lawDetailListData = lawDetailListData;
		viewmap.clear();
		viewmap2.clear();
	}

	public Adapter_ChoseCompanyList setPlanid(String planid) {
		this.planid = planid;
		return this;
	}

	public void getChoseResult() {
		final List<Base_Entity> choseResult = new ArrayList<Base_Entity>();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				for (int i = 0; i < lawDetailListData.size(); i++) {
					if(viewmap.get(lawDetailListData.get(i).getId()) != null
							&& viewmap.get(lawDetailListData.get(i).getId())){
						Base_Entity check=new Entity_Check().init();
						String resultid=Util_String.get16Uuid();
//						check.put(3, resultid);
						final Base_Entity resultentity;
						if(viewmap2.get(lawDetailListData.get(i).getId()) != null){
							
							switch (viewmap2.get(lawDetailListData.get(i).getId())) {
							case 0:
								check.put(2, "职业卫生检查");

								
								break;
							case 1:
								check.put(2, "企业安全检查");
								break;
							case 2:
								check.put(2, "职业卫生检查");
								break;

							default:
								check.put(2, "职业卫生检查");
								break;
							}
						}else{
							check.put(2, "职业卫生检查");
						}
//						for (int j = 1; j < resultentity.size(); j++) {
//							String resultfield=resultentity.getField(j);
//							if(!resultfield.startsWith("_")){
//								final String optionid=Util_String.get16Uuid();
//								String jianchaleixing=resultentity.getClass().getSimpleName();
////								if(jianchaleixing.equalsIgnoreCase("Entity_CheckHealthResult")){
//////									Help_DB.create().save2update(new Entity_CheckHealthDetail().init().setId(optionid).put("来自何结果项目", resultentity.getId()));
////
////									
////								}else if(jianchaleixing.equalsIgnoreCase("Entity_CheckSaveResult")){
//////									Help_DB.create().save2update(new Entity_CheckSaveDetail().init().setId(optionid).put("来自何结果项目", resultentity.getId()));
////
////									
////								}
////								resultentity.put(j, optionid);
//								
//							}
//						}
//						Help_DB.create().save2update(resultentity);

						
						String companyname=lawDetailListData.get(i).getValue(0);
								String companyid=lawDetailListData.get(i).getValue(37);
								check.put(4, companyname);
								check.put(5, companyid);
								check.put(15, "1");
								check.put(1, Static_ConstantLib.planduty+companyname+"_"+Util_String.getDate());
								check.put(0, planid);
								check.put(6, "0");
								check.put(14, Static_InfoApp.create().getUserName());
								check.put(16, "0");
								check.put(10, "0");
								Help_DB.create().save2update(check);
						
						
					}
				}
				Static_InfoApp.create().getContext().sendBroadcast(new Intent(Activity_ChoseCompany_ToCheck.finish));
				
			}
		}).start();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lawDetailListData.size();
	}

	public String getEditstate() {
		return editstate;
	}

	public Adapter_ChoseCompanyList setEditstate(String editstate) {
		this.editstate = editstate;
		return this;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lawDetailListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(
				R.layout.item_activity_chose_company_check, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				(int) (Static_InfoApp.create().getAppScreenHigh() / 12)));
		((TextView) convertView.findViewById(R.id.lawDetailItem))
				.setText(lawDetailListData.get(position).getValue(0));
		final AutoCheckBox chose = (AutoCheckBox) convertView
				.findViewById(R.id.againchoseCheck);
		final AutoCheckGroup checkgroup = (AutoCheckGroup) convertView
				.findViewById(R.id.checkgroup);
		Util_MatchTip.initAllScreenText(convertView);
		if (viewmap.get(lawDetailListData.get(position).getId()) != null
				&& viewmap.get(lawDetailListData.get(position).getId())) {
			chose.setChecked(true);
		} else {
			chose.setChecked(false);
		}
		chose.setOnCheckedChangeListener(new com.wisdomregulation.frame.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(View buttonView, boolean isChecked) {
				viewmap.put(lawDetailListData.get(position).getId(), isChecked);

			}
		});
		if (viewmap2.get(lawDetailListData.get(position).getId()) != null) {
			checkgroup.setCheck(viewmap2.get(lawDetailListData.get(position)
					.getId()));
		} else {

		}
		checkgroup
				.setOnCheckedChangeListener(new com.wisdomregulation.frame.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(View buttonView,
							boolean isChecked) {
						if (isChecked) {
							int checkresultindex=getId2Index(buttonView.getId());
							viewmap2.put(lawDetailListData.get(position)
									.getId(), checkresultindex);
						}

					}
				});
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (editstate.equals("0")) {
					context.startActivity(new Intent(context,
							Tab_Check.class).putExtra("checkEntity",
							(lawDetailListData.get(position))));
				}
			}
		});
		return convertView;
	}
	public int getId2Index(int id){
		int checkresultindex = 0;
		switch (id) {
		case R.id.check1:
			checkresultindex = 0;
			break;
		case R.id.check2:
			checkresultindex = 1;
			break;
		case R.id.check3:
			checkresultindex = 2;
			break;

		default:
			break;
		}
		return checkresultindex;
	}
}
