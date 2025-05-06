package com.example.myapplication.module

import android.util.Log
import androidx.room.Room
import com.example.myapplication.KRApplication
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.entity.ChatRecord
import com.example.myapplication.database.entity.MessageType
import com.example.myapplication.database.entity.User
import com.fasterxml.jackson.databind.ObjectMapper
import com.tencent.kuikly.core.render.android.export.KuiklyRenderBaseModule
import com.tencent.kuikly.core.render.android.export.KuiklyRenderCallback
import java.util.concurrent.CompletableFuture

class KRDatabaseModule: KuiklyRenderBaseModule() {
    companion object {
        const val MODULE_NAME = "KRDatabaseModule"
    }

    private fun initDatabase() {
        Thread {
            val db = Room.databaseBuilder(
                KRApplication.Companion.application,
                AppDatabase::class.java, "sqlite.db"
            ).build()

            val userDao = db.userDao()
            val users: List<User> = userDao.getAll()

            if (users.isEmpty()) {
                userDao.insert(
                    User(
                        uid = 1,
                        name = "Helen",
                    )
                )
            }

            val chatRecordDao = db.ChatRecordDao()
            val chatRecords: List<ChatRecord> = chatRecordDao.getAll()

            if (chatRecords.isEmpty()) {
                for (i in 0 until 100) {
                    chatRecordDao.insert(
                        ChatRecord(
                            uid = i,
                            userId = 1,
                            timeStamp = (10000 + i).toLong(),
                            content = "aassaasa $i kkk",
                            sent = i % 4 != 0,
                            type = MessageType.NORMAL
                        )
                    )
                }
            }
            Log.i(MODULE_NAME, "init chatRecords: $chatRecords")
        }.start()
    }

    private fun getChatRecords(params: Int, callback: KuiklyRenderCallback?) {
        var records = CompletableFuture.supplyAsync {
            val userId = params

            val db = Room.databaseBuilder(
                KRApplication.Companion.application,
                AppDatabase::class.java, "sqlite.db"
            ).build()

            val chatRecordDao = db.ChatRecordDao()
            val chatRecords: List<ChatRecord> = if (userId == -1)  chatRecordDao.getAll() else chatRecordDao.getByUserId(userId)

            Log.i(MODULE_NAME, "getChatRecords 111: $chatRecords")
            chatRecords
        }.join()

        val mapper = ObjectMapper()
        val json = mapper.writeValueAsString(records)
        Log.i(MODULE_NAME, "chatRecords json 222: $json")
        callback?.invoke(mapOf(
            "records" to json
        ))
    }

    private fun getFriendName(userId: Int, callback: KuiklyRenderCallback?) {
        var name = CompletableFuture.supplyAsync {
            val db = Room.databaseBuilder(
                KRApplication.Companion.application,
                AppDatabase::class.java, "sqlite.db"
            ).build()

            val userDao = db.userDao()
            val user: User = userDao.getUserById(userId)
            Log.i(MODULE_NAME, "getUser: $user")
            user.name
        }.join().toString()
        callback?.invoke(mapOf(
            "name" to name
        ))
    }

    // 传输基本类型、数组、字符串
    override fun call(method: String, params: Any?, callback: KuiklyRenderCallback?): Any? {
        return when (method) {
            "init" -> initDatabase()
            "pullChatRecords" -> getChatRecords(params as Int, callback)
            "getFriendName" -> getFriendName(params as Int, callback)
            else -> super.call(method, params, callback)
        }
    }
}