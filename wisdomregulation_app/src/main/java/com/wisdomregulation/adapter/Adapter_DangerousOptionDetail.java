package com.wisdomregulation.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.mode.Mode_EvidenceCollect;
import com.wisdomregulation.allactivity.single.Activity_PictureCollect;
import com.wisdomregulation.allactivity.tmp.Tmp_VoiceActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.dialog.DateTimePickDialog;
import com.wisdomregulation.entityfile.EntityS_File;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_File;
import com.wisdomregulation.utils.Util_MatchTip;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_DangerousOptionDetail {
	private Activity context;
	private Base_Entity detailMapData;
	private LinearLayout content;
	private int editState = 0;
	private Map<Integer, View> viewmap = new HashMap<Integer, View>();
	private String filepathon;
	private String filepathoff;
	private File dir;
	private GridLayout gridlayout;
	private GridLayout gridlayout2;
	public Adapter_DangerousOptionDetail(Activity context,
			Base_Entity detailMapData,LinearLayout content) {
		super();
		this.context = context;
		this.detailMapData = detailMapData;
		this.content = content;
	}


	public Base_Entity getResult() {
		String id=detailMapData.getId();
		Base_Entity result = detailMapData.clear();
		for (int i = 0; i < result.size(); i++) {
			View view = viewmap.get(i);
			if (view != null&&view.getVisibility()==View.VISIBLE) {
				result.put(i, Util_MatchTip.view2String(view));
			}
		}
		result.put(15, filepathon);
		result.setId(id);
		return result;
	}

	public Adapter_DangerousOptionDetail initView() {
		viewmap.clear();
		content.removeAllViews();
		for (int i = 0; i < getCount(); i++) {
			View add = getView(i, content);
			if (add != null) {

				if(add.getVisibility()==View.VISIBLE){
					content.addView(getView3(content));
					content.addView(add);
					content.addView(getView3(content));
					content.addView(getView2(content));
				}
			}
			

		}
		return this;
	}
	



	public String getFilepathon() {
		return filepathon;
	}


	public Adapter_DangerousOptionDetail setFilepathon(String filepathon) {
		this.filepathon = filepathon;
		return this;
	}


	public String getFilepathoff() {
		return filepathoff;
	}


	public Adapter_DangerousOptionDetail setFilepathoff(String filepathoff) {
		this.filepathoff = filepathoff;
		return this;
	}
	public Adapter_DangerousOptionDetail initGrid2(){
		List<EntityS_File> needshowfilelist=new ArrayList<EntityS_File>();
		needshowfilelist.clear();
		File delete=new File(Static_InfoApp.create().getPath()+"/ZhiCollect/"+filepathoff+"/"+".jpg");
		if(delete.exists()){
			delete.delete();
		}
		File dir = new File(Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/"+filepathoff);
		if (dir.exists()) {
			List<File> filelist = Util_File.getFileSort(dir);
			for (int i = 0; i < filelist.size(); i++) {
				needshowfilelist.add(new EntityS_File(context, filelist.get(i)));
			}
		}
		if(needshowfilelist.size()>0){
			Adapter_CheckOptionDetailGrid adapter2=new Adapter_CheckOptionDetailGrid(context, needshowfilelist, gridlayout2).setCandelete(true).initView();
		}
		
		return this;
	}

	public Adapter_DangerousOptionDetail initGrid(){
		List<EntityS_File> needshowfilelist=new ArrayList<EntityS_File>();
		needshowfilelist.clear();
		File delete=new File(Static_InfoApp.create().getPath()+"/ZhiCollect/"+filepathon+"/"+".jpg");
		if(delete.exists()){
			delete.delete();
		}
		File dir = new File(Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/"+filepathon);
		if (dir.exists()) {
			List<File> filelist = Util_File.getFileSort(dir);
			for (int i = 0; i < filelist.size(); i++) {
				needshowfilelist.add(new EntityS_File(context, filelist.get(i)));
			}
		}
		if(needshowfilelist.size()>0){
			Adapter_CheckOptionDetailGrid adapter2=new Adapter_CheckOptionDetailGrid(context, needshowfilelist, gridlayout).setCandelete(false).initView();
		}
		
		return this;
	}
	public Adapter_DangerousOptionDetail setEditState(int editState) {
		this.editState = editState;
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
		View convertView = LayoutInflater.from(context).inflate(
				R.layout.item_activity_dangerous_optioncontent, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT ));
		Util_MatchTip.initAllScreenText(convertView);
		LinearLayout detaillayout1=(LinearLayout) convertView.findViewById(R.id.detaillayout1);
		LinearLayout detaillayout2=(LinearLayout) convertView.findViewById(R.id.detaillayout2);
		LinearLayout detaillayout3=(LinearLayout) convertView.findViewById(R.id.detaillayout3);
		LinearLayout detaillayout4=(LinearLayout) convertView.findViewById(R.id.detaillayout4);
		LinearLayout detaillayout5=(LinearLayout) convertView.findViewById(R.id.detaillayout5);
		LinearLayout detaillayout6=(LinearLayout) convertView.findViewById(R.id.detaillayout6);
		LinearLayout detaillayout7=(LinearLayout) convertView.findViewById(R.id.detaillayout7);
		LinearLayout detaillayout8=(LinearLayout) convertView.findViewById(R.id.detaillayout8);
		LinearLayout detaillayout9=(LinearLayout) convertView.findViewById(R.id.detaillayout9);
		TextView detailfield1=(TextView) convertView.findViewById(R.id.detailfield1);
		TextView detailfield2=(TextView) convertView.findViewById(R.id.detailfield2);
		TextView detailfield3=(TextView) convertView.findViewById(R.id.detailfield3);
		TextView detailfield4=(TextView) convertView.findViewById(R.id.detailfield4);
		TextView detailfield5=(TextView) convertView.findViewById(R.id.detailfield5);
		TextView detailfield6=(TextView) convertView.findViewById(R.id.detailfield6);
		TextView detailfield7=(TextView) convertView.findViewById(R.id.detailfield7);
		TextView detailfield8=(TextView) convertView.findViewById(R.id.detailfield8);
		TextView detailfield9=(TextView) convertView.findViewById(R.id.detailfield9);
		
		TextView detailvalue1=(TextView) convertView.findViewById(R.id.detailvalue1);
		TextView detailvalue2=(TextView) convertView.findViewById(R.id.detailvalue2);
		EditText detailvalue3=(EditText) convertView.findViewById(R.id.detailvalue3);
		TextView detailvalue4=(TextView) convertView.findViewById(R.id.detailvalue4);
		TextView detailvalue5=(TextView) convertView.findViewById(R.id.detailvalue5);
		final EditText detailvalue8=(EditText) convertView.findViewById(R.id.detailvalue8);
		
		
		RadioButton detailradio0=(RadioButton) convertView.findViewById(R.id.detailradio0);
		RadioButton detailradio1=(RadioButton) convertView.findViewById(R.id.detailradio1);
		RadioGroup detailgroup0=(RadioGroup) convertView.findViewById(R.id.detailgroup0);
		RadioGroup detailgroup1=(RadioGroup) convertView.findViewById(R.id.detailgroup1);
		RadioButton detailradio00=(RadioButton) convertView.findViewById(R.id.detailradio00);
		RadioButton detailradio01=(RadioButton) convertView.findViewById(R.id.detailradio01);
		RadioButton detailradio02=(RadioButton) convertView.findViewById(R.id.detailradio02);
		RadioButton detailradio03=(RadioButton) convertView.findViewById(R.id.detailradio03);
		String fieldtext=detailMapData.getFieldChinese(position);
		if(fieldtext.equals("整改人")){
			detailfield8.setText(detailMapData.getFieldChinese(position)+"：  ");
			detaillayout8.setVisibility(View.VISIBLE);
			detailvalue8.setVisibility(View.VISIBLE);
			detailvalue8.setText(detailMapData.getValue(position));
		}
		else if(fieldtext.equals("上次复查时间")){
			detailfield8.setText("复查时间"+"：  ");
			detaillayout8.setVisibility(View.VISIBLE);
			detailvalue8.setVisibility(View.VISIBLE);
			detailvalue8.setText(detailMapData.getValue(position));
			detailvalue8.setFocusable(false);
			detailvalue8.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					new DateTimePickDialog(context, "").show(detailvalue8);
					
				}
			});
			
		}
		else if(fieldtext.equals("整改落实资金")){
			detailfield8.setText("整改投入资金"+"：  ");
			detaillayout8.setVisibility(View.VISIBLE);
			detailvalue8.setVisibility(View.VISIBLE);
			detailvalue8.setInputType(InputType.TYPE_CLASS_NUMBER);
			detailvalue8.setText(detailMapData.getValue(position));
		}
		else if(fieldtext.equals("复查描述")){
			detailfield3.setText(detailMapData.getFieldChinese(position)+"：  ");
			detaillayout3.setVisibility(View.VISIBLE);
			detailvalue3.setVisibility(View.VISIBLE);
			detailvalue3.setText(detailMapData.getValue(position));
			detailvalue3.setOnTouchListener(null);
		}
		else if(fieldtext.equals("是否整改完成")){
			detailgroup0.setVisibility(View.VISIBLE);
			detailfield6.setText("整改结果"+"：  ");

			detailradio0.setText("整改完成");
			detailradio1.setText("仍然存在问题");
			if(!detailMapData.getValue(position).equals(" ")){
				if(detailMapData.getValue(position).equals("1")){
					detailgroup0.check(detailradio0.getId());
				}else {
					detailgroup0.check(detailradio1.getId());
				}
			}
			detaillayout6.setVisibility(View.VISIBLE);
		}
		else if(fieldtext.equals("隐患整改前图片")){
			gridlayout=(GridLayout) convertView.findViewById(R.id.detaillayouts);
			detailfield7.setText("隐患整改前证据"+"：  ");
			detaillayout7.setVisibility(View.VISIBLE);
		}
		else if(fieldtext.equals("隐患整改后图片")){
			gridlayout2=(GridLayout) convertView.findViewById(R.id.detaillayouts2);
			detailfield9.setText("隐患整改后证据"+"：  ");
			detailgroup1.setVisibility(View.VISIBLE);
			detailfield7.setText(fieldtext+"：  ");
			detailradio00.setText("照片\n采集");
			detailradio00.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					context.startActivity(new Intent(context, Activity_PictureCollect.class).putExtra("savehead", Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/"+filepathoff));
					
				}
			});
			detailradio01.setText("视频\n采集");
			detailradio01.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Static_InfoApp.create().showToast("暂不支持视频采集");
