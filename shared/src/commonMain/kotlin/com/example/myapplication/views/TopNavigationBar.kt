package com.example.myapplication.views

import com.tencent.kuikly.core.base.Color
import com.tencent.kuikly.core.base.ComposeView
import com.tencent.kuikly.core.base.ComposeAttr
import com.tencent.kuikly.core.base.ComposeEvent
import com.tencent.kuikly.core.base.ViewBuilder
import com.tencent.kuikly.core.base.ViewContainer
import com.tencent.kuikly.core.base.attr.ImageUri
import com.tencent.kuikly.core.directives.vif
import com.tencent.kuikly.core.layout.FlexAlign
import com.tencent.kuikly.core.layout.FlexJustifyContent
import com.tencent.kuikly.core.module.RouterModule
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.views.Image
import com.tencent.kuikly.core.views.Text
import com.tencent.kuikly.core.views.View

internal class TopNavigationBarView: ComposeView<TopNavigationBarViewAttr, TopNavigationBarViewEvent>() {
    
    override fun createEvent(): TopNavigationBarViewEvent {
        return TopNavigationBarViewEvent()
    }

    override fun createAttr(): TopNavigationBarViewAttr {
        return TopNavigationBarViewAttr()
    }

    override fun body(): ViewBuilder {
        val ctx = this
        return {
            View {
                attr {
                    marginTop(ctx.pagerData.statusBarHeight)
                    backgroundColor(Color.WHITE)
                    allCenter()
                    flexDirectionRow()
                    height(50f)
                }
                View {
                    attr {
                        width(50f)
                        allCenter()
                    }
                    vif ({ !ctx.attr.backDisable }) {
                        Image {
                            attr {
                                size(20f, 20f)
                                src(ImageUri.commonAssets("left-arrow.png"))
                            }
                            event {
                                click {
                                    getPager().acquireModule<RouterModule>(RouterModule.MODULE_NAME).closePage()
                                }
                            }
                        }
                    }
                }
                Text {
                    attr {
                        text(ctx.attr.title)
                        color(Color.BLACK)
                        width(pagerData.pageViewWidth - 100f)
                        textAlignCenter()
                        fontSize(17f)
                    }
                }
                View {
                    attr {
                        width(50f)
                        allCenter()
                    }
                    vif ({ ctx.attr.moreButton }) {
                        Image {
                            attr {
                                size(20f, 20f)
                                src(ImageUri.commonAssets("more.png"))
                            }
                            event {
                                click {
                                    getPager().acquireModule<RouterModule>(RouterModule.MODULE_NAME).closePage()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


internal class TopNavigationBarViewAttr : ComposeAttr() {
    var title : String by observable("")
    var backDisable = false
    var moreButton = false
}

internal class TopNavigationBarViewEvent : ComposeEvent() {
    
}

internal fun ViewContainer<*, *>.TopNavBar(init: TopNavigationBarView.() -> Unit) {
    addChild(TopNavigationBarView(), init)
}