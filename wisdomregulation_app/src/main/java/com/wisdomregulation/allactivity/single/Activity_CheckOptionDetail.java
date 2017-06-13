package com.wisdomregulation.allactivity.single;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_CheckOptionDetail;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.allactivity.mode.Mode_EvidenceCollect;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entitylibrary.Entity_LibraryLawDependence;
import com.wisdomregulation.data.entityother.Entity_CheckDetail;
import com.wisdomregulation.data.entityother.Entity_Company;
import com.wisdomregulation.data.entityother.Entity_LibraryLawDep;
import com.wisdomregulation.data.entityother.Entity_OrgData;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_Db;
import com.wisdomregulation.utils.Util_File;
import com.wisdomregulation.utils.Util_MatchTip;
import com.wisdomregulation.utils.Util_String;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Activity_CheckOptionDetail extends Base_AyActivity {
	LinearLayout content;
	TextView title;
	TextView ishistoryText;
	String titletext;
	Base_Entity optionresult;
	private Adapter_CheckOptionDetail adapter;
	private TmpBroadcastReceiver receiver;
	private boolean isnew=true;
	private boolean ishistory;
	private String id;
	private String filepath;
	private String planid;
	private String linkid;
	private String qiyecode;
	
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_check_itemdetail);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub


		titletext = this.getIntent().getStringExtra("title");
		ishistory = this.getIntent().getBooleanExtra("ishistory", false);
		if(ishistory){
			ishistoryText.setText("退出查看");
		}
		planid = this.getIntent().getStringExtra("planid");
		linkid = this.getIntent().getStringExtra("linkid");
		qiyecode = this.getIntent().getStringExtra("qiyecode");

		receiver = new TmpBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Mode_EvidenceCollect.refresh);
		this.registerReceiver(receiver, filter);
	}
	@Override
	public void finish() {
		//System.out.println("发出刷新");
		
		super.finish();
		if(isnew){
			Util_File.deleteFile(new File(Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/"+filepath));
		}else{
			if(adapter.getIsdangerous()==0){
				Util_File.deleteFile(new File(Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/"+filepath));
			}
			
		}
		Activity_CheckOptionDetail.this.sendBroadcast(new Intent(Activity_CheckOptionList.refresh));
	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		content=(LinearLayout)findViewById(R.id.fieldContent);
		title=(TextView)findViewById(R.id.newtitle);
		ishistoryText=(TextView)findViewById(R.id.ishistoryText);

		title.setEllipsize(TruncateAt.MARQUEE);
		title.setFocusable(true);
		title.setMarqueeRepeatLimit(1);
		title.setFocusableInTouchMode(true);
		title.setSingleLine();
		title.setText(titletext);
		Activity_CheckOptionDetail.this.showWait();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				optionresult=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().search(new Entity_CheckDetail().clear().put(19, planid).put(1, linkid).put(25, qiyecode)));
				if(Util_MatchTip.isnotnull(optionresult)){
					isnew=false;
					
				}else{
					optionresult=new Entity_CheckDetail().clear().put(19, planid).put(1, linkid).put(25, qiyecode);
					
					
				}
				id = optionresult.getId().equals("")?Util_String.get16Uuid():optionresult.getId();
				optionresult.setId(id);

				filepath = Help_DB.create().getTableName(optionresult)+"@"+id+"_wszgq";
				if(new File(Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/"+filepath).exists()){
					
				}else{
					new File(Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/"+filepath).mkdirs();
				}
				Base_Entity jianchayaoqiuentity=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().search(new Entity_LibraryLawDependence().setId(optionresult.getValue(1))));
				final String jianchayaoqiu=jianchayaoqiuentity.getValue(5);
				String zhifajiluguanlian=jianchayaoqiuentity.getValue(8);
				final List<String> falvyiju=new ArrayList<String>();
				final List<String> zhifayiju=new ArrayList<String>();
				final List<String> yinhuanleixing=new ArrayList<String>();
				if(!zhifajiluguanlian.equals("")&&!zhifajiluguanlian.equals(" ")){
					List<Base_Entity> falist=Util_MatchTip.getSearchResult(Help_DB.create().search(new Entity_LibraryLawDep().put(2, zhifajiluguanlian)));
					for (int i = 0; i < falist.size(); i++) {
						String falvyijuname=falist.get(i).getValue(1);
						String zhifayijuname=falist.get(i).getValue(0);
						if(!falvyijuname.equals("")&&!zhifayijuname.equals("")){
							falvyiju.add(falvyijuname);
							zhifayiju.add(zhifayijuname);
						}
					}
				}
				List<Base_Entity> yinhuanleilist=Util_MatchTip.getSearchResult(Help_DB.create().search(new Entity_OrgData().put(1, "yhzl")));
				for (int i = 0; i < yinhuanleilist.size(); i++) {
					yinhuanleixing.add(yinhuanleilist.get(i).getValue(3));
				}

				
				Activity_CheckOptionDetail.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						adapter = new Adapter_CheckOptionDetail(Activity_CheckOptionDetail.this, optionresult, content)
						.setEditState(1).setList(falvyiju, zhifayiju, yinhuanleixing, jianchayaoqiu).setFilepath(filepath).initView().initGrid();
						Activity_CheckOptionDetail.this.dissmissWait();
						
					}
				});
				
				
			}
		}).start();

		
		
		
	}
	public void initGrid(){
		adapter.initGrid();
	}
	@Override
	public void toMore(View view) {
		// TODO Auto-generated method stub
		super.toMore(view);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
		try {
			Activity_CheckOptionDetail.this.unregisterReceiver(receiver);
		} catch (Exception e) {
			// TODO: handle exception
		}
		super.onDestroy();
		
	}
	public void tofinishOption(View view) {
		
		if(Util_String.isInsertAll(adapter.getEdittextlist())||adapter.getIsdangerous()==0){
			if(!ishistory){
				final Base_Entity result=adapter.getResult();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						//System.out.println("企业代码保存:"+qiyecode);
						result.put(42, "政府抽查");
						result.put(25, qiyecode);
						result.put(23, Util_Db.code2idtoString(new Entity_Company().put(37, qiyecode), 0));
						result.put(19, planid).put(1, linkid).put(18, titletext);
						Help_DB.create().save2update(result);
						isnew=false;
						Activity_CheckOptionDetail.this.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								Activity_CheckOptionDetail.this.finish();
								
							}
						});
					
						
					}
				}).start();
			
				
			}else{
				Activity_CheckOptionDetail.this.finish();
			}
		}else{
			
			showToast("整改人未填写");
		}


		
		
		
	}
	class TmpBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Mode_EvidenceCollect.refresh)) {
				initGrid();
			}

		}

	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

}
