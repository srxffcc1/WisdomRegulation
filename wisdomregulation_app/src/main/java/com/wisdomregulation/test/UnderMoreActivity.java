package com.wisdomregulation.test;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.wisdomregulation.R;
import com.wisdomregulation.staticlib.Static_InfoApp;

public class UnderMoreActivity extends Activity{
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.test_list);
	final List<String> testl=new ArrayList<String>();
	for (int i = 0; i < 10; i++) {
		testl.add(new String(i+""));
	}
	ListView ll=(ListView) this.findViewById(R.id.testlist);
	ll.setAdapter(new BaseAdapter() {
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView=LayoutInflater.from(UnderMoreActivity.this).inflate(R.layout.test_listitem, null);
			View view=LayoutInflater.from(UnderMoreActivity.this).inflate(R.layout.test_pop, null);
			final PopupWindow popupWindow = new PopupWindow(view,
		    		(int)Static_InfoApp.create().getAppScreenWidth()/2, 300);
			convertView.findViewById(R.id.showpop).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {

				    popupWindow.setTouchable(true);
				    popupWindow.setOutsideTouchable(true);
//				    popupWindow.setTouchInterceptor(new OnTouchListener() {
//
//				        @Override
//				        public boolean onTouch(View v, MotionEvent event) {
//
//
//				            return false;
//				            // 这里如果返回true的话，touch事件将被拦截
//				            // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
//				        }
//				    });

				    // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
				    // 我觉得这里是API的一个bug
				    popupWindow.setBackgroundDrawable(UnderMoreActivity.this.getResources().getDrawable(R.color.tomato));
				    if(popupWindow.isShowing()){
				    	 popupWindow.dismiss();
				    }else{
				    	 popupWindow.showAsDropDown(v);
				    }
				    // 设置好参数之后再show
				   
					
				}
			});
			return convertView;
		}
		
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return testl.size();
		}
	});
}
}
