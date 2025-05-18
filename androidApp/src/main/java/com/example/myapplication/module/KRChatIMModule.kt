package com.example.myapplication.module

import android.util.Log
import android.widget.Toast
import com.example.myapplication.KRApplication
import com.tencent.kuikly.core.render.android.export.KuiklyRenderBaseModule
import com.tencent.kuikly.core.render.android.export.KuiklyRenderCallback
import io.openim.android.sdk.OpenIMClient
import io.openim.android.sdk.listener.OnAdvanceMsgListener
import io.openim.android.sdk.listener.OnBase
import io.openim.android.sdk.listener.OnConnListener
import io.openim.android.sdk.listener.OnConversationListener
import io.openim.android.sdk.listener.OnFriendshipListener
import io.openim.android.sdk.listener.OnGroupListener
import io.openim.android.sdk.listener.OnUserListener
import io.openim.android.sdk.models.InitConfig


class KRChatIMModule : KuiklyRenderBaseModule() {
    companion object {
        const val MODULE_NAME = "KRChatIMModule"
    }
    private val DEFAULT_HOST = "" // TODO: 输入服务器地址

    fun init() {
        val initConfig = InitConfig(
            "http://$DEFAULT_HOST:10002", //SDK api地址
            "ws://$DEFAULT_HOST:10001", //SDK WebSocket地址
            KRApplication.application.filesDir.absolutePath,//SDK数据库存储目录
        )

        val isInitSucceed = OpenIMClient.getInstance().initSDK(
            KRApplication.application,
            initConfig,
            object : OnConnListener {
                override fun onConnectFailed(p0: Int, p1: String?) {
                    Log.i(KRChatIMModule.MODULE_NAME, "connect fail: $p1")
                }

                override fun onConnectSuccess() {
                    Log.i(KRChatIMModule.MODULE_NAME, "connect success")
                }

                override fun onConnecting() {
                    Log.i(KRChatIMModule.MODULE_NAME, "connecting...")
                }

                override fun onKickedOffline() {
                    Log.i(KRChatIMModule.MODULE_NAME, "KickedOffline...")
                }

                override fun onUserTokenExpired() {
                    Log.i(KRChatIMModule.MODULE_NAME, "KickedOffline...")
                }

                override fun onUserTokenInvalid(p0: String?) {
                    Log.i(KRChatIMModule.MODULE_NAME, "UserTokenInvalid...")
                }
            }
        )
        Toast.makeText(KRApplication.application, "openIM init status: $isInitSucceed", Toast.LENGTH_SHORT).show()

        // Set listener
        // sdk采用的set方式，多次set会替换上次set,建议使用一个中间件使用add方式分发执行回调,参考demo IMEvent这个类
        // 当前登录用户资料变更回调
        OpenIMClient.getInstance().userInfoManager.setOnUserListener(object : OnUserListener {})
        // 收到新消息，已读回执，消息撤回监听。
        OpenIMClient.getInstance().messageManager.setAdvancedMsgListener(object : OnAdvanceMsgListener {})
        // 好关系发生变化监听
        OpenIMClient.getInstance().friendshipManager.setOnFriendshipListener(object : OnFriendshipListener {})
        // 会话新增或改变监听
        OpenIMClient.getInstance().conversationManager.setOnConversationListener(object : OnConversationListener {})
        // 群组关系发生改变监听
        OpenIMClient.getInstance().groupManager.setOnGroupListener(object : OnGroupListener {})

        OpenIMClient.getInstance().login(object : OnBase<String?> {
            override fun onError(code: Int, error: String?) {
                Log.i(KRChatIMModule.MODULE_NAME, "OpenIM login err: $error")
            }

            override fun onSuccess(data: String?) {
                //其他api调用必须保证登录回调成功后操作
                Log.i(KRChatIMModule.MODULE_NAME, "OpenIM login success: $data")
            }
        }, "userID", "imToken")
    }

    fun login(params: String) {
        val ans = params.split("-and-")
        Log.i(MODULE_NAME, "params: $params, ans: $ans")

        if (ans.size == 2) {
            val name = ans[0]
            val token = ans[1]
            Log.i(MODULE_NAME, "login name: $name")
            Log.i(MODULE_NAME, "login token: $token")
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