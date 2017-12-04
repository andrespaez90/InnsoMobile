package com.innso.mobile.ui.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;

import com.innso.mobile.R;
import com.innso.mobile.databinding.ActivityMainBinding;
import com.innso.mobile.ui.adapters.GenericAdapter;
import com.innso.mobile.ui.helpers.NavigationViewHelper;
import com.innso.mobile.ui.interfaces.GenericItemView;
import com.innso.mobile.ui.itemViews.LineChartItem;
import com.innso.mobile.ui.models.list.GenericAdapterFactory;
import com.innso.mobile.ui.models.list.GenericItemAbstract;

public class MainActivity extends AppCompatActivity {

    private final int TYPE_GRAPH = 2002;

    private ActivityMainBinding binding;

    private GenericAdapter adapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView();
    }

    private void initView() {
        NavigationViewHelper.disableShiftMode(binding.navigationView);
        initAdapter();
        binding.recyclerViewSales.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.recyclerViewSales.setAdapter(adapater);
    }

    private void initAdapter() {
        adapater = new GenericAdapter(new GenericAdapterFactory() {
            @Override
            public GenericItemView onCreateViewHolder(ViewGroup parent, int viewType) {
                switch (viewType) {
                    default:
                    case TYPE_GRAPH:
                        return new LineChartItem(parent.getContext());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapater.addItem(new GenericItemAbstract(null, TYPE_GRAPH));
    }

}

