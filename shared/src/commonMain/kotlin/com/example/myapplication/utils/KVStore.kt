package com.example.myapplication.utils

import com.tencent.kuikly.core.module.CallbackFn
import com.tencent.kuikly.core.module.Module

class KVStore: Module() {
    override fun moduleName(): String = "KRKVStoreModule"
    companion object {
        const val MODULE_NAME = "KVStore"
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

    fun getStoreValue(key: String, callbackFn: CallbackFn) {
        toNative(
            false,
            "getStoreValue",
            key,
            callbackFn,
            false
        )
    }
}