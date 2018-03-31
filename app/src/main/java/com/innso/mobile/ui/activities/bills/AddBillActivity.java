package com.innso.mobile.ui.activities.bills;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.innso.mobile.R;
import com.innso.mobile.databinding.ActivityAddBillBinding;
import com.innso.mobile.ui.TextWatcher.MoneyTextWatcher;
import com.innso.mobile.ui.activities.BaseActivity;
import com.innso.mobile.ui.viewModels.AddBillViewModel;
import com.innso.mobile.utils.CameraUtil;
import com.innso.mobile.utils.ErrorUtils;
import com.innso.mobile.utils.FileUtil;
import com.innso.mobile.utils.MoneyUtil;

public class AddBillActivity extends BaseActivity {

    private ActivityAddBillBinding binding;

    private AddBillViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_bill);
        viewModel = new AddBillViewModel();
        binding.setViewModel(viewModel);
        initViews();
    }

    private void initViews() {
        enableActionBack();
        binding.editTextValue.addTextChangedListener(new MoneyTextWatcher(binding.editTextValue, "CO"));
        binding.editTextTaxes.addTextChangedListener(new MoneyTextWatcher(binding.editTextTaxes, "CO"));
        binding.editTextTaxes.setHint(MoneyUtil.getBasicCurrencyPrice("CO", 0));
        binding.editTextValue.setHint(MoneyUtil.getBasicCurrencyPrice("CO", 0));
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscribe();
    }

    public void subscribe() {
        disposable.add(viewModel.observableSnackBar().subscribe(event -> showError(binding.getRoot(), event.getMessage())));
        disposable.add(viewModel.onCameraOpen().subscribe(o -> requestStoragePermissions(), event -> showError(binding.getRoot(), ErrorUtils.getMessageError(event))));
        disposable.add(viewModel.onShowCustomers().subscribe(this::showCustomersDialog));
        disposable.add(viewModel.onDatePickerClick().subscribe(this::showDatePicker));
        disposable.add(viewModel.showAlertDialog().subscribe(this::showAlertDialog));
    }

    @Override
    public void onDateSelected(String date) {
        viewModel.updateDate(date);
    }

    @Override
    protected void successStoragePermission() {
        super.successStoragePermission();
        CameraUtil.openCamera(this, FileUtil.getPathForBillFolder(), REQUEST_IMAGE_CAPTURE);
    }

    private void showCustomersDialog(CharSequence names[]) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona un Cliente");
        builder.setItems(names, (dialog, which) -> viewModel.onCustomerSelected(which));
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                viewModel.loadImageFile();
            } else {
                showError(binding.getRoot(), getString(R.string.error_not_load_image));
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

}
