package com.innso.mobile.ui.itemViews.news

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.innso.mobile.R
import com.innso.mobile.ui.bindings.NewaBindings
import com.innso.mobile.ui.interfaces.GenericItemView
import com.innso.mobile.ui.models.news.GenericNews

class ItemGenericNews : FrameLayout, GenericItemView<GenericNews> {

    private lateinit var binding: com.innso.mobile.databinding.ItemGenericNewsBinding

    private var news: GenericNews? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_generic_news, this, true)
        layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
    }

    override fun bind(data: GenericNews) {
        this.news = data
        binding.textViewTitle.text = data.title
        NewaBindings.loadCoverNews(binding.imageViewNewsCover, data.urlImage, data.sourceName)
    }

    override fun getData(): GenericNews? {
        return news
    }
}
