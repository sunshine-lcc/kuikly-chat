/*
 * Tencent is pleased to support the open source community by making KuiklyUI
 * available.
 * Copyright (C) 2025 THL A29 Limited, a Tencent company. All rights reserved.
 * Licensed under the License of KuiklyUI;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * https://github.com/Tencent-TDS/KuiklyUI/blob/main/LICENSE
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.myapplication.base

import com.tencent.kuikly.core.base.*
import com.tencent.kuikly.core.base.attr.ImageUri
import com.tencent.kuikly.core.directives.vif
import com.tencent.kuikly.core.module.RouterModule
import com.tencent.kuikly.core.views.Image
import com.tencent.kuikly.core.views.Text
import com.tencent.kuikly.core.views.View
import com.tencent.kuikly.core.reactive.handler.*

internal class NavigationBar: ComposeView<NavigationBarAttr, ComposeEvent>() {
    override fun createEvent(): ComposeEvent {
        return ComposeEvent()
    }

    override fun createAttr(): NavigationBarAttr {
        return NavigationBarAttr()
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


internal class NavigationBarAttr : ComposeAttr() {
    var title : String by observable("")
    var backDisable = false
    var moreButton = false
}

internal fun ViewContainer<*, *>.NavBar(init: NavigationBar.() -> Unit) {
    addChild(NavigationBar(), init)
}