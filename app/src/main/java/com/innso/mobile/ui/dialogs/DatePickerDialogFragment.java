package com.innso.mobile.ui.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.innso.mobile.ui.models.DatePickerModel;
import com.innso.mobile.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public interface FragmentDialogListener {
        void onPickedDate(DialogFragment dialog, String date);
    }

    public static final String PICKER_DATE = "pickerDate";

    private Calendar startCalendar;

    private Calendar maxCalendar;

    private Calendar minCalendar;

    private FragmentDialogListener fragmentDialogListener;


    public static DatePickerDialogFragment newInstance(DatePickerModel datePickerModel) {
        DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
        if (!datePickerModel.isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(PICKER_DATE, datePickerModel);
            datePickerDialogFragment.setArguments(bundle);
        }
        return datePickerDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        initDate(bundle != null ? bundle.getParcelable(PICKER_DATE) : null);
    }

    private void initDate(DatePickerModel pickerModel) {
        startCalendar = pickerModel != null && pickerModel.getStartCalendar() != null ?
                pickerModel.getStartCalendar() : Calendar.getInstance();

        maxCalendar = pickerModel != null ? pickerModel.getMaxCalendar() : startCalendar;

        minCalendar = pickerModel != null ? pickerModel.getMinCalendar() : null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this,
                startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH));
        if (maxCalendar != null) {
            datePickerDialog.getDatePicker().setMaxDate(maxCalendar.getTimeInMillis());
        }
        if (minCalendar != null) {
            datePickerDialog.getDatePicker().setMinDate(minCalendar.getTimeInMillis());
        }
        return datePickerDialog;
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        if (fragmentDialogListener != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.DEFAULT_FORMAT_DATE, Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.YEAR, year);
            fragmentDialogListener.onPickedDate(DatePickerDialogFragment.this, simpleDateFormat.format(calendar.getTime()));
        }
    }

    public void setListener(FragmentDialogListener listener) {
        fragmentDialogListener = listener;
    }
}
