package com.akash.beautifulbhaluka.domain.repository

import com.akash.beautifulbhaluka.domain.model.Job
import com.akash.beautifulbhaluka.domain.model.JobApplication
import com.akash.beautifulbhaluka.domain.model.JobCategory
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for Job data operations
 * Following Clean Architecture principles
 */
interface JobRepository {

    /**
     * Get all jobs with optional filtering
     */
    suspend fun getJobs(
        category: JobCategory? = null,
        searchQuery: String? = null,
        page: Int = 1,
        pageSize: Int = 10
    ): Result<Pair<List<Job>, Int>> // Returns (jobs, totalPages)

    /**
     * Get a specific job by ID
     */
    suspend fun getJobById(jobId: String): Result<Job>

    /**
     * Get featured jobs
     */
    suspend fun getFeaturedJobs(): Result<List<Job>>

    /**
     * Search jobs
     */
    suspend fun searchJobs(query: String): Result<List<Job>>

    /**
     * Observe favorite job IDs
     */
    fun observeFavoriteJobIds(): Flow<Set<String>>

    /**
     * Toggle favorite status
     */
    suspend fun toggleFavorite(jobId: String): Result<Boolean>

    /**
     * Check if job is favorite
     */
    suspend fun isFavorite(jobId: String): Boolean

    /**
     * Get favorite jobs
     */
    suspend fun getFavoriteJobs(): Result<List<Job>>

    /**
     * Get user's job applications
     */
    suspend fun getUserApplications(): Result<List<JobApplication>>

    /**
     * Apply for a job
     */
    suspend fun applyForJob(
        jobId: String,
        coverLetter: String?,
        resumeUrl: String?
    ): Result<JobApplication>

    /**
     * Post a new job (for employers)
     */
    suspend fun postJob(job: Job): Result<Job>

    /**
     * Update job posting
     */
    suspend fun updateJob(job: Job): Result<Job>

    /**
     * Delete job posting
     */
    suspend fun deleteJob(jobId: String): Result<Unit>
}

