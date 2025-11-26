package com.akash.beautifulbhaluka.presentation.screens.bookbuddy.author

import com.akash.beautifulbhaluka.domain.model.Reaction
import com.akash.beautifulbhaluka.domain.model.Writing
import com.akash.beautifulbhaluka.domain.model.WritingCategory

data class AuthorProfile(
    val authorId: String = "",
    val authorName: String = "",
    val profileImageUrl: String? = null,
    val bio: String = "",
    val totalPosts: Int = 0,
    val totalLikes: Int = 0,
    val totalComments: Int = 0
)

enum class PostSortType {
    NEWEST, OLDEST, MOST_LIKED, MOST_COMMENTED
}

enum class PostStatus {
    ALL, PUBLISHED, DRAFT
}

data class AuthorWritingsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val authorProfile: AuthorProfile = AuthorProfile(),
    val writings: List<Writing> = emptyList(),
    val filteredWritings: List<Writing> = emptyList(),
    val selectedCategory: WritingCategory = WritingCategory.ALL,
    val selectedSortType: PostSortType = PostSortType.NEWEST,
    val selectedStatus: PostStatus = PostStatus.PUBLISHED,
    val isOwner: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val writingToDelete: String? = null
)

sealed class AuthorWritingsAction {
    data class LoadWritings(val authorId: String) : AuthorWritingsAction()
    object Refresh : AuthorWritingsAction()
    data class NavigateToDetail(val writingId: String) : AuthorWritingsAction()
    data class ReactToWriting(val writingId: String, val reaction: Reaction) :
        AuthorWritingsAction()

    data class FilterByCategory(val category: WritingCategory) : AuthorWritingsAction()
    data class SortBy(val sortType: PostSortType) : AuthorWritingsAction()
    data class FilterByStatus(val status: PostStatus) : AuthorWritingsAction()
    data class DeleteWriting(val writingId: String) : AuthorWritingsAction()
    data class ShowDeleteDialog(val writingId: String) : AuthorWritingsAction()
    object DismissDeleteDialog : AuthorWritingsAction()
    object NavigateToPublish : AuthorWritingsAction()
    object NavigateToDrafts : AuthorWritingsAction()
    object NavigateToEditProfile : AuthorWritingsAction()
    data class EditWriting(val writingId: String) : AuthorWritingsAction()
}

