package com.wisdomregulation.allactivity.single;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity_ModelDetail extends Base_AyActivity implements
OnChartValueSelectedListener{
	private LineChart mChart;

	@Override
	public void setRootView() {
		this.setContentView(R.layout.activity_model_detail);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

		
		
	}
	
	private void initChart() {
        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);
//
//        // add empty data
        mChart.setData(data);
        mChart.setNoDataText("正在连接现场");
        mChart.setDescription("");
        new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				feedMultiple();
				
			}
		}, 800);
	}
    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "现场模拟量");
        set.setAxisDependency(AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.RED);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.BLACK);
        set.setValueTextSize(9f);
        
        return set;
    }
    private void feedMultiple() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                for(int i = 0; i < 50000000; i++) {
                	if(Activity_ModelDetail.this.isFinishing()){
                		break;
                	}else{
                		Activity_ModelDetail.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                        		 addEntry();
                        	
                           
                        }
                    });
                	}

                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    private void addEntry() {

        LineData data = mChart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            // add a new x-value first
            data.addXValue(new SimpleDateFormat("HH:mm:ss").format(new Date()));
            data.addEntry(new Entry((float) (Math.random() * 40) + 30f, set.getEntryCount()), 0);


            // let the chart know it's data has changed
            mChart.notifyDataSetChanged();

            // limit the number of visible entries
            mChart.setVisibleXRangeMaximum(20);
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            mChart.moveViewToX(data.getXValCount() - 21);

            // this automatically refreshes the chart (calls invalidate())
            // mChart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }
	
	@Override
	public void initWidget() {

        mChart=(LineChart)findViewById(R.id.chart1);
		initView();
		
	}
	public void initView(){
		initChart();

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	class TmpBroadcastReceiver extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent) {

			
		}
		
	}

	@Override
	public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected() {
		// TODO Auto-generated method stub
		
	}
}
