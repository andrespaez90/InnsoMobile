package com.innso.mobile.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.innso.mobile.InnsoApplication;
import com.innso.mobile.R;
import com.innso.mobile.di.components.ActivityComponent;
import com.innso.mobile.di.components.DaggerActivityComponent;
import com.innso.mobile.ui.BaseFragment;
import com.innso.mobile.ui.dialogs.BasicDialog;
import com.innso.mobile.ui.dialogs.DatePickerDialogFragment;
import com.innso.mobile.ui.factories.SnackBarFactory;
import com.innso.mobile.ui.models.DatePickerModel;
import com.innso.mobile.ui.views.LoadingView;
import com.innso.mobile.utils.FileUtil;
import com.innso.mobile.viewModels.AndroidViewModel;
import com.innso.mobile.viewModels.models.FinishActivityModel;
import com.innso.mobile.viewModels.models.StartActionModel;
import com.innso.mobile.viewModels.models.StartActivityModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.CallSuper;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.reactivex.disposables.CompositeDisposable;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class BaseActivity extends AppCompatActivity {

    protected final int REQUEST_IMAGE_CAPTURE = 1001;

    private List<BaseFragment> pendingForClose = new ArrayList<>();

    private List<BaseFragment> pendingForOpen = new ArrayList<>();

    protected CompositeDisposable disposable;

    private FragmentManager fragmentManager;

    protected ProgressDialog progressDialog;

    protected LoadingView loadingView;

    private boolean paused;

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fragmentManager = this.getSupportFragmentManager();
        this.disposable = new CompositeDisposable();
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

    protected void enableActionBack() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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

    protected void subscribeViewModel(AndroidViewModel viewModel, View root) {
        viewModel.loaderState().observe(this, this::showLoading);
        viewModel.startActivity().observe(this, this::startActivity);
        viewModel.closeView().observe(this, this::close);
        viewModel.showDialog().observe(this, this::showDialog);
        viewModel.snackBarMessage().observe(this, (event -> showMessage(event.getTypeSnackBar(), root, event.getMessage(), Snackbar.LENGTH_SHORT)));
    }

    public void showLoading(Boolean showing) {
        initLoading();
        if (showing) {
            loadingView.showProgressBar();
        } else {
            loadingView.hideProgressBar();
        }
    }

    private void initLoading() {
        if (loadingView == null) {
            loadingView = new LoadingView(this);
            this.addContentView(loadingView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    protected final void startActivity(StartActivityModel startActivityModel) {
        Intent intent = new Intent(getBaseContext(), startActivityModel.getActivity());
        if (startActivityModel.getBundle() != null) {
            intent.putExtras(startActivityModel.getBundle());
        }
        Bundle bundle = new Bundle();
        ActivityOptionsCompat activityOptions = getSceneTransition(startActivityModel.getActivity());
        if (activityOptions != null) {
            bundle = activityOptions.toBundle();
        }
        startActivityForResult(intent, startActivityModel.getCode(), bundle);
    }

    @Nullable
    protected ActivityOptionsCompat getSceneTransition(Class clazz) {
        return null;
    }

    protected final void close(FinishActivityModel finishActivityModel) {
        if (finishActivityModel.getIntent() != null) {
            setResult(finishActivityModel.getCode(), finishActivityModel.getIntent());
        } else {
            setResult(finishActivityModel.getCode());
        }
        finish();
    }

    protected final void showDialog(BasicDialog dialog) {
        dialog.show(getSupportFragmentManager(), dialog.getClass().getName());
    }

    protected final void startAction(StartActionModel startActionModel) {
        Intent intent = new Intent(startActionModel.getAction());
        if (startActionModel.getBundle() != null) {
            intent.putExtras(startActionModel.getBundle());
        }
        startActivityForResult(intent, startActionModel.getCode());
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

            findViewById(fragment.getContainer()).setVisibility(View.VISIBLE);

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

    @NeedsPermission({"android.permission.WRITE_EXTERNAL_STORAGE"})
    @CallSuper
    protected void successStoragePermission() {
        FileUtil.generateBasicFolders(getString(R.string.app_name));
    }

    public void requestStoragePermissions() {
        BaseActivityPermissionsDispatcher.successStoragePermissionWithPermissionCheck(this);
    }

    public void showDatePicker(DatePickerModel model) {
        DatePickerDialogFragment datePicker = DatePickerDialogFragment.newInstance(model);
        datePicker.setListener((dialog, date) -> onDateSelected(date));
        datePicker.show(getSupportFragmentManager(), DatePickerDialogFragment.class.getSimpleName());
    }

    public void onDateSelected(String date) {

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

    public void showAlertDialog(int message) {
        showAlertDialog(R.string.app_name, message);
    }

    public void showAlertDialog(int title, int message) {

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    finish();
                })
                .setIcon(R.drawable.innso_logo)
                .show();
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
