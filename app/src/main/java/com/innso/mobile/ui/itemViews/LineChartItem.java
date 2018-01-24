package com.innso.mobile.ui.itemViews;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
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

    public void addChartInformation(List<Double> values) {

        customLineChart.clear();

        List<Entry> entries = new ArrayList<>();

        float min = 0;
        float max = 100;

        for (int i = 0, size = values.size(); i < size; i++) {
            double value = values.get(i);
            max = max < value ? (float) value : max;
            entries.add(new Entry(i, (float) value));
        }

        LineDataSet dataSet = new LineDataSet(entries, null);
        dataSet.setLineWidth(2.5f);
        dataSet.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        dataSet.setCircleColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));

        dataSet.setCircleRadius(5);
        dataSet.setCircleHoleRadius(3);
        dataSet.setDrawValues(false);

        dataSet.setHighLightColor(Color.TRANSPARENT);

        LineData lineData = new LineData(dataSet);

        customLineChart.getAxisRight().setAxisMinimum(min);
        customLineChart.getAxisLeft().setAxisMinimum(min);
        customLineChart.getAxisRight().setAxisMaximum(max);
        customLineChart.getAxisLeft().setAxisMaximum(max);
        customLineChart.setData(lineData);
        customLineChart.invalidate();
    }

}
