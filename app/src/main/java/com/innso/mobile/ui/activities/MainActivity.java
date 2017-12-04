package com.innso.mobile.ui.activities;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.innso.mobile.R;
import com.innso.mobile.databinding.ActivityMainBinding;
import com.innso.mobile.ui.helpers.NavigationViewHelper;
import com.innso.mobile.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView();
    }

    private void initView() {
        NavigationViewHelper.disableShiftMode(binding.navigationView);
        setupDataSet();
    }

    private void setupDataSet() {
        LineChart chart = binding.chartSales;
        chart.getXAxis().setValueFormatter((value, axis) -> DateUtils.getMonth((int) value).substring(0, 3));
        chart.getXAxis().setGranularity(1f);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setAxisMinimum(-0.4f);
        chart.getXAxis().setAxisMaximum(11.4f);
        chart.getAxisLeft().setDrawAxisLine(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisRight().setDrawAxisLine(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setGranularityEnabled(true);
        chart.getAxisLeft().setGranularityEnabled(true);
        chart.getAxisRight().setGranularity(5f);
        chart.getAxisLeft().setGranularity(5f);
        chart.setScaleEnabled(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        addChartInforamtion();
    }

    private void addChartInforamtion() {

        binding.chartSales.clear();

        List<Entry> entries = new ArrayList<>();

        for (int i = 0, size = 12; i < size; i++) {
            entries.add(new Entry(i, 0));
        }

        LineDataSet dataSet = new LineDataSet(entries, null);
        dataSet.setLineWidth(2.5f);
        dataSet.setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        dataSet.setCircleColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        dataSet.setCircleRadius(5);
        dataSet.setCircleHoleRadius(3);
        dataSet.setDrawValues(false);

        dataSet.setHighLightColor(Color.TRANSPARENT);

        LineData lineData = new LineData(dataSet);

        float min = 0;
        float max = 100;

        binding.chartSales.getAxisRight().setAxisMinimum(min);
        binding.chartSales.getAxisLeft().setAxisMinimum(min);
        binding.chartSales.getAxisRight().setAxisMaximum(max);
        binding.chartSales.getAxisLeft().setAxisMaximum(max);

        binding.chartSales.setData(lineData);
        binding.chartSales.invalidate();
    }
}

