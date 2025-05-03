package com.example.myapplication.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatRecord(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "user_name") val userName: String?,
    @ColumnInfo(name = "time_stamp") val timeStamp: String?,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "direction") val direction: String?,
    @ColumnInfo(name = "type") val type: String?,
)