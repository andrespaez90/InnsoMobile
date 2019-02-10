package com.innso.mobile.viewModels.models

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes

class StartActivityModel(val activity: Class<*>) {

    var bundle: Bundle? = null

    var code: Int = 0

    var flags: Int = 0

    fun setFlags(flags: Int): StartActivityModel = apply { this.flags = flags }

    constructor(activity: Class<*>, bundle: Bundle? = null, code: Int = 0) : this(activity) {
        this.bundle = bundle
        this.code = code
    }

    constructor(activity: Class<*>, code: Int = 0) : this(activity) {
        this.code = code
    }
}

class FinishActivityModel(val code: Int) {

    var intent: Intent? = null

    constructor(code: Int, intent: Intent) : this(code) {
        this.intent = intent
    }
}


class StartActionModel(val action: String, val bundle: Bundle? = null, val uri: Uri? = null) {

    var code: Int = 0

    constructor(action: String, bundle: Bundle?, code: Int) : this(action, bundle) {
        this.code = code
    }
}

class DialogParams(val title: Int, val message: Int, val icon: Int,
                   val cancelable: Boolean = false, @StringRes val positiveText: Int, val positiveAction: View.OnClickListener)