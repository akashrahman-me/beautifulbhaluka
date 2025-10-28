package com.akash.beautifulbhaluka.domain.usecase

import com.akash.beautifulbhaluka.domain.model.Job
import com.akash.beautifulbhaluka.domain.model.JobCategory
import com.akash.beautifulbhaluka.domain.repository.JobRepository

/**
 * Use case for fetching jobs with filtering
 */
class GetJobsUseCase(
    private val repository: JobRepository
) {
    suspend operator fun invoke(
        category: JobCategory? = null,
        searchQuery: String? = null,
        page: Int = 1,
        pageSize: Int = 10
    ): Result<Pair<List<Job>, Int>> {
        return repository.getJobs(category, searchQuery, page, pageSize)
    }
}
