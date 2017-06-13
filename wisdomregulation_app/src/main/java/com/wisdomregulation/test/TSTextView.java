package com.wisdomregulation.test;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class TSTextView extends TextView{

	public TSTextView(Context context) {
		super(context);
		setText("试试自已");
		
		Typeface tte=Typeface.createFromAsset(context.getAssets(), "simhei.ttf");
		setTypeface(tte);
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

}
