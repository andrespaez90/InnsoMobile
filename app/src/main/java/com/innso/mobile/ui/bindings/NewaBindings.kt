package com.innso.mobile.ui.bindings

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.innso.mobile.InnsoApplication
import com.innso.mobile.R

object NewaBindings {

    @JvmStatic
    @BindingAdapter("load_image", "source_name")
    fun loadCoverNews(imageView: ImageView, imagePath: String, sourceName: String? = null) {
        val context = InnsoApplication.get().applicationContext
        Glide.with(context)
                .load(imagePath)
                .asBitmap()
                .placeholder(getPlaceHolder(imageView.context, sourceName))
                .into(imageView)
    }

    private fun getPlaceHolder(context: Context, sourceName: String?): Int {
        return when (sourceName) {
            context.getString(R.string.source_caracol_radio) -> R.drawable.caracol_radio_placeholder
            context.getString(R.string.source_el_pulzo) -> R.drawable.pulzo_placeholder
            context.getString(R.string.source_el_tiempo) -> R.drawable.el_tiempo_placeholder
            else -> R.drawable.news_placeholder
        }
    }
}
