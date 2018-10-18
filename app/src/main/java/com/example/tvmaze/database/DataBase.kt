package com.example.tvmaze.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tvmaze.database.dao.ShowDAO
import com.example.tvmaze.database.model.Show

@Database(entities = [(Show::class)], version = 1)
abstract class DataBase : RoomDatabase() {

    abstract fun showDAO(): ShowDAO
}