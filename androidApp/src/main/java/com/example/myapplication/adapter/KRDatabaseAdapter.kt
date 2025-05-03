package com.example.myapplication.adapter

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.entity.ChatRecord
import com.example.myapplication.database.entity.User
import com.tencent.kuikly.core.render.android.export.KuiklyRenderBaseModule
import com.tencent.kuikly.core.render.android.export.KuiklyRenderCallback

class KRDatabaseAdapter: KuiklyRenderBaseModule() {
    companion object {
        const val MODULE_NAME = "KRDatabaseAdapter"
    }

    private fun initDatabase() {
        Thread {
            val db = Room.databaseBuilder(
                kuiklyRenderContext?.context as Context,
                AppDatabase::class.java, "mydb"
            ).build()
            val userDao = db.userDao()
//            userDao.insert(User(
//                uid = 1,
//                firstName = "111",
//                lastName = "111111"
//            ))
            val users: List<User> = userDao.getAll()

            val chatRecordDao = db.ChatRecordDao()
//            chatRecordDao.insert(ChatRecord(
//                uid = 0,
//                userName = "Steve",
//                timeStamp = "1",
//                content = "1212212112",
//                direction = "send",
//                type = "normal"
//            ))
            val chatRecords: List<ChatRecord> = chatRecordDao.getAll()

            Log.i(MODULE_NAME, "users: $users")
            Log.i(MODULE_NAME, "chatRecords: $chatRecords")
        }.start()
    }

    // 传输基本类型、数组、字符串
    override fun call(method: String, params: Any?, callback: KuiklyRenderCallback?): Any? {
        return when (method) {
            "init" -> initDatabase()
            else -> super.call(method, params, callback)
        }
    }
}