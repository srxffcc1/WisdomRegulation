package com.wisdomregulation.adapter;

import android.app.Activity;
import android.content.Intent;
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
import com.wisdomregulation.frame.AutoCheckBox;
import com.wisdomregulation.frame.CallBack;
import com.wisdomregulation.frame.OnCheckedChangeListener;
import com.wisdomregulation.pop.Pop_Tool;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_File;
import com.wisdomregulation.utils.Util_MatchTip;
import com.wisdomregulation.utils.Util_String;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_CheckOptionDetail {
	private Activity context;
	private Base_Entity detailMapData;
	private LinearLayout content;
	private int editState = 0;
	private Map<Integer, View> viewmap = new HashMap<Integer, View>();
	private List<View> willhide=new ArrayList<View>();
	private TextView needyiju=null;
	private GridLayout gridlayout;
	private File dir;
	private String filepath;
	private List<String> falvyiju;
	private List<String> zhifayiju;
	private List<String> yinhuanleixing;
	private String jianchayaoqiu;
	private int isdangerous=0;
	private List<EditText> edittextlist=new ArrayList<EditText>();
	public Adapter_CheckOptionDetail(Activity context,
			Base_Entity detailMapData, LinearLayout content) {
		super();
		this.context = context;
		this.detailMapData = detailMapData;
		this.content = content;
	}

	public String getFilepath() {
		return filepath;
	}

	public int getIsdangerous() {
		return isdangerous;
	}

	public Adapter_CheckOptionDetail setList(List<String> falvyiju,
			List<String> zhifayiju, List<String> yinhuanleixing,
			String jianchayaoqiu) {
		this.falvyiju = falvyiju;
		this.zhifayiju = zhifayiju;
		this.yinhuanleixing = yinhuanleixing;
		this.jianchayaoqiu = jianchayaoqiu;
		return this;
	}

	public Adapter_CheckOptionDetail setFilepath(String filepath) {
		this.filepath = filepath;
		return this;
	}

	public Base_Entity getResult() {

		String fromiddetail = detailMapData.getValue(1);
		String id = detailMapData.getId();
		String checkid =detailMapData.getValue(19);
		if (id == null || id.equals("")) {
			id = Util_String.get16Uuid();
		}
		Base_Entity result = detailMapData.init().clearOther();
		result.setId(id);

				
		for (int i = 0; i < result.size(); i++) {
			View view = viewmap.get(i);
			if (view != null&&view.getVisibility()==View.VISIBLE) {
				String logg=Util_MatchTip.view2String(view);
				result.put(i, logg);
			}
		}
		result.put(1, fromiddetail)
		.put(19, checkid);
		result.put(30, filepath);
		result.put(3, "");
		return result;
	}

	public Adapter_CheckOptionDetail initView() {
		viewmap.clear();
		content.removeAllViews();
		for (int i = 0; i < getCount(); i++) {
			View add = getView(i, content);
			if (add != null) {

				if(add.getVisibility()==View.VISIBLE){
					if(add.getTag()!=null&&add.getTag().toString().equals("willhide")){
						View view31=getView3(content);
						View view32=getView3(content);
						View view21=getView2(content);
						if(isdangerous==0){
							add.setVisibility(View.GONE);
							view31.setVisibility(View.GONE);
							view32.setVisibility(View.GONE);
							view21.setVisibility(View.GONE);
						}
						willhide.add(view31);
						willhide.add(view32);
						willhide.add(view21);
						content.addView(view31);
						content.addView(add);
						content.addView(view32);
						content.addView(view21);
					}else{
						content.addView(getView3(content));
						content.addView(add);
						content.addView(getView3(content));
						content.addView(getView2(content));
					}

				}

			}
			

		}
		return this;
	}
	public Adapter_CheckOptionDetail initGrid(){
		List<EntityS_File> needshowfilelist=new ArrayList<EntityS_File>();
		needshowfilelist.clear();
		File delete=new File(Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/"+filepath+".jpg");
		if(delete.exists()){
			delete.delete();
		}
		dir = new File(Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/"+filepath);
		if (dir.exists()) {
			List<File> filelist = Util_File.getFileSort(dir);
			for (int i = 0; i < filelist.size(); i++) {
				needshowfilelist.add(new EntityS_File(context, filelist.get(i)));
			}
		}
		if(needshowfilelist.size()>0){
			Adapter_CheckOptionDetailGrid adapter2=new Adapter_CheckOptionDetailGrid(context, needshowfilelist, gridlayout).initView();
		}
		
		return this;
	}
	public Adapter_CheckOptionDetail setEditState(int editState) {
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
	public List<EditText> getEdittextlist() {
		return edittextlist;
	}
	public View getView(int position, ViewGroup parent) {
		View convertView = LayoutInflater.from(context).inflate(
				R.layout.item_activity_check_optioncontent, null);
		
		convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT ));
		LinearLayout detaillayout1=(LinearLayout) convertView.findViewById(R.id.detaillayout1);
		LinearLayout detaillayout2=(LinearLayout) convertView.findViewById(R.id.detaillayout2);
		LinearLayout detaillayout3=(LinearLayout) convertView.findViewById(R.id.detaillayout3);
		LinearLayout detaillayout4=(LinearLayout) convertView.findViewById(R.id.detaillayout4);
		LinearLayout detaillayout5=(LinearLayout) convertView.findViewById(R.id.detaillayout5);
		LinearLayout detaillayout6=(LinearLayout) convertView.findViewById(R.id.detaillayout6);
		LinearLayout detaillayout7=(LinearLayout) convertView.findViewById(R.id.detaillayout7);
		TextView detailfield1=(TextView) convertView.findViewById(R.id.detailfield1);
		TextView detailfield2=(TextView) convertView.findViewById(R.id.detailfield2);
		TextView detailfield3=(TextView) convertView.findViewById(R.id.detailfield3);
		final TextView detailfield4=(TextView) convertView.findViewById(R.id.detailfield4);
		TextView detailfield5=(TextView) convertView.findViewById(R.id.detailfield5);
		TextView detailfield6=(TextView) convertView.findViewById(R.id.detailfield6);
		TextView detailfield7=(TextView) convertView.findViewById(R.id.detailfield7);
		
		EditText detailvalue1=(EditText) convertView.findViewById(R.id.detailvalue1);
		final EditText detailvalue2=(EditText) convertView.findViewById(R.id.detailvalue2);

		EditText detailvalue3=(EditText) convertView.findViewById(R.id.detailvalue3);
		final EditText detailvalue4=(EditText) convertView.findViewById(R.id.detailvalue4);
		TextView detailvalue5=(TextView) convertView.findViewById(R.id.detailvalue5);
		
		final RadioButton detailradio0=(RadioButton) convertView.findViewById(R.id.detailradio0);
		RadioButton detailradio1=(RadioButton) convertView.findViewById(R.id.detailradio1);
		RadioButton detailradio2=(RadioButton) convertView.findViewById(R.id.detailradio2);
		RadioButton detailradio00=(RadioButton) convertView.findViewById(R.id.detailradio00);
		RadioButton detailradio01=(RadioButton) convertView.findViewById(R.id.detailradio01);
		RadioButton detailradio02=(RadioButton) convertView.findViewById(R.id.detailradio02);
		RadioButton detailradio03=(RadioButton) convertView.findViewById(R.id.detailradio03);
		RadioGroup detailgroup0=(RadioGroup) convertView.findViewById(R.id.detailgroup0);
		RadioGroup detailgroup1=(RadioGroup) convertView.findViewById(R.id.detailgroup1);
		Util_MatchTip.initAllScreenText(convertView);
		
		String fieldtext=detailMapData.getFieldChinese(position);
		if(fieldtext.equals("检查要求")){
			detailfield1.setText(fieldtext+"：  ");
			detaillayout1.setVisibility(View.VISIBLE);
			detailvalue1.setVisibility(View.VISIBLE);
			detailvalue1.setKeyListener(null);
			detailvalue1.setText(jianchayaoqiu);
		}
		else if(fieldtext.equals("整改人")){
			convertView.setTag("willhide");
			willhide.add(convertView);
			detailfield4.setText(fieldtext+"：  ");
			detaillayout4.setVisibility(View.VISIBLE);
			detailvalue4.setVisibility(View.VISIBLE);
			detailvalue4.setText(detailMapData.getValue(position));
			edittextlist.add(detailvalue4);
		}
		else if(fieldtext.equals("检查时间")){
			convertView.setTag("willhide");
			willhide.add(convertView);
			detailfield4.setText(fieldtext+"：  ");
			detaillayout4.setVisibility(View.VISIBLE);
			detailvalue4.setVisibility(View.VISIBLE);
			detailvalue4.setText(detailMapData.getValue(position));
			detailvalue4.setFocusable(false);
			detailvalue4.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					new DateTimePickDialog(context, "").show(detailvalue4);
					
				}
			});
			

		}
		else if(fieldtext.equals("法律依据")){
			if(zhifayiju!=null&&zhifayiju.size()>0){
				final AutoCheckBox checkbox=(AutoCheckBox) convertView.findViewById(R.id.pass2);
				
				checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(View buttonView, boolean isChecked) {
						if(isChecked){
							Pop_Tool.pop_chose_list("无可选法律条目",context, detailvalue2, detailvalue2.getWidth(),(int)(Static_InfoApp.create().getAppScreenWidth()/1.2), falvyiju, checkbox,new CallBack() {
								
								@Override
								public void back(Object resultlist) {
									if(zhifayiju!=null&&zhifayiju.size()>0){
										needyiju.setText(zhifayiju.get((Integer) resultlist));
									}
										
									
								}
							});
						}
						
						
					}
				});
				detailfield2.setText(fieldtext+"：  ");
				detaillayout2.setVisibility(View.VISIBLE);
				detailvalue2.setVisibility(View.VISIBLE);
				detailvalue2.setKeyListener(null);
				detailvalue2.setText(detailMapData.getValue(position));
				detailvalue2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						
					}
				});
			}else{
				final AutoCheckBox checkbox=(AutoCheckBox) convertView.findViewById(R.id.pass2);
				checkbox.setClickable(false);
				detailfield2.setText(fieldtext+"：  ");
				detaillayout2.setVisibility(View.VISIBLE);
				detailvalue2.setVisibility(View.VISIBLE);
				detailvalue2.setKeyListener(null);
				detailvalue2.setText("《中华人民共和国安全生产法》第32条");
				detailvalue2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						
					}
				});
			}


		}
		else if(fieldtext.equals("隐患类型")){
			convertView.setTag("willhide");
			willhide.add(convertView);
			final AutoCheckBox checkbox=(AutoCheckBox) convertView.findViewById(R.id.pass2);
			checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(View buttonView, boolean isChecked) {
					if(isChecked){
						Pop_Tool.pop_chose_list("无可选类型",context, detailvalue2, detailvalue2.getWidth(),(int)(Static_InfoApp.create().getAppScreenWidth()/1.8), yinhuanleixing, checkbox,null);
							
					}
					
				}
			});
			detailfield2.setText(fieldtext+"：  ");
			detaillayout2.setVisibility(View.VISIBLE);
			detailvalue2.setVisibility(View.VISIBLE);
			detailvalue2.setKeyListener(null);
			detailvalue2.setText(detailMapData.getValue(position));
			detailvalue2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					
				}
			});
			
		}
		else if(fieldtext.equals("执法依据")){
			detailfield1.setText(fieldtext+"：  ");
			detaillayout1.setVisibility(View.VISIBLE);
			detailvalue1.setVisibility(View.VISIBLE);
			detailvalue1.setKeyListener(null);
			String de=detailMapData.getValue(position);
			detailvalue1.setText(de);
			needyiju=detailvalue1;
		}
		else if(fieldtext.equals("隐患描述")){
			convertView.setTag("willhide");
			willhide.add(convertView);
			detailfield3.setText(fieldtext+"：  ");
			detaillayout3.setVisibility(View.VISIBLE);
			detailvalue3.setVisibility(View.VISIBLE);
			detailvalue3.setText(detailMapData.getValue(position));
		}
		else if(fieldtext.equals("隐患级别")){
			detailgroup0.setVisibility(View.VISIBLE);
			detailfield6.setText(fieldtext+"：  ");

			detailradio0.setText("无隐患");
			detailradio1.setText("一般隐患");
			detailradio2.setText("重大隐患");
			detailgroup0.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					if(checkedId==detailradio0.getId()){
						isdangerous=0;
						for (int i = 0; i < willhide.size(); i++) {
							willhide.get(i).setVisibility(View.GONE);
						}
					}else{
						isdangerous=1;
						for (int i = 0; i < willhide.size(); i++) {
							willhide.get(i).setVisibility(View.VISIBLE);
						}
					}
					
				}
			});
			if(!detailMapData.getValue(position).equals(" ")&&!detailMapData.getValue(position).equals("")){
				if(detailMapData.getValue(position).equals("3")){
					isdangerous=0;
					detailgroup0.check(detailradio0.getId());
				}else if (detailMapData.getValue(position).equals("0")){
					isdangerous=1;
					detailgroup0.check(detailradio1.getId());
				}else{
					isdangerous=1;
					detailgroup0.check(detailradio2.getId());
				}
			}else{
				isdangerous=0;
				detailgroup0.check(detailradio0.getId());
			}

			detaillayout6.setVisibility(View.VISIBLE);
		}
		else if(fieldtext.equals("证据采集")){
			convertView.setTag("willhide");
			willhide.add(convertView);
			gridlayout=(GridLayout) convertView.findViewById(R.id.detaillayouts);
			detailgroup1.setVisibility(View.VISIBLE);
			detailfield7.setText(fieldtext+"：  ");
			detailradio00.setText("照片\n采集");
			detailradio00.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					context.startActivity(new Intent(context, Activity_PictureCollect.class).putExtra("savehead", Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/"+filepath));
					
				}
			});
			detailradio01.setText("视频\n采集");
			detailradio01.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Static_InfoApp.create().showToast("暂不支持视频采集");
//					context.startActivity(new Intent(context, Tmp_VideoActivity.class).putExtra("savehead", Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/"+filepath));
					
				}
			});
			detailradio02.setText("音频\n采集");
			detailradio02.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					context.startActivity(new Intent(context, Tmp_VoiceActivity.class).putExtra("savehead", Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/"+filepath));
					
				}
			});
			detailradio03.setText("来自\n文件");
			detailradio03.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					context.startActivity(new Intent(context, Mode_EvidenceCollect.class).putExtra("savehead", Static_InfoApp.create().getPath()+"/ZhiCollect/"+"/"+filepath));
				}
			});
			detaillayout7.setVisibility(View.VISIBLE);
		}
		else{
			convertView.setVisibility(View.GONE);
		}
		if(convertView.getVisibility()!=View.GONE){
			viewmap.put(position, convertView);
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
