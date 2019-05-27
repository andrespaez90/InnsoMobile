package com.innso.mobile.ui.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.innso.mobile.InnsoApplication
import com.innso.mobile.R
import java.io.File

object GeneralBindings {

    @JvmStatic
    @BindingAdapter("load_image", "place_holder")
    fun loadImage(imageView: ImageView, imagePath: String, placeHolder: Int? = null) {
        val context = InnsoApplication.get().applicationContext
        val options: RequestOptions = RequestOptions()
                .placeholder(placeHolder ?: R.mipmap.ic_launcher)
                .error(placeHolder ?: R.mipmap.ic_launcher)

        Glide.with(context).asBitmap()
                .load(imagePath)
                .apply(options)
                .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("load_image_file")
    fun loadImageFile(imageView: ImageView, fileImage: File?) {
        if (fileImage != null) {
            Glide.with(imageView.context).load(fileImage).into(imageView)
        }
    }

}
