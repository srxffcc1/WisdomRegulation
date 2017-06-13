package com.wisdomregulation.allactivity.tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.allactivity.single.Activity_AgainAdvice;
import com.wisdomregulation.allactivity.single.Activity_BookEdit;
import com.wisdomregulation.allactivity.single.Activity_CheckAgainOptionList;
import com.wisdomregulation.allactivity.single.Activity_CompanyDetail;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_BookList;
import com.wisdomregulation.data.entityother.Entity_Check;
import com.wisdomregulation.data.entityother.Entity_Company;
import com.wisdomregulation.dialog.Dialog_Tool;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_Finish;

import java.util.List;

public class Tab_CheckAgain extends Base_AyActivity {
	private FrameLayout tabMianContent;
	RadioButton radio1;
	RadioButton radio2;
	RadioButton radio3;
	TextView hisoryText;
	private PopupWindow popupWindow;
	private Base_Entity checkAgainEntity;
	private Base_Entity companyEntity;
	private Base_Entity bookList;
	private int block=2;
	private String againid;
	private boolean ishistory;
	private String qiyeid;
	private String planid;
	@Override
	public void setRootView() {
		if(Static_InfoApp.create().istest()){
		this.setContentView(R.layout.tab_againcheck_debug);
		}else{
			this.setContentView(R.layout.tab_againcheck);
		}

	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub

		checkAgainEntity = (Base_Entity) this.getIntent().getSerializableExtra("againcheck");
		ishistory=this.getIntent().getBooleanExtra("ishistory", false);

		planid = checkAgainEntity.getValue(0);
		againid = checkAgainEntity.getId();
		qiyeid = checkAgainEntity.getValue(5);
		List org=Help_DB.create().setIsall(false).search(new Entity_Company().init().put(37, qiyeid));
		List<Base_Entity> tmp=(List<Base_Entity>) org.get(1);
		if(tmp!=null&&tmp.size()>0){
			companyEntity=tmp.get(0);
			String fuchaid=checkAgainEntity.getId();
			String jianchaid=checkAgainEntity.getId();
			bookList=new Entity_BookList().init().put(0, jianchaid).put(7, qiyeid);
		}
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		
	}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		tabMianContent=(FrameLayout)findViewById(R.id.tabMianContent);
		radio1=(RadioButton)findViewById(R.id.radio1);
		radio2=(RadioButton)findViewById(R.id.radio2);
		radio3=(RadioButton)findViewById(R.id.radio3);
		hisoryText=(TextView)findViewById(R.id.hisoryText);
		if(ishistory){
			hisoryText.setText("退出查看");
		}
		switch (block) {
		case 1:
			radio1.performClick();
			break;
		case 2:
			radio2.performClick();
			break;
		case 3:
			radio3.performClick();
			break;

		default:
			radio2.performClick();
			break;
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	public void showCompanyDetail(View view) {
		block = 1;
		tabMianContent.removeAllViews();
		tabMianContent.addView(getIntentContentView(
				
				tabMianContent,
				"tabMianContent1",
				new Intent(Tab_CheckAgain.this,
						Activity_CompanyDetail.class)
						.putExtra("ishistory", ishistory)
						.putExtra("companyEntity", companyEntity)
						.putExtra("editState", 0).putExtra("tomoreState", 0)));
	}

	public void showDangerousList(View view) {
		block = 2;
		tabMianContent.removeAllViews();
		tabMianContent.addView(getIntentContentView(
				tabMianContent, "tabMianContent1", new Intent(
						Tab_CheckAgain.this, Activity_CheckAgainOptionList.class)
						.putExtra("ishistory", ishistory)
						.putExtra("checkAgainEntity", checkAgainEntity)));

	}

	public void showBook(View view) {
		block = 3;
		tabMianContent.removeAllViews();
		tabMianContent.addView(getIntentContentView(
				
				tabMianContent,
				"tabMianContent1",
				new Intent(Tab_CheckAgain.this, Activity_BookEdit.class)
						.putExtra("ishistory", ishistory)
						.putExtra("bookList", bookList)
						.putExtra("companyname",companyEntity.getValue(0))));
	}
	@Override
	public void toMore(View view) {
		// TODO Auto-generated method stub
		super.toMore(view);
		Tab_CheckAgain.this.startActivity(new Intent(Tab_CheckAgain.this, Activity_AgainAdvice.class).putExtra("checkAgainEntity", checkAgainEntity));
	}


	public void finishAgainCheck(View view){
		if(!ishistory){
			Tab_CheckAgain.this.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					
					Tab_CheckAgain.this.showWait();
					
				}
			});
			final Base_Entity finsihAgain=new Entity_Check().init().setId(againid).put(5, qiyeid).put(0, planid);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					boolean isneedagain=Util_Finish.findCheckIsNeedAgain(finsihAgain);
					if(isneedagain){
						Tab_CheckAgain.this.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								Tab_CheckAgain.this.dissmissWait();
								
							}
						});
						Tab_CheckAgain.this.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								Dialog_Tool.showDialog_FinishAgainCheck(Tab_CheckAgain.this, finsihAgain);
								
							}
						});
					}else{
						Help_DB.create().update(finsihAgain.put(10, "1"));
						Tab_CheckAgain.this.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								
								Tab_CheckAgain.this.dissmissWait();
								Tab_CheckAgain.this.finish();
								
							}
						});
					}
					
				}
			}).start();
			
		}
		else{
			finish();
		}

	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
}
