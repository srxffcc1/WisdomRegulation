package com.frame.fileselector.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.wisdomregulation.frame.R;

import org.json.JSONArray;
import org.json.JSONException;


public class MainActivity extends Activity implements OnClickListener {
	private MainActivity instance = this;
	private final int REQUEST = 100;
	private TextView tvContent;
	private Button btnSelect;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initLayout();
	}

	private void initLayout() {
		setContentView(R.layout.file_activity_main);
		
		tvContent = (TextView) findViewById(R.id.tv_main_content);
		btnSelect = (Button) findViewById(R.id.btn_main_select);
		
		initEvent();
	}

	private void initEvent() {
		btnSelect.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.btn_main_select) {
			FileHomeActivity.actionStart(instance, Environment.getDataDirectory().getAbsolutePath(), REQUEST, "files");

		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case Activity.RESULT_OK:
			switch (requestCode) {
			case REQUEST:
				try {
					JSONArray array = new JSONArray(data.getStringExtra("files"));
					StringBuilder builder = new StringBuilder();
					int length = array.length();
					for (int i = 0; i < length; i++) {
						builder.append(array.get(i)).append("\r\n");
					}
					tvContent.setText(builder.toString());
				} catch (JSONException e) {
					
				}
				break;
			}
			break;
		case Activity.RESULT_CANCELED:
			break;
		}
	}
}
