package com.wisdomregulation.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.single.Activity_Contact;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

import java.util.List;

public class Adapter_GovernmentContacts extends BaseAdapter{
	private Activity context;
	private List<Base_Entity> contactslistdata;
	private String editstate="0";
	public Adapter_GovernmentContacts(Activity context, List<Base_Entity> contactslistdata) {
		super();
		this.context = context;
		this.contactslistdata = contactslistdata;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return contactslistdata.size();
	}
	
	public String getEditstate() {
		return editstate;
	}

	public Adapter_GovernmentContacts setEditstate(String editstate) {
		this.editstate = editstate;
		return this;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return contactslistdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_contacts_list, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/9.6)));
		TextView contactname=(TextView) convertView.findViewById(R.id.contactname);
		contactname.setText(contactslistdata.get(position).getValue(0));
		TextView contactpost=(TextView) convertView.findViewById(R.id.contactpost);
		contactpost.setText(contactslistdata.get(position).getValue(3));
		final String phone=contactslistdata.get(position).getValue(5);
		convertView.findViewById(R.id.passtocontact).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context, Activity_Contact.class).putExtra("contactEntity", contactslistdata.get(position)));
				
			}
		});
		convertView.findViewById(R.id.passtocontact2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context, Activity_Contact.class).putExtra("contactEntity", contactslistdata.get(position)));
				
			}
		});
		convertView.findViewById(R.id.passtophone).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ phone));
				context.startActivity(intent);
				
			}
		});
		convertView.findViewById(R.id.passtomessage).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Uri smsToUri = Uri.parse("smsto:"+phone);
				Intent mIntent = new Intent(android.content.Intent.ACTION_SENDTO,
						smsToUri);
				mIntent.putExtra("sms_body", "");
				context.startActivity(mIntent);
				
			}
		});
		Util_MatchTip.initAllScreenText(convertView,contactslistdata.get(position).getKeyword());
		return convertView;
	}

}
