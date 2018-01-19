package com.innso.mobile.ui.viewModels;

import android.view.View;

import com.innso.mobile.ui.activities.AddBillActivity;

public class BillListViewModel extends BaseViewModel {

    public void onClick(View view){
        startActivityEvent.onNext(AddBillActivity.class);
    }
}
