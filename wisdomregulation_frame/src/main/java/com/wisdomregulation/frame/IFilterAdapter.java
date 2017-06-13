package com.wisdomregulation.frame;

import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

/**
 * Created by King2016 on 2017/1/18.
 */

public interface IFilterAdapter {
    int getCount();
    void setWeightDataChangeListener(OnDataChangeListener2 Listener2);
    void notifyDataSetChanged();
    View getView(int position, ViewGroup parent);
    Object getItem(int position);
    long getItemId(int position);
    IFilterAdapter initView(ViewGroup group);
    Map<String, String>  getResultmap();

}
