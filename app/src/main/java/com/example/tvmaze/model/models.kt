package com.example.tvmaze.model

import com.google.gson.annotations.SerializedName

data class Show(@SerializedName("id") var id: Int = 0
                , @SerializedName("url") var url: String? = ""
                , @SerializedName("name") var name: String? = ""
                , @SerializedName("type") var type: String? = ""
                , @SerializedName("language") var language: String? = ""
                , @SerializedName("genres") var genres: List<String>? = listOf()
                , @SerializedName("status") var status: String? = ""
                , @SerializedName("runtime") var runtime: Int? = 0
                , @SerializedName("premiered") var premiered: String? = ""
                , @SerializedName("officialSite") var officialSite: String? = ""
                , @SerializedName("schedule") var schedule: Schedule?
                , @SerializedName("rating") var rating: Rating?
                , @SerializedName("weight") var weight: Int? = 0
                , @SerializedName("network") var network: Network?
                , @SerializedName("webChannel") var webChannel: Network?
                , @SerializedName("externals") var externals: Externals?
                , @SerializedName("image") var image: Image?
                , @SerializedName("summary") var summary: String?
                , @SerializedName("updated") var updated: Float? = 0f
                , @SerializedName("_links") var links: Links?)

data class Image(@SerializedName("medium") var medium: String? = ""
                 , @SerializedName("original") var original: String? = "")

data class Externals(@SerializedName("tvrage") var tvrage: Int? = 0
                     , @SerializedName("thetvdb") var original: Int? = 0,
                     @SerializedName("imdb") var imdb: String? = "")

data class Network(@SerializedName("id") var id: Int = 0
                   , @SerializedName("name") var name: String = ""
                   , @SerializedName("country") var country: Country?)

data class Country(@SerializedName("name") var name: String = ""
                   , @SerializedName("code") var code: String = ""
                   , @SerializedName("timezone") var timezone: String = "")

data class Schedule(@SerializedName("time") var time: String? = ""
                    , @SerializedName("days") var days: List<String>? = listOf())

data class Rating(@SerializedName("average") var average: Float? = 0.0f)

data class Links(@SerializedName("self") var self: HRef
                 , @SerializedName("previousepisode") var previousepisode: HRef)

data class HRef(@SerializedName("href") var average: String = "")

data class SearchResult(@SerializedName("score") var score: Float? = 0f
                        , @SerializedName("show") var show: Show?)
