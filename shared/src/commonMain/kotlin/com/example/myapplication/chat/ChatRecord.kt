package com.example.myapplication.chat

class ChatRecord(
    val uid: Int?,
    val userId: Int?,
    val timeStamp: Long?,
    val content: String?,
    val isSent: Boolean?,
    val type: MessageType?,
)

enum class MessageType {
    NORMAL, LOCATION, CALL
}