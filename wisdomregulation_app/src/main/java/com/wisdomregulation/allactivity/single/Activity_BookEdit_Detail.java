package com.wisdomregulation.allactivity.single;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_BookDetail;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.helporg2017.Pdf_Shark2017;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_Print;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_BookEdit_Detail extends Base_AyActivity {

	private TextView newtitle;
	private LinearLayout fieldContent;
	private Base_Entity searchbook;
	private Map<String, EditText> valueViewMap = new HashMap<String, EditText>();
	private Base_Entity bookentity;
	private Adapter_BookDetail adapter;
	private int editState = 1;
	public boolean canedit;
	private LinearLayout canhide;
	public Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 222:
				print(true);
				break;

			default:
				break;
			}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_book_detail);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

		editState = this.getIntent().getIntExtra("editState", 1);

		searchbook = (Base_Entity) this.getIntent().getSerializableExtra(
				"searchbook");
		String id=searchbook.getId();
		List org = Help_DB.create().search(searchbook);
		List<Base_Entity> tmp = (List<Base_Entity>) org.get(1);
		if (tmp.size() > 0) {
			bookentity = tmp.get(0);
		}

		if (bookentity == null) {
			bookentity = searchbook.init();
		}
	}
	@Override
	public void toMore(View view) {
		// TODO Auto-generated method stub
		super.toMore(view);
		canedit=!canedit;
		if(canedit){
			
			((TextView)view).setText("取消编辑");
			adapter.setEditState(canedit).initView();
			adapter.setFocus(0);
		}else{
			hideInputSoft();
			((TextView)view).setText("编    辑");
			adapter.setEditState(canedit).initView();
		}
		

		
		
	}
	public void print(boolean canprint){
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Activity_BookEdit_Detail.this.dissmissWait();
				Util_Print.print(Static_InfoApp.create().getPath()+"/preview.pdf",Activity_BookEdit_Detail.this,2101);
//					Activity_BookEdit_Detail.this.dissmissWait();
//					File file = new File(Static_InfoApp.create().getPath()+"/preview.pdf");
//					Intent i = new Intent(Intent.ACTION_VIEW);
//					i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//					i.setPackage(Static_ConstantLib.pack);
//					i.setDataAndType(Uri.fromFile(file), "application/pdf");
//					Activity_BookEdit_Detail.this.startActivity(i);
			}
		}, 300);
	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		newtitle=(TextView)findViewById(R.id.newtitle);
		fieldContent=(LinearLayout)findViewById(R.id.fieldContent);
		canhide=(LinearLayout)findViewById(R.id.canhide);
		if(editState==1){
			canedit=true;
		}else{
			canedit=false;
		}
		if(editState!=1){
			canhide.setVisibility(View.VISIBLE);
		}
		adapter = new Adapter_BookDetail(this, bookentity, fieldContent)
				.setEditState(canedit).initView();
	}

	public void prints(View view) {
		final Base_Entity saveresult = adapter.getResult();
		for (int i = 0; i < saveresult.size(); i++) {
			//System.out.println("待打印物品"+saveresult.getFieldChinese(i)+":"+saveresult.getValue(i));
		}
		Activity_BookEdit_Detail.this.showWait();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Help_DB.create().save2update(saveresult);
				Pdf_Shark2017.create().setFileout(Static_InfoApp.create().getPath()+"/preview.pdf").open().printerMaster(saveresult).close();
				handler.sendEmptyMessage(222);
			}
		}).start();
	}

	public void savecancel(View view) {
		final Base_Entity saveresult = adapter.getResult();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Help_DB.create().save2update(saveresult);
				
			}
		}).start();
		
		Activity_BookEdit_Detail.this.finish();
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
}
