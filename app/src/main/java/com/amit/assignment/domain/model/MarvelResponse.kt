package com.amit.assignment.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Series(
    @Json(name = "collectionURI")
    val collectionURI: String = "",
    @Json(name = "available")
    val available: Int = 0,
    @Json(name = "returned")
    val returned: Int = 0,
    @Json(name = "items")
    val items: List<Item>?
) : Parcelable

@Parcelize
data class Events(
    @Json(name = "collectionURI")
    val collectionURI: String = "",
    @Json(name = "available")
    val available: Int = 0,
    @Json(name = "returned")
    val returned: Int = 0,
    @Json(name = "items")
    val items: List<Item>?
) : Parcelable

@Parcelize
data class Character(
    @Json(name = "thumbnail")
    val thumbnail: Thumbnail,
    @Json(name = "urls")
    val urls: List<UrlsItem>?,
    @Json(name = "stories")
    val stories: Stories,
    @Json(name = "series")
    val series: Series,
    @Json(name = "comics")
    val comics: Comics,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "description")
    val description: String = "",
    @Json(name = "modified")
    val modified: String = "",
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "resourceURI")
    val resourceURI: String = "",
    @Json(name = "events")
    val events: Events
) : Parcelable

@Parcelize
data class UrlsItem(
    @Json(name = "type")
    val type: String = "",
    @Json(name = "url")
    val url: String = ""
) : Parcelable

@Parcelize
data class Item(
    @Json(name = "name")
    val name: String = "",
    @Json(name = "resourceURI")
    val resourceURI: String = ""
) : Parcelable

data class Data(
    @Json(name = "total")
    val total: Int = 0,
    @Json(name = "offset")
    val offset: Int = 0,
    @Json(name = "limit")
    val limit: Int = 0,
    @Json(name = "count")
    val count: Int = 0,
    @Json(name = "results")
    val results: List<Character>?
)

@Parcelize
data class Stories(
    @Json(name = "collectionURI")
    val collectionURI: String = "",
    @Json(name = "available")
    val available: Int = 0,
    @Json(name = "returned")
    val returned: Int = 0,
    @Json(name = "items")
    val items: List<Item>?
) : Parcelable

data class MarvelResponse(
    @Json(name = "copyright")
    val copyright: String = "",
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "data")
    val data: Data,
    @Json(name = "attributionHTML")
    val attributionHTML: String = "",
    @Json(name = "attributionText")
    val attributionText: String = "",
    @Json(name = "etag")
    val etag: String = "",
    @Json(name = "status")
    val status: String = ""
)

@Parcelize
data class Thumbnail(
    @Json(name = "path")
    val path: String = "",
    @Json(name = "extension")
    val extension: String = ""
) : Parcelable {
    fun getUrl() = "$path.$extension".replace("http", "https")
}

@Parcelize
data class Comics(
    @Json(name = "collectionURI")
    val collectionURI: String = "",
    @Json(name = "available")
    val available: Int = 0,
    @Json(name = "returned")
    val returned: Int = 0,
    @Json(name = "items")
    val items: List<Item>?
) : Parcelable
