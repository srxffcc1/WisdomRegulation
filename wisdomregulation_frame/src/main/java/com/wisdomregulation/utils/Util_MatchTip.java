package com.wisdomregulation.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.frame.PassLinearLayout;
import com.wisdomregulation.frame.R;
import com.wisdomregulation.map.ExpandMap;
import com.wisdomregulation.staticlib.Static_ConstantLib;
import com.wisdomregulation.staticlib.Static_InfoApp;

import java.util.ArrayList;
import java.util.List;

public class Util_MatchTip {
	/**
	 * 将缩放倍数转为计算出的px值
	 * @param scaleSize
	 * @return
	 */
	public static int getScreenTextSize(double scaleSize) {
		double high = Static_InfoApp.create().getAppScreenHigh();
		int px = (int) (high / scaleSize);
		return px;
	}
	/**
	 * 将缩放倍数转为计算出的px值
	 * @param scaleSize
	 * @return
	 */
	public static int getViewTextSize(double scaleSize) {
		double high = Static_InfoApp.create().getViewScreenHigh();
		int px = (int) (high / scaleSize);
		return px;
	}
	/**
	 * 按屏幕的尺寸适配
	 * @param view
	 */
	public static void initAllScreenText(View view) {
		initAllScreenText(view, Static_ConstantLib.TEXT_NORMAL);
	}
	/**
	 * 按屏幕尺寸适配并高亮关键字
	 * @param view
	 * @param keywords
	 */
	public static void initAllScreenText(View view,String keywords) {
		initAllScreenText(view, Static_ConstantLib.TEXT_NORMAL,keywords);
	}
	/**
	 * 对视图尺寸适配
	 * @param view
	 */
	public static void initAllViewText(View view) {
		initAllViewText(view, Static_ConstantLib.TEXT_NORMAL);
	}
	
	/**
	 * 视图中提取特定文字
	 * @param view
	 * @return
	 */
	public static String view2String(View view) {
		String result = "";
		List<View> allchild = getAllChildViews(view);

		for (int i = 0; i < allchild.size(); i++) {
			if(allchild.get(i).getTag()!=null&&allchild.get(i).getTag().toString().equals("goal")){
				if (allchild.get(i) instanceof EditText) {
					EditText edit = ((EditText) allchild.get(i));
					if (edit.getVisibility() == View.VISIBLE) {
						result = edit.getText().toString();
						return result;
					}
				}
				else if (allchild.get(i) instanceof RadioGroup) {
					RadioGroup group = (RadioGroup) allchild.get(i);
					if (group.getVisibility() == View.VISIBLE) {
						for (int j = 0; j < group.getChildCount(); j++) {
							if(group.getChildAt(j) instanceof RadioButton){
								RadioButton button = (RadioButton) group.getChildAt(j);
								if (button.isChecked()) {
									result = button.getText().toString();
								}
							}
							

						}
						if(result.equals("无隐患")){
							result="3";
						}else if(result.equals("一般隐患")){
							result="0";
						}else if(result.equals("重大隐患")){
							result="1";
						}else if(result.equals("整改完成")){
							result="1";
						}else if(result.equals("仍然存在问题")){
							result="0";
						}
						return result;
					}
				}
				else if (allchild.get(i) instanceof PassLinearLayout) {
					PassLinearLayout group = (PassLinearLayout) allchild.get(i);
					if (group.getVisibility() == View.VISIBLE) {
						if (group.isChecked()) {
							result = group.getText().toString();
							return result;
						}
						
					}
				}
			}

			
		}
		
		return result;
	}
	

