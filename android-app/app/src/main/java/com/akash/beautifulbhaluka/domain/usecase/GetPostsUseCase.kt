package com.akash.beautifulbhaluka.domain.usecase

import com.akash.beautifulbhaluka.domain.model.Post
import com.akash.beautifulbhaluka.domain.repository.SocialRepository

class GetPostsUseCase(
    private val repository: SocialRepository
) {
    suspend operator fun invoke(): Result<List<Post>> {
        return repository.getPosts()
    }
}
