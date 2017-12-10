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

public class ItemDetailMonth extends FrameLayout {

    private ItemDetailMonthBinding binding;

    public ItemDetailMonth(@NonNull Context context) {
        super(context);
        init();
    }

    public ItemDetailMonth(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemDetailMonth(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.item_detail_month, this, true);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

}
