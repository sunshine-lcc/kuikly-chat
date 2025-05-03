package com.example.myapplication.home.view

import com.example.myapplication.home.HomePage
import com.tencent.kuikly.core.base.Color
import com.tencent.kuikly.core.base.ComposeView
import com.tencent.kuikly.core.base.ComposeAttr
import com.tencent.kuikly.core.base.ComposeEvent
import com.tencent.kuikly.core.base.ViewBuilder
import com.tencent.kuikly.core.base.ViewContainer
import com.tencent.kuikly.core.base.attr.ImageUri
import com.tencent.kuikly.core.views.Image
import com.tencent.kuikly.core.views.Text
import com.tencent.kuikly.core.views.View

internal class BottomNavigationBarView: ComposeView<BottomNavigationBarViewAttr, BottomNavigationBarViewEvent>() {
    
    override fun createEvent(): BottomNavigationBarViewEvent {
        return BottomNavigationBarViewEvent()
    }

    override fun createAttr(): BottomNavigationBarViewAttr {
        return BottomNavigationBarViewAttr()
    }

    override fun body(): ViewBuilder {
        val ctx = this
        return {
            View {
                attr {
                    flexDirectionRow()
                    height(75f)
                    allCenter()
                }
                View {
                    attr {
                        width(getPager().pageData.pageViewWidth / 3)
                        flexDirectionColumn()
                        backgroundColor(if ((getPager() as HomePage).getStage() == "Message") Color.RED else Color.TRANSPARENT)
                        allCenter()
                    }
                    event {
                        click { clickParams ->
                            val i = getPager() as HomePage
                            i.setStage("Message")
                        }
                    }
                    Image {
                        attr {
                            // absolutePosition(12f + getPager().pageData.statusBarHeight, -17f - 12f + getPager().pageData.pageViewWidth, 12f, 12f)
                            size(30f, 30f)
                            src(ImageUri.pageAssets("R-C.png"))
                        }
                    }
                    Text {
                        attr {
                            color(Color.BLACK)
                            fontSize(20f)
                            text("消息")
                        }
                    }
                }
                View {
                    attr {
                        width(getPager().pageData.pageViewWidth / 3)
                        flexDirectionColumn()
                        allCenter()
                        backgroundColor(if ((getPager() as HomePage).getStage() == "Contact") Color.RED else Color.TRANSPARENT)
                    }
                    event {
                        click { clickParams ->
                            val i = getPager() as HomePage
                            i.setStage("Contact")
                        }
                    }
                    Image {
                        attr {
                            // absolutePosition(12f + getPager().pageData.statusBarHeight, -17f - 12f + getPager().pageData.pageViewWidth, 12f, 12f)
                            size(30f, 30f)
                            src(ImageUri.pageAssets("0e42a72d-39fc-4cef-8d47-77f957752262_medium_thumb.jpg"))
                        }
                    }
                    Text {
                        attr {
                            color(Color.BLACK)
                            fontSize(20f)
                            text("联系人")
                        }
                    }
                }
                View {
                    attr {
                        width(getPager().pageData.pageViewWidth / 3)
                        flexDirectionColumn()
                        allCenter()
                        backgroundColor(if ((getPager() as HomePage).getStage() == "Setting") Color.RED else Color.TRANSPARENT)
                    }
                    event {
                        click { clickParams ->
                            val i = getPager() as HomePage
                            i.setStage("Setting")
                        }
                    }
                    Image {
                        attr {
                            // absolutePosition(12f + getPager().pageData.statusBarHeight, -17f - 12f + getPager().pageData.pageViewWidth, 12f, 12f)
                            size(30f, 30f)
                            src(ImageUri.pageAssets("4106126ea416e23288ceeffe672d0ad7.jpg"))
                        }
                    }
                    Text {
                        attr {
                            color(Color.BLACK)
                            fontSize(20f)
                            text("设置")
                        }
                    }
                }
            }
        }
    }
}


internal class BottomNavigationBarViewAttr : ComposeAttr() {

}

internal class BottomNavigationBarViewEvent : ComposeEvent() {
    
}

internal fun ViewContainer<*, *>.BottomNavBar(init: BottomNavigationBarView.() -> Unit) {
    addChild(BottomNavigationBarView(), init)
}