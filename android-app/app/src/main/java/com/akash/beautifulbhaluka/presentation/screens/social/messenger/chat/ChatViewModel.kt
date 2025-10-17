package com.akash.beautifulbhaluka.presentation.screens.social.messenger.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.domain.model.Message
import com.akash.beautifulbhaluka.domain.model.MessageType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

/**
 * ViewModel for Chat screen
 */
class ChatViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    fun onAction(action: ChatAction) {
        when (action) {
            is ChatAction.LoadMessages -> loadMessages(action.conversationId)
            is ChatAction.UpdateMessageText -> updateMessageText(action.text)
            is ChatAction.SendMessage -> sendMessage()
            is ChatAction.DeleteMessage -> deleteMessage(action.messageId)
            is ChatAction.ClearError -> clearError()
        }
    }

    private fun loadMessages(conversationId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                // Mock data - replace with actual repository call
                val messages = getMockMessages(conversationId)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        messages = messages,
                        conversationId = conversationId,
                        participantName = "Sarah Johnson",
                        participantAvatar = "https://i.pravatar.cc/150?img=1",
                        isOnline = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message)
                }
            }
        }
    }

    private fun updateMessageText(text: String) {
        _uiState.update { it.copy(messageText = text) }
    }

    private fun sendMessage() {
        val currentState = _uiState.value
        val messageText = currentState.messageText.trim()

        if (messageText.isEmpty()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSending = true) }

            try {
                // Create new message
                val newMessage = Message(
                    id = UUID.randomUUID().toString(),
                    conversationId = currentState.conversationId,
                    senderId = "current_user",
                    senderName = "You",
                    senderAvatar = "https://i.pravatar.cc/150?img=10",
                    content = messageText,
                    timestamp = Date(),
                    isRead = true,
                    isSent = true,
                    messageType = MessageType.TEXT
                )

                // Add to messages list
                _uiState.update {
                    it.copy(
                        isSending = false,
                        messageText = "",
                        messages = it.messages + newMessage
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isSending = false, error = e.message)
                }
            }
        }
    }

    private fun deleteMessage(messageId: String) {
        _uiState.update {
            it.copy(messages = it.messages.filter { msg -> msg.id != messageId })
        }
    }

    private fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    private fun getMockMessages(conversationId: String): List<Message> {
        val now = System.currentTimeMillis()
        return listOf(
            Message(
                id = "1",
                conversationId = conversationId,
                senderId = "user1",
                senderName = "Sarah Johnson",
                senderAvatar = "https://i.pravatar.cc/150?img=1",
                content = "Hey! How are you doing?",
                timestamp = Date(now - 3600000),
                isRead = true,
                isSent = true,
                messageType = MessageType.TEXT
            ),
            Message(
                id = "2",
                conversationId = conversationId,
                senderId = "current_user",
                senderName = "You",
                senderAvatar = "https://i.pravatar.cc/150?img=10",
                content = "I'm good! Just finished work. How about you?",
                timestamp = Date(now - 3500000),
                isRead = true,
                isSent = true,
                messageType = MessageType.TEXT
            ),
            Message(
                id = "3",
                conversationId = conversationId,
                senderId = "user1",
                senderName = "Sarah Johnson",
                senderAvatar = "https://i.pravatar.cc/150?img=1",
                content = "Same here! Want to grab coffee this weekend?",
                timestamp = Date(now - 3400000),
                isRead = true,
                isSent = true,
                messageType = MessageType.TEXT
            ),
            Message(
                id = "4",
                conversationId = conversationId,
                senderId = "current_user",
                senderName = "You",
                senderAvatar = "https://i.pravatar.cc/150?img=10",
                content = "Sounds great! Saturday afternoon works for me",
                timestamp = Date(now - 3300000),
                isRead = true,
                isSent = true,
                messageType = MessageType.TEXT
            ),
            Message(
                id = "5",
                conversationId = conversationId,
                senderId = "user1",
                senderName = "Sarah Johnson",
                senderAvatar = "https://i.pravatar.cc/150?img=1",
                content = "Perfect! Let's meet at the usual place at 3 PM?",
                timestamp = Date(now - 3200000),
                isRead = true,
                isSent = true,
                messageType = MessageType.TEXT
            ),
            Message(
                id = "6",
                conversationId = conversationId,
                senderId = "current_user",
                senderName = "You",
                senderAvatar = "https://i.pravatar.cc/150?img=10",
                content = "See you then! 👍",
                timestamp = Date(now - 3100000),
                isRead = false,
                isSent = true,
                messageType = MessageType.TEXT
            )
        )
    }
}

