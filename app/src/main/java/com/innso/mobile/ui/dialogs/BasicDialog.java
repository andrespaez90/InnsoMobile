package com.innso.mobile.ui.dialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.innso.mobile.InnsoApplication;
import com.innso.mobile.R;
import com.innso.mobile.utils.ScreenUtilsKt;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;

public abstract class BasicDialog<T extends ViewDataBinding> extends DialogFragment {

    protected T binding;

    protected ProgressDialog progressDialog;


    @NonNull
    @Override
    @CallSuper
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = getDialogAlreadySetup();
        initDialog(dialog);
        setupWindow(dialog);
        return dialog;
    }

    protected void initDialog(Dialog dialog) {
        binding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), getLayout(), null, false);
        View view = binding.getRoot();
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dialog.setContentView(view);
        bindViews();
    }

    protected void bindViews() {
    }

    @NonNull
    @Override
    public Context getContext() {
        return super.getContext() == null ? InnsoApplication.get().getBaseContext() : super.getContext();
    }

    protected Dialog getDialogAlreadySetup() {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    protected void setupWindow(Dialog dialog) {
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.color.transparent_dark_gray);
            int screenHeight = ScreenUtilsKt.getScreenSize(getContext())[1];
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, screenHeight);
        }
    }

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

    protected abstract int getLayout();
}

