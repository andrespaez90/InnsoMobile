package com.innso.mobile.ui;

import android.app.ProgressDialog;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Pair;
import android.view.View;

import com.innso.mobile.R;
import com.innso.mobile.ui.factories.SnackBarFactory;

public class BaseFragment extends Fragment {

    protected ProgressDialog progressDialog;

    private void initProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }
    }


    public void showProgressDialog(Pair<Boolean, Integer> progressData) {
        initProgressDialog();
        if (progressData.first) {
            progressDialog.setMessage(getString(progressData.second));
            progressDialog.show();
        } else {
            progressDialog.hide();
        }
    }

    protected void removeFragmentByTag(String fragmentName) {

        Fragment fragment = getFragmentManager().findFragmentByTag(fragmentName);

        if (fragment != null) {

            getFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    protected void replaceFragment(BaseFragment fragment, int container) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();

        if (getFragmentManager().getBackStackEntryCount() >= 0) {

            ft.setCustomAnimations(fragment.getEnter(), fragment.getExit(), fragment.getPopEnter(), fragment.getPopExit());
        }

        ft.replace(container, fragment, fragment.getName());

        ft.commit();
    }

    protected void replaceFragment(BaseFragment fragment) {

        replaceFragment(fragment, fragment.getContainer());
    }

    private void showMessage(@SnackBarFactory.SnackBarType String type, @NonNull View view, String message, int duration) {
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

    public void close() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    public String getName() {
        return this.getClass().getName();
    }

    public int getEnter() {
        return 0;
    }

    public int getExit() {
        return 0;
    }

    public int getPopEnter() {
        return 0;
    }

    public int getPopExit() {
        return 0;
    }

    public int getContainer() {
        return R.id.container;
    }
}
