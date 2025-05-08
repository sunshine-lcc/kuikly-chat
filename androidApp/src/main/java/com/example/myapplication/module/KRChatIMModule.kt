package com.example.myapplication.module

import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.myapplication.KRApplication
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions
import com.tencent.kuikly.core.render.android.export.KuiklyRenderBaseModule
import com.tencent.kuikly.core.render.android.export.KuiklyRenderCallback


class KRChatIMModule : KuiklyRenderBaseModule() {
    companion object {
        const val MODULE_NAME = "KRChatIMModule"
    }

    fun init() {
        val options = EMOptions()
        // TODO: 修改成自己的appKey
        options.appKey = ""
        // 其他 EMOptions 配置......
        // context 为上下文，在 Application 或者 Activity 中可以用 this 代替
        EMClient.getInstance().init(KRApplication.application, options)
    }

    fun login(params: String) {
        val ans = params.split("-and-")
        Log.i(MODULE_NAME, "params: $params, ans: $ans")

        if (ans.size == 2) {
            val name = ans[0]
            val token = ans[1]
            Log.i(MODULE_NAME, "login name: $name")
            Log.i(MODULE_NAME, "login token: $token")

            EMClient.getInstance().loginWithToken(name, token, object : EMCallBack {
                // 登录成功回调
                override fun onSuccess() {
                    // 回调位于异步线程，处理 UI 相关需切换到主线程
                    Looper.prepare()
                    Toast.makeText(KRApplication.application, "环信IM登陆成功", Toast.LENGTH_SHORT)
                        .show()
                    EMClient.getInstance().logout(true)
                    Looper.loop()
                }

                // 登录失败回调，包含错误信息
                override fun onError(code: Int, error: String?) {
                    // 回调位于异步线程，处理 UI 相关需切换到主线程
                    Log.e(MODULE_NAME, "环信IM登录失败：code: $code, error: $error")
                    when (code) {
                        // User is already log in
                        200 -> EMClient.getInstance().logout(true)
                    }
                }
            })
        } else {
            Log.e(MODULE_NAME, "wrong param: $params")
        }
    }

    // 传输基本类型、数组、字符串
    override fun call(method: String, params: Any?, callback: KuiklyRenderCallback?): Any? {
        return when (method) {
            "init" -> init()
            "login" -> login(params as String)
            else -> super.call(method, params, callback)
        }
    }
}