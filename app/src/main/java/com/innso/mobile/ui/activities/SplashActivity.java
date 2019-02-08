package com.innso.mobile.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.messaging.FirebaseMessaging;
import com.innso.mobile.R;
import com.innso.mobile.databinding.ActivitySplashBinding;
import com.innso.mobile.viewModels.onBoarding.SplashViewModel;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;

public class SplashActivity extends BaseActivity {

    private SplashViewModel splashViewModel;

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseMessaging.getInstance().subscribeToTopic("News");
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
        splashViewModel.startActivity().observe(this, this::startActivity);
        splashViewModel.snackBarMessage().observe(this, event -> showError(binding.getRoot(), event.getMessage()));
        splashViewModel.updateVersionEvent().observe(this, this::showForceUpdateDialog);
        splashViewModel.showLoader().observe(this, this::showLoaders);
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

    protected ActivityOptionsCompat getSceneTransition(Class clazz) {
        return ActivityOptionsCompat.makeSceneTransitionAnimation(this, binding.imageViewLogo, getString(R.string.app_name));
    }
}
