package com.example.myapplication.views

import com.tencent.kuikly.core.base.Color
import com.tencent.kuikly.core.base.ComposeView
import com.tencent.kuikly.core.base.ComposeAttr
import com.tencent.kuikly.core.base.ComposeEvent
import com.tencent.kuikly.core.base.ViewBuilder
import com.tencent.kuikly.core.base.ViewContainer
import com.tencent.kuikly.core.base.attr.ImageUri
import com.tencent.kuikly.core.directives.vif
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
                    paddingTop(ctx.pagerData.statusBarHeight)
                    backgroundColor(Color.WHITE)
                }
                // nav bar
                View {
                    attr {
                        height(44f)
                        allCenter()
                    }

                    Text {
                        attr {
                            text(ctx.attr.title)
                            color(Color.BLACK)
                            fontSize(17f)
                        }
                    }

                }

                vif({!ctx.attr.backDisable}) {
                    Image {
                        attr {
                            absolutePosition(12f + getPager().pageData.statusBarHeight, 12f, 12f, 12f)
                            size(10f, 17f)
                            src("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAsAAAASBAMAAAB/WzlGAAAAElBMVEUAAAAAAAAAAAAAAAAAAAAAAADgKxmiAAAABXRSTlMAIN/PELVZAGcAAAAkSURBVAjXYwABQTDJqCQAooSCHUAcVROCHBiFECTMhVoEtRYA6UMHzQlOjQIAAAAASUVORK5CYII=")
                        }
                        event {
                            click {
                                getPager().acquireModule<RouterModule>(RouterModule.MODULE_NAME).closePage()
                            }
                        }
                    }
                }
                vif({ctx.attr.moreButton}) {
                    Image {
                        attr {
                            absolutePosition(12f + getPager().pageData.statusBarHeight, -17f - 12f + getPager().pageData.pageViewWidth, 12f, 12f)
                            size(17f, 17f)
                            src(ImageUri.pageAssets("3226aad105442dce35b8460d490074b8.jpeg"))
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