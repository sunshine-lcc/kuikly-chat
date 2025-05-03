package com.example.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.database.dao.ChatRecordDao
import com.example.myapplication.database.dao.UserDao
import com.example.myapplication.database.entity.ChatRecord
import com.example.myapplication.database.entity.User

@Database(entities = [User::class, ChatRecord::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun ChatRecordDao(): ChatRecordDao
}