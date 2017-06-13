package com.wisdomregulation.allactivity.single;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_ExpandHistoryOptionList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_CheckDetail;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.map.ExpandMap;

import java.util.ArrayList;
import java.util.List;

public class Activity_History_CheckOption extends Base_AyActivity {
	private ExpandableListView inspectExpandList;
	private Adapter_ExpandHistoryOptionList adapter;
	private List<ExpandMap> datalist;
	private Base_Entity searchtarget;
	private String qiyecode;
	private String planid;
	private Base_Entity checkentity;

	@Override
	public void setRootView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_history_check_item);

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

		checkentity = (Base_Entity) this.getIntent().getSerializableExtra(
				"checkEntity");
		qiyecode = checkentity.getValue(5);
		planid = checkentity.getValue(0);
		datalist = new ArrayList<ExpandMap>();
		adapter = new Adapter_ExpandHistoryOptionList(this, datalist,
				inspectExpandList, true);
		searchtarget = new Entity_CheckDetail().init().put(25, qiyecode)
				.put(19, planid);

		//System.out.println("进来了1：Activity_History_CheckOption");
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		Activity_History_CheckOption.this.showWait();
		//System.out.println("进来了2：Activity_History_CheckOption");
		new Thread(new Runnable() {

			@Override
			public void run() {

				org = Help_DB.create().setLogic("and").setIsfull(true)
						.search(searchtarget);
				Activity_History_CheckOption.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						datalist.clear();
						List<Base_Entity> tmpbaselist = (List<Base_Entity>) org
								.get(1);
						for (int i = 0; i < tmpbaselist.size(); i++) {
							datalist.add(new ExpandMap("")
									.setBaseentity(tmpbaselist.get(i)));
						}

						Activity_History_CheckOption.this.dissmissWait();
						adapter.notifyDataSetChanged();
						inspectExpandList.setOnGroupClickListener(new OnGroupClickListener() {
							
							@Override
							public boolean onGroupClick(ExpandableListView parent, View v,
									int groupPosition, long id) {
								return true;
							}
						});
						for (int i = 0; i < adapter.getGroupCount(); i++) {
//							sourceGrid.collapseGroup(i);
							inspectExpandList.expandGroup(i);
						}

					}
				});

			}
		}).start();

	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		inspectExpandList=(ExpandableListView)findViewById(R.id.inspectExpandList);
        inspectExpandList.setAdapter(adapter);
		initView();
	}

}
