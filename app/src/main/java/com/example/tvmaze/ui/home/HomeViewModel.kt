package com.example.tvmaze.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvmaze.database.DataBaseRepositoryImpl
import com.example.tvmaze.database.model.Show
import com.example.tvmaze.network.NetworkRepositoryImpl

class HomeViewModel(val db: DataBaseRepositoryImpl, val network: NetworkRepositoryImpl) : ViewModel() {
    private lateinit var shows: MutableLiveData<MutableList<Show>>
    var page = 0

    fun getShows(): MutableLiveData<MutableList<Show>> {
        if (!::shows.isInitialized) {
            shows = MutableLiveData()
            loadShows()
        }
        return shows
    }

    fun loadShows() {
        shows.value = db.getShows().value?.toMutableList()
    }
}
