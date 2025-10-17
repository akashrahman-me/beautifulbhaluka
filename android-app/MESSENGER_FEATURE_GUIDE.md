# Messenger Feature Documentation

## 📱 Overview
A complete, ultra-modern messenger system integrated into the Beautiful Bhaluka Social platform. The messenger provides real-time chat functionality with a clean, minimalistic design following Material 3 guidelines.

---

## 🏗️ Architecture

### Layer Structure (Following MVVM + Repository Pattern)

```
messenger/
├── domain/
│   └── model/
│       ├── Message.kt          # Message domain model
│       └── Conversation.kt     # Conversation domain model
│
└── presentation/
    └── screens/
        └── social/
            └── messenger/
                ├── MessengerScreen.kt       # Conversations list screen
                ├── MessengerViewModel.kt    # List screen view model
                ├── MessengerUiState.kt     # UI state & actions
                │
                └── chat/
                    ├── ChatScreen.kt        # Individual chat screen
                    ├── ChatViewModel.kt     # Chat view model
                    └── ChatUiState.kt      # Chat state & actions
```

---

## 🎨 Design Principles

### Visual Design
- **Ultra-modern & Minimalistic**: Clean bubble-style messages with proper alignment
- **Professional Spacing**: Consistent 16dp rhythm with breathing room
- **Clear Hierarchy**: Online indicators, read receipts, typing indicators
- **Smooth Interactions**: Swipe actions, contextual menus, smooth scrolling
- **Material 3 Theming**: Full support for light/dark themes with dynamic colors

### UX Features
1. **Conversation List**
   - Search functionality with real-time filtering
   - Filter chips (All, Unread, Pinned)
   - Online status indicators
   - Unread message badges
   - Pin/unpin conversations
   - Swipe actions for quick access

2. **Chat Interface**
   - Bubble-style messages with proper alignment
   - Read receipts (single/double check marks)
   - Typing indicators
   - Message timestamps
   - Copy/delete message actions
   - Auto-scroll to latest messages
   - Multi-line text input
   - Attachment support (placeholder for future)

---

## 📊 Data Models

### Message
```kotlin
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
    TEXT, IMAGE, VIDEO, AUDIO, FILE
}
```

### Conversation
```kotlin
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
```

---

## 🎯 Features Implemented

### Messenger Screen (Conversations List)
✅ **Search & Filter**
- Real-time search across names and messages
- Filter chips: All, Unread, Pinned
- No results state with helpful message

✅ **Conversation Cards**
- Avatar with online indicator (green dot)
- Name and last message preview
- Timestamp with smart formatting
- Unread message badge
- Pin indicator badge
- Typing indicator animation
- Contextual menu (Pin, Mark as read, Delete)

✅ **States**
- Loading state with spinner
- Empty state with call-to-action
- Error state with retry
- No results state for search/filter

✅ **Actions**
- Click conversation → Open chat
- New message button → Create conversation
- Pin/unpin conversations
- Delete conversations
- Mark as read
- Search conversations
- Pull to refresh

---

### Chat Screen (Individual Conversation)

✅ **Message Display**
- Bubble design with proper alignment (sent vs received)
- Sender avatars
- Message timestamps
- Read receipts (✓ sent, ✓✓ read)
- Message grouping
- Auto-scroll to latest

✅ **Top Bar**
- Back navigation
- Participant name and avatar
- Online/typing status
- Action buttons: Video call, Voice call, More options

✅ **Input Bar**
- Multi-line text input
- Attach file button
- Send button with loading state
- Disabled state when empty
- Modern rounded design

✅ **Interactions**
- Long-press message for menu
- Copy message
- Delete own messages
- Auto-scroll on new messages

---

## 🔧 Integration

### Navigation Structure
```kotlin
SocialTab.MESSAGES -> 
    NavHost(messengerNavController) {
        composable("messenger") { 
            MessengerScreen() 
        }
        composable("chat/{conversationId}") { 
            ChatScreen(conversationId) 
        }
    }
```

### Tab Integration
The messenger is added as a new tab in `SocialScreen.kt`:
- **Position**: Second tab (between Feed and Profile)
- **Icon**: Material Icons.AutoMirrored.Filled.Message
- **Label**: "Messages"

---

## 📱 UI Components

### Reusable Components Created

1. **ConversationCard**
   - Avatar with online indicator
   - Participant name
   - Last message preview
   - Timestamp
   - Unread badge
   - Pin indicator
   - Context menu

2. **MessageBubble**
   - Adaptive alignment (sent/received)
   - Rounded corners with proper radius
   - Timestamp below bubble
   - Read receipt indicators
   - Long-press menu

3. **ChatInputBar**
   - Text field with multi-line support
   - Attach button
   - Send button with loading state
   - Proper padding and elevation

