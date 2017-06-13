package com.wisdomregulation.frame;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;

public class AutoCheckBox extends AutoClickImageView implements Checkable,IAutoCheck{
	private OnCheckedChangeListener mOnCheckedChangeListener;
	private OnCheckedChangeListener2 mOnCheckedChangeListener2;
    private boolean mBroadcasting;
	public AutoCheckBox(Context context) {
		this(context, null);

		// TODO Auto-generated constructor stub
	}
	public AutoCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setScaleType(ScaleType.FIT_CENTER);
		// TODO Auto-generated constructor stub
	}
	private boolean mChecked;
	private static final int[] CHECKED_STATE_SET = { android.R.attr.state_checked };
	@Override
	public int[] onCreateDrawableState(int extraSpace) {
		final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
		if (isChecked()) {
			mergeDrawableStates(drawableState, CHECKED_STATE_SET);
		}
		return drawableState;
	}
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		AutoCheckBox.this.setClickable(true);
	}
	@Override
	public void setChecked(boolean checked) {
		if (mChecked != checked) {
			mChecked = checked;
			refreshDrawableState();
		}
		
		if (mBroadcasting) {
            return;
        }
		
		mBroadcasting = true;
		if(mOnCheckedChangeListener!=null){
			
			mOnCheckedChangeListener.onCheckedChanged(AutoCheckBox.this,mChecked);
		}
		if(mOnCheckedChangeListener2!=null){
			mOnCheckedChangeListener2.onCheckedChanged(AutoCheckBox.this,mChecked);
		}
		mBroadcasting = false;
	}
	@Override
	public boolean isChecked() {
		return mChecked;
	}
	@Override
	public void toggle() {
		setChecked(!mChecked);
		
	}
    @Override
    public boolean performClick() {
    	if(this.isEnabled()){
    		toggle();
    	}
        return true;
    }
    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener){
    	
    	mOnCheckedChangeListener=onCheckedChangeListener;
    }
    @Override
    public void setOnCheckedChangeListener2(OnCheckedChangeListener2 onCheckedChangeListener2){
    	
    	mOnCheckedChangeListener2=onCheckedChangeListener2;
    }

}