	/**
	 * 对字符按屏幕比例适配
	 * @param view
	 * @param textsizeScale
	 */
	public static void initAllViewText(View view, double textsizeScale) {
		List<View> allchild = getAllChildViews(view);
		for (int i = 0; i < allchild.size(); i++) {
			if (allchild.get(i) instanceof TextView) {

//				com.wisdomregulation.utils.Log.v("TextSize", px2sp(view.getContext(),((TextView) (allchild.get(i))).getTextSize())+"");
				if(allchild.get(i).getId()== R.id.newtitle){
					
					((TextView) (allchild.get(i))).setTextSize(TypedValue.COMPLEX_UNIT_PX,
							Util_MatchTip.getViewTextSize(Static_ConstantLib.TEXT_NORMAL2));
				}
				if(allchild.get(i).getId()==R.id.newtitle2){
					
					((TextView) (allchild.get(i))).setTextSize(TypedValue.COMPLEX_UNIT_PX,
							Util_MatchTip.getViewTextSize(Static_ConstantLib.TEXT_NORMAL3));
				}
//				if(allchild.get(i).getId()==R.id.titletext){
//					
//					((TextView) (allchild.get(i))).setTextSize(TypedValue.COMPLEX_UNIT_PX,
//							Util_MatchTip.getViewTextSize(textsizeScale));
//				}
				else{
					int orgtextsize=px2sp(view.getContext(),((TextView) (allchild.get(i))).getTextSize());
					if(orgtextsize!=14){
						((TextView) (allchild.get(i))).setTextSize(TypedValue.COMPLEX_UNIT_PX,
								Util_MatchTip.getViewTextSize(textsizeScale));//以后再考虑吧
					}else{
						((TextView) (allchild.get(i))).setTextSize(TypedValue.COMPLEX_UNIT_PX,
								Util_MatchTip.getViewTextSize(textsizeScale));
					}
					
				}
				

			}
			if (allchild.get(i) instanceof ListView) {

				((ListView) (allchild.get(i))).setCacheColorHint(0);
				((ListView) (allchild.get(i))).setVerticalScrollBarEnabled(false);
				((ListView) (allchild.get(i))).setSelector(R.color.alpha);
				((ListView) (allchild.get(i))).setBackgroundColor(Color.parseColor("#E5E5E5"));
				((ListView) (allchild.get(i))).setDivider(new ColorDrawable(Color.BLACK));
				((ListView) (allchild.get(i))).setDividerHeight(0);
			}
			if (allchild.get(i) instanceof ImageView) {

			}
			if (allchild.get(i) instanceof ExpandableListView) {
				((ExpandableListView) (allchild.get(i))).setGroupIndicator(null);
			}
			if (allchild.get(i) instanceof ViewGroup) {

			}
			if(allchild.get(i) instanceof ScrollView){
				((ScrollView) (allchild.get(i))).setVerticalScrollBarEnabled(false);
			}
		}
	}
	/**
	 * 对字符按视图比例适配
	 * @param view
	 * @param textsizeScale
	 */
	public static void initAllScreenText(View view, double textsizeScale) {
		List<View> allchild = getAllChildViews(view);
		for (int i = 0; i < allchild.size(); i++) {
			if (allchild.get(i) instanceof TextView) {
				if(allchild.get(i).getId()==R.id.newtitle){
					((TextView) (allchild.get(i))).setTextSize(TypedValue.COMPLEX_UNIT_PX,
							Util_MatchTip.getViewTextSize(Static_ConstantLib.TEXT_NORMAL2));
				}else{
					((TextView) (allchild.get(i))).setTextSize(TypedValue.COMPLEX_UNIT_PX,
							Util_MatchTip.getViewTextSize(textsizeScale));
				}

			}
			if (allchild.get(i) instanceof ListView) {

				((ListView) (allchild.get(i))).setCacheColorHint(0);
				((ListView) (allchild.get(i))).setVerticalScrollBarEnabled(false);
				((ListView) (allchild.get(i))).setSelector(R.color.alpha);
				((ListView) (allchild.get(i))).setBackgroundColor(Color.parseColor("#E5E5E5"));
				((ListView) (allchild.get(i))).setDivider(new ColorDrawable(Color.BLACK));
				((ListView) (allchild.get(i))).setDividerHeight(0);
			}
			if (allchild.get(i) instanceof ImageView) {

			}
			if (allchild.get(i) instanceof ExpandableListView) {
				((ExpandableListView) (allchild.get(i))).setGroupIndicator(null);
			}
			if (allchild.get(i) instanceof ViewGroup) {

			}
			if(allchild.get(i) instanceof ScrollView){
				((ScrollView) (allchild.get(i))).setVerticalScrollBarEnabled(false);
			}
		}
	}
	/**
	 * 对字符按屏幕比例适配并高亮关键字
	 * @param view
	 * @param textsizeScale
	 * @param keyword
	 */
	public static void initAllScreenText(View view, double textsizeScale,String keyword) {
		List<View> allchild = getAllChildViews(view);
		for (int i = 0; i < allchild.size(); i++) {
			if (allchild.get(i) instanceof TextView) {
				if(allchild.get(i).getId()==R.id.newtitle){
					((TextView) (allchild.get(i))).setTextSize(TypedValue.COMPLEX_UNIT_PX,
							Util_MatchTip.getViewTextSize(Static_ConstantLib.TEXT_NORMAL2));
					String orgtext=((TextView) (allchild.get(i))).getText().toString();
					((TextView) (allchild.get(i))).setText(Util_String.highlight(orgtext, keyword));
				}else{
					((TextView) (allchild.get(i))).setTextSize(TypedValue.COMPLEX_UNIT_PX,
							Util_MatchTip.getViewTextSize(textsizeScale));
					String orgtext=((TextView) (allchild.get(i))).getText().toString();
					((TextView) (allchild.get(i))).setText(Util_String.highlight(orgtext, keyword));
				}
				
			}
			if (allchild.get(i) instanceof ListView) {

				((ListView) (allchild.get(i))).setCacheColorHint(0);
				((ListView) (allchild.get(i))).setVerticalScrollBarEnabled(false);
				((ListView) (allchild.get(i))).setSelector(R.color.alpha);
				((ListView) (allchild.get(i))).setBackgroundColor(Color.parseColor("#E5E5E5"));
				((ListView) (allchild.get(i))).setDivider(new ColorDrawable(Color.BLACK));
				((ListView) (allchild.get(i))).setDividerHeight(0);
			}
			if (allchild.get(i) instanceof ImageView) {

			}
			if (allchild.get(i) instanceof ExpandableListView) {
				((ExpandableListView) (allchild.get(i))).setGroupIndicator(null);
			}
			if (allchild.get(i) instanceof ViewGroup) {

			}
			if(allchild.get(i) instanceof ScrollView){
				((ScrollView) (allchild.get(i))).setVerticalScrollBarEnabled(false);
			}
		}
	}

	/**
	 * 获得所有子view
	 * @param view
	 * @return
	 */
	public static List<View> getAllChildViews(View view) {
		List<View> allchildren = new ArrayList<View>();
		
		if (view instanceof ViewGroup) {
			ViewGroup vp = (ViewGroup) view;
			allchildren.add(view);
			for (int i = 0; i < vp.getChildCount(); i++) {
				View viewchild = vp.getChildAt(i);
				allchildren.addAll(getAllChildViews(viewchild));
				
			}
		} else {
			allchildren.add(view);
		}
		return allchildren;

	}
	


	/**
	 * 获得所有子view
	 * @param expandpap
	 * @return
	 */
	public static List<Object> getAllChildList(ExpandMap expandpap) {
		List<Object> allChildList = new ArrayList<Object>();
		if (expandpap.size() != 0) {
			for (int i = 0; i < expandpap.size(); i++) {
				allChildList.add(expandpap);
				allChildList.addAll(getAllChildList(expandpap.get(i)));
			}
		} else {
			allChildList.add(expandpap);
		}
		return allChildList;

	}

	/**
	 * db的帮助类直接获得查询结果集
	 * @param org
	 * @return
	 */
	public static List<Base_Entity> getSearchResult(List org) {
		List<Base_Entity> result = new ArrayList<Base_Entity>();
		if (org != null && org.size() > 1) {
			result = (List<Base_Entity>) org.get(1);
		} else {
		}
		return result;

	}
	

	/**
	 * 只获得一个结果
	 * @param org
	 * @return
	 */
	public static Base_Entity getSearchResultOnlyOne(List org) {
		Base_Entity result = null;
		List org1 = getSearchResult(org);
		if (org1 != null && org1.size() > 0) {
			result = getSearchResult(org).get(0);
		}else{
			result=new Base_Entity();
		}

		return result;

	}
	/**
	 * 判断null 不为null则返回true 为null返回false
	 * @param object
	 * @return
	 */
	public static boolean isnotnull(Object object){
		boolean result=false;
		if(object!=null){
			result=true;
		}
		if(object instanceof Base_Entity){
			if(((Base_Entity)object).size()==0){
				result=false;
			}
		}
		return result;
		
	}

	/**
	 * dp转px
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int dp2px(Context context, int dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	/**
	 * dip转px
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		Resources r = context.getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, r.getDisplayMetrics());
		return (int) px;
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
	 */
	public static int px2sp(Context context, float pxValue) {
		float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 sp 的单位 转成为 px
	 */
	public static int sp2px(Context context, float spValue) {
		float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}
}
