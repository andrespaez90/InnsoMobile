package com.innso.mobile.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;

import com.innso.mobile.InnsoApplication;
import com.innso.mobile.di.components.ActivityComponent;
import com.innso.mobile.di.components.DaggerActivityComponent;
import com.innso.mobile.ui.BaseFragment;
import com.innso.mobile.ui.factories.SnackBarFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class BaseActivity extends AppCompatActivity {

    private List<BaseFragment> pendingForClose = new ArrayList<>();

    private List<BaseFragment> pendingForOpen = new ArrayList<>();

    protected CompositeDisposable disposable;

    private FragmentManager fragmentManager;

    protected ProgressDialog progressDialog;

    //protected Loading loadingBar;

    private boolean paused;

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disposable = new CompositeDisposable();
    }

    public ActivityComponent getComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(InnsoApplication.get().getAppComponent())
                .build();
    }

    public void setupActionBar(Toolbar toolbar) {
        setupActionBar(toolbar, true);
    }

    protected void setupActionBar(Toolbar toolbar, boolean showButtonBack) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(showButtonBack);
            actionBar.setDisplayShowHomeEnabled(showButtonBack);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        disposable.clear();
        paused = true;
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        paused = false;
        checkPendingView();
    }

    protected void startActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    protected boolean isLoading() {
        return (progressDialog != null && progressDialog.isShowing());
    }

    private void checkPendingView() {

        for (BaseFragment fragmentView : pendingForOpen) {
            addFragment(fragmentView, true);
        }

        pendingForOpen.clear();

        pendingForOpen = new ArrayList<>();

        for (BaseFragment fragmentView : pendingForClose) {
            onClose(fragmentView);
        }

        pendingForClose.clear();

        pendingForClose = new ArrayList<>();
    }

    protected void addFragment(BaseFragment fragment, boolean addToStack) {
        addFragment(fragment, fragment.getContainer(), addToStack);
    }

    protected void addFragment(BaseFragment fragment, int containerViewId, boolean addToStack) {
        if (paused || isFinishing()) {

            pendingForOpen.add(fragment);

        } else {

            FragmentTransaction ft = fragmentManager.beginTransaction();

            if (fragmentManager.getBackStackEntryCount() >= 0) {

                ft.setCustomAnimations(fragment.getEnter(), fragment.getExit(), fragment.getPopEnter(), fragment.getPopExit());
            }

            ft.add(containerViewId, fragment, fragment.getName());

            if (addToStack) {

                ft.addToBackStack(fragment.getName());
            }

            ft.commit();
        }
    }

    protected void addFragment(int containerViewId, BaseFragment fragment, List<Pair<View, String>> sharedElements, boolean addToBackStack) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (sharedElements != null) {

            for (Pair<View, String> pair : sharedElements) {

                fragmentTransaction.addSharedElement(pair.first, pair.second);
            }
        }

        if (addToBackStack) {

            fragmentTransaction.addToBackStack(fragment.getTag());
        }

        fragmentTransaction.add(containerViewId, fragment, fragment.getTag()).commit();
    }

    protected void replaceFragment(BaseFragment fragment) {
        if (paused || isFinishing()) {

            pendingForOpen.add(fragment);

        } else {

            FragmentTransaction ft = fragmentManager.beginTransaction();

            if (fragmentManager.getBackStackEntryCount() >= 0) {

                ft.setCustomAnimations(fragment.getEnter(), fragment.getExit(), fragment.getPopEnter(), fragment.getPopExit());
            }

            ft.replace(fragment.getContainer(), fragment, fragment.getName());

            ft.commit();
        }
    }

    public void onClose(BaseFragment view) {
        if (!paused && !isFinishing()) {

            if (getFragmentManager().getBackStackEntryCount() < 1) {
                finish();
            }
            fragmentManager.popBackStack(view.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);


        } else {
            pendingForClose.add(view);
        }
    }

    public void clearBackStack() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private void initProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }
    }

    //private void initLoading() {
    //if (loadingBar == null) {
    //   loadingBar = new Loading(this);
    //     this.addContentView(loadingBar, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    //   }
    // }

    public void showProgressDialog(@StringRes int message) {
        if (message != 0) {
            initProgressDialog();
            progressDialog.setMessage(getString(message));
            try {
                progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showProgressDialog(String message) {
        initProgressDialog();
        progressDialog.setMessage(message);
        try {
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showProgressDialog(Pair<Boolean, Integer> progressData) {
        initProgressDialog();
        if (progressData.first) {
            progressDialog.setMessage(getString(progressData.second));
            progressDialog.show();
        } else {
            progressDialog.hide();
            progressDialog.dismiss();
        }
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void showMessage(@SnackBarFactory.SnackBarType String type, @NonNull View view, String message) {
        SnackBarFactory.getSnackBar(type, view, message, Snackbar.LENGTH_LONG).show();
    }

    public void showMessage(@SnackBarFactory.SnackBarType String type, @NonNull View view, String message, int duration) {
        SnackBarFactory.getSnackBar(type, view, message, duration).show();
    }

    public void showMessage(@ColorInt int color, @NonNull View view, String message, int duration) {
        SnackBarFactory.getSnackBar(color, view, message, duration).show();
    }

    public void showError(@NonNull View view, String message) {
        showMessage(SnackBarFactory.TYPE_ERROR, view, message, Snackbar.LENGTH_LONG);
    }

    public void showInfoMessage(View view, String message) {
        showMessage(SnackBarFactory.TYPE_INFO, view, message, Snackbar.LENGTH_LONG);
    }


}
