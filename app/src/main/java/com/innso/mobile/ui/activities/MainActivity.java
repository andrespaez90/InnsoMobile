package com.innso.mobile.ui.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.innso.mobile.R;
import com.innso.mobile.databinding.ActivityMainBinding;
import com.innso.mobile.ui.BaseFragment;
import com.innso.mobile.ui.fragments.CompanyFragment;
import com.innso.mobile.ui.helpers.NavigationViewHelper;
import com.innso.mobile.ui.itemViews.ItemDetailMonth;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

    private BaseFragment lastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView();
        initListeners();
    }

    private void initView() {
        NavigationViewHelper.disableShiftMode(binding.navigationView);
        createMonthDetails();
    }

    private void initListeners() {
        binding.navigationView.setOnNavigationItemSelectedListener(this);

    }

    private void createMonthDetails() {
        for (int i = 0, limit = 12; i < limit; i++) {
            binding.layoutDetailContainer.addView(new ItemDetailMonth(getBaseContext()));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_company:
                replaceFragment(new CompanyFragment());
                break;
            case R.id.action_main:
                binding.container.removeAllViews();
                break;

        }
        return true;
    }
}

