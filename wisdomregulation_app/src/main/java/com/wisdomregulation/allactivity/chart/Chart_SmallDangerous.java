
package com.wisdomregulation.allactivity.chart;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener.ChartGesture;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.staticlib.Static_ConstantLib;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_String;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Chart_SmallDangerous extends Base_AyActivity implements OnChartValueSelectedListener ,OnChartGestureListener{
	private HorizontalBarChart mChartBar;
	private PieChart mChartPie;
	public Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			
			case R.string.finishoverchart:
				
				break;
			
			default:
				break;
			}
		}

	};

	private Typeface tf;

	private DecimalFormat mFormat;

	private ArrayList<String> yVals3show;
	private ArrayList<String> yVals4show;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_chart_smalldangerous);
		
	}

	@Override
	public void initData() {

	}

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		mChartBar=(HorizontalBarChart)findViewById(R.id.chart1);
		mChartPie=(PieChart)findViewById(R.id.chart2);
		yVals3show = new ArrayList<String>();
		yVals4show = new ArrayList<String>();
		tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");//设置字体

		mFormat = new DecimalFormat("#.00");
		
		
		initchartbar();
        initchartpie();
		getchartData();
		
	}
	private void initchartpie() {
		Legend mLegend = mChartPie.getLegend();
		mLegend.setEnabled(false);
		mChartPie.setDescription("");
		mChartPie.setHoleRadius(50f); //实心圆  
		mChartPie.setTransparentCircleRadius(54f); // 半透明圈  
		mChartPie.animateY(2500);//设置动画速度
		mChartPie.setVisibility(View.GONE);
		mChartPie.setRotationEnabled(false);
	}
	private void initchartbar() {
		mChartBar.animateY(2500);//设置动画速度
		mChartBar.setOnChartValueSelectedListener(this);
//		mChartBar.setScaleEnabled(false);
		mChartBar.setPinchZoom(true);
		mChartBar.setNoDataText("等待载入中");
		mChartBar.setDescription("");
        XAxis xl = mChartBar.getXAxis();
        xl.setPosition(XAxisPosition.BOTTOM);
        xl.setTypeface(tf);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(true);
        xl.setGridLineWidth(0.3f);
        
        YAxis yl = mChartBar.getAxisLeft();
        yl.setTypeface(tf);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setGridLineWidth(0.3f);
        yl.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        yl.setEnabled(false);
        
        YAxis yr = mChartBar.getAxisRight();
        yr.setTypeface(tf);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        
        
//        mChartBar.setVisibility(View.GONE);
	}
	@Override
	public void initView() {
		
		
	}
	public void getchartData(){
		KJHttp kjh = new KJHttp();
		HttpParams params = new HttpParams();
		params.put("type", "xzqhqx");
		kjh.post(Static_InfoApp.create().getiphead()+Static_ConstantLib.chartsmalldangerousinfo, params, new HttpCallBack() {

			@Override
			public void onSuccess(final String t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				//System.out.println("返回数据:"+t);
				Chart_SmallDangerous.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						try {
							boolean isjson=Util_String.isjsonArray(t);
							if(isjson){
								setData(new JSONArray(t));
							}else{
								Toast.makeText(Chart_SmallDangerous.this, "服务器出错请稍后再试", Toast.LENGTH_SHORT).show();
								Chart_SmallDangerous.this.finish();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
			}

			@Override
			public void onFailure(int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(errorNo, strMsg);
				Toast.makeText(Chart_SmallDangerous.this, "服务器出错请稍后再试", Toast.LENGTH_SHORT).show();
				Chart_SmallDangerous.this.finish();
			}
			
		});
	}
	@Override
	public void onNothingSelected() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onValueSelected(Entry arg0, int arg1, Highlight arg2) {
		toastshow("区域:"+yVals4show.get(arg0.getXIndex())+"--数量:"+yVals3show.get(arg0.getXIndex())+"个一般隐患");
		
	}
	public void changeMaxSize(ArrayList<Float> yVals2){
		float max=0;
		for (int i = 0; i < yVals2.size(); i++) {
			if(max<yVals2.get(i)){
				max=yVals2.get(i);
			}
		}
		YAxis yr = mChartBar.getAxisRight();
		yr.setAxisMaxValue((max+12));
		YAxis yl = mChartBar.getAxisLeft();
		yl.setAxisMaxValue((max+12));
	}
	public void setValueShow(ArrayList<String> yVals3,ArrayList<String> yVals4){
		
		yVals3show.addAll(yVals3);
		yVals4show.addAll(yVals4);
	}
	public void setData(JSONArray array){
		ArrayList<BarEntry> baryVals1 = new ArrayList<BarEntry>();
		ArrayList<Entry> pieyVals1 = new ArrayList<Entry>();
		ArrayList<Float> yVals2 = new ArrayList<Float>();
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<String> yVals3 = new ArrayList<String>();
        ArrayList<String> yVals4 = new ArrayList<String>();
        yVals3.clear();
        for (int i = 0; i < array.length(); i++) {
            try {
				JSONObject jsobj=new JSONObject(array.get(i).toString());
				xVals.add(jsobj.getString("res1"));
				yVals4.add(jsobj.getString("res1"));
				baryVals1.add(new BarEntry(Float.parseFloat(jsobj.getString("res4")), i));
				pieyVals1.add(new Entry(Float.parseFloat(jsobj.getString("res4")), i));
				yVals2.add(Float.parseFloat(jsobj.getString("res4")));
				yVals3.add(jsobj.getString("res4"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        changeMaxSize(yVals2);
        setValueShow(yVals3,yVals4);
//        setPieData(pieyVals1, xVals);
        setBarData(baryVals1, xVals);
	}
	private void setPieData(ArrayList<Entry> pieyVals1, ArrayList<String> xVals) {
		PieDataSet pieSet = new PieDataSet(pieyVals1, "地区和企业数量统计");
        pieSet.setSelectionShift(10f);
        pieSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        pieSet.setDrawValues(false);
        pieSet.setSliceSpace(5f);
        PieData piedata = new PieData(xVals, pieSet);
        piedata.setValueFormatter(new ValueFormatter() {
			
			@Override
			public String getFormattedValue(float arg0, Entry arg1, int arg2,
					ViewPortHandler arg3) {
				
				String tmp=String.format("%.2f",arg0);
				return tmp+"%";
			}
		});
        piedata.setValueTextSize(10f);
        piedata.setValueTextColor(Color.BLACK);
        piedata.setValueTypeface(tf);
        
        
        mChartPie.setData(piedata);
        mChartPie.invalidate();
	}
	private void setBarData(ArrayList<BarEntry> yVals1, ArrayList<String> xVals) {
		BarDataSet set1 = new BarDataSet(yVals1, "地区一般隐患统计");
        set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        set1.setValueFormatter(new ValueFormatter() {
			
			@Override
			public String getFormattedValue(float arg0, Entry arg1, int arg2,
					ViewPortHandler arg3) {
				
				String tmp=(int)arg0+"";
				return tmp;
			}
		});
        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(15f);
        data.setValueTypeface(tf);
        mChartBar.setData(data);
        mChartBar.invalidate();
	}
	@Override
	public void onChartGestureStart(MotionEvent me,
			ChartGesture lastPerformedGesture) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onChartGestureEnd(MotionEvent me,
			ChartGesture lastPerformedGesture) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onChartLongPressed(MotionEvent me) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onChartDoubleTapped(MotionEvent me) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onChartSingleTapped(MotionEvent me) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onChartTranslate(MotionEvent me, float dX, float dY) {
		// TODO Auto-generated method stub
		
	}
 
}
