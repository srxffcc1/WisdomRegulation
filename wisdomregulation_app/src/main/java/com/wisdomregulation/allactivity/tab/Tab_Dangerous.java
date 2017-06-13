package com.wisdomregulation.allactivity.tab;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.allactivity.single.Activity_DangerousHaveCheckCompany;
import com.wisdomregulation.allactivity.single.Activity_DangerousNoHaveCheckCompany;

public class Tab_Dangerous extends Base_AyActivity {
private FrameLayout tabMianContent;
RadioButton radioc1;
RadioButton radioc2;

private int block=1;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.tab_serioussecuritycheck);

	}

	@Override
	public void initData() {

	}

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		tabMianContent=(FrameLayout)findViewById(R.id.tabMianContent);
		radioc1=(RadioButton)findViewById(R.id.radioc1);
		radioc2=(RadioButton)findViewById(R.id.radioc2);
		switch (block) {
		case 0:
			radioc1.performClick();
			break;
		case 1:
			radioc2.performClick();
			break;
			
		default:
			radioc1.performClick();
			break;
		}
	}
	public void showCheckCompany(View view){
		tabMianContent.removeAllViews();
		tabMianContent.addView(getIntentContentView(tabMianContent, "showCheckCompany", new Intent(Tab_Dangerous.this, Activity_DangerousHaveCheckCompany.class)));
	}
	public void showNotCheckCompany(View view){
		tabMianContent.removeAllViews();
		tabMianContent.addView(getIntentContentView( tabMianContent, "showNotCheckCompany", new Intent(Tab_Dangerous.this, Activity_DangerousNoHaveCheckCompany.class)));
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

}
