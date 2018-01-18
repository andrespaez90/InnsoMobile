package com.innso.mobile.ui.bindings;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

public class GeneralBindings {

    @BindingAdapter("load_image_file")
    public static void loadImageFile(ImageView imageView, File fileImage) {
        if (fileImage != null) {
            Glide.with(imageView.getContext()).load(fileImage).into(imageView);
        }
    }

}
