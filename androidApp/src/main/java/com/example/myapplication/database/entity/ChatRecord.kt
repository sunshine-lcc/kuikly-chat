package com.example.myapplication.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatRecord(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "user_id") val userId: Int?,
    @ColumnInfo(name = "time_stamp") val timeStamp: Long?,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "is_sent") val isSent: Boolean?,
    @ColumnInfo(name = "type") val type: MessageType?,
)

enum class MessageType {
    NORMAL, LOCATION, CALL
}