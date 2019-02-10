package com.innso.mobile.api.config


import android.content.Context
import com.innso.mobile.BuildConfig
import com.innso.mobile.R
import javax.inject.Inject
import javax.inject.Singleton


const val BEARER = "Bearer "
const val AUTHORIZATION = "authorization"
const val PARAM_AUTHORIZATION = "auth"

@Singleton
class ApiConfig @Inject constructor(private val context: Context) {

    var DEBUG = true

    fun getFirebaseUrl(): String = context.getString(R.string.firebase_database)

    fun getFuntionsUrlBase(): String = context.getString(R.string.firebase_functions)

    fun getUrlNews(): String = context.getString(R.string.api_news)

    fun getNewsApiKey(): String = BuildConfig.newsApi
}
