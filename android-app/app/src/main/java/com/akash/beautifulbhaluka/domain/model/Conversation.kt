package com.akash.beautifulbhaluka.domain.model

import java.util.*

/**
 * Domain model for a conversation/chat
 */
data class Conversation(
    val id: String,
    val participantId: String,
    val participantName: String,
    val participantAvatar: String,
    val lastMessage: String,
    val lastMessageTime: Date,
    val unreadCount: Int,
    val isOnline: Boolean,
    val isTyping: Boolean = false,
    val isPinned: Boolean = false
)

