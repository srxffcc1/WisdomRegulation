package com.wisdomregulation.allactivity.mode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_CollectList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.allactivity.single.Activity_PictureCollect;
import com.wisdomregulation.allactivity.tmp.Tmp_VideoActivity;
import com.wisdomregulation.allactivity.tmp.Tmp_VoiceActivity;
import com.wisdomregulation.entityfile.EntityS_File;
import com.wisdomregulation.map.ExpandMap;
import com.wisdomregulation.pop.Pop_Tool;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_File;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Mode_EvidenceCollect extends Base_AyActivity {

	private PopupWindow pop;
	private String voicepath;
	RecyclerView sourceGrid;
	GridLayoutManager staggeredGridLayoutManager;
	LinearLayout needshowunder;
	private List<ExpandMap> expandList;
	public static final String refresh = "Mode_EvidenceCollect.refresh";
	public static final String show = "Mode_EvidenceCollect.show";
	public static final String dismiss = "Mode_EvidenceCollect.dismiss";
	public static final String dialogdismiss = "Mode_EvidenceCollect.dialogdismiss";
	private TmpBroadcastReceiver receiver;
	private Adapter_CollectList adapter;
	private String savehead;

	@Override
	public void setRootView() {
		this.setContentView(R.layout.tab_evidence_menu);
		new File(Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/tmp"+"/").mkdirs();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
		try {
			Mode_EvidenceCollect.this.unregisterReceiver(receiver);
		} catch (Exception e) {
			// TODO: handle exception
		}
		super.onDestroy();
		
	}
	@Override
	public void toMore(View view) {
		pop = Pop_Tool.pop_evidencemore(this, view, R.layout.pop_edidence_chose);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}
	public void finishChoseEvidence(View view){
		if(savehead!=null){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					List<File> selectfile=adapter.getFileSelectList();
					for (int i = 0; i < selectfile.size(); i++) {
						selectfile.get(i).renameTo(new File(savehead+"/"+selectfile.get(i).getName()));
					}
					if(selectfile.size()>0){
						Static_InfoApp.create().getContext().sendBroadcast(new Intent(Mode_EvidenceCollect.refresh));
					}
				}
			}).start();
			finish();
		}else{

		}	
	}
	public void finishChoseDelete(View view){

			new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					List<File> selectfile=adapter.getFileSelectList();
					for (int i = 0; i < selectfile.size(); i++) {
						selectfile.get(i).delete();
					}
					if(selectfile.size()>0){
						Static_InfoApp.create().getContext().sendBroadcast(new Intent(Mode_EvidenceCollect.refresh));
					}
					
				}
			}).start();

	}
	public void cancelChoseEvidence(View view){
		adapter.setCancheck(false);
		adapter.checkBoxFresh();
		Static_InfoApp.create().getContext().sendBroadcast(new Intent(Mode_EvidenceCollect.dismiss));
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);


	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub

		savehead = this.getIntent().getStringExtra("savehead");
		expandList=new ArrayList<ExpandMap>();
		adapter = new Adapter_CollectList(this, expandList);

	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		sourceGrid=(RecyclerView) findViewById(R.id.sourceGrid);
		needshowunder=(LinearLayout)findViewById(R.id.needshowunder);
		staggeredGridLayoutManager = new GridLayoutManager(this,4, OrientationHelper.VERTICAL,false);
		adapter = new Adapter_CollectList(this, expandList);
		sourceGrid.setLayoutManager(staggeredGridLayoutManager);
		sourceGrid.setAdapter(adapter);
		needshowunder.setVisibility(View.INVISIBLE);
		receiver = new TmpBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Mode_EvidenceCollect.refresh);
		filter.addAction(Mode_EvidenceCollect.show);
		filter.addAction(Mode_EvidenceCollect.dismiss);
		filter.addAction(Mode_EvidenceCollect.dialogdismiss);
		this.registerReceiver(receiver, filter);
		initView();
	}


	public void initView(){
		adapter.clear();
		Mode_EvidenceCollect.this.showWait();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				init();
				Mode_EvidenceCollect.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						adapter.notifyDataSetChanged();
						Mode_EvidenceCollect.this.dissmissWait();

					}
				});
//		
			}
		}).start();
	}
	public void init() {
		File delete = new File(Static_InfoApp.create().getPath() + "/ZhiCollect/" + "/tmp" + "/" + ".jpg");
		if (delete.exists()) {
			delete.delete();
		}
		File dir = new File(Static_InfoApp.create().getPath() + "/ZhiCollect/" + "/tmp");
		expandList.clear();
		if (dir.exists()) {
			List<File> file = Util_File.getFileSort(dir);
			for (int i = 0; i < file.size(); i++) {
				String datastring = new SimpleDateFormat("yyyy-MM-dd")
						.format(file.get(i).lastModified());
				if (expandList.size() > 0) {
					if (expandList.get(expandList.size() - 1).getName().equals(datastring)) {
						expandList.add(new ExpandMap(datastring).setViewtype(1).setEntityvalue(new EntityS_File(this, file.get(i))));
					} else {
						expandList.add(new ExpandMap(datastring).setViewtype(0));
						expandList.add(new ExpandMap(datastring).setViewtype(1).setEntityvalue(new EntityS_File(this, file.get(i))));
					}
				} else {
					expandList.add(new ExpandMap(datastring).setViewtype(0));
					expandList.add(new ExpandMap(datastring).setViewtype(1).setEntityvalue(new EntityS_File(this, file.get(i))));
				}
			}

		}
//		expandList.add(new ExpandMap("2017-06-14").setViewtype(0));
	}
	public void showPictureCollect(View view) {
		pop.dismiss();
		startActivity(new Intent(this, Activity_PictureCollect.class).putExtra("savehead", Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/tmp"));
		
	}
	public void showVideoCollect(View view) {
		pop.dismiss();
		if(Static_InfoApp.create().isshow()){
			startActivity(new Intent(this, Tmp_VideoActivity.class).putExtra("savehead", Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/tmp"));
		}else{
			Static_InfoApp.create().showToast("暂不支持视频采集");
		}
		
//		
	}

	public void showVoiceCollect(View view) {
		pop.dismiss();
		startActivity(new Intent(this, Tmp_VoiceActivity.class).putExtra("savehead", Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/tmp"));
	}

	class TmpBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Mode_EvidenceCollect.refresh)) {
				initView();
			}
			if (intent.getAction().equals(Mode_EvidenceCollect.show)) {
				needshowunder.setVisibility(View.VISIBLE);
			}
			if (intent.getAction().equals(Mode_EvidenceCollect.dismiss)) {
				needshowunder.setVisibility(View.INVISIBLE);
			}
			if (intent.getAction().equals(Mode_EvidenceCollect.dialogdismiss)) {
				Mode_EvidenceCollect.this.dissmissWait();
			}

		}

	}
}
