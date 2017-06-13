package com.wisdomregulation.frame;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.wisdomregulation.utils.Util_MatchTip;

import java.util.ArrayList;
import java.util.List;

public class AutoCheckGroup extends LinearLayout {
	private List<View> mchild = new ArrayList<View>();
	private CheckedStateTracker mchildlisteener;
	private OnCheckedChangeListener onCheckedChangeListener;
	private boolean mProtectFromCheckedChange;
	private int childid;
	private int index=0;
	private String tag="but1";
	public AutoCheckGroup(Context context) {
		super(context);
		setOrientation(VERTICAL);
		init();
	}
	public AutoCheckGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public void setCheck(int index){
		this.index=index;
		if(mchild.size()>index){
			((AutoCheckBox)(mchild.get(index))).setChecked(true);
		}
	}

	public void setOnCheckedChangeListener(
			OnCheckedChangeListener onCheckedChangeListener) {
		this.onCheckedChangeListener = onCheckedChangeListener;
	}
	private void init() {
		mchildlisteener = new CheckedStateTracker();
	}
	public int getCheckIndex(){
		return index;
	}
	public int getCheckedId(){
		return childid;
	}
	public int getCheckTagIndex(){
		String tagindex=tag.replaceAll("but", "");
		int dex=0;
		try {
			dex=Integer.parseInt(tagindex);
		}catch (Exception e){
			e.printStackTrace();
			dex=0;
		}
		return dex;
	}
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		List<View> tmp=Util_MatchTip.getAllChildViews(AutoCheckGroup.this);
		mchild.clear();
		int childcount = tmp.size();
		for (int i = 0; i < childcount; i++) {
			if (tmp.get(i) instanceof IAutoCheck) {
				((AutoCheckBox) (tmp.get(i))).setOnCheckedChangeListener2(mchildlisteener);
				mchild.add(tmp.get(i));
			}
		}
		if(mchild.size()>index){
			((AutoCheckBox)(mchild.get(index))).setChecked(true);
		}
	}
	@Override
	public void addView(View child) {
		// TODO Auto-generated method stub
		super.addView(child);
	}
	// private void init() {
	// mChildOnCheckedChangeListener = new CheckedStateTracker();
	// mPassThroughListener = new PassThroughHierarchyChangeListener();
	// super.setOnHierarchyChangeListener(mPassThroughListener);
	// }
	class CheckedStateTracker implements OnCheckedChangeListener2 {
		@Override
		public void onCheckedChanged(View buttonView, boolean isChecked) {
			if (mProtectFromCheckedChange) {
				return;
			}
			mProtectFromCheckedChange = true;
			for (int i = 0; i < mchild.size(); i++) {
				((AutoCheckBox) (mchild.get(i))).setChecked(false);
			}
			((AutoCheckBox) (buttonView)).setChecked(true);
			if(onCheckedChangeListener!=null){
				onCheckedChangeListener.onCheckedChanged(buttonView, isChecked);
			}
			mProtectFromCheckedChange = false;
			childid = buttonView.getId();
			tag=buttonView.getTag()!=null?buttonView.getTag().toString():"but1";
		}
	}
}