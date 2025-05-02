package com.example.myapplication

import com.example.myapplication.base.BasePager
import com.example.myapplication.base.BottomNavigationBar
import com.example.myapplication.base.TopNavBar
import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.base.Color
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

@Page("ContactPage")
internal class ContactPage : BasePager() {
    private var contactsList by observableList<String>()

    override fun created() {
        super.created()
        contactsList.add("阿明")
        contactsList.add("阿红")
        contactsList.add("阿芳")
        contactsList.add("阿聪")
    }

    override fun viewWillUnload() {
        super.viewWillUnload()
        acquireModule<RouterModule>(RouterModule.MODULE_NAME).openPage("router")
    }

    override fun body(): ViewBuilder {
        val ctx = this
        return {
            attr {
                backgroundColor(Color.WHITE)
            }
            TopNavBar {
                attr {
                    title = "联系人"
                    backDisable = true
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
                        src(ImageUri.commonAssets("bbe436e4_E775769_910a88b4.png"))
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
                    marginTop(20f)
                }
                vfor ({ ctx.contactsList }) { item ->
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
            BottomNavigationBar {
                attr {}
            }
        }
    }
}