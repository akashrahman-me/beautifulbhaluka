package com.akash.beautifulbhaluka.presentation.screens.social.messenger.chat

import com.akash.beautifulbhaluka.domain.model.Message

/**
 * UI State for Chat screen
 */
data class ChatUiState(
    val isLoading: Boolean = false,
    val messages: List<Message> = emptyList(),
    val conversationId: String = "",
    val participantName: String = "",
    val participantAvatar: String = "",
    val isOnline: Boolean = false,
    val isTyping: Boolean = false,
    val messageText: String = "",
    val isSending: Boolean = false,
    val error: String? = null
)

sealed class ChatAction {
    data class LoadMessages(val conversationId: String) : ChatAction()
    data class UpdateMessageText(val text: String) : ChatAction()
    object SendMessage : ChatAction()
    data class DeleteMessage(val messageId: String) : ChatAction()
    object ClearError : ChatAction()
}

