package com.innso.mobile.ui.viewModels;

import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import com.innso.mobile.managers.FirebaseStorageManager;
import com.innso.mobile.ui.TextWatcher.MoneyTextWatcher;
import com.innso.mobile.ui.factories.SnackBarFactory;
import com.innso.mobile.ui.models.DatePickerModel;
import com.innso.mobile.utils.DateUtils;
import com.innso.mobile.utils.FileUtil;
import com.innso.mobile.utils.ImageUtils;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;

public class AddBillViewModel extends BaseViewModel {

    public ObservableField<String> code = new ObservableField<>("");

    public ObservableField<String> day = new ObservableField<String>("");

    public ObservableField<String> month = new ObservableField<String>("");

    public ObservableField<String> year = new ObservableField<String>("");

    public ObservableField<File> billImage = new ObservableField<>();

    private ObservableField<String> value = new ObservableField<>("");

    private ObservableField<String> taxes = new ObservableField<>("");

    private PublishSubject<Boolean> openCamera = PublishSubject.create();

    private File reducedFile;

    private String date = "";

    protected String urlPhotoFirebase;

    public void onDateClick(View view) {
        showDatePicker.onNext(new DatePickerModel());
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

    public void updateDate(String date) {
        if (!TextUtils.isEmpty(date)) {
            Calendar calendar = DateUtils.getCalendarFromString(date);
            date = DateUtils.parseDateToStringWithFormat(calendar.getTime(), "dd/MM/yyyy");
            day.set(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
            month.set(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
            year.set(String.valueOf(calendar.get(Calendar.YEAR)));
        }
    }

    public void onCameraClick(View view) {
        openCamera.onNext(true);
    }

    public void loadImageFile() {
        reducedFile = ImageUtils.resizeImage(FileUtil.getLastFileModifiedOnFolder(FileUtil.getPathForBillFolder()));
        billImage.set(reducedFile);
    }

    public void uploadImage(View view) {

        if (TextUtils.isEmpty(urlPhotoFirebase)) {
            showLoading();
            disposables.add(FirebaseStorageManager.attemptToUploadPhoto(FileUtil.BILL, reducedFile)
                    .subscribe(o -> showSnackBarMessage(SnackBarFactory.TYPE_INFO, "subido", 100), this::showServiceError));
        }
    }

    public Observable<Boolean> onCameraOpen() {
        return openCamera.observeOn(AndroidSchedulers.mainThread());
    }
}
