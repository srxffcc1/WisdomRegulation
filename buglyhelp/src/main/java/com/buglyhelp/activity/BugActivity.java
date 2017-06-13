package com.buglyhelp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.buglyhelp.R;

/**
 * Created by King6rf on 2017/3/22.
 */

public class BugActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug);
        TextView mycrash= (TextView) findViewById(R.id.btnTestJavaCrash);
        mycrash.setText(getIntent().getStringExtra("crash"));
    }
}
