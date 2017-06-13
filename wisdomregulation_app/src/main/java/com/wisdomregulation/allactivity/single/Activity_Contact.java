package com.wisdomregulation.allactivity.single;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;

import java.util.List;

public class Activity_Contact extends Base_AyActivity {
	private List<Object> org;
	private TextView contactname;
	private TextView contactsex;
	private TextView contactdept;
	private TextView contactpost;
	private TextView contactphone1;
	private TextView contactphone2;
	private Base_Entity contactEntity;
	
	
	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_contact_detail);

	}

	@Override
	public void initData() {

		contactEntity = (Base_Entity) this.getIntent().getSerializableExtra("contactEntity");
		contactname.setText(contactEntity.getValue(0));
		contactsex.setText(contactEntity.getValue(1));
		contactdept.setText(contactEntity.getValue(2));
		contactpost.setText(contactEntity.getValue(3));
		contactphone1.setText(contactEntity.getValue(4));
		contactphone2.setText(contactEntity.getValue(5));

	}

	@Override
	public void initWidget() {
		contactname=(TextView)findViewById(R.id.contactname);
		contactsex=(TextView)findViewById(R.id.contactsex);
		contactdept=(TextView)findViewById(R.id.contactdept);
		contactpost=(TextView)findViewById(R.id.contactpost);
		contactphone1=(TextView)findViewById(R.id.contactphone1);
		contactphone2=(TextView)findViewById(R.id.contactphone2);
	}

	public void passtomessage(View view){
		Uri smsToUri = Uri.parse("smsto:"+contactEntity.getValue(5));
		Intent mIntent = new Intent(android.content.Intent.ACTION_SENDTO,
				smsToUri);
		mIntent.putExtra("sms_body", "");
		startActivity(mIntent);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
	

}
