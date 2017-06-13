package com.wisdomregulation.frame;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by King2016 on 2017/1/18.
 */

public abstract class FilterAdapter implements  IFilterAdapter{
    public OnDataChangeListener2 Listener2;
    public Activity context;
    public List<FilterEntity> entitylist;
    public FilterAdapter(Activity context, List<FilterEntity> entitylist) {
        this.context = context;
        this.entitylist = entitylist;
    }

    @Override
    public int getCount() {
        return entitylist.size();
    }

    @Override
    public void setWeightDataChangeListener(OnDataChangeListener2 Listener2) {
        this.Listener2=Listener2;
    }

    @Override
    public void notifyDataSetChanged() {
        Listener2.onDataChanged();
    }



    @Override
    public Object getItem(int position) {
        return entitylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public IFilterAdapter initView(ViewGroup viewGroup) {
        viewGroup.removeAllViews();
        for (int i = 0; i < getCount(); i++) {
            View add=getView(i, viewGroup);
            if(add!=null){
                if(add.getVisibility()==View.VISIBLE){
                    viewGroup.addView(add);
                }

            }
        }
        return this;
    }


}
