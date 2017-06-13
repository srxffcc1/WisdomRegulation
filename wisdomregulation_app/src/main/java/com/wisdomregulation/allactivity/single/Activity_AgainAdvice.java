package com.wisdomregulation.allactivity.single;

import android.view.View;
import android.widget.EditText;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_Check;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.utils.Util_MatchTip;

public class Activity_AgainAdvice extends Base_AyActivity {
private EditText fuchaAdvice;
private Base_Entity checkAgainEntity;
private Base_Entity resultentity;
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_againcheck_advice);
		
		
	}
	
	@Override
	public void initData() {
		// TODO Auto-generated method stub

		checkAgainEntity=(Base_Entity) this.getIntent().getSerializableExtra("checkAgainEntity");
		String againid=checkAgainEntity.getId();
		resultentity=Util_MatchTip.getSearchResultOnlyOne(Help_DB.create().search(new Entity_Check().init().setId(againid)));
		fuchaAdvice.setText(resultentity.getValue(17));

	}
	@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			
		}
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		fuchaAdvice=(EditText)findViewById(R.id.fuchaAdvice);
		
	}
	public void overedit(View view){
		resultentity.put(17, fuchaAdvice.getText().toString());
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Help_DB.create().save2update(resultentity);
				
			}
		}).start();
		
		Activity_AgainAdvice.this.finish();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
}
