package com.example.tvmaze.database

import androidx.lifecycle.LiveData
import com.example.tvmaze.database.model.Show

class DataBaseRepositoryImpl(private val db: DataBase) : DataBaseRepository {
    override fun getShows(): LiveData<List<Show>> {
        return db.showDAO().getShows()
    }

    override fun getShow(showID: Int): LiveData<Show> {
        return db.showDAO().getShowByID(showID)
    }

    override fun addShows(shows: List<Show>) {
        db.showDAO().insertAll(shows)
    }

    override fun addShow(show: Show) {
        db.showDAO().insert(show)
    }
}