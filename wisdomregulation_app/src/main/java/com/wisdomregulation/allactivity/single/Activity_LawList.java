package com.wisdomregulation.allactivity.single;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_LawList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_Check;
import com.wisdomregulation.data.entityother.Entity_Law;
import com.wisdomregulation.help.Help_DB;

import java.util.ArrayList;
import java.util.List;

public class Activity_LawList extends Base_AyActivity {
private ListView caseList;
TextView newtitle;
ImageView lawadd;
private List<Base_Entity> caselistdata;
private int casefrom;
private boolean isall = true;
private Base_Entity searchtarget;
private BaseAdapter adapter;
public static final String refreshadapter = "Activity_LawList.refreshadapter";

private TmpBroadcastReceiver receiver;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_law_list);

	}
	@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();

		}
	@Override
		public void toMore(View view) {
		switch (casefrom) {
		case 0:
			Base_Entity check=new Entity_Check().init().put(16, "1").put(10, "0");
			this.startActivity(new Intent(this, Activity_ChoseCompany_ToLaw.class).putExtra("choselaw", check));
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;

		default:
			break;
		}
		}
	@Override
	public void initData() {
		// TODO Auto-generated method stub


		caselistdata=new ArrayList<Base_Entity>();
		searchtarget = new Entity_Law().clear().put(1, newtitle.getText().toString()).putlogic2value(11, "<>","归档阶段");
		adapter = new Adapter_LawList(Activity_LawList.this, caselistdata);

		receiver = new TmpBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(refreshadapter);
		this.registerReceiver(receiver, filter);
		
	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
        caseList=(ListView)findViewById(R.id.caseList);
        newtitle=(TextView)findViewById(R.id.newtitle);
        lawadd=(ImageView)findViewById(R.id.lawadd);
        casefrom = this.getIntent().getIntExtra("caseFrom", 0);
        lawadd.setVisibility(View.INVISIBLE);
        lawadd.setEnabled(false);
        switch (casefrom) {
            case 0:
                lawadd.setVisibility(View.VISIBLE);
                lawadd.setEnabled(true);
                newtitle.setText("执法检查发现");
                break;
            case 1:
                newtitle.setText("上级部门交办");
                break;
            case 2:
                newtitle.setText("同级部门交办");
                break;
            case 3:
                newtitle.setText("下级部门移交");
                break;
            case 4:
                newtitle.setText("群众投诉举报");
                break;
            case 5:
                newtitle.setText("其他案件来源");
                break;

            default:
                break;
        }
        caseList.setAdapter(adapter);
		initView();
		
	}

	public void initView(){
		Activity_LawList.this.showWait();
		setLimt();
		new Handler().postDelayed(new Runnable() {
			public void run() {
				
				
				org = Help_DB.create().setLimit(nowlmit).setNeedcount(true)
						.setIsfull(isall).setLogic("and").search(searchtarget);
				allpage = getAllPage(Integer.parseInt(org.get(0).toString()));
				caselistdata.clear();
				caselistdata.addAll((List<Base_Entity>) org.get(1));
				adapter.notifyDataSetChanged();
				pageDown.setText(pageIndex + "/" + allpage+"\n总条目:"+org.get(0).toString()+" 本页:"+caselistdata.size());
				Activity_LawList.this.dissmissWait();
			}
		}, 500);
	}


	@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			try {
				this.unregisterReceiver(receiver);
			} catch (Exception e) {

			}
		}

	private class TmpBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent.getAction().equals(refreshadapter)) {
				initView();
			}


		}

	}
}
