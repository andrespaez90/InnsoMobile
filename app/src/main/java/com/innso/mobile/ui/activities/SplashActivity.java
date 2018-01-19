package com.innso.mobile.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;

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
        binding.setViewModel(splashViewModel);
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
        disposable.add(splashViewModel.updateVersionEvent().subscribe(this::showForceUpdateDialog));
        disposable.add(splashViewModel.showLoader().subscribe(this::showLoaders));
    }

    private void showLoaders(Boolean show) {
        if (show) {
            binding.progressbar.setVisibility(View.VISIBLE);
            binding.textViewSplashText.setVisibility(View.VISIBLE);
            binding.buttonRetry.setVisibility(View.GONE);
        } else {
            binding.progressbar.setVisibility(View.INVISIBLE);
            binding.textViewSplashText.setVisibility(View.INVISIBLE);
            binding.buttonRetry.setVisibility(View.VISIBLE);
        }
    }

    private void showForceUpdateDialog(Boolean update) {
        if (update) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.splash_new_version)
                    .setMessage(R.string.splash_get_latest_version)
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        startActivity(new Intent(Intent.ACTION_VIEW)
                                .setData(Uri.parse(getString(R.string.url_play_store))));
                        finish();
                    })
                    .setIcon(R.mipmap.ic_launcher)
                    .setCancelable(false)
                    .show();
        }
    }

    @Override
    protected void startActivity(Class clazz) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, binding.imageViewLogo, getString(R.string.app_name));
        startActivity(new Intent(this, clazz), options.toBundle());
    }

}
