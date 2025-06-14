package com.example.myapplication.pages.chat

import com.example.myapplication.base.BasePager
import com.example.myapplication.base.BridgeModule
import com.example.myapplication.utils.Database
import com.example.myapplication.views.TopNavBar
import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.base.Border
import com.tencent.kuikly.core.base.BorderStyle
import com.tencent.kuikly.core.base.Color
import com.tencent.kuikly.core.base.ViewBuilder
import com.tencent.kuikly.core.base.ViewRef
import com.tencent.kuikly.core.base.attr.ImageUri
import com.tencent.kuikly.core.directives.vbind
import com.tencent.kuikly.core.directives.vforLazy
import com.tencent.kuikly.core.layout.FlexDirection
import com.tencent.kuikly.core.log.KLog
import com.tencent.kuikly.core.nvi.serialization.json.JSONArray
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.reactive.handler.observableList
import com.tencent.kuikly.core.views.Image
import com.tencent.kuikly.core.views.Input
import com.tencent.kuikly.core.views.List
import com.tencent.kuikly.core.views.ListView
import com.tencent.kuikly.core.views.Text
import com.tencent.kuikly.core.views.View
import com.tencent.kuikly.core.views.compose.Button

@Page("ChatPage")
internal class ChatPage : BasePager() {
    private var friendId = -1
    private var friendName by observable("")
    private var chatRecords by observableList<ChatRecord>()
    lateinit var listViewRef: ViewRef<ListView<*, *>>
    // ... | listInited | listFirstShowed
    private var setListOffEndVec: Int = 0b00000000

    override fun created() {
        super.created()
        acquireModule<Database>(Database.MODULE_NAME).init("")

        this.friendId = pagerData.params.optInt("userId")
        KLog.i("ChatPage", "getUserId: ${this.friendId}")

        acquireModule<Database>(Database.MODULE_NAME).pullChatRecords(this.friendId) {
            KLog.i("ChatPage", "records 333: ${it?.optString("records").toString()}")
            val temp = JSONArray(it?.optString("records").toString())
            val len = temp.length()

            for (i in 0 until len) {
                val tempJSONObject = temp.optJSONObject(i)
                KLog.i("ChatPage", "tempJSONObj 444: $tempJSONObject")
                chatRecords.add(
                    ChatRecord(
                        uid = tempJSONObject?.optInt("uid"),
                        userId = tempJSONObject?.optInt("userId"),
                        timeStamp = tempJSONObject?.optLong("timeStamp"),
                        content = tempJSONObject?.optString("content"),
                        sent = tempJSONObject?.optBoolean("sent"),
                        type = MessageType.valueOf(tempJSONObject?.optString("type").toString())
                    )
                )
            }

            this.setListOffEndVec = this.setListOffEndVec or 0b00000010
            KLog.i("ChatPage", "chatRecords.size 333: ${chatRecords.size}")
        }

        acquireModule<Database>(Database.MODULE_NAME).getFriendName(this.friendId) {
            this.friendName = it?.optString("name").toString()
            KLog.i("ChatPage", "getFriendName: ${this.friendName}")
        }
    }

    override fun body(): ViewBuilder {
        val ctx = this
        return {
            vbind({ ctx.friendName }) {
                TopNavBar {
                    attr {
                        backDisable = false
                        title = ctx.friendName
                    }
                }
            }

            List {
                attr {
                    flex(1f)
                    flexDirection(FlexDirection.COLUMN)
                }
                ref {
                    ctx.listViewRef = it
                }
                event {
                    scroll { scrollParams ->
                        KLog.i("ChatPage", "scrollParams: $scrollParams")
                    }
                    contentSizeChanged { width, height ->
                        if ((ctx.setListOffEndVec and 3) != 3) {
                            ctx.listViewRef.view?.setContentOffset(
                                0f,
                                height - getPager().pageData.pageViewHeight + 110f + getPager().pageData.statusBarHeight,
                                false
                            )
                            if ((ctx.setListOffEndVec and 2) != 0) {
                                ctx.setListOffEndVec = ctx.setListOffEndVec or 0b00000001
                            }
                        }
                    }
                }
                vforLazy({ ctx.chatRecords }) { record, index, count ->
                    View {
                        attr {
                            maxHeight(150f)
                            margin(5f, 0f, 5f, 0f)
                            alignItemsCenter()
                            if (record.sent == true) {
                                flexDirectionRow()
                            } else {
                                flexDirection(FlexDirection.ROW_REVERSE)
                            }
                        }
                        Image {
                            attr {
                                size(35f, 35f)
                                src(ImageUri.pageAssets("u=2231848948,299743226&fm=253&gp=0.jpg"))
                            }
                        }
                        View {
                            attr {
                                padding(5f, 10f, 5f, 10f)
                                backgroundColor(Color.GREEN)
                                maxWidth(pagerData.pageViewWidth * 0.8f)
                                borderRadius(5f)
                                if (record.sent == true) {
                                    marginLeft(10f)
                                } else {
                                    marginRight(10f)
                                }
                            }
                            Text {
                                attr {
                                    fontSize(20f)
                                    text(record.content.toString())
                                }
                            }
                        }
                    }
                }
            }

            View {
                attr {
                    flexDirectionRow()
                    alignItemsCenter()
                    marginBottom(20f)
                    backgroundColor(Color.GRAY)
                }
                Input {
                    attr {
                        border(Border(2f, BorderStyle.SOLID, Color.BLACK))
                        borderRadius(5f)
                        width(pagerData.pageViewWidth - 130)
                        height(40f)
                        margin(0f, 20f, 0f, 20f)
                        placeholder("输入框提示")
                        placeholderColor(Color.BLACK)
                        color(Color.BLACK)
                    }
                }
                Button {
                    attr {
                        border(Border(2f, BorderStyle.SOLID, Color.BLACK))
                        titleAttr {
                            text("发送")
                            fontSize(20f)
                            color(Color.BLACK)
                        }
                        borderRadius(10f)
                        backgroundColor(Color.GREEN)
                        width(70f)
                        height(40f)
                        marginRight(40f)
                    }
                    event {
                        click { clickParams ->
                            getPager().acquireModule<BridgeModule>(BridgeModule.MODULE_NAME)
                                .toast("点击了发送按钮")
                        }
                    }
                }
            }
        }
    }
}
