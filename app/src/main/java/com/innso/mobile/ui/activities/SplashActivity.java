package com.innso.mobile.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.innso.mobile.R;
import com.innso.mobile.databinding.ActivitySplashBinding;
import com.innso.mobile.ui.viewModels.SplashViewModel;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity {

    private SplashViewModel splashViewModel;

    private ActivitySplashBinding binding;

    @Inject
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        this.splashViewModel = new SplashViewModel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscribe();
        splashViewModel.validateAuthentication();
    }

    private void subscribe() {
        disposable.add(splashViewModel.observableSnackBar().subscribe(event -> showError(binding.getRoot(), event.getMessage())));
        disposable.add(splashViewModel.observableStartActivity().subscribe(this::startActivity));
    }

    @Override
    protected void startActivity(Class clazz){
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, binding.imageViewLogo, getString(R.string.app_name));
        startActivity(new Intent(this, clazz), options.toBundle());
    }

}
