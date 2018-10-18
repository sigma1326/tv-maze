package com.example.tvmaze.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "show")
data class Show(@PrimaryKey(autoGenerate = false) @ColumnInfo(name = "show_id") var showID: Int = 0
                , @ColumnInfo(name = "name") var name: String = ""
                , @ColumnInfo(name = "url") var url: String = ""
                , @ColumnInfo(name = "runtime") var runtime: String = ""
                , @ColumnInfo(name = "premiered") var premiered: String = ""
                , @ColumnInfo(name = "schedule") var schedule: String = ""
                , @ColumnInfo(name = "medium_image") var medium_image: String = ""
                , @ColumnInfo(name = "large_image") var large_image: String = ""
                , @ColumnInfo(name = "summary") var summary: String = ""
)