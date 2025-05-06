package com.example.myapplication.module

import android.util.Log
import androidx.room.Room
import com.example.myapplication.KRApplication
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.entity.ChatRecord
import com.fasterxml.jackson.databind.ObjectMapper
import com.tencent.kuikly.core.render.android.export.KuiklyRenderBaseModule
import com.tencent.kuikly.core.render.android.export.KuiklyRenderCallback
import com.tencent.mmkv.MMKV
import java.util.concurrent.CompletableFuture

class KRKVStoreModule: KuiklyRenderBaseModule() {
    companion object {
        const val MODULE_NAME = "KRKVStoreModule"
    }

    fun test() {
        val mmkv = MMKV.initialize(KRApplication.application)
        Log.i(MODULE_NAME, "mmkv init rootdir: $mmkv")

        val kv = MMKV.defaultMMKV()
        kv.encode("contactList", setOf("阿明", "阿红", "阿芳", "阿聪"))
        kv.encode("messageList", 100)
    }

    fun getStoreValue(key: String, callback: KuiklyRenderCallback?) {
        var value = CompletableFuture.supplyAsync {
            val kv = MMKV.defaultMMKV()

            when (key) {
                "contactList" -> {
                    return@supplyAsync kv.decodeStringSet(key)
                }
                "messageList" -> {
                    return@supplyAsync kv.decodeInt(key)
                }
            }
        }.join()

        Log.i(KRDatabaseModule.Companion.MODULE_NAME, "KVStoreModule 000: $value")
        val mapper = ObjectMapper()
        val json = mapper.writeValueAsString(value)
        Log.i(KRDatabaseModule.Companion.MODULE_NAME, "KVStoreModule 111: $json")
        callback?.invoke(mapOf(
            "value" to json
        ))
    }

    // 传输基本类型、数组、字符串
    override fun call(method: String, params: Any?, callback: KuiklyRenderCallback?): Any? {
        return when (method) {
            "init" -> test()
            "getStoreValue" -> getStoreValue(params as String, callback)
            else -> super.call(method, params, callback)
        }
    }
}