package com.innso.mobile.ui.bindings

import android.graphics.Typeface
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.innso.mobile.utils.StringUtils

object ProfileBindings {

    @JvmStatic
    @BindingAdapter("user_info")
    fun userInfo(textView: TextView, userInfo: String?) {
        userInfo?.let {
            textView.text = StringUtils.setSpannablesFromRegex(userInfo,
                    "^(.*?)\\n", StyleSpan(Typeface.BOLD), RelativeSizeSpan(1.2f))
        }
    }

}
