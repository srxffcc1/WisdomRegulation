package com.wisdomregulation.allactivity.mode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.allactivity.single.Activity_CheckAgain;
import com.wisdomregulation.allactivity.single.Activity_Plan;

public class Mode_CheckMain extends Base_AyActivity {
	private FrameLayout contentMain;
	RadioButton radio1;
	RadioButton radio2;
	public static final String nowcheckagain = "Mode_CheckMain.nowcheckagain";
	private int block = 1;
	private TmpBroadcastReceiver receiver;

	@Override
	public void setRootView() {
		this.setContentView(R.layout.tab_check2again);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

		block = this.getIntent().getIntExtra("checkoragain", 1);
		receiver = new TmpBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(nowcheckagain);
		this.registerReceiver(receiver, filter);
	}

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		contentMain=(FrameLayout)findViewById(R.id.tabMianContent);
		radio1=(RadioButton)findViewById(R.id.radio1);
		radio2=(RadioButton)findViewById(R.id.radio2);
		switch (block) {
		case 1:
			radio1.performClick();
			break;
		case 2:
			radio2.performClick();
			break;

		default:
			radio1.performClick();
			break;
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	public void showLaw(View view) {
		block = 1;
		contentMain.removeAllViews();
		contentMain.addView(getIntentContentView(contentMain, "showLaw",
				new Intent(Mode_CheckMain.this, Activity_Plan.class)));
	}

	public void showLawAgain(View view) {
		block = 2;
		contentMain.removeAllViews();
		contentMain.addView(getIntentContentView(contentMain, "showLawAgain",
				new Intent(Mode_CheckMain.this,
						Activity_CheckAgain.class)));
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

	@Override
	public void toMore(View view) {
//		this.startActivity(new Intent(this, Mode_History.class));
	}

	private class TmpBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(nowcheckagain)) {

				radio2.performClick();
			}

		}

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub

	}

}
