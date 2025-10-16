package com.akash.beautifulbhaluka.presentation.screens.social.profile

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.ui.graphics.vector.ImageVector
import com.akash.beautifulbhaluka.domain.model.Post
import com.akash.beautifulbhaluka.domain.model.SocialProfile

data class SocialProfileUiState(
    val isLoading: Boolean = false,
    val profile: SocialProfile? = null,
    val posts: List<Post> = emptyList(),
    val selectedTab: ProfileTab = ProfileTab.POSTS,
    val isEditMode: Boolean = false,
    val editBio: String = "",
    val editLocation: String = "",
    val editWebsite: String = "",
    val isSaving: Boolean = false,
    val error: String? = null
)

enum class ProfileTab(val displayName: String, val icon: ImageVector) {
    POSTS("Posts", Icons.Outlined.Article),
    ABOUT("About", Icons.Outlined.Info),
    FRIENDS("Friends", Icons.Outlined.Group),
    PHOTOS("Photos", Icons.Outlined.Photo)
}

sealed class SocialProfileAction {
    data class LoadProfile(val userId: String) : SocialProfileAction()
    data class SelectTab(val tab: ProfileTab) : SocialProfileAction()
    object ToggleEditMode : SocialProfileAction()
    data class UpdateBio(val bio: String) : SocialProfileAction()
    data class UpdateLocation(val location: String) : SocialProfileAction()
    data class UpdateWebsite(val website: String) : SocialProfileAction()
    object SaveProfile : SocialProfileAction()
    object FollowUser : SocialProfileAction()
    object UnfollowUser : SocialProfileAction()
    object ChangeProfileImage : SocialProfileAction()
    object ChangeCoverImage : SocialProfileAction()
}
