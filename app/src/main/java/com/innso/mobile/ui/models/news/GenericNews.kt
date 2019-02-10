package com.innso.mobile.ui.models.news

import com.innso.mobile.api.models.news.Articles

class GenericNews(articles: Articles) {

    var title: String = articles.title

    val description: String = articles.description

    val urlImage: String = articles.cover ?: ""

    val sourceName: String = articles.source.name ?: ""


}