//					context.startActivity(new Intent(context, Tmp_VideoActivity.class).putExtra("savehead", Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/"+filepathoff));
					
				}
			});
			detailradio02.setText("音频\n采集");
			detailradio02.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					context.startActivity(new Intent(context, Tmp_VoiceActivity.class).putExtra("savehead", Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/"+filepathoff));
					
				}
			});
			detailradio03.setText("来自\n文件");
			detailradio03.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					context.startActivity(new Intent(context, Mode_EvidenceCollect.class).putExtra("savehead", Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/"+filepathoff));
				}
			});
			detaillayout9.setVisibility(View.VISIBLE);
		}
		else{
			convertView.setVisibility(View.GONE);
		}
		if(convertView.getVisibility()!=View.GONE){
			viewmap.put(position, convertView);
		}else{
			
		}
		
		return convertView;
	}
	public View getView2(ViewGroup parent) {
		View convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_detail_line, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/960)));
		Util_MatchTip.initAllScreenText(convertView);
		return convertView;
	}
	public View getView3(ViewGroup parent) {
		View convertView=LayoutInflater.from(context).inflate(R.layout.item_activity_detail_empty, null);
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(Static_InfoApp.create().getAppScreenHigh()/42.6)));
		Util_MatchTip.initAllScreenText(convertView);
		return convertView;
	}
}
