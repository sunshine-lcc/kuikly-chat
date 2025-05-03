package com.example.myapplication.utils

import com.tencent.kuikly.core.module.Module

class Database: Module() {
    override fun moduleName(): String = "KRDatabaseAdapter"
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
}