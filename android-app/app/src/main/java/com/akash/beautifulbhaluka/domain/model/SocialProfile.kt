package com.akash.beautifulbhaluka.domain.model

data class SocialProfile(
    val userId: String,
    val userName: String,
    val userProfileImage: String,
    val coverImage: String,
    val bio: String = "",
    val location: String = "",
    val website: String = "",
    val postsCount: Int = 0,
    val friendsCount: Int = 0,
    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val isFollowing: Boolean = false,
    val isFriend: Boolean = false
)

