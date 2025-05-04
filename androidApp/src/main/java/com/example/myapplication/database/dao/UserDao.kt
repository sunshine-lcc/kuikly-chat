package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.database.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE uid = :userId")
    fun getUserById(userId: Int): User

    @Query("SELECT * FROM user WHERE name LIKE :name")
    fun findByName(name: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Insert
    fun insert(obj: User)

    @Insert
    fun insert(list: List<User>)

    @Delete
    fun delete(user: User)
}