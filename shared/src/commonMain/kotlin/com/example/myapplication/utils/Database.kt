package com.example.myapplication.utils

import com.tencent.kuikly.core.module.CallbackFn
import com.tencent.kuikly.core.module.Module

class Database: Module() {
    override fun moduleName(): String = "KRDatabaseModule"
    companion object {
        const val MODULE_NAME = "DatabaseModule"
    }

    fun init(content: String) {
        toNative(
            false,
            "init",
            content,
            null,
            false
        )
    }

    fun pullChatRecords(userId: Int = -1, callbackFn: CallbackFn) {
        toNative(
            false,
            "pullChatRecords",
            userId,
            callbackFn,
            false
        )
    }

    fun getFriendName(userId: Int = -1, callbackFn: CallbackFn) {
        toNative(
            false,
            "getFriendName",
            userId,
            callbackFn,
            false
        )
    }
}