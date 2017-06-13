package com.wisdomregulation.pop;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_PopList;
import com.wisdomregulation.frame.AutoCheckBox;
import com.wisdomregulation.frame.CallBack;
import com.wisdomregulation.staticlib.Static_ConstantLib;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

/**
 * popwindow工具类
 * @author King2016s
 *
 */
public class Pop_Tool {
	public static PopupWindow pop_orgin_more (Activity activity,View view){
		View contentView = LayoutInflater.from(activity).inflate(
	            R.layout.pop_orgin_more, null);
		Util_MatchTip.initAllScreenText(contentView,Static_ConstantLib.TEXT_NORMAL);
	    PopupWindow popupWindow = new PopupWindow(contentView,
	    		(int)(Static_InfoApp.create().getAppScreenWidth()/2.8), (int)(Static_InfoApp.create().getAppScreenWidth()/2.8), true);

	    popupWindow.setTouchable(true);

	    popupWindow.setTouchInterceptor(new OnTouchListener() {

	        @Override
	        public boolean onTouch(View v, MotionEvent event) {


	            return false;
	            // 这里如果返回true的话，touch事件将被拦截
	            // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
	        }
	    });

	    // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
	    // 我觉得这里是API的一个bug
	    popupWindow.setBackgroundDrawable(activity.getResources().getDrawable(R.color.white2));

	    // 设置好参数之后再show
	    popupWindow.showAsDropDown(view);
	    return popupWindow;
	}
	public static PopupWindow pop_orgin_more (Activity activity,View view,int wid){
		View contentView = LayoutInflater.from(activity).inflate(
	            R.layout.pop_orgin_more, null);
		Util_MatchTip.initAllScreenText(contentView,Static_ConstantLib.TEXT_NORMAL);
	    PopupWindow popupWindow = new PopupWindow(contentView,
	    		wid, (int)(Static_InfoApp.create().getAppScreenWidth()/2.8), true);

	    popupWindow.setTouchable(true);

	    popupWindow.setTouchInterceptor(new OnTouchListener() {

	        @Override
	        public boolean onTouch(View v, MotionEvent event) {


	            return false;
	            // 这里如果返回true的话，touch事件将被拦截
	            // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
	        }
	    });

	    // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
	    // 我觉得这里是API的一个bug
	    popupWindow.setBackgroundDrawable(activity.getResources().getDrawable(R.color.white2));

	    // 设置好参数之后再show
	    popupWindow.showAsDropDown(view);
	    return popupWindow;
	}
	public static PopupWindow pop_chose_list (String needempty,Activity activity,final View view,int wid,int hig,final List<String> listdata,final AutoCheckBox checkbox,final CallBack callback){
		View contentView = LayoutInflater.from(activity).inflate(
	            R.layout.pop_chose_list, null);
		Util_MatchTip.initAllScreenText(contentView,Static_ConstantLib.TEXT_NORMAL);
	    PopupWindow popupWindow = new PopupWindow(contentView,
	    		wid, hig, true);

	    popupWindow.setTouchable(true);
	    popupWindow.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				if (((TextView) view).getText().toString().trim().equals("")) {
					if(listdata!=null&&listdata.size()>0){
						((TextView) view).setText(listdata.get(0));
					}
					
					if (callback != null) {

						callback.back(0);
					}

				}
				if (checkbox != null) {
					checkbox.setChecked(false);
				}

			}
		});
	    popupWindow.setTouchInterceptor(new OnTouchListener() {

	        @Override
	        public boolean onTouch(View v, MotionEvent event) {

	            return false;
	            // 这里如果返回true的话，touch事件将被拦截
	            // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
	        }
	    });
	    TextView empty=(TextView) contentView.findViewById(R.id.emptyview);
	    empty.setText(needempty);
	    ListView poplist=(ListView) contentView.findViewById(R.id.poplist);
	    poplist.setEmptyView(empty);
	    poplist.setAdapter(new Adapter_PopList(activity, listdata,popupWindow,view,callback));
	    // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
	    // 我觉得这里是API的一个bug
	    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFBDBDBD")));

	    // 设置好参数之后再show
	    popupWindow.showAsDropDown(view);
	    return popupWindow;
	}
	public static PopupWindow pop_normal (Activity activity,View view,int Rid){
		View contentView = LayoutInflater.from(activity).inflate(
				Rid, null);
		Util_MatchTip.initAllScreenText(contentView,Static_ConstantLib.TEXT_NORMAL);
	    PopupWindow popupWindow = new PopupWindow(contentView,
	    		(int)(Static_InfoApp.create().getAppScreenWidth()/2.8), (int)(Static_InfoApp.create().getAppScreenWidth()/2.8), true);

	    popupWindow.setTouchable(true);

	    popupWindow.setTouchInterceptor(new OnTouchListener() {

	        @Override
	        public boolean onTouch(View v, MotionEvent event) {


	            return false;
	            // 这里如果返回true的话，touch事件将被拦截
	            // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
	        }
	    });

	    // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
	    // 我觉得这里是API的一个bug
	    popupWindow.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.textview_border8));

	    // 设置好参数之后再show
	    popupWindow.showAsDropDown(view);
	    return popupWindow;
	}
	public static PopupWindow pop_evidencemore (Activity activity,View view,int Rid){
		View contentView = LayoutInflater.from(activity).inflate(
				Rid, null);
		Util_MatchTip.initAllScreenText(contentView,Static_ConstantLib.TEXT_NORMAL);
	    PopupWindow popupWindow = new PopupWindow(contentView,
	    		(int)(Static_InfoApp.create().getAppScreenWidth()/2.8), (int)(Static_InfoApp.create().getAppScreenWidth()/2.8), true);

	    popupWindow.setTouchable(true);

	    popupWindow.setTouchInterceptor(new OnTouchListener() {

	        @Override
	        public boolean onTouch(View v, MotionEvent event) {


	            return false;
	            // 这里如果返回true的话，touch事件将被拦截
	            // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
	        }
	    });

	    // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
	    // 我觉得这里是API的一个bug
	    popupWindow.setBackgroundDrawable(activity.getResources().getDrawable(R.color.white2));

	    // 设置好参数之后再show
//	    popupWindow.showAsDropDown(view, -500, 200);
	    popupWindow.showAsDropDown(view);
	    return popupWindow;
	}
	
}
