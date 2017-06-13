package com.wisdomregulation.allactivity.mode;

import android.content.Intent;
import android.view.View;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.allactivity.single.Activity_LawList;
import com.wisdomregulation.allactivity.single.Activity_Law_Zip;

public class Mode_Law_Main extends Base_AyActivity {

	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_law_menu);

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

		
	}

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		
	}

	@Override
	public void toMore(View view) {
		// TODO Auto-generated method stub
		super.toMore(view);
		switch (view.getId()) {
		case R.id.lawzip:
			Mode_Law_Main.this.startActivity(new Intent(Mode_Law_Main.this, Activity_Law_Zip.class));
			break;
		case R.id.lawfrom1:
			Mode_Law_Main.this.startActivity(new Intent(Mode_Law_Main.this, Activity_LawList.class).putExtra("caseFrom", 0));
			break;
		case R.id.lawfrom2:
			Mode_Law_Main.this.startActivity(new Intent(Mode_Law_Main.this, Activity_LawList.class).putExtra("caseFrom", 1));
			break;
		case R.id.lawfrom3:
			Mode_Law_Main.this.startActivity(new Intent(Mode_Law_Main.this, Activity_LawList.class).putExtra("caseFrom", 2));
			break;
		case R.id.lawfrom4:
			Mode_Law_Main.this.startActivity(new Intent(Mode_Law_Main.this, Activity_LawList.class).putExtra("caseFrom", 3));
			break;
		case R.id.lawfrom5:
			Mode_Law_Main.this.startActivity(new Intent(Mode_Law_Main.this, Activity_LawList.class).putExtra("caseFrom", 4));
			break;
		case R.id.lawfrom6:
			Mode_Law_Main.this.startActivity(new Intent(Mode_Law_Main.this, Activity_LawList.class).putExtra("caseFrom", 5));
			break;
		default:
			break;
		}
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
}
