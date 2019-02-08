package com.innso.mobile.ui.activities;

import android.os.Bundle;

import com.innso.mobile.R;
import com.innso.mobile.databinding.ActivityCreateUserBinding;
import com.innso.mobile.ui.viewModels.NewUserViewModel;

import androidx.databinding.DataBindingUtil;

public class CreateUserActivity extends BaseActivity {

    private ActivityCreateUserBinding binding;

    private NewUserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_user);
        viewModel = new NewUserViewModel();
        binding.setViewModel(viewModel);
        getComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscribe();
    }

    public void subscribe() {
        disposable.add(viewModel.observableSnackBar().subscribe(event -> showMessage(event.getTypeSnackBar(), binding.getRoot(), event.getMessage())));
        disposable.add(viewModel.userCreated().subscribe(message -> showError(binding.getRoot(), message)));
    }


}
