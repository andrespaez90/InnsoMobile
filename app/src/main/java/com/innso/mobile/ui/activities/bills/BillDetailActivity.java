package com.innso.mobile.ui.activities.bills;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.innso.mobile.R;
import com.innso.mobile.ui.activities.BaseActivity;
import com.innso.mobile.ui.bindings.GeneralBindings;
import com.innso.mobile.ui.models.Bill;

public class BillDetailActivity extends BaseActivity {

    private Bill bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        getExtraInfo();
        initViews();
    }

    private void getExtraInfo() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            bill = bundle.getParcelable("bill");
        } else {
            finish();
        }
    }

    private void initViews() {
        enableActionBack();
        ((TextView) findViewById(R.id.textView_value)).setText(getString(R.string.format_value, bill.value));
        ((TextView) findViewById(R.id.textView_taxes)).setText(getString(R.string.format_taxes, bill.taxes));
        ((TextView) findViewById(R.id.textView_total)).setText(getString(R.string.format_total, bill.total));
        ((TextView) findViewById(R.id.textView_customer_name)).setText(bill.customer);
        ((TextView) findViewById(R.id.textView_date)).setText(bill.date);
        ImageView imageView = ((ImageView) findViewById(R.id.imageView_bill));
        GeneralBindings.loadImage(imageView, bill.imageUrl);
    }
}
