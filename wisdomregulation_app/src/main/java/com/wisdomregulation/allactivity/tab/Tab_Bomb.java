package com.wisdomregulation.allactivity.tab;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.allactivity.single.Activity_BombInfo;
import com.wisdomregulation.allactivity.single.Activity_Point;

public class Tab_Bomb extends Base_AyActivity {
private FrameLayout tabMianContent;
RadioButton radioc1;
RadioButton radioc2;
private int block=2;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.tab_bomb);

	}
	@Override
		public void initData() {
			// TODO Auto-generated method stub

			
		}

	@Override
		public void initWidget() {
			// TODO Auto-generated method stub

		tabMianContent=(FrameLayout)findViewById(R.id.tabMianContent);
		radioc1=(RadioButton)findViewById(R.id.bombradioc1);
		radioc2=(RadioButton)findViewById(R.id.bombradioc2);
			switch (block) {
			case 1:
				radioc1.performClick();
				break;
			case 2:
				radioc2.performClick();
				break;
				
			default:
				radioc2.performClick();
				break;
			}
		}
	public void showInfo(View view){
		tabMianContent.removeAllViews();
		tabMianContent.addView(getIntentContentView(tabMianContent, "showCheckCompany", new Intent(Tab_Bomb.this, Activity_BombInfo.class)));
	}
	public void showPoint(View view){
		tabMianContent.removeAllViews();
		tabMianContent.addView(getIntentContentView(tabMianContent, "showNotCheckCompany", new Intent(Tab_Bomb.this, Activity_Point.class)));
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

}
