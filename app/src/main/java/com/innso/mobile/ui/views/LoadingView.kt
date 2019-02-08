package com.innso.mobile.ui.views


import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.innso.mobile.R

class LoadingView : RelativeLayout {

    lateinit var progressBar: ProgressBar
        private set

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        val view = View.inflate(context, R.layout.view_base_progress, this)
        progressBar = view.findViewById(R.id.progressBar)
        setBackgroundColor(ContextCompat.getColor(context, R.color.white_transparent))
        isClickable = true
    }

    fun showProgressBar() {
        this.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        this.visibility = View.GONE
        progressBar.visibility = View.GONE
    }
}
