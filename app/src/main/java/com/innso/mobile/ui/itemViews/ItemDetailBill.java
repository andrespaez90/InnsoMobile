package com.innso.mobile.ui.itemViews;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.innso.mobile.R;
import com.innso.mobile.databinding.ItemDetailBillBinding;
import com.innso.mobile.ui.bindings.GeneralBindings;
import com.innso.mobile.ui.interfaces.GenericItemView;
import com.innso.mobile.ui.models.Bill;

public class ItemDetailBill extends FrameLayout implements GenericItemView<Bill> {

    private ItemDetailBillBinding binding;

    private Bill bill;

    public ItemDetailBill(@NonNull Context context) {
        super(context);
        init();
    }

    public ItemDetailBill(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemDetailBill(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.item_detail_bill, this, true);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void bind(Bill data) {
        this.bill = data;
        binding.textViewDate.setText(this.bill.date);
        binding.textViewTotal.setText(this.bill.total);
        GeneralBindings.loadImage(binding.imageViewBillImage, bill.imageUrl);
    }

    @Override
    public Bill getData() {
        return bill;
    }
}
