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
import com.innso.mobile.ui.models.ItemDetailModel;
import com.innso.mobile.ui.viewModels.FinanceViewModel;
import com.innso.mobile.utils.DateUtils;
import com.innso.mobile.utils.MoneyUtil;

import java.util.List;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

    private BillListFragment billListFragment;

    private CompanyFragment companyFragment;

    private ProfileFragment profileFragment;

    private FinanceViewModel financeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        financeViewModel = new FinanceViewModel();
        financeViewModel.getSummary();
        initView();
        initListeners();
        binding.setViewModel(financeViewModel);
    }

    private void initView() {
        NavigationViewHelper.disableShiftMode(binding.navigationView);
    }

    private void initListeners() {
        binding.navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscribe();
    }

    private void subscribe() {
        disposable.add(financeViewModel.onRevenueUpdated().subscribe(this::updateInformation));
        disposable.add(financeViewModel.onTotalRevenueUpdated().subscribe(this::updateTotalRevenue));
    }

    private void updateTotalRevenue(Double totalRevenue) {
        binding.textViewTotalRevenue.setText(getString(R.string.accounting_revenue,
                MoneyUtil.getBasicCurrencyPrice("CO", totalRevenue)));
    }

    private void updateInformation(List<Double> values) {
        binding.cardViewChartSales.addChartInformation(values);
        binding.layoutDetailContainer.removeAllViews();
        for (int i = 0, limit = values.size(); i < limit; i++) {
            binding.layoutDetailContainer.addView(new ItemDetailMonth(getBaseContext(),
                    new ItemDetailModel(DateUtils.getMonth(i), MoneyUtil.getBasicCurrencyPrice("CO", values.get(i)))));
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

