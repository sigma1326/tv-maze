package com.example.tvmaze.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.tvmaze.database.model.Show


@Dao
interface ShowDAO {
    @Query("SELECT * FROM show order by show_id")
    fun getShows(): LiveData<List<Show>>

    @Query("SELECT * FROM show where show_id=:showID")
    fun getShowByID(showID: Int): LiveData<Show>

    @Insert(onConflict = REPLACE)
    fun insert(show: Show)

    @Insert(onConflict = REPLACE)
    fun insertAll(shows: List<Show>)

    @Delete
    fun delete(show: Show)
}