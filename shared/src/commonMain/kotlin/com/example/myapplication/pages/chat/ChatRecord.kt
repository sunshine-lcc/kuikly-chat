package com.example.myapplication.pages.chat

class ChatRecord(
    val uid: Int?,
    val userId: Int?,
    val timeStamp: Long?,
    val content: String?,
    val sent: Boolean?,
    val type: MessageType?,
)

enum class MessageType {
    NORMAL, LOCATION, CALL
}