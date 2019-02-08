package com.innso.mobile.ui.activities;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import com.innso.mobile.R;
import com.innso.mobile.databinding.ActivityLoginBinding;
import com.innso.mobile.ui.viewModels.LoginViewModel;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        viewModel = new LoginViewModel();
        binding.setViewModel(viewModel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscribe();
    }

    public void subscribe(){
        disposable.add(viewModel.observableSnackBar().subscribe(event -> showError(binding.getRoot(), event.getMessage())));
        disposable.add(viewModel.observableStartActivity().subscribe(this::startActivity));
    }
}
