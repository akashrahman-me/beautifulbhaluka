package com.akash.beautifulbhaluka.presentation.screens.social.messenger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.domain.model.Conversation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

/**
 * ViewModel for Messenger screen
 */
class MessengerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MessengerUiState())
    val uiState: StateFlow<MessengerUiState> = _uiState.asStateFlow()

    init {
        loadConversations()
    }

    fun onAction(action: MessengerAction) {
        when (action) {
            is MessengerAction.LoadConversations -> loadConversations()
            is MessengerAction.Refresh -> refresh()
            is MessengerAction.Search -> search(action.query)
            is MessengerAction.SelectFilter -> selectFilter(action.filter)
            is MessengerAction.DeleteConversation -> deleteConversation(action.conversationId)
            is MessengerAction.PinConversation -> pinConversation(action.conversationId)
            is MessengerAction.MarkAsRead -> markAsRead(action.conversationId)
        }
    }

    private fun loadConversations() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                // Mock data - replace with actual repository call
                val conversations = getMockConversations()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        conversations = conversations,
                        filteredConversations = conversations
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message)
                }
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }

            try {
                val conversations = getMockConversations()
                _uiState.update {
                    it.copy(
                        isRefreshing = false,
                        conversations = conversations,
                        filteredConversations = filterConversations(conversations, it.searchQuery, it.selectedFilter)
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isRefreshing = false) }
            }
        }
    }

    private fun search(query: String) {
        _uiState.update {
            it.copy(
                searchQuery = query,
                filteredConversations = filterConversations(it.conversations, query, it.selectedFilter)
            )
        }
    }

    private fun selectFilter(filter: ConversationFilter) {
        _uiState.update {
            it.copy(
                selectedFilter = filter,
                filteredConversations = filterConversations(it.conversations, it.searchQuery, filter)
            )
        }
    }

    private fun deleteConversation(conversationId: String) {
        _uiState.update {
            val updatedConversations = it.conversations.filter { conv -> conv.id != conversationId }
            it.copy(
                conversations = updatedConversations,
                filteredConversations = filterConversations(updatedConversations, it.searchQuery, it.selectedFilter)
            )
        }
    }

    private fun pinConversation(conversationId: String) {
        _uiState.update {
            val updatedConversations = it.conversations.map { conv ->
                if (conv.id == conversationId) conv.copy(isPinned = !conv.isPinned)
                else conv
            }.sortedByDescending { conv -> conv.isPinned }

            it.copy(
                conversations = updatedConversations,
                filteredConversations = filterConversations(updatedConversations, it.searchQuery, it.selectedFilter)
            )
        }
    }

    private fun markAsRead(conversationId: String) {
        _uiState.update {
            val updatedConversations = it.conversations.map { conv ->
                if (conv.id == conversationId) conv.copy(unreadCount = 0)
                else conv
            }

            it.copy(
                conversations = updatedConversations,
                filteredConversations = filterConversations(updatedConversations, it.searchQuery, it.selectedFilter)
            )
        }
    }

    private fun filterConversations(
        conversations: List<Conversation>,
        query: String,
        filter: ConversationFilter
    ): List<Conversation> {
        var filtered = conversations

        // Apply filter
        filtered = when (filter) {
            ConversationFilter.ALL -> filtered
            ConversationFilter.UNREAD -> filtered.filter { it.unreadCount > 0 }
            ConversationFilter.PINNED -> filtered.filter { it.isPinned }
        }

        // Apply search
        if (query.isNotBlank()) {
            filtered = filtered.filter {
                it.participantName.contains(query, ignoreCase = true) ||
                        it.lastMessage.contains(query, ignoreCase = true)
            }
        }

        return filtered
    }

    private fun getMockConversations(): List<Conversation> {
        return listOf(
            Conversation(
                id = "1",
                participantId = "user1",
                participantName = "Sarah Johnson",
                participantAvatar = "https://i.pravatar.cc/150?img=1",
                lastMessage = "Hey! How are you doing?",
                lastMessageTime = Date(System.currentTimeMillis() - 3600000),
                unreadCount = 3,
                isOnline = true,
                isPinned = true
            ),
            Conversation(
                id = "2",
                participantId = "user2",
                participantName = "Michael Chen",
                participantAvatar = "https://i.pravatar.cc/150?img=12",
                lastMessage = "Thanks for the help yesterday!",
                lastMessageTime = Date(System.currentTimeMillis() - 7200000),
                unreadCount = 0,
                isOnline = false
            ),
            Conversation(
                id = "3",
                participantId = "user3",
                participantName = "Emma Wilson",
                participantAvatar = "https://i.pravatar.cc/150?img=5",
                lastMessage = "Let's catch up this weekend",
                lastMessageTime = Date(System.currentTimeMillis() - 86400000),
                unreadCount = 1,
                isOnline = true
            ),
            Conversation(
                id = "4",
                participantId = "user4",
                participantName = "James Anderson",
                participantAvatar = "https://i.pravatar.cc/150?img=13",
                lastMessage = "Perfect! See you then 👍",
                lastMessageTime = Date(System.currentTimeMillis() - 172800000),
                unreadCount = 0,
                isOnline = false
            ),
            Conversation(
                id = "5",
                participantId = "user5",
                participantName = "Olivia Taylor",
                participantAvatar = "https://i.pravatar.cc/150?img=9",
                lastMessage = "Did you get my email?",
                lastMessageTime = Date(System.currentTimeMillis() - 259200000),
                unreadCount = 5,
                isOnline = true,
                isPinned = true
            )
        )
    }
}

