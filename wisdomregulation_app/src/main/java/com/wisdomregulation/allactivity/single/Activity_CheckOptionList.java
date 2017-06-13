package com.wisdomregulation.allactivity.single;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.ExpandableListView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_ExpandInspect;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entitylibrary.Entity_LibraryLawDependence;
import com.wisdomregulation.data.entityother.Entity_CheckDetail;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.map.ExpandMap;
import com.wisdomregulation.utils.Util_Db;
import com.wisdomregulation.utils.Util_MatchTip;
import com.wisdomregulation.utils.Util_String;

import java.util.ArrayList;
import java.util.List;

public class Activity_CheckOptionList extends Base_AyActivity {
	private ExpandableListView inspectExpandList;
	private List<ExpandMap> expandList;
	private Base_Entity checkEntity;
	private Adapter_ExpandInspect adapter;
	private String tablename;
	public static final String refresh = "Activity_InspectList.refresh";
	private TmpBroadcastReceiver receiver;
	private boolean ishistory;
	private String checkid;
	private List<Base_Entity> optionlist;
	private String planid;
	private String qiyecode;
	private List<ExpandMap> expandListtmp;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_check_item);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

//		Activity_CheckOptionList.this.showWait();
		expandList = new ArrayList<ExpandMap>();
		ishistory = this.getIntent().getBooleanExtra("ishistory", false);
		checkEntity = (Base_Entity) this.getIntent().getSerializableExtra(
				"checkEntity");
		tablename = checkEntity.getValue(2);
		qiyecode = checkEntity.getValue(5);
		planid = checkEntity.getValue(0);
		adapter = new Adapter_ExpandInspect(Activity_CheckOptionList.this,
				expandList, inspectExpandList, ishistory).setCheckid(planid);
		//System.out.println("企业代码:"+qiyecode);
		adapter.setQiyecode(qiyecode);


		// if(tablename.equals("职业卫生检查")){
		// save2helthEntity=new Entity_CheckResult_Health().init();
		// }if(tablename.equals("企业安全检查")){
		// save2helthEntity=new Entity_CheckResult_Save().init();
		//
		// }else{
		// save2helthEntity=new Entity_CheckResult_Health().init();
		// }
//		save2helthEntity = new Entity_CheckResult_Health().init();
		
		
		
		
	}

	public void initView() {
		Activity_CheckOptionList.this.showWait();
		new Thread(new Runnable() {
			
			

			@Override
			public void run() {
				final List<String> issaved=new ArrayList<String>();//获得一个已经保存的列表
				issaved.clear();
				issaved.addAll(Util_Db.code2idtoStringList(new Entity_CheckDetail().put(19, planid).put(25, qiyecode), 1));
				adapter.setIssave(issaved);
				if(expandList.size()==0){
					expandListtmp = new ArrayList<ExpandMap>();
					optionlist = Util_MatchTip.getSearchResult(Help_DB.create().setOrder(Util_String.getchinese2pinyin("检查项目三级")+" desc").search(new Entity_LibraryLawDependence()));
					
					expandListtmp.clear();
					for (int i = 0; i < optionlist.size(); i++) {
						String sanjifenlei = optionlist.get(i).getValue(3);
						boolean has = false;
						if (expandListtmp.size() != 0) {
							for (int j = 0; j < expandListtmp.size(); j++) {
								String tmpname = expandListtmp.get(j).getName();
								if (tmpname.equals(sanjifenlei)) {
									has = true;
								} else {

								}
							}
						} else {
							has = false;
						}
						if (!has) {
							expandListtmp.add(new ExpandMap(sanjifenlei));
						}
					}
					for (int i = 0; i < expandListtmp.size(); i++) {
						for (int j = 0; j < optionlist.size(); j++) {
								String tmpname = expandListtmp.get(i).getName();
								String sanjifenlei = optionlist.get(j).getValue(3);
								if (tmpname.equals(sanjifenlei)) {
									ExpandMap tmpex=new ExpandMap(optionlist.get(j).getValue(4)).setId(optionlist.get(j).getId());
									expandListtmp.get(i).add(tmpex);
								}
							}
						}
					Activity_CheckOptionList.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							expandList.clear();
							expandList.addAll(expandListtmp);
							adapter.notifyDataSetChanged();
							Activity_CheckOptionList.this.dissmissWait();
							
						}
					});
				}else{
//					for (int i = 0; i < inspectExpandList.getCount(); i++) {
//						inspectExpandList.collapseGroup(i);
//					}
//					for (int i = 0; i < inspectExpandList.getCount(); i++) {
//						inspectExpandList.expandGroup(i);
//					}
					Activity_CheckOptionList.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							adapter.notifyDataSetChanged();
							Activity_CheckOptionList.this.dissmissWait();
							
						}
					});

				}

				
			}
		}).start();
				
				




	}

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		inspectExpandList=(ExpandableListView)findViewById(R.id.inspectExpandList);
        inspectExpandList.setAdapter(adapter);
		receiver = new TmpBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(refresh);
		this.registerReceiver(receiver, filter);
		initView();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			this.unregisterReceiver(receiver);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private class TmpBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(refresh)) {
				//System.out.println("刷新");
				initView();
			}

		}

	}

}
