package com.innso.mobile.ui.itemViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout

import com.innso.mobile.R
import com.innso.mobile.databinding.ItemGenericFinanceDetailBinding
import com.innso.mobile.ui.bindings.GeneralBindings
import com.innso.mobile.ui.interfaces.GenericItemView
import com.innso.mobile.ui.models.Bill
import androidx.databinding.DataBindingUtil

class ItemDetailBill : FrameLayout, GenericItemView<Bill> {

    private lateinit var binding: ItemGenericFinanceDetailBinding

    private lateinit var bill: Bill

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

    override fun bind(data: Bill) {
        this.bill = data
        binding.textViewDate.text = this.bill.date
        binding.textViewTotal.text = this.bill.total
        binding.textViewCustomer.text = this.bill.customer
        GeneralBindings.loadImage(binding.imageViewBillImage, bill.imageUrl)
    }

    override fun getData(): Bill? {
        return bill
    }
}
