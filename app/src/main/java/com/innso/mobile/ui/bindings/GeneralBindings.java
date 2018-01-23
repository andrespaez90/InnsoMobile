package com.innso.mobile.ui.bindings;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.innso.mobile.InnsoApplication;
import com.innso.mobile.R;

import java.io.File;

public class GeneralBindings {

    @BindingAdapter("load_image")
    public static void loadImage(ImageView imageView, String imagePath) {
        Context context = InnsoApplication.get().getApplicationContext();
        Glide.with(context)
                .load(imagePath)
                .asBitmap()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    @BindingAdapter("load_image_file")
    public static void loadImageFile(ImageView imageView, File fileImage) {
        if (fileImage != null) {
            Glide.with(imageView.getContext()).load(fileImage).into(imageView);
        }
    }

}
