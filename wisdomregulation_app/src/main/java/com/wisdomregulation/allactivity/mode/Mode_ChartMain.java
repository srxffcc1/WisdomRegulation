package com.wisdomregulation.allactivity.mode;

import android.widget.LinearLayout;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_ChartMenuList;
import com.wisdomregulation.allactivity.base.Base_AyActivity;

import java.util.ArrayList;
import java.util.List;

public class Mode_ChartMain extends Base_AyActivity{
private LinearLayout content;
private List<String> securtityMenuListData;
 	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_chart_main_menu);
		
	}
 	@Override
 		public void initData() {
 			// TODO Auto-generated method stub

 			securtityMenuListData=new ArrayList<String>();
 			securtityMenuListData.add("企业数量");
// 			securtityMenuListData.add("执法人数");
// 			securtityMenuListData.add("企业自查");
// 			securtityMenuListData.add("隐患数量");
// 			securtityMenuListData.add("整改率");
// 			securtityMenuListData.add("文书统计");
 		}
 	@Override
 		public void initWidget() {
 			// TODO Auto-generated method stub
		content=(LinearLayout)findViewById(R.id.securtityMenuList);
 			Adapter_ChartMenuList adapter=new Adapter_ChartMenuList(Mode_ChartMain.this, securtityMenuListData, content);
 			adapter.initView();
 		}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

}
