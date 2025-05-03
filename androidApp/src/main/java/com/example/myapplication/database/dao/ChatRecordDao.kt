package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.database.entity.ChatRecord

@Dao
interface ChatRecordDao {
    @Query("SELECT * FROM chatrecord")
    fun getAll(): List<ChatRecord>

    @Query("SELECT * FROM chatrecord WHERE uid IN (:chatIds)")
    fun loadAllByIds(chatIds: IntArray): List<ChatRecord>

    @Query("SELECT * FROM chatrecord WHERE user_name LIKE :name LIMIT 1")
    fun findByName(name: String): ChatRecord

    @Insert
    fun insertAll(vararg chats: ChatRecord)

    @Insert
    fun insert(obj: ChatRecord)

    @Insert
    fun insert(list: List<ChatRecord>)

    @Delete
    fun delete(user: ChatRecord)
}