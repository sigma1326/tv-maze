package com.example.tvmaze.network

import com.example.tvmaze.database.DataBaseRepositoryImpl
import com.example.tvmaze.management.AppManager
import com.example.tvmaze.model.SearchResult
import com.example.tvmaze.model.Show
import com.pawegio.kandroid.runAsync
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface NetworkResponse {
    fun onSuccess(response: String, responseObjects: MutableList<Show>)
    fun onError(errorMessage: String)
}


interface NetworkResponseSearch {
    fun onSuccess(response: String, responseObjects: MutableList<SearchResult>)
    fun onError(errorMessage: String)
}

interface ShowsRepository {
    @GET("shows/{id}")
    fun getShow(@Path("id") id: String): Flowable<Show>

    @GET("shows")
    fun getShowsByPage(@Query("page") pageNumber: Int): Flowable<MutableList<Show>>

    @GET("search/shows")
    fun searchShows(@Query("q") name: String): Flowable<MutableList<SearchResult>>
}

interface NetworkRepository {
    fun getShow(id: String, networkResponse: NetworkResponse)

    fun getShowsByPage(pageNumber: Int, networkResponse: NetworkResponse)

    fun searchShows(name: String, networkResponse: NetworkResponseSearch)
}

class NetworkRepositoryImpl(val db: DataBaseRepositoryImpl) : NetworkRepository {
    override fun searchShows(name: String, networkResponse: NetworkResponseSearch) {
        try {
            runAsync {
                AppManager.networkService.searchShows(name)
                        .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result ->
                                    val cleanList: MutableList<SearchResult> = emptyList<SearchResult>().toMutableList()
                                    result.forEach {
                                        if (it.show != null && it.show?.image != null) {
                                            cleanList.add(it)
                                        }
                                    }

                                    networkResponse.onSuccess("success", cleanList)
                                }
                                , { error ->
                            error.printStackTrace()
                            networkResponse.onError(error.message.toString())
                        })
            }
        } catch (e: Exception) {
            e.printStackTrace()
            networkResponse.onError(e.message.toString())
        }
    }

    override fun getShow(id: String, networkResponse: NetworkResponse) {
        try {
            runAsync {
                AppManager.networkService.getShow(id)
                        .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result ->
                                    networkResponse.onSuccess("success", mutableListOf(result))
                                }
                                , { error ->
                            error.printStackTrace()
                            networkResponse.onError(error.message.toString())
                        })
            }
        } catch (e: Exception) {
            e.printStackTrace()
            networkResponse.onError(e.message.toString())
        }
    }

    override fun getShowsByPage(pageNumber: Int, networkResponse: NetworkResponse) {
        try {
            runAsync {
                AppManager.networkService.getShowsByPage(pageNumber)
                        .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result ->
                                    networkResponse.onSuccess("success", result)
                                }
                                , { error ->
                            error.printStackTrace()
                            networkResponse.onError(error.message.toString())
                        })
            }
        } catch (e: Exception) {
            e.printStackTrace()
            networkResponse.onError(e.message.toString())
        }
    }
}
