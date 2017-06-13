package com.wisdomregulation.frame;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by King2016s on 2017/1/19.
 */

public class FrameUtil {
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
}
