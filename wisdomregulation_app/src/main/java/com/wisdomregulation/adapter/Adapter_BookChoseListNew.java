package com.wisdomregulation.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wisdomregulation.R;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.staticlib.Static_BookLib;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

import java.util.List;
import java.util.Map;

public class Adapter_BookChoseListNew extends BaseAdapter{
	private Activity context;
	private Map<String,Boolean> bookViewMap;
	public Bitmap bp;
	private List<Base_Entity> booklistdata;
	public Adapter_BookChoseListNew(Activity context,List<Base_Entity> booklistdata,
			Map<String, Boolean> bookViewMap) {
		super();
		this.context = context;
		this.bookViewMap = bookViewMap;
		this.booklistdata=booklistdata;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Static_BookLib.BookNameList2017.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return Static_BookLib.BookNameList2017[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView!=null){
			holder=(ViewHolder)convertView.getTag();
		}
		else{
			holder = new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.item_dialog_book_chose, null);
			int height=(int)Static_InfoApp.create().getAppScreenHigh()/7;
			convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));
			holder.vhbookcheck=(CheckBox) convertView.findViewById(R.id.bookcheck);
			holder.vhtopimagecontent=(LinearLayout) convertView.findViewById(R.id.topimagecontent);
			holder.vhtopimage=(ImageView) convertView.findViewById(R.id.topimage);
			convertView.setTag(holder);
		}
		if(position!=0){
			holder.vhtopimagecontent.setVisibility(View.GONE);
			String bookname=getItem(position).toString();
			holder.vhbookcheck.setText(position+","+bookname);
//			bookViewMap.put(Static_ConstantLib.BookNameList[position], false);
			holder.vhbookcheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(buttonView.isEnabled()){
					bookViewMap.put(Static_BookLib.BookNameList2017[position], isChecked);
					}
				}
			});
			Boolean checkflag=bookViewMap.get(Static_BookLib.BookNameList2017[position]);
			if(checkflag!=null&&checkflag){
				holder.vhbookcheck.setEnabled(true);
				holder.vhbookcheck.setChecked(true);
			}else{
				holder.vhbookcheck.setEnabled(true);
				holder.vhbookcheck.setChecked(false);
			}
			if(booklistdata!=null){
				for (int i = 0; i < booklistdata.size(); i++) {
					if(booklistdata.get(i).getValue(4).equals(bookname)){
//						bookViewMap.put(Static_ConstantLib.BookNameList[position], false);
						holder.vhbookcheck.setEnabled(false);
						holder.vhbookcheck.setChecked(true);
						
					}
				}
			}

			
		}else{
			holder.vhbookcheck.setEnabled(false);
			holder.vhtopimagecontent.setVisibility(View.VISIBLE);
//			if(bp!=null){
//				imageView.setImageBitmap(bp);
//			}
//			else{
//			ViewTreeObserver vto2 = holder.vhtopimage.getViewTreeObserver();    
//		    vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {  
//		        @Override    
//		        public void onGlobalLayout() {  
//		        	imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//		        	bp=Util_Image.decodeSampledBitmapFromResource(context.getResources(), R.drawable.bookimagetitle, imageView.getWidth(), imageView.getHeight());
//		        	imageView.setImageBitmap(bp);
//		        }    
//		    }); 
//			}
		}

		Util_MatchTip.initAllScreenText(convertView);
		return convertView;
	}
	static class ViewHolder{
		CheckBox vhbookcheck;
		LinearLayout vhtopimagecontent;
		ImageView vhtopimage;
	}

}
