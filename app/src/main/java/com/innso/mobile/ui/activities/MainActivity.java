package com.innso.mobile.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.innso.mobile.R;
import com.innso.mobile.databinding.ActivityMainBinding;
import com.innso.mobile.ui.fragments.CompanyFragment;
import com.innso.mobile.ui.fragments.ProfileFragment;
import com.innso.mobile.ui.itemViews.ItemDetailMonth;
import com.innso.mobile.ui.models.ItemDetailModel;
import com.innso.mobile.ui.viewModels.FinanceViewModel;
import com.innso.mobile.utils.DateUtils;
import com.innso.mobile.utils.MoneyUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

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
        binding.loaderCash.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
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
        disposable.addAll(financeViewModel.onRevenueUpdated().subscribe(this::updateRevenueInformation),
                financeViewModel.onExpensesUpdated().subscribe(this::updateExpenseInformation),
                financeViewModel.onTotalMonthUpdated().subscribe(this::updateTotalValueMonth),
                financeViewModel.onTotalRevenueUpdated().subscribe(this::updateRevenueValue),
                financeViewModel.onTotalExpenditureUpdated().subscribe(this::updateExpenditureValue),
                financeViewModel.onTotalBanksUpdated().subscribe(value -> updateValue(binding.textViewBankSummaryValue, value)),
                financeViewModel.onTotalCashUpdated().subscribe(value -> updateValue(binding.textViewSummaryCashValue, value)),
                financeViewModel.observableShowLoading().subscribe(this::showLoaders),
                financeViewModel.startActivityEvent().subscribe(this::startActivity));
    }

    private void updateValue(TextView view, Double value) {
        view.setText(MoneyUtil.getBasicCurrencyPrice("CO", value));
    }

    private void updateRevenueValue(Double totalRevenue) {
        updateViewValueWithFormat(binding.textViewTotalRevenue, totalRevenue, R.string.accounting_revenue);
    }

    private void updateExpenditureValue(Double totalExpenditure) {
        updateViewValueWithFormat(binding.textViewTotalExpenditure, totalExpenditure, R.string.accounting_expenditure);
    }

    private void updateViewValueWithFormat(TextView view, Double value, int stringFormat) {
        view.setText(getString(stringFormat, MoneyUtil.getBasicCurrencyPrice("CO", value)));
    }

    private void showLoaders(boolean showLoader) {
        if (showLoader)
            binding.cardViewChartSales.clearInformation();

        int loaderVisibility = showLoader ? View.VISIBLE : View.INVISIBLE;
        int textVisibility = showLoader ? View.INVISIBLE : View.VISIBLE;

        binding.loaderBank.setVisibility(loaderVisibility);
        binding.loaderCash.setVisibility(loaderVisibility);

        binding.textViewSummaryCashValue.setVisibility(textVisibility);
        binding.textViewBankSummaryValue.setVisibility(textVisibility);
        binding.textViewTotalExpenditure.setVisibility(textVisibility);
        binding.textViewTotalRevenue.setVisibility(textVisibility);

    }

    private void updateRevenueInformation(List<Double> values) {
        binding.cardViewChartSales.addChartInformation(values,
                ContextCompat.getColor(getBaseContext(), R.color.colorPrimaryDark),
                ContextCompat.getColor(getBaseContext(), R.color.colorPrimaryDark));
    }

    private void updateExpenseInformation(List<Double> values) {
        binding.cardViewChartSales.addChartInformation(values,
                ContextCompat.getColor(getBaseContext(), android.R.color.holo_red_dark),
                ContextCompat.getColor(getBaseContext(), android.R.color.holo_red_dark));
    }

    private void updateTotalValueMonth(List<Double> values) {
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
}

