package com.wisdomregulation.allactivity.mode;

import android.widget.LinearLayout;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_DangerousMenuList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;

import java.util.ArrayList;
import java.util.List;

public class Mode_DangerousCheckMain extends Base_AyActivity{
private LinearLayout content;
private List<String> securtityMenuListData;
 	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_dangerous_menu);
		
	}
 	@Override
 		public void initData() {
 			// TODO Auto-generated method stub

 			securtityMenuListData=new ArrayList<String>();
 			securtityMenuListData.add("重大隐患排查");
 			securtityMenuListData.add("隐患排查标准");
 			securtityMenuListData.add("政府抽查隐患");
 		}
 	@Override
 		public void initWidget() {
 			// TODO Auto-generated method stub
		content=(LinearLayout)findViewById(R.id.securtityMenuList);
 			Adapter_DangerousMenuList adapter=new Adapter_DangerousMenuList(Mode_DangerousCheckMain.this, securtityMenuListData, content);
 			adapter.initView();
 		}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

}
