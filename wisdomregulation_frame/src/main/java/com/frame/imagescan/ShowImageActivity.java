package com.frame.imagescan;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.wisdomregulation.frame.R;

import java.util.List;


public class ShowImageActivity extends Activity {
	private GridView mGridView;
	private List<String> list;
	private ChildAdapter adapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_show_image_activity);
		
		mGridView = (GridView) findViewById(R.id.child_grid);
		list = getIntent().getStringArrayListExtra("data");
		
		adapter = new ChildAdapter(this, list, mGridView);
		mGridView.setAdapter(adapter);
		
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}




	
	

	
}
