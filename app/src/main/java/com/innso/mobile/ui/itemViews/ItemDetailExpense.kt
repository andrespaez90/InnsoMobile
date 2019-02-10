package com.innso.mobile.ui.itemViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout

import com.innso.mobile.R
import com.innso.mobile.databinding.ItemGenericFinanceDetailBinding
import com.innso.mobile.ui.bindings.GeneralBindings
import com.innso.mobile.ui.interfaces.GenericItemView
import com.innso.mobile.ui.models.Expense
import androidx.databinding.DataBindingUtil

class ItemDetailExpense : FrameLayout, GenericItemView<Expense> {

    private lateinit var binding: ItemGenericFinanceDetailBinding

    private lateinit var expense: Expense

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_generic_finance_detail, this, true)
        layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
    }

    override fun bind(data: Expense) {
        this.expense = data
        binding.textViewDate.text = this.expense.date
        binding.textViewTotal.text = this.expense.total
        binding.textViewCustomer.text = this.expense.customer
        GeneralBindings.loadImage(binding.imageViewBillImage, expense.imageUrl)
    }

    override fun getData(): Expense? {
        return expense
    }
}
