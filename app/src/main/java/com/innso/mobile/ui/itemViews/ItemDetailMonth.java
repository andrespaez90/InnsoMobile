package com.innso.mobile.ui.itemViews;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.innso.mobile.R;
import com.innso.mobile.databinding.ItemDetailMonthBinding;
import com.innso.mobile.ui.models.ItemDetailModel;

public class ItemDetailMonth extends FrameLayout {

    private ItemDetailMonthBinding binding;

    public ItemDetailMonth(@NonNull Context context, ItemDetailModel item) {
        super(context);
        init(item);
    }

    public ItemDetailMonth(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemDetailMonth(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(ItemDetailModel item) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.item_detail_month, this, true);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        binding.textViewMonth.setText(item.title);
        binding.textViewAmount.setText(item.value);
    }

}
