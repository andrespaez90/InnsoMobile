package com.innso.mobile.ui.itemViews;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.innso.mobile.R;
import com.innso.mobile.databinding.ItemGenericFinanceDetailBinding;
import com.innso.mobile.ui.bindings.GeneralBindings;
import com.innso.mobile.ui.interfaces.GenericItemView;
import com.innso.mobile.ui.models.Expense;

public class ItemDetailExpense extends FrameLayout implements GenericItemView<Expense> {

    private ItemGenericFinanceDetailBinding binding;

    private Expense expense;

    public ItemDetailExpense(@NonNull Context context) {
        super(context);
        init();
    }

    public ItemDetailExpense(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemDetailExpense(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.item_generic_finance_detail, this, true);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void bind(Expense data) {
        this.expense = data;
        binding.textViewDate.setText(this.expense.date);
        binding.textViewTotal.setText(this.expense.total);
        binding.textViewCustomer.setText(this.expense.customer);
        GeneralBindings.loadImage(binding.imageViewBillImage, expense.imageUrl);
    }

    @Override
    public Expense getData() {
        return expense;
    }
}
