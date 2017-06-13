package com.wisdomregulation.frame;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by King2016 on 2017/1/18.
 */

public class FilterLayout extends LinearLayout{
    CallBack cancelback;
    CallBack submitback;
    private LinearLayout choselayout;
    private LinearLayout bodylayout;
    OnDataChangeListener2 listener2;
    IFilterAdapter adapter;

    public FilterLayout(Context context) {
        this(context, null);
    }

    public FilterLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setCancelback(CallBack cancelback) {
        this.cancelback = cancelback;
    }

    public void setSubmitback(CallBack submitback) {
        this.submitback = submitback;
    }

    private void init(Context context) {

        bodylayout=new LinearLayout(context);
        bodylayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        bodylayout.setOrientation(LinearLayout.VERTICAL);
        choselayout = new LinearLayout(context);
        choselayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        choselayout.setOrientation(LinearLayout.HORIZONTAL);
        Button cancel=new Button(context);
        cancel.setText("取消");
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterLayout.this.setVisibility(View.GONE);
                if(cancelback!=null){
                    cancelback.back(null);
                }
            }
        });
        cancel.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT,1));
        Button submit=new Button(context);
        submit.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT,1));
        submit.setText("确认");
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterLayout.this.setVisibility(View.GONE);
                if(submitback!=null){
                    submitback.back(null);
                }
            }
        });
        choselayout.addView(cancel);
        choselayout.addView(submit);
        this.addView(bodylayout);
        this.addView(choselayout);
        listener2=new FilterLayoutWeightDataChangeListener();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FilterLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
    public void setAdapter(IFilterAdapter adapter){
        this.adapter=adapter;
        adapter.setWeightDataChangeListener(listener2);
    }
    class FilterLayoutWeightDataChangeListener implements OnDataChangeListener2 {

        @Override
        public void onDataChanged() {
            adapter.initView(bodylayout);
        }
    }
}
