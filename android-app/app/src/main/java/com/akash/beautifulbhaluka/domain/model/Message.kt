package com.akash.beautifulbhaluka.domain.model

import java.util.*

/**
 * Domain model for a chat message
 */
data class Message(
    val id: String,
    val conversationId: String,
    val senderId: String,
    val senderName: String,
    val senderAvatar: String,
    val content: String,
    val timestamp: Date,
    val isRead: Boolean,
    val isSent: Boolean,
    val messageType: MessageType = MessageType.TEXT,
    val mediaUrl: String? = null
)

enum class MessageType {
    TEXT,
    IMAGE,
    VIDEO,
    AUDIO,
    FILE
}

