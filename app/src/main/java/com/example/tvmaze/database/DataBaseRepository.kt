package com.example.tvmaze.database

import androidx.lifecycle.LiveData
import com.example.tvmaze.database.model.Show

interface DataBaseRepository {
    fun getShows(): LiveData<List<Show>>
    fun getShow(showID: Int): LiveData<Show>
    fun addShows(shows: List<Show>)
    fun addShow(show: Show)
}