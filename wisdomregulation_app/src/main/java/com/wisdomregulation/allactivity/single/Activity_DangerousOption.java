package com.wisdomregulation.allactivity.single;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_DangerousOptionDetail;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.allactivity.mode.Mode_EvidenceCollect;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_InfoApp;

import java.io.File;

public class Activity_DangerousOption extends Base_AyActivity {
	LinearLayout content;
	TextView title;
	TextView historyText;
	String titletext;
	String type;
	Base_Entity option;
	private String pid;
	private String fieldname;
	private String fielddetailname;
	private Adapter_DangerousOptionDetail adapter;
	private boolean ishistory;
	private String id;
	private String filepathon;
	private String filepathoff;
	private TmpBroadcastReceiver receiver;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_dangerous_fromlist);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub

		option=(Base_Entity) this.getIntent().getSerializableExtra("dangerousEntity");
		ishistory = this.getIntent().getBooleanExtra("ishistory", false);

		id = option.getId();
		receiver = new TmpBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Mode_EvidenceCollect.refresh);
		this.registerReceiver(receiver, filter);
	}

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		content=(LinearLayout)findViewById(R.id.fieldContent);
		title=(TextView)findViewById(R.id.newtitle);
		historyText=(TextView)findViewById(R.id.historyText);
		if(ishistory){
			historyText.setText("退出查看");
		}
		initView();
		
	}
	@Override
	public void initView() {

		Activity_DangerousOption.this.showWait();
		new Handler().postDelayed(new Runnable() {
			


			@Override
			public void run() {
				filepathon = Help_DB.create().getTableName(option)+"@"+id+"_wszgq";
				filepathoff = Help_DB.create().getTableName(option)+"@"+id+"_wszgh";
				if(new File(Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/"+filepathon).exists()){
					
				}else{
					new File(Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/"+filepathon).mkdirs();
				}
				if(new File(Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/"+filepathoff).exists()){
					
				}else{
					new File(Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/"+filepathoff).mkdirs();
				}
				adapter = new Adapter_DangerousOptionDetail(Activity_DangerousOption.this, option, content).setFilepathon(filepathon).setFilepathoff(filepathoff).initView().initGrid().initGrid2();
				Activity_DangerousOption.this.dissmissWait();
			}
		}, 300);
	}
	@Override
	public void toMore(View view) {
		// TODO Auto-generated method stub
		super.toMore(view);
	}

	public void tofinishOption(View view) {
		if(!ishistory){
			Base_Entity result=adapter.getResult();
			Help_DB.create().save2update(result);
			this.finish();
			Static_InfoApp.create().getContext().sendBroadcast(new Intent(Activity_CheckAgainOptionList.refresh));
			
		}else{
			this.finish();
		}
		Activity_DangerousOption.this.sendBroadcast(new Intent(Activity_DangerousCheckList.refresh));
		
		
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			Activity_DangerousOption.this.unregisterReceiver(receiver);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void initGrid(){
		//System.out.println("刷新表格");
		adapter.initGrid();
		adapter.initGrid2();
	}
	class TmpBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Mode_EvidenceCollect.refresh)) {
				initGrid();
			}

		}

	}

}
