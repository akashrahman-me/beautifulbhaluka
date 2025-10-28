package com.akash.beautifulbhaluka.domain.usecase

import com.akash.beautifulbhaluka.domain.model.Job
import com.akash.beautifulbhaluka.domain.repository.JobRepository

/**
 * Use case for fetching favorite jobs
 */
class GetFavoriteJobsUseCase(
    private val repository: JobRepository
) {
    suspend operator fun invoke(): Result<List<Job>> {
        return repository.getFavoriteJobs()
    }
}
