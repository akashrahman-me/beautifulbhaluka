package com.akash.beautifulbhaluka.presentation.screens.social.messenger

import com.akash.beautifulbhaluka.domain.model.Conversation

/**
 * UI State for Messenger screen
 */
data class MessengerUiState(
    val isLoading: Boolean = false,
    val conversations: List<Conversation> = emptyList(),
    val filteredConversations: List<Conversation> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val selectedFilter: ConversationFilter = ConversationFilter.ALL
)

enum class ConversationFilter {
    ALL,
    UNREAD,
    PINNED
}

sealed class MessengerAction {
    object LoadConversations : MessengerAction()
    object Refresh : MessengerAction()
    data class Search(val query: String) : MessengerAction()
    data class SelectFilter(val filter: ConversationFilter) : MessengerAction()
    data class DeleteConversation(val conversationId: String) : MessengerAction()
    data class PinConversation(val conversationId: String) : MessengerAction()
    data class MarkAsRead(val conversationId: String) : MessengerAction()
}

