package com.akash.beautifulbhaluka.domain.usecase

import com.akash.beautifulbhaluka.domain.model.SocialProfile
import com.akash.beautifulbhaluka.domain.repository.SocialRepository

class GetProfileUseCase(
    private val repository: SocialRepository
) {
    suspend operator fun invoke(userId: String): Result<SocialProfile> {
        return repository.getProfile(userId)
    }
}
