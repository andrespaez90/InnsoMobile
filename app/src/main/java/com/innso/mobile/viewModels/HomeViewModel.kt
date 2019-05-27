package com.innso.mobile.viewModels

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.innso.mobile.api.controllers.NewsControllerApi
import com.innso.mobile.ui.activities.base.WEB_VIEW_URL_PARAM
import com.innso.mobile.ui.activities.base.WebViewActivity
import com.innso.mobile.ui.models.news.GenericNews
import com.innso.mobile.viewModels.models.StartActivityModel
import javax.inject.Inject

class HomeViewModel : AndroidViewModel() {

    private val topLines: MutableLiveData<List<GenericNews>> = MutableLiveData()

    @Inject
    lateinit var newsController: NewsControllerApi

    init {
        getComponent().inject(this)
        loadNews()
    }


    /**
     * Functions
     */

    fun loadNews() {
        disposables.add(newsController.getTopLines().map { it.mapTo(ArrayList()) { GenericNews(it) } }
                .subscribe({ topLines.postValue(it) }, this::showServiceError))
    }

    fun openLink(link: String) {
        val extras = Bundle()
        extras.putString(WEB_VIEW_URL_PARAM, link)
        startActivity.postValue(StartActivityModel(WebViewActivity::class.java, extras))
    }

    /**
     * Live Data
     */

    fun topLinesChange(): LiveData<List<GenericNews>> = topLines
}