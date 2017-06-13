
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

public class Chart_CompanyCount extends Base_AyActivity implements OnChartValueSelectedListener ,OnChartGestureListener{
	private HorizontalBarChart mChartBar;
	private PieChart mChartPie;
	private String specialip="http://221.192.132.39:8001/sjzzhaj/";
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
		setContentView(R.layout.activity_chart_companycount);
		
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
		mChartBar.setDescription("");
		mChartBar.setNoDataText("等待载入中");
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
		kjh.post(specialip+Static_ConstantLib.chartcompanycount, params, new HttpCallBack() {

			@Override
			public void onSuccess(final String t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				Chart_CompanyCount.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						try {
							if(Static_InfoApp.create().isshow()){
								
								try {
									setData(new JSONArray("[{\"typeCode\":\"130100\",\"name\":\"石家庄市\",\"qyCount\":\"77\",\"percent\":\"12.107\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130102\",\"name\":\"长安区\",\"qyCount\":\"16\",\"percent\":\"2.516\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130104\",\"name\":\"桥西区\",\"qyCount\":\"39\",\"percent\":\"6.132\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130105\",\"name\":\"新华区\",\"qyCount\":\"30\",\"percent\":\"4.717\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130108\",\"name\":\"裕华区\",\"qyCount\":\"61\",\"percent\":\"9.591\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130103\",\"name\":\"高新区\",\"qyCount\":\"21\",\"percent\":\"3.302\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130107\",\"name\":\"矿区\",\"qyCount\":\"12\",\"percent\":\"1.887\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130185\",\"name\":\"鹿泉区\",\"qyCount\":\"41\",\"percent\":\"6.447\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130182\",\"name\":\"藁城区\",\"qyCount\":\"60\",\"percent\":\"9.434\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130183\",\"name\":\"晋州市\",\"qyCount\":\"4\",\"percent\":\"0.629\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130184\",\"name\":\"新乐市\",\"qyCount\":\"20\",\"percent\":\"3.145\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130132\",\"name\":\"元氏县\",\"qyCount\":\"32\",\"percent\":\"5.031\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130133\",\"name\":\"赵县\",\"qyCount\":\"14\",\"percent\":\"2.201\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130126\",\"name\":\"灵寿县\",\"qyCount\":\"14\",\"percent\":\"2.201\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130131\",\"name\":\"平山县\",\"qyCount\":\"8\",\"percent\":\"1.258\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130127\",\"name\":\"高邑县\",\"qyCount\":\"24\",\"percent\":\"3.774\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130123\",\"name\":\"正定县\",\"qyCount\":\"8\",\"percent\":\"1.258\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130125\",\"name\":\"行唐县\",\"qyCount\":\"2\",\"percent\":\"0.314\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130129\",\"name\":\"深泽县\",\"qyCount\":\"7\",\"percent\":\"1.101\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130121\",\"name\":\"井陉县\",\"qyCount\":\"48\",\"percent\":\"7.547\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130130\",\"name\":\"无极县\",\"qyCount\":\"51\",\"percent\":\"8.019\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130124\",\"name\":\"栾城区\",\"qyCount\":\"27\",\"percent\":\"4.245\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130135\",\"name\":\"赞皇县\",\"qyCount\":\"4\",\"percent\":\"0.629\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs320190\",\"name\":\"正定新区\",\"qyCount\":\"0\",\"percent\":\"0.0\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs320191\",\"name\":\"循环化工园区\",\"qyCount\":\"16\",\"percent\":\"2.516\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjz320192\",\"name\":\"空港工业园区\",\"qyCount\":\"0\",\"percent\":\"0.0\",\"order\":0,\"childVos\":null}]"));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}else{
								boolean isjson=Util_String.isjsonArray(t);
								if(isjson){

										setData(new JSONArray(t));
									
									
								}else{
									Toast.makeText(Chart_CompanyCount.this, "服务器出错请稍后再试", Toast.LENGTH_SHORT).show();
									Chart_CompanyCount.this.finish();
								}
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
				if(Static_InfoApp.create().isshow()){
					
					try {
						setData(new JSONArray("[{\"typeCode\":\"130100\",\"name\":\"石家庄市\",\"qyCount\":\"77\",\"percent\":\"12.107\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130102\",\"name\":\"长安区\",\"qyCount\":\"16\",\"percent\":\"2.516\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130104\",\"name\":\"桥西区\",\"qyCount\":\"39\",\"percent\":\"6.132\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130105\",\"name\":\"新华区\",\"qyCount\":\"30\",\"percent\":\"4.717\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130108\",\"name\":\"裕华区\",\"qyCount\":\"61\",\"percent\":\"9.591\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130103\",\"name\":\"高新区\",\"qyCount\":\"21\",\"percent\":\"3.302\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130107\",\"name\":\"矿区\",\"qyCount\":\"12\",\"percent\":\"1.887\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130185\",\"name\":\"鹿泉区\",\"qyCount\":\"41\",\"percent\":\"6.447\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130182\",\"name\":\"藁城区\",\"qyCount\":\"60\",\"percent\":\"9.434\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130183\",\"name\":\"晋州市\",\"qyCount\":\"4\",\"percent\":\"0.629\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130184\",\"name\":\"新乐市\",\"qyCount\":\"20\",\"percent\":\"3.145\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130132\",\"name\":\"元氏县\",\"qyCount\":\"32\",\"percent\":\"5.031\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130133\",\"name\":\"赵县\",\"qyCount\":\"14\",\"percent\":\"2.201\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130126\",\"name\":\"灵寿县\",\"qyCount\":\"14\",\"percent\":\"2.201\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130131\",\"name\":\"平山县\",\"qyCount\":\"8\",\"percent\":\"1.258\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130127\",\"name\":\"高邑县\",\"qyCount\":\"24\",\"percent\":\"3.774\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130123\",\"name\":\"正定县\",\"qyCount\":\"8\",\"percent\":\"1.258\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130125\",\"name\":\"行唐县\",\"qyCount\":\"2\",\"percent\":\"0.314\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130129\",\"name\":\"深泽县\",\"qyCount\":\"7\",\"percent\":\"1.101\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130121\",\"name\":\"井陉县\",\"qyCount\":\"48\",\"percent\":\"7.547\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130130\",\"name\":\"无极县\",\"qyCount\":\"51\",\"percent\":\"8.019\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130124\",\"name\":\"栾城区\",\"qyCount\":\"27\",\"percent\":\"4.245\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs130135\",\"name\":\"赞皇县\",\"qyCount\":\"4\",\"percent\":\"0.629\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs320190\",\"name\":\"正定新区\",\"qyCount\":\"0\",\"percent\":\"0.0\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjzs320191\",\"name\":\"循环化工园区\",\"qyCount\":\"16\",\"percent\":\"2.516\",\"order\":0,\"childVos\":null},{\"typeCode\":\"sjz320192\",\"name\":\"空港工业园区\",\"qyCount\":\"0\",\"percent\":\"0.0\",\"order\":0,\"childVos\":null}]"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}else{
				Toast.makeText(Chart_CompanyCount.this, "服务器出错请稍后再试", Toast.LENGTH_SHORT).show();
				Chart_CompanyCount.this.finish();
				}
			}
			
		});
	}
	@Override
	public void onNothingSelected() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onValueSelected(Entry arg0, int arg1, Highlight arg2) {
		toastshow("区域:"+yVals4show.get(arg0.getXIndex())+"--数量:"+yVals3show.get(arg0.getXIndex())+"个企业");
		
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
				xVals.add(jsobj.getString("name"));
				yVals4.add(jsobj.getString("name"));
				baryVals1.add(new BarEntry(Float.parseFloat(jsobj.getString("percent")), i));
				pieyVals1.add(new Entry(Float.parseFloat(jsobj.getString("qyCount")), i));
				yVals2.add(Float.parseFloat(jsobj.getString("percent")));
				yVals3.add(jsobj.getString("qyCount"));
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
		BarDataSet set1 = new BarDataSet(yVals1, "地区和企业数量统计");
        set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        set1.setValueFormatter(new ValueFormatter() {
			
			@Override
			public String getFormattedValue(float arg0, Entry arg1, int arg2,
					ViewPortHandler arg3) {
				
				String tmp=String.format("%.2f",arg0);
				return tmp+"%";
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
