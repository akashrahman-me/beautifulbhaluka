package com.akash.beautifulbhaluka.domain.usecase

import com.akash.beautifulbhaluka.domain.repository.JobRepository

/**
 * Use case for toggling job favorite status
 */
class ToggleFavoriteJobUseCase(
    private val repository: JobRepository
) {
    suspend operator fun invoke(jobId: String): Result<Boolean> {
        return repository.toggleFavorite(jobId)
    }
}
