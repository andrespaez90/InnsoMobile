package com.innso.mobile.ui.viewModels;

import androidx.databinding.ObservableField;
import com.google.android.material.snackbar.Snackbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import com.innso.mobile.R;
import com.innso.mobile.api.controllers.CustomerController;
import com.innso.mobile.api.controllers.FinanceController;
import com.innso.mobile.api.models.cutomers.Customer;
import com.innso.mobile.managers.FirebaseStorageManager;
import com.innso.mobile.ui.textWatcher.MoneyTextWatcher;
import com.innso.mobile.ui.factories.SnackBarFactory;
import com.innso.mobile.ui.models.DatePickerModel;
import com.innso.mobile.utils.DateUtils;
import com.innso.mobile.utils.FileUtil;
import com.innso.mobile.utils.ImageUtils;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;

public class AddBillViewModel extends BaseViewModel {

    public ObservableField<String> code = new ObservableField<>("");

    public ObservableField<String> day = new ObservableField<>("");

    public ObservableField<String> month = new ObservableField<>("");

    public ObservableField<String> year = new ObservableField<>("");

    public ObservableField<File> billImage = new ObservableField<>();

    public ObservableField<String> customer = new ObservableField<>("");

    private ObservableField<String> value = new ObservableField<>("");

    private ObservableField<String> taxes = new ObservableField<>("");

    private PublishSubject<Boolean> openCamera = PublishSubject.create();

    private PublishSubject<CharSequence[]> showCustomers = PublishSubject.create();

    private CharSequence customersNames[] = new CharSequence[0];

    private File reducedFile;

    private String date = "";

    protected String urlPhotoFirebase;

    @Inject
    FinanceController financeController;

    @Inject
    CustomerController customerController;

    public AddBillViewModel() {
        getComponent().inject(this);
        customerController.getCustomers().subscribe(this::updateCustomers, this::showServiceError);
    }

    private void updateCustomers(List<Customer> customers) {
        customersNames = new CharSequence[customers.size()];
        for (int i = 0, size = customers.size(); i < size; i++) {
            customersNames[i] = customers.get(i).getName();
        }
    }

    public void onDateClick(View view) {
        showDatePicker.onNext(new DatePickerModel());
    }

    public void onCustomerSelected(int position) {
        if (position <= customersNames.length) {
            customer.set(customersNames[position].toString());
        }
    }

    public void watcherValue(Editable editable) {
        String text = editable.toString();
        if (!value.get().equals(text)) {
            value.set(getFormattedValue(text));
        }
    }

    public void watcherTaxes(Editable editable) {
        String text = editable.toString();
        if (!taxes.get().equals(text)) {
            taxes.set(getFormattedValue(text));
        }
    }

    private String getFormattedValue(String text) {
        String regex = MoneyTextWatcher.getMoneyRegexForParseToDouble("CO");
        String price = text.replaceAll(regex, "");
        price = price.replaceAll(",", ".");
        return price;
    }

    public void updateDate(String newDate) {
        if (!TextUtils.isEmpty(newDate)) {
            Calendar calendar = DateUtils.getCalendarFromString(newDate);
            date = DateUtils.parseDateToStringWithFormat(calendar.getTime(), "dd/MM/yyyy");
            day.set(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
            month.set(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
            year.set(String.valueOf(calendar.get(Calendar.YEAR)));
        }
    }

    public void onCameraClick(View view) {
        openCamera.onNext(true);
    }

    public void onCustomerClick(View view) {
        showCustomers.onNext(customersNames);
    }

    public void loadImageFile() {
        reducedFile = ImageUtils.resizeImage(FileUtil.getLastFileModifiedOnFolder(FileUtil.getPathForBillFolder()));
        billImage.set(reducedFile);
    }

    public void uploadImage(View view) {

        if (!isInformationValid()) {
            if (TextUtils.isEmpty(urlPhotoFirebase)) {
                showLoading();
                disposables.add(FirebaseStorageManager.attemptToUploadPhoto(FileUtil.BILL, reducedFile)
                        .subscribe(this::successUploadPhoto, this::showServiceError));
            }
        } else {
            showSnackBarMessage(SnackBarFactory.TYPE_INFO, R.string.error_empty_fields, Snackbar.LENGTH_LONG);
        }
    }

    private boolean isInformationValid() {
        return TextUtils.isEmpty(date) || TextUtils.isEmpty(value.get()) || reducedFile == null
                || TextUtils.isEmpty(taxes.get()) || TextUtils.isEmpty(code.get());
    }

    private void successUploadPhoto(String urlImage) {
        disposables.add(financeController.addBill(code.get(), date, customer.get(), value.get(), taxes.get(), urlImage)
                .subscribe(this::onSaveBill, this::showServiceError));
    }

    public void onSaveBill() {
        showAlertDialog.onNext(R.string.copy_bill_added);
    }

    public Observable<Boolean> onCameraOpen() {
        return openCamera.observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<CharSequence[]> onShowCustomers() {
        return showCustomers.observeOn(AndroidSchedulers.mainThread());
    }
}
