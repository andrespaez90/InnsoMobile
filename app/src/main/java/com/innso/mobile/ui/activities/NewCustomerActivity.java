package com.innso.mobile.ui.activities;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import com.innso.mobile.R;
import com.innso.mobile.databinding.ActivityNewCustomerBinding;
import com.innso.mobile.ui.factories.SnackBarFactory;
import com.innso.mobile.ui.viewModels.NewCustomerViewModel;

public class NewCustomerActivity extends BaseActivity {

    private ActivityNewCustomerBinding binding;

    private NewCustomerViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_customer);
        viewModel = new NewCustomerViewModel();
        binding.setViewModel(viewModel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscribe();
    }

    public void subscribe() {
        disposable.add(viewModel.observableSnackBar().subscribe(event -> showMessage(event.getTypeSnackBar(), binding.getRoot(), event.getMessage())));
        disposable.add(viewModel.onCustomerCreated().subscribe(message -> showMessage(SnackBarFactory.TYPE_INFO, binding.getRoot(), message)));
    }


}
