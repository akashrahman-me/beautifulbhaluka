package com.akash.beautifulbhaluka.domain.usecase

import com.akash.beautifulbhaluka.domain.model.Post
import com.akash.beautifulbhaluka.domain.model.PostPrivacy
import com.akash.beautifulbhaluka.domain.repository.SocialRepository

class CreatePostUseCase(
    private val repository: SocialRepository
) {
    suspend operator fun invoke(
        content: String,
        images: List<String> = emptyList(),
        videoUrl: String? = null,
        privacy: PostPrivacy = PostPrivacy.PUBLIC,
        location: String? = null
    ): Result<Post> {
        if (content.isBlank() && images.isEmpty() && videoUrl == null) {
            return Result.failure(Exception("পোস্ট খালি হতে পারে না"))
        }
        return repository.createPost(content, images, videoUrl, privacy, location)
    }
}
