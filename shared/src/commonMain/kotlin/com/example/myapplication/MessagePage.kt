package com.example.myapplication

import com.example.myapplication.base.BasePager
import com.example.myapplication.base.NavBar
import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.base.Color
import com.tencent.kuikly.core.base.ComposeEvent
import com.tencent.kuikly.core.base.ComposeView
import com.tencent.kuikly.core.base.ViewBuilder
import com.tencent.kuikly.core.base.attr.ImageUri
import com.tencent.kuikly.core.directives.vfor
import com.tencent.kuikly.core.module.RouterModule
import com.tencent.kuikly.core.reactive.handler.observableList
import com.tencent.kuikly.core.views.Image
import com.tencent.kuikly.core.views.Input
import com.tencent.kuikly.core.views.List
import com.tencent.kuikly.core.views.Text
import com.tencent.kuikly.core.views.View

@Page("MessagePage")
internal class MessagePage : BasePager() {
    private var defaultList by observableList<Int>()

    override fun created() {
        super.created()
        for (i in 0 until 5) {
            defaultList.add(i)
        }
    }

    override fun body(): ViewBuilder {
        val ctx = this
        return {
            attr {
                backgroundColor(Color.WHITE)
            }
            NavBar {
                attr {
                    title = "首页"
                    backDisable = true
                    moreButton = true
                }
            }

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

            List {
                attr {
                    flex(1f)
                    marginTop(15f)
                }
                vfor({ctx.defaultList}) { item ->
                    View {
                        attr {
                            flexDirectionRow()
                            marginTop(10f)
                            alignItemsCenter()
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
    }
}
