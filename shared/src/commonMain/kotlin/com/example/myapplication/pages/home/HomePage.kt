package com.example.myapplication.pages.home

import com.example.myapplication.base.BasePager
import com.example.myapplication.utils.ChatIM
import com.example.myapplication.views.TopNavBar
import com.example.myapplication.utils.KVStore
import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.base.Color
import com.tencent.kuikly.core.base.ViewBuilder
import com.tencent.kuikly.core.base.attr.ImageUri
import com.tencent.kuikly.core.directives.vbind
import com.tencent.kuikly.core.directives.vfor
import com.tencent.kuikly.core.directives.vif
import com.tencent.kuikly.core.log.KLog
import com.tencent.kuikly.core.module.RouterModule
import com.tencent.kuikly.core.nvi.serialization.json.JSONArray
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject
import com.tencent.kuikly.core.pager.PageData
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.reactive.handler.observableList
import com.tencent.kuikly.core.views.Image
import com.tencent.kuikly.core.views.Input
import com.tencent.kuikly.core.views.List
import com.tencent.kuikly.core.views.Text
import com.tencent.kuikly.core.views.View

@Page("HomePage")
internal class HomePage : BasePager() {
    private var curStage by observable("Message")
    private var contactsList by observableList<String>()
    private var messageList by observableList<Int>()

    fun setStage(stage: String) {
        this.curStage = stage
    }

    fun getStage(): String {
        return this.curStage
    }

    override fun created() {
        super.created()
        KLog.i(pageName, "stage: ${pagerData.stage}")
        if (pagerData.stage != "") {
            this.curStage = pagerData.stage
        }

        acquireModule<KVStore>(KVStore.MODULE_NAME).init()
        acquireModule<KVStore>(KVStore.MODULE_NAME).getStoreValue("contactList") {
            KLog.i("HomePage", "contactList: ${it?.optString("value")}")
            val result = JSONArray(it?.optString("value").toString())
            val len = result.length()

            for (i in 0 until len) {
                val temp = result.optString(i)
                contactsList.add(temp.toString())
            }
        }

        acquireModule<KVStore>(KVStore.MODULE_NAME).getStoreValue("messageList") {
            KLog.i("HomePage", "messageList: ${it?.optString("value")}")
            val result = it?.optInt("value") as Int

            for (i in 0 until result) {
                messageList.add(i)
            }
        }

        acquireModule<ChatIM>(ChatIM.MODULE_NAME).init()
        acquireModule<ChatIM>(ChatIM.MODULE_NAME).login("", "")
    }

    override fun body(): ViewBuilder {
        val ctx = this
        return {
            attr {
                backgroundColor(Color.WHITE)
            }
            TopNavBar {
                attr {
                    title = ""
                    when (ctx.curStage) {
                        "Message" -> title = "首页"
                        "Contact" -> title = "联系人"
                        "Setting" -> title = "设置"
                    }
                    backDisable = true
                    moreButton = true
                }
            }

            vif ({ ctx.curStage != "Setting"}) {
                View {
                    attr {
                        flexDirectionRow()
                        alignItemsCenter()
                    }
                    Image {
                        attr {
                            marginLeft(20f)
                            src(ImageUri.pageAssets("bbe436e4_E775769_910a88b4.png"))
                            size(30f, 30f)
                        }
                    }
                    Input {
                        attr {
                            size(200f, 30f)
                            marginLeft(20f)
                            placeholder("输入框提示")
                            placeholderColor(Color.BLACK)
                            color(Color.BLACK)
                        }
                    }
                }
            }

            vbind({ ctx.curStage }) {
                when (ctx.curStage) {
                    "Message" -> {
                        List {
                            attr {
                                flex(1f)
                                marginTop(15f)
                            }
                            vfor({ ctx.messageList }) { item ->
                                View {
                                    attr {
                                        flexDirectionRow()
                                        marginTop(10f)
                                        alignItemsCenter()
                                    }
                                    event {
                                        click { clickParams ->
                                            getPager().acquireModule<RouterModule>(RouterModule.MODULE_NAME)
                                                .openPage(
                                                    "ChatPage",
                                                    JSONObject().apply {
                                                        put("userId", 1)
                                                    })
                                        }
                                    }
                                    Image {
                                        attr {
                                            src(ImageUri.pageAssets("u=2231848948,299743226&fm=253&gp=0.jpg"))
                                            size(50f, 50f)
                                        }
                                    }
                                    View {
                                        attr {
                                            flexDirectionColumn()
                                        }
                                        Text {
                                            attr {
                                                fontSize(25f)
                                                text("联系人")
                                            }
                                        }
                                        Text {
                                            attr {
                                                fontSize(15f)
                                                text("这是第 $item 条消息")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    "Contact" -> {
                        List {
                            attr {
                                flex(1f)
                                marginTop(20f)
                            }
                            vfor({ ctx.contactsList }) { item ->
                                View {
                                    attr {
                                        flexDirectionRow()
                                        alignItemsCenter()
                                        margin(10f, 0f, 10f, 0f)
                                    }
                                    Image {
                                        attr {
                                            size(50f, 50f)
                                            src(ImageUri.pageAssets("u=371927902,3140277954&fm=253&gp=0.jpg"))
                                        }
                                    }
                                    Text {
                                        attr {
                                            fontSize(23f)
                                            text(item)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    "Setting" -> {
                        List {
                            attr {
                                flex(1f)
                                flexDirectionColumn()
                                marginTop(20f)
                            }
                            View {
                                attr {
                                    flexDirectionRow()
                                    marginLeft(10f)
                                }
                                Image {
                                    attr {
                                        size(50f, 50f)
                                        src(ImageUri.pageAssets("u=371927902,3140277954&fm=253&gp=0.jpg"))
                                    }
                                }
                                View {
                                    attr {
                                        marginLeft(10f)
                                        flexDirectionColumn()
                                    }
                                    Text {
                                        attr {
                                            fontSize(27f)
                                            text("少时诵诗书山山水水")
                                        }
                                    }
                                    Text {
                                        attr {
                                            fontSize(17f)
                                            text("账号：666666666")
                                        }
                                    }
                                }
                            }
                            View {
                                attr {
                                    marginTop(15f)
                                    flexDirectionRow()
                                }
                                Image {
                                    attr {
                                        size(25f, 25f)
                                        marginLeft(10f)
                                        src(ImageUri.pageAssets("info.png"))
                                    }
                                }
                                Text {
                                    attr {
                                        fontSize(20f)
                                        marginLeft(10f)
                                        width(pagerData.pageViewWidth - 90f)
                                        text("关于")
                                    }
                                }
                                Image {
                                    attr {
                                        size(25f, 25f)
                                        marginRight(10f)
                                        src(ImageUri.pageAssets("right.png"))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            BottomNavBar {
                attr {}
            }
        }
    }

}

internal val PageData.stage: String
    get() {
        return params.optString("stage")
    }