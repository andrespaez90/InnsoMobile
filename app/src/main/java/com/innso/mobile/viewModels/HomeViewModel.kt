package com.innso.mobile.viewModels

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.innso.mobile.api.controllers.NewsControllerApi
import com.innso.mobile.ui.models.news.GenericNews
import com.innso.mobile.viewModels.models.StartActionModel
import javax.inject.Inject

class HomeViewModel : AndroidViewModel() {

    private val topLines: MutableLiveData<List<GenericNews>> = MutableLiveData()

    @Inject
    lateinit var newsController: NewsControllerApi

    init {
        getComponent().inject(this)
        disposables.add(newsController.getTopLines().map { it.mapTo(ArrayList()) { GenericNews(it) } }
                .subscribe({ topLines.postValue(it) }, this::showServiceError))
    }


    /**
     * Functions
     */

    fun openLink(link: String) {
        startAction.postValue(StartActionModel(Intent.ACTION_VIEW, null, Uri.parse(link)))
    }

    /**
     * Live Data
     */

    fun topLinesChange(): LiveData<List<GenericNews>> = topLines
}