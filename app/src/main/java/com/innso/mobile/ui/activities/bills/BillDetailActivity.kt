package com.innso.mobile.ui.activities.bills

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

import com.innso.mobile.R
import com.innso.mobile.ui.activities.BaseActivity
import com.innso.mobile.ui.bindings.GeneralBindings
import com.innso.mobile.ui.models.Bill

class BillDetailActivity : BaseActivity() {

    private lateinit var bill: Bill

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_detail)
        getExtraInfo()
        initViews()
    }

    private fun getExtraInfo() {
        val bundle = intent.extras
        if (bundle != null) {
            bill = bundle.getParcelable("bill")
        } else {
            finish()
        }
    }

    private fun initViews() {
        enableActionBack()
        findViewById<TextView>(R.id.textView_value).text = getString(R.string.format_value, bill.value)
        findViewById<TextView>(R.id.textView_taxes).text = getString(R.string.format_taxes, bill!!.taxes)
        findViewById<TextView>(R.id.textView_total).text = getString(R.string.format_total, bill.total)
        findViewById<TextView>(R.id.textView_customer_name).text = bill.customer
        findViewById<TextView>(R.id.textView_date).text = bill.date
        val imageView = findViewById<ImageView>(R.id.imageView_bill)
        GeneralBindings.loadImage(imageView, bill.imageUrl)
    }
}
