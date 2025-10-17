package com.akash.beautifulbhaluka.domain.model

data class Friend(
    val userId: String,
    val userName: String,
    val profileImage: String,
    val mutualFriends: Int = 0,
    val isFriend: Boolean = true
)