4. **FilterChipsRow**
   - Horizontal scrollable chips
   - Selection state
   - Material 3 FilterChip component

---

## 🎨 Design Tokens

### Colors
- **Primary Messages**: `MaterialTheme.colorScheme.primary`
- **Received Messages**: `MaterialTheme.colorScheme.surfaceVariant`
- **Online Indicator**: `Color(0xFF4CAF50)` (Green)
- **Unread Badge**: `MaterialTheme.colorScheme.primary`

### Spacing
- **Card Padding**: 16dp horizontal, 12dp vertical
- **Message Bubble Padding**: 16dp horizontal, 10dp vertical
- **Section Spacing**: 8dp between items
- **Input Bar Padding**: 16dp all around

### Typography
- **Participant Name**: `titleMedium` (Bold for unread)
- **Last Message**: `bodyMedium` (Medium for unread)
- **Timestamp**: `bodySmall`
- **Message Content**: `bodyLarge`

### Border Radius
- **Message Bubbles**: 20dp (adaptive corners)
- **Search Field**: 16dp
- **Input Bar**: 24dp
- **Avatars**: CircleShape
- **Cards**: 20dp

---

## 🔄 State Management

### MessengerViewModel States
```kotlin
data class MessengerUiState(
    val isLoading: Boolean = false,
    val conversations: List<Conversation> = emptyList(),
    val filteredConversations: List<Conversation> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val selectedFilter: ConversationFilter = ConversationFilter.ALL
)
```

### ChatViewModel States
```kotlin
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
```

---

## 🚀 Future Enhancements

### Planned Features
1. **Media Support**
   - Image messages
   - Video messages
   - Voice messages
   - File attachments

2. **Advanced Features**
   - Voice/video calls integration
   - Group chats
   - Message reactions (emoji)
   - Message forwarding
   - Star/bookmark messages
   - Message search within chat

3. **Real-time Features**
   - WebSocket integration
   - Real-time typing indicators
   - Real-time message delivery
   - Push notifications

4. **User Experience**
   - Voice input
   - Message templates
   - Quick replies
   - Message scheduling
   - End-to-end encryption indicator

5. **Repository Layer**
   - Replace mock data with real API calls
   - Local database caching (Room)
   - Offline support with sync
   - Message pagination

---

## 📝 Usage Example

```kotlin
// Navigate to messenger
selectedTab = SocialTab.MESSAGES

// Open specific conversation
messengerNavController.navigate("chat/conversation_id_123")

// Handle actions
viewModel.onAction(MessengerAction.Search("John"))
viewModel.onAction(MessengerAction.SelectFilter(ConversationFilter.UNREAD))
viewModel.onAction(MessengerAction.PinConversation(conversationId))

// Send message
chatViewModel.onAction(ChatAction.UpdateMessageText("Hello!"))
chatViewModel.onAction(ChatAction.SendMessage)
```

---

## 🧪 Testing Considerations

### Unit Tests Needed
- [ ] MessengerViewModel logic
- [ ] ChatViewModel logic
- [ ] Filter and search functionality
- [ ] Message sending flow
- [ ] State updates

### UI Tests Needed
- [ ] Conversation card rendering
- [ ] Message bubble alignment
- [ ] Search functionality
- [ ] Filter selection
- [ ] Message sending
- [ ] Navigation flow

---

## ✅ Implementation Checklist

- [x] Domain models (Message, Conversation)
- [x] UI states and actions
- [x] ViewModels with business logic
- [x] Messenger screen with search & filters
- [x] Chat screen with message bubbles
- [x] Integration into SocialScreen
- [x] Navigation setup
- [x] Mock data for development
- [x] Loading, error, and empty states
- [x] Online/typing indicators
- [x] Read receipts
- [x] Contextual menus
- [x] Material 3 theming
- [x] Responsive design
- [ ] Repository implementation (TODO)
- [ ] API integration (TODO)
- [ ] Local database (TODO)
- [ ] Real-time updates (TODO)
- [ ] Push notifications (TODO)

---

## 📚 Related Files

### Core Implementation
- `domain/model/Message.kt`
- `domain/model/Conversation.kt`
- `presentation/screens/social/messenger/MessengerScreen.kt`
- `presentation/screens/social/messenger/MessengerViewModel.kt`
- `presentation/screens/social/messenger/MessengerUiState.kt`
- `presentation/screens/social/messenger/chat/ChatScreen.kt`
- `presentation/screens/social/messenger/chat/ChatViewModel.kt`
- `presentation/screens/social/messenger/chat/ChatUiState.kt`

### Integration
- `presentation/screens/social/SocialScreen.kt`

---

**The messenger feature is production-ready for UI/UX testing and can be connected to a backend service when ready.**

