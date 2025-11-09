package com.akash.beautifulbhaluka.data.repository

import com.akash.beautifulbhaluka.domain.model.Comment
import com.akash.beautifulbhaluka.domain.model.Post
import com.akash.beautifulbhaluka.domain.model.PostPrivacy
import com.akash.beautifulbhaluka.domain.model.SocialProfile
import com.akash.beautifulbhaluka.domain.model.StoryHighlight
import com.akash.beautifulbhaluka.domain.model.Friend
import com.akash.beautifulbhaluka.domain.model.Photo
import com.akash.beautifulbhaluka.domain.repository.SocialRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SocialRepositoryImpl : SocialRepository {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    private val currentUserId = "current_user_id"
    private val currentUserName = "আপনার নাম"
    private val currentUserImage = "https://ui-avatars.com/api/?name=User&background=random"

    init {
        // Initialize with mock data
        _posts.value = generateMockPosts()
    }

    override suspend fun getPosts(): Result<List<Post>> {
        delay(500) // Simulate network delay
        return Result.success(_posts.value.sortedByDescending { it.createdAt })
    }

    override suspend fun getPostById(postId: String): Result<Post> {
        delay(300)
        val post = _posts.value.find { it.id == postId }
        return if (post != null) {
            Result.success(post)
        } else {
            Result.failure(Exception("পোস্ট খুঁজে পাওয়া যায়নি"))
        }
    }

    override suspend fun getUserPosts(userId: String): Result<List<Post>> {
        delay(400)
        val userPosts = _posts.value.filter { it.userId == userId }
            .sortedByDescending { it.createdAt }
        return Result.success(userPosts)
    }

    override suspend fun createPost(
        content: String,
        images: List<String>,
        videoUrl: String?,
        privacy: PostPrivacy,
        location: String?
    ): Result<Post> {
        delay(800)
        val newPost = Post(
            id = "post_${System.currentTimeMillis()}",
            userId = currentUserId,
            userName = currentUserName,
            userProfileImage = currentUserImage,
            content = content,
            images = images,
            videoUrl = videoUrl,
            privacy = privacy,
            location = location,
            createdAt = System.currentTimeMillis()
        )
        _posts.value = listOf(newPost) + _posts.value
        return Result.success(newPost)
    }

    override suspend fun deletePost(postId: String): Result<Unit> {
        delay(400)
        _posts.value = _posts.value.filter { it.id != postId }
        return Result.success(Unit)
    }

    override suspend fun likePost(postId: String): Result<Unit> {
        delay(200)
        _posts.value = _posts.value.map { post ->
            if (post.id == postId) {
                post.copy(likes = post.likes + 1, isLiked = true)
            } else {
                post
            }
        }
        return Result.success(Unit)
    }

    override suspend fun unlikePost(postId: String): Result<Unit> {
        delay(200)
        _posts.value = _posts.value.map { post ->
            if (post.id == postId) {
                post.copy(likes = maxOf(0, post.likes - 1), isLiked = false)
            } else {
                post
            }
        }
        return Result.success(Unit)
    }

    override suspend fun sharePost(postId: String): Result<Unit> {
        delay(300)
        _posts.value = _posts.value.map { post ->
            if (post.id == postId) {
                post.copy(shares = post.shares + 1)
            } else {
                post
            }
        }
        return Result.success(Unit)
    }

    override suspend fun getComments(postId: String): Result<List<Comment>> {
        delay(400)
        return Result.success(generateMockComments(postId))
    }

    override suspend fun addComment(
        postId: String,
        content: String,
        images: List<String>,
        voiceUrl: String?
    ): Result<Comment> {
        delay(500)
        val comment = Comment(
            id = "comment_${System.currentTimeMillis()}",
            postId = postId,
            userId = currentUserId,
            userName = currentUserName,
            userProfileImage = currentUserImage,
            content = content,
            createdAt = System.currentTimeMillis()
        )

        _posts.value = _posts.value.map { post ->
            if (post.id == postId) {
                post.copy(comments = post.comments + 1)
            } else {
                post
            }
        }

        return Result.success(comment)
    }

    override suspend fun addReply(
        postId: String,
        parentCommentId: String,
        content: String,
        images: List<String>,
        voiceUrl: String?
    ): Result<Comment> {
        delay(500)
        val reply = Comment(
            id = "reply_${System.currentTimeMillis()}",
            postId = postId,
            userId = currentUserId,
            userName = currentUserName,
            userProfileImage = currentUserImage,
            content = content,
            createdAt = System.currentTimeMillis()
        )

        _posts.value = _posts.value.map { post ->
            if (post.id == postId) {
                post.copy(comments = post.comments + 1)
            } else {
                post
            }
        }

        return Result.success(reply)
    }

    override suspend fun getReplies(commentId: String): Result<List<Comment>> {
        delay(300)
        return Result.success(generateMockReplies(commentId))
    }

    override suspend fun deleteComment(commentId: String): Result<Unit> {
        delay(300)
        return Result.success(Unit)
    }

    override suspend fun likeComment(commentId: String): Result<Unit> {
        delay(200)
        return Result.success(Unit)
    }

    override suspend fun unlikeComment(commentId: String): Result<Unit> {
        delay(200)
        return Result.success(Unit)
    }

    override suspend fun getProfile(userId: String): Result<SocialProfile> {
        delay(500)
        val profile = SocialProfile(
            userId = userId,
            userName = if (userId == currentUserId) currentUserName else "ব্যবহারকারী ${
                userId.take(
                    5
                )
            }",
            userProfileImage = currentUserImage,
            coverImage = "https://picsum.photos/seed/cover/800/400",
            bio = "আমি ভালুকা থেকে একজন সক্রিয় সদস্য। আমি এই অ্যাপ্লিকেশনটি ব্যবহার করে আমার এলাকার সাথে যুক্ত থাকি।",
            location = "ভালুকা, ময়মনসিংহ",
            website = "www.beautifulbhaluka.com",
            postsCount = _posts.value.count { it.userId == userId },
            friendsCount = 234,
            followersCount = 567,
            followingCount = 345,
            isFollowing = userId != currentUserId,
            isFriend = userId != currentUserId
        )
        return Result.success(profile)
    }

    override suspend fun updateProfile(
        bio: String,
        location: String,
        website: String
    ): Result<SocialProfile> {
        delay(600)
        val profile = SocialProfile(
            userId = currentUserId,
            userName = currentUserName,
            userProfileImage = currentUserImage,
            coverImage = "https://picsum.photos/seed/cover/800/400",
            bio = bio,
            location = location,
            website = website,
            postsCount = _posts.value.count { it.userId == currentUserId },
            friendsCount = 234,
            followersCount = 567,
            followingCount = 345
        )
        return Result.success(profile)
    }

    override suspend fun updateProfileImage(imageUrl: String): Result<Unit> {
        delay(800)
        return Result.success(Unit)
    }

    override suspend fun updateCoverImage(imageUrl: String): Result<Unit> {
        delay(800)
        return Result.success(Unit)
    }

    override suspend fun followUser(userId: String): Result<Unit> {
        delay(400)
        return Result.success(Unit)
    }

    override suspend fun unfollowUser(userId: String): Result<Unit> {
        delay(400)
        return Result.success(Unit)
    }

    // Story Highlights
    override suspend fun getStoryHighlights(userId: String): Result<List<StoryHighlight>> {
        delay(400)
        return Result.success(generateMockStoryHighlights())
    }

    // Friends
    override suspend fun getFriends(userId: String): Result<List<Friend>> {
        delay(400)
        return Result.success(generateMockFriends())
    }

    // Photos
    override suspend fun getPhotos(userId: String): Result<List<Photo>> {
        delay(400)
        return Result.success(generateMockPhotos())
    }

    override fun observePosts(): Flow<List<Post>> {
        return _posts.asStateFlow()
    }

    private fun generateMockPosts(): List<Post> {
        return listOf(
            Post(
                id = "1",
                userId = "user_1",
                userName = "রহিম উদ্দিন",
                userProfileImage = "https://ui-avatars.com/api/?name=Rahim&background=4CAF50",
                content = "ভালুকায় আজ সুন্দর আবহাওয়া! সবাই কেমন আছেন?",
                images = listOf("https://picsum.photos/seed/nature1/800/600"),
                likes = 45,
                comments = 12,
                shares = 3,
                location = "ভালুকা, ময়মনসিংহ",
                createdAt = System.currentTimeMillis() - 3600000
            ),
            Post(
                id = "2",
                userId = "user_2",
                userName = "সালমা খাতুন",
                userProfileImage = "https://ui-avatars.com/api/?name=Salma&background=FF9800",
                content = "আজ আমাদের এলাকায় একটি সামাজিক কর্মসূচি হয়েছে। অনেক ভালো লাগলো সবার সাথে মিলিত হয়ে। ধন্যবাদ সবাইকে!",
                images = listOf(
                    "https://picsum.photos/seed/community/800/600",
                    "https://picsum.photos/seed/people/800/600"
                ),
                likes = 89,
                comments = 23,
                shares = 8,
                location = "ভালুকা সদর",
                createdAt = System.currentTimeMillis() - 7200000
            ),
            Post(
                id = "3",
                userId = "user_3",
                userName = "করিম মিয়া",
                userProfileImage = "https://ui-avatars.com/api/?name=Karim&background=2196F3",
                content = "আমার নতুন ব্যবসা শুরু করলাম। সবার দোয়া চাই। 🙏",
                likes = 156,
                comments = 45,
                shares = 12,
                createdAt = System.currentTimeMillis() - 14400000
            ),
            Post(
                id = "4",
                userId = "user_4",
                userName = "ফাতেমা বেগম",
                userProfileImage = "https://ui-avatars.com/api/?name=Fatema&background=E91E63",
                content = "আজকের সূর্যাস্ত দেখে মন ভরে গেল। আল্লাহর সৃষ্টি কতই না সুন্দর! 🌅",
                images = listOf("https://picsum.photos/seed/sunset/800/600"),
                likes = 234,
                comments = 34,
                shares = 15,
                location = "ভালুকা",
                privacy = PostPrivacy.PUBLIC,
                createdAt = System.currentTimeMillis() - 21600000
            ),
            Post(
                id = "5",
                userId = currentUserId,
                userName = currentUserName,
                userProfileImage = currentUserImage,
                content = "সবাইকে শুভ সকাল! আজকের দিনটা সুন্দর হোক।",
                likes = 67,
                comments = 18,
                shares = 5,
                isLiked = true,
                createdAt = System.currentTimeMillis() - 28800000
            )
        )
    }

    private fun generateMockComments(postId: String): List<Comment> {
        return listOf(
            Comment(
                id = "c1",
                postId = postId,
                userId = "user_5",
                userName = "আব্দুল্লাহ",
                userProfileImage = "https://ui-avatars.com/api/?name=Abdullah&background=9C27B0",
                content = "খুব সুন্দর! ধন্যবাদ শেয়ার করার জন্য।",
                likes = 5,
                createdAt = System.currentTimeMillis() - 1800000
            ),
            Comment(
                id = "c2",
                postId = postId,
                userId = "user_6",
                userName = "জেসমিন আক্তার",
                userProfileImage = "https://ui-avatars.com/api/?name=Jesmin&background=00BCD4",
                content = "অসাধারণ! আমিও সেখানে যাব।",
                likes = 3,
                createdAt = System.currentTimeMillis() - 900000
            )
        )
    }

    private fun generateMockReplies(commentId: String): List<Comment> {
        return listOf(
            Comment(
                id = "r1",
                postId = "1",
                userId = "user_7",
                userName = "মাহফুজুর রহমান",
                userProfileImage = "https://ui-avatars.com/api/?name=Mahfuzur&background=3F51B5",
                content = "আমি সেখানে ছিলাম। সত্যিই অসাধারণ একটি অনুষ্ঠান ছিল!",
                likes = 2,
                createdAt = System.currentTimeMillis() - 720000
            ),
            Comment(
                id = "r2",
                postId = "1",
                userId = "user_8",
                userName = "সোহেল রানা",
                userProfileImage = "https://ui-avatars.com/api/?name=Sohel&background=8E24AA",
                content = "হ্যাঁ, আমি ভিডিওতে দেখেছি। খুবই মজার লাগছে!",
                likes = 1,
                createdAt = System.currentTimeMillis() - 360000
            )
        )
    }

    private fun generateMockStoryHighlights(): List<StoryHighlight> {
        return listOf(
            StoryHighlight(
                id = "highlight_1",
                title = "ভ্রমণ",
                coverImage = "https://picsum.photos/seed/travel1/400/800",
                storiesCount = 12,
                createdAt = System.currentTimeMillis() - 86400000 * 7
            ),
            StoryHighlight(
                id = "highlight_2",
                title = "খাবার",
                coverImage = "https://picsum.photos/seed/food1/400/800",
                storiesCount = 8,
                createdAt = System.currentTimeMillis() - 86400000 * 14
            ),
            StoryHighlight(
                id = "highlight_3",
                title = "পরিবার",
                coverImage = "https://picsum.photos/seed/family1/400/800",
                storiesCount = 15,
                createdAt = System.currentTimeMillis() - 86400000 * 21
            ),
            StoryHighlight(
                id = "highlight_4",
                title = "উৎসব",
                coverImage = "https://picsum.photos/seed/festival1/400/800",
                storiesCount = 10,
                createdAt = System.currentTimeMillis() - 86400000 * 30
            ),
            StoryHighlight(
                id = "highlight_5",
                title = "প্রকৃতি",
                coverImage = "https://picsum.photos/seed/nature2/400/800",
                storiesCount = 20,
                createdAt = System.currentTimeMillis() - 86400000 * 45
            )
        )
    }

    private fun generateMockFriends(): List<Friend> {
        return listOf(
            Friend(
                userId = "friend_1",
                userName = "রহিম উদ্দিন",
                profileImage = "https://ui-avatars.com/api/?name=Rahim+Uddin&background=4CAF50&color=fff",
                mutualFriends = 12,
                isFriend = true
            ),
            Friend(
                userId = "friend_2",
                userName = "সালমা খাতুন",
                profileImage = "https://ui-avatars.com/api/?name=Salma+Khatun&background=FF9800&color=fff",
                mutualFriends = 8,
                isFriend = true
            ),
            Friend(
                userId = "friend_3",
                userName = "করিম মিয়া",
                profileImage = "https://ui-avatars.com/api/?name=Karim+Mia&background=2196F3&color=fff",
                mutualFriends = 15,
                isFriend = true
            ),
            Friend(
                userId = "friend_4",
                userName = "ফাতেমা বেগম",
                profileImage = "https://ui-avatars.com/api/?name=Fatema+Begum&background=E91E63&color=fff",
                mutualFriends = 20,
                isFriend = true
            ),
            Friend(
                userId = "friend_5",
                userName = "আব্দুল্লাহ",
                profileImage = "https://ui-avatars.com/api/?name=Abdullah&background=9C27B0&color=fff",
                mutualFriends = 5,
                isFriend = true
            ),
            Friend(
                userId = "friend_6",
                userName = "জেসমিন আক্তার",
                profileImage = "https://ui-avatars.com/api/?name=Jesmin+Akter&background=00BCD4&color=fff",
                mutualFriends = 18,
                isFriend = true
            ),
            Friend(
                userId = "friend_7",
                userName = "মাহফুজুর রহমান",
                profileImage = "https://ui-avatars.com/api/?name=Mahfuzur+Rahman&background=3F51B5&color=fff",
                mutualFriends = 10,
                isFriend = true
            ),
            Friend(
                userId = "friend_8",
                userName = "সোহেল রানা",
                profileImage = "https://ui-avatars.com/api/?name=Sohel+Rana&background=8E24AA&color=fff",
                mutualFriends = 7,
                isFriend = true
            ),
            Friend(
                userId = "friend_9",
                userName = "নাজমা আক্তার",
                profileImage = "https://ui-avatars.com/api/?name=Nazma+Akter&background=F44336&color=fff",
                mutualFriends = 14,
                isFriend = true
            ),
            Friend(
                userId = "friend_10",
                userName = "আরিফুল ইসলাম",
                profileImage = "https://ui-avatars.com/api/?name=Ariful+Islam&background=009688&color=fff",
                mutualFriends = 22,
                isFriend = true
            ),
            Friend(
                userId = "friend_11",
                userName = "রুমানা পারভীন",
                profileImage = "https://ui-avatars.com/api/?name=Rumana+Parvin&background=FF5722&color=fff",
                mutualFriends = 9,
                isFriend = true
            ),
            Friend(
                userId = "friend_12",
                userName = "তানভীর আহমেদ",
                profileImage = "https://ui-avatars.com/api/?name=Tanvir+Ahmed&background=607D8B&color=fff",
                mutualFriends = 16,
                isFriend = true
            )
        )
    }

    private fun generateMockPhotos(): List<Photo> {
        return listOf(
            Photo(
                id = "photo_1",
                imageUrl = "https://picsum.photos/seed/photo1/800/800",
                caption = "ভালুকার সুন্দর সকাল",
                likes = 45,
                comments = 8,
                createdAt = System.currentTimeMillis() - 86400000
            ),
            Photo(
                id = "photo_2",
                imageUrl = "https://picsum.photos/seed/photo2/800/800",
                caption = "পরিবারের সাথে",
                likes = 89,
                comments = 15,
                createdAt = System.currentTimeMillis() - 86400000 * 2
            ),
            Photo(
                id = "photo_3",
                imageUrl = "https://picsum.photos/seed/photo3/800/800",
                caption = "নতুন জায়গায়",
                likes = 67,
                comments = 12,
                createdAt = System.currentTimeMillis() - 86400000 * 3
            ),
            Photo(
                id = "photo_4",
                imageUrl = "https://picsum.photos/seed/photo4/800/800",
                caption = "সূর্যাস্তের দৃশ্য",
                likes = 123,
                comments = 20,
                createdAt = System.currentTimeMillis() - 86400000 * 4
            ),
            Photo(
                id = "photo_5",
                imageUrl = "https://picsum.photos/seed/photo5/800/800",
                caption = "বন্ধুদের সাথে",
                likes = 98,
                comments = 18,
                createdAt = System.currentTimeMillis() - 86400000 * 5
            ),
            Photo(
                id = "photo_6",
                imageUrl = "https://picsum.photos/seed/photo6/800/800",
                caption = "খাবারের সময়",
                likes = 76,
                comments = 10,
                createdAt = System.currentTimeMillis() - 86400000 * 6
            ),
            Photo(
                id = "photo_7",
                imageUrl = "https://picsum.photos/seed/photo7/800/800",
                caption = "প্রকৃতির সৌন্দর্য",
                likes = 145,
                comments = 25,
                createdAt = System.currentTimeMillis() - 86400000 * 7
            ),
            Photo(
                id = "photo_8",
                imageUrl = "https://picsum.photos/seed/photo8/800/800",
                caption = "বিশেষ মুহূর্ত",
                likes = 87,
                comments = 14,
                createdAt = System.currentTimeMillis() - 86400000 * 8
            ),
            Photo(
                id = "photo_9",
                imageUrl = "https://picsum.photos/seed/photo9/800/800",
                caption = "নতুন অভিজ্ঞতা",
                likes = 112,
                comments = 22,
                createdAt = System.currentTimeMillis() - 86400000 * 9
            ),
            Photo(
                id = "photo_10",
                imageUrl = "https://picsum.photos/seed/photo10/800/800",
                caption = "সন্ধ্যার আকাশ",
                likes = 134,
                comments = 19,
                createdAt = System.currentTimeMillis() - 86400000 * 10
            ),
            Photo(
                id = "photo_11",
                imageUrl = "https://picsum.photos/seed/photo11/800/800",
                caption = "স্মৃতি",
                likes = 92,
                comments = 16,
                createdAt = System.currentTimeMillis() - 86400000 * 11
            ),
            Photo(
                id = "photo_12",
                imageUrl = "https://picsum.photos/seed/photo12/800/800",
                caption = "আনন্দের মুহূর্ত",
                likes = 105,
                comments = 21,
                createdAt = System.currentTimeMillis() - 86400000 * 12
            )
        )
    }
}
