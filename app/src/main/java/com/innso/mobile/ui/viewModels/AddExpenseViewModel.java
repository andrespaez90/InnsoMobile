package com.innso.mobile.ui.viewModels;

import androidx.databinding.ObservableField;
import com.google.android.material.snackbar.Snackbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import com.innso.mobile.R;
import com.innso.mobile.api.controllers.FinanceController;
import com.innso.mobile.managers.FirebaseStorageManager;
import com.innso.mobile.ui.textWatcher.MoneyTextWatcher;
import com.innso.mobile.ui.factories.SnackBarFactory;
import com.innso.mobile.ui.models.DatePickerModel;
import com.innso.mobile.utils.DateUtils;
import com.innso.mobile.utils.FileUtil;
import com.innso.mobile.utils.ImageUtils;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;

public class AddExpenseViewModel extends BaseViewModel {

    public ObservableField<String> day = new ObservableField<>("");

    public ObservableField<String> month = new ObservableField<>("");

    public ObservableField<String> year = new ObservableField<>("");

    public ObservableField<File> billImage = new ObservableField<>();

    public ObservableField<String> expenseConcept = new ObservableField<>("");

    private ObservableField<String> value = new ObservableField<>("");

    private ObservableField<String> taxes = new ObservableField<>("");

    private PublishSubject<Boolean> openCamera = PublishSubject.create();

    private PublishSubject<Boolean> showCustomers = PublishSubject.create();

    private File reducedFile;

    private String date = "";

    protected String urlPhotoFirebase;

    @Inject
    FinanceController financeController;

    public AddExpenseViewModel() {
        getComponent().inject(this);
    }

    public void onDateClick(View view) {
        showDatePicker.onNext(new DatePickerModel());
    }

    public void onExpenseConceptSelected(String concept) {
        expenseConcept.set(concept);
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

    public void loadImageFile() {
        reducedFile = ImageUtils.resizeImage(FileUtil.getLastFileModifiedOnFolder(FileUtil.getPathForExpensesFolder()));
        billImage.set(reducedFile);
    }

    public void uploadImage(View view) {

        if (!isInformationValid()) {
            if (TextUtils.isEmpty(urlPhotoFirebase)) {
                showLoading();
                disposables.add(FirebaseStorageManager.attemptToUploadPhoto(FileUtil.EXPENSE, reducedFile)
                        .subscribe(this::successUploadPhoto, this::showServiceError));
            }
        } else {
            showSnackBarMessage(SnackBarFactory.TYPE_INFO, R.string.error_empty_fields, Snackbar.LENGTH_LONG);
        }
    }

    private boolean isInformationValid() {
        return TextUtils.isEmpty(date) || TextUtils.isEmpty(value.get()) || reducedFile == null
                || TextUtils.isEmpty(taxes.get());
    }

    private void successUploadPhoto(String urlImage) {
        disposables.add(financeController.addExpene(date, expenseConcept.get(), value.get(), taxes.get(), urlImage)
                .subscribe(this::onSaveBill, this::showServiceError));
    }

    public void onSaveBill() {
        showAlertDialog.onNext(R.string.copy_expense_added);
    }

    public void onCameraClick(View view) {
        openCamera.onNext(true);
    }

    public void onConceptClick(View view) {
        showCustomers.onNext(true);
    }

    public Observable<Boolean> onCameraOpen() {
        return openCamera.observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> onShowCustomers() {
        return showCustomers.observeOn(AndroidSchedulers.mainThread());
    }
}
