package com.wisdomregulation.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.utils.Util_MatchTip;

public class Adapter_BookDetail2 {
    private Activity context;
    private Base_Entity detailMapData;
    private LinearLayout content;

    private boolean editState = false;

    public Adapter_BookDetail2(Activity context,
                               Base_Entity detailMapData, LinearLayout content) {
        super();
        this.context = context;
        this.detailMapData = detailMapData;
        this.content = content;
    }


    public Adapter_BookDetail2 initView() {

        content.removeAllViews();
        for (int i = 0; i < getCount(); i++) {
            View add = getView(i, content);
            if (add != null) {
                if (add.getVisibility() == View.VISIBLE) {
                    content.addView(add);
                }

            }
        }
        return this;
    }


    public int getCount() {
        // TODO Auto-generated method stub
        return detailMapData.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return detailMapData.getFieldChinese(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    public View getView(int position, ViewGroup parent) {
        View convertView = null;
        String fieldtext = getItem(position).toString();
        convertView = LayoutInflater.from(context).inflate(R.layout.item_activity_book_content5, null);
        ((TextView) convertView.findViewById(R.id.bookcontentName2)).setText(fieldtext);
        final EditText valueedit = ((EditText) convertView.findViewById(R.id.bookcontentValue2));

        valueedit.setText(detailMapData.getValue(position));


        if (editState) {
//			valueedit.setEnabled(true);
        } else {
            valueedit.setOnClickListener(null);
            valueedit.setOnTouchListener(null);
            valueedit.setClickable(false);
            valueedit.setKeyListener(null);
        }
        Util_MatchTip.initAllScreenText(convertView);
        return convertView;
    }


}
