package com.example.myapplication.utils

import com.tencent.kuikly.core.module.CallbackFn
import com.tencent.kuikly.core.module.Module

class ChatIM: Module() {
    override fun moduleName(): String = "KRChatIMModule"
    companion object {
        const val MODULE_NAME = "ChatIMModule"
    }

    fun init() {
        toNative(
            false,
            "init",
            null,
            null,
            false
        )
    }

    fun login(userName: String, token: String) {
        toNative(
            false,
            "login",
            userName.plus("-and-").plus(token),
            null,
            false
        )
    }
}