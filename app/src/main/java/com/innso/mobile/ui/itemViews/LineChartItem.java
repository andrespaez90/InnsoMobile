package com.innso.mobile.ui.itemViews;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.innso.mobile.R;
import com.innso.mobile.ui.adapters.CustomLineChart;
import com.innso.mobile.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class LineChartItem extends LinearLayout {

    private CustomLineChart customLineChart;

    public LineChartItem(Context context) {
        super(context);
        init();
    }

    public LineChartItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LineChartItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initChart();
        addView(customLineChart, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.size_chart)));
    }

    private void initChart() {
        customLineChart = new CustomLineChart(getContext());
        customLineChart.getXAxis().setValueFormatter((value, axis) -> DateUtils.getMonth((int) value).substring(0, 3));
        customLineChart.getXAxis().setGranularity(1f);
        customLineChart.getXAxis().setDrawGridLines(false);
        customLineChart.getXAxis().setDrawAxisLine(false);
        customLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        customLineChart.getXAxis().setAxisMinimum(-0.4f);
        customLineChart.getXAxis().setAxisMaximum(11.4f);
        customLineChart.getAxisLeft().setDrawAxisLine(false);
        customLineChart.getAxisLeft().setDrawLabels(false);
        customLineChart.getAxisRight().setDrawAxisLine(false);
        customLineChart.getAxisRight().setDrawLabels(false);
        customLineChart.getAxisLeft().setDrawGridLines(false);
        customLineChart.getAxisRight().setGranularityEnabled(true);
        customLineChart.getAxisLeft().setGranularityEnabled(true);
        customLineChart.getAxisRight().setGranularity(5f);
        customLineChart.getAxisLeft().setGranularity(5f);
        customLineChart.setScaleEnabled(false);
    }

    public void addChartInformation(List<Double> values, int lineColor, int circleColor) {

        List<ILineDataSet> dataSets;

        dataSets = customLineChart.getData() == null ? new ArrayList<>() : customLineChart.getData().getDataSets();

        List<Entry> entries = new ArrayList<>();
        for (int i = 0, size = values.size(); i < size; i++) {
            double value = values.get(i);
            entries.add(new Entry(i, (float) value));
        }
        LineDataSet newDataLine = new LineDataSet(entries, null);
        newDataLine.setLineWidth(2.5f);
        newDataLine.setColor(lineColor);
        newDataLine.setCircleColor(circleColor);

        newDataLine.setCircleRadius(5);
        newDataLine.setCircleHoleRadius(3);
        newDataLine.setDrawValues(false);

        newDataLine.setHighLightColor(Color.TRANSPARENT);

        dataSets.add(0, newDataLine);

        LineData lineData = new LineData(dataSets);


        float min = 0;
        float max = 100;

        for (int j = 0, size = dataSets.size(); j < size; j++) {
            List<Entry> lineEntries = ((LineDataSet) dataSets.get(j)).getValues();
            for (int i = 0, entrySize = lineEntries.size(); i < entrySize; i++) {
                double value = lineEntries.get(i).getY();
                max = max < value ? (float) value : max;
            }
        }

        customLineChart.getAxisRight().setAxisMinimum(min);
        customLineChart.getAxisLeft().setAxisMinimum(min);
        customLineChart.getAxisRight().setAxisMaximum(max);
        customLineChart.getAxisLeft().setAxisMaximum(max);
        customLineChart.setData(lineData);
        customLineChart.invalidate();
    }

    public void clearInformation() {
        customLineChart.clear();
    }
}
