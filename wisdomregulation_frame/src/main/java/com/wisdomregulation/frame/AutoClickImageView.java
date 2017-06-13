package com.wisdomregulation.frame;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * @ClassName: FilterImageView
 * @Description:  点击时显示明暗变化(滤镜效果)的ImageView
 * @author LinJ
 * @date 2015-1-6 下午2:13:46
 *
 */
public class AutoClickImageView extends ImageView implements GestureDetector.OnGestureListener{
    public String clicktype="scale";
    /**   监听手势*/
    private GestureDetector mGestureDetector;
    public AutoClickImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector=new GestureDetector(context, this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //在cancel里将滤镜取消，注意不要捕获cacncel事件,mGestureDetector里有对cancel的捕获操作
        //在滑动GridView时，AbsListView会拦截掉Move和UP事件，直接给子控件返回Cancel
        if(event.getActionMasked()== MotionEvent.ACTION_CANCEL||event.getActionMasked()== MotionEvent.ACTION_UP){
            if(clicktype.equals("scale")){
                scaleToNormal();
            }else{
                removeFilter();
            }
        }
        return mGestureDetector.onTouchEvent(event);
    }

    /**
     *   设置滤镜
     */
    private void setFilter() {
        //先获取设置的src图片
        Drawable drawable=getDrawable();
        //当src图片为Null，获取背景图片
        if (drawable==null) {
            drawable=getBackground();
        }
        if(drawable!=null){
            //设置滤镜
            drawable.setColorFilter(Color.GRAY,PorterDuff.Mode.MULTIPLY);;
        }
    }
    private void scaleToSmaller()
    {
        Animation loadAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_xysize_smaller_anim);
        this.clearAnimation();
        this.startAnimation(loadAnimation);
    }
    private void scaleToNormal()
    {
        Animation loadAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_xysize_normal_anim);
        this.clearAnimation();
        this.startAnimation(loadAnimation);
    }
    /**
     *   清除滤镜
     */
    private void removeFilter() {
        //先获取设置的src图片
        Drawable drawable=getDrawable();
        //当src图片为Null，获取背景图片
        if (drawable==null) {
            drawable=getBackground();
        }
        if(drawable!=null){
            //清除滤镜
            drawable.clearColorFilter();
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if(clicktype.equals("scale")){
            scaleToSmaller();
        }else{
            setFilter();
        }
        //这里必须返回true，表示捕获本次touch事件
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if(clicktype.equals("scale")){
            scaleToNormal();
        }else{
            removeFilter();
        }
        performClick();

        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //长安时，手动触发长安事件
        performLongClick();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        // TODO Auto-generated method stub
        return false;
    }
}