package com.innso.mobile.api.controllers


import com.innso.mobile.api.models.news.Articles
import com.innso.mobile.api.models.news.TopLinesResponse
import com.innso.mobile.api.services.NewsServices
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsControllerApi @Inject constructor(private val services: NewsServices) {

    fun getTopLines(): Single<List<Articles>> =
            services.getTopLines("co", 1)
                    .map { it.articles }
                    .subscribeOn(Schedulers.io())

}
