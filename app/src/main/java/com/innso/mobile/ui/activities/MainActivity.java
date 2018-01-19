package com.innso.mobile.ui.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;

import com.innso.mobile.R;
import com.innso.mobile.databinding.ActivityMainBinding;
import com.innso.mobile.ui.fragments.BillListFragment;
import com.innso.mobile.ui.fragments.CompanyFragment;
import com.innso.mobile.ui.fragments.ProfileFragment;
import com.innso.mobile.ui.helpers.NavigationViewHelper;
import com.innso.mobile.ui.itemViews.ItemDetailMonth;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

    private BillListFragment billListFragment;

    private CompanyFragment companyFragment;

    private ProfileFragment profileFragment;

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
                replaceFragment(getCompanyFragment());
                break;
            case R.id.action_profile:
                replaceFragment(getProfileFragment());
                break;
            case R.id.action_bills:
                replaceFragment(getBillListFragment());
                break;
            case R.id.action_main:
                binding.container.setVisibility(View.GONE);
                break;

        }
        return true;
    }

    private CompanyFragment getCompanyFragment() {
        if (companyFragment == null) {
            companyFragment = new CompanyFragment();
        }
        return companyFragment;
    }

    private ProfileFragment getProfileFragment() {
        if (profileFragment == null) {
            profileFragment = new ProfileFragment();
        }
        return profileFragment;
    }

    private BillListFragment getBillListFragment() {
        if (billListFragment == null) {
            billListFragment = new BillListFragment();
        }
        return billListFragment;
    }

}

