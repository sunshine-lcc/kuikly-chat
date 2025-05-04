package com.example.myapplication.chat

import com.example.myapplication.base.BasePager
import com.example.myapplication.utils.Database
import com.example.myapplication.views.TopNavBar
import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.base.Border
import com.tencent.kuikly.core.base.BorderStyle
import com.tencent.kuikly.core.base.Color
import com.tencent.kuikly.core.base.ViewBuilder
import com.tencent.kuikly.core.base.attr.ImageUri
import com.tencent.kuikly.core.directives.vbind
import com.tencent.kuikly.core.directives.vfor
import com.tencent.kuikly.core.layout.FlexDirection
import com.tencent.kuikly.core.log.KLog
import com.tencent.kuikly.core.nvi.serialization.json.JSONArray
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.reactive.handler.observableList
import com.tencent.kuikly.core.views.Image
import com.tencent.kuikly.core.views.Input
import com.tencent.kuikly.core.views.List
import com.tencent.kuikly.core.views.Text
import com.tencent.kuikly.core.views.View
import com.tencent.kuikly.core.views.compose.Button

@Page("ChatPage")
internal class ChatPage : BasePager() {
    private var friendId = -1;
    private var friendName by observable("");
    private var chatRecords by observableList<ChatRecord>()

    override fun created() {
        super.created()
        acquireModule<Database>(Database.MODULE_NAME).init("")

        this.friendId = pagerData.params.optInt("userId")
        KLog.i("ChatPage", "getUserId: ${this.friendId}")

        acquireModule<Database>(Database.MODULE_NAME).pullChatRecords(this.friendId) {
            val temp = JSONArray(it?.optString("records").toString())
            val len = temp.length()

            for (i in 0 until len) {
                val tempJSONObject = temp.optJSONObject(i)
                chatRecords.add(ChatRecord(
                    uid = tempJSONObject?.optInt("uid"),
                    userId = tempJSONObject?.optInt("userId"),
                    timeStamp = tempJSONObject?.optLong("timeStamp"),
                    content = tempJSONObject?.optString("content"),
                    isSent = tempJSONObject?.optBoolean("isSent"),
                    type = MessageType.valueOf(tempJSONObject?.optString("type").toString())
                ))
            }

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
            vbind ({ctx.friendName}) {
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
                    marginTop(10f)
                    flexDirectionColumn()
                }
                vfor ({ctx.chatRecords}) { record ->
                    View {
                        attr {
                            maxHeight(150f)
                            margin(5f, 0f, 5f, 0f)
                            alignItemsCenter()
                            if (record.isSent == true) {
                                flexDirectionRow()
                            } else {
                                flexDirection(FlexDirection.ROW_REVERSE)
                            }
                        }
                        Image {
                            attr {
                                size(50f, 50f)
                                src(ImageUri.pageAssets("u=2231848948,299743226&fm=253&gp=0.jpg"))
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
                        size(pagerData.pageViewWidth * 0.68f, 40f)
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
                        backgroundColor(Color.GREEN)
                        width(70f)
                        height(40f)
                    }
                }
            }
        }
    }
}