package com.innso.mobile.api.models.news

import com.google.gson.annotations.SerializedName

class TopLinesResponse(@SerializedName("articles") val articles: List<Articles>)

class Articles(@SerializedName("source") val source: ArticleSource, @SerializedName("title") val title: String,
               @SerializedName("description") val description: String, @SerializedName("urlToImage") val cover: String?,
               @SerializedName("url") val link: String?)

class ArticleSource(@SerializedName("id") val id: String?, @SerializedName("name") val name: String?)