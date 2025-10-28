package com.akash.beautifulbhaluka.data.repository

import com.akash.beautifulbhaluka.domain.model.*
import com.akash.beautifulbhaluka.domain.repository.JobRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Implementation of JobRepository
 * Currently using mock data, can be extended to use real API/Database
 */
class JobRepositoryImpl : JobRepository {

    private val _favoriteJobIds = MutableStateFlow<Set<String>>(emptySet())
    private val mockJobs = generateMockJobs()
    private val mockApplications = mutableListOf<JobApplication>()

    override suspend fun getJobs(
        category: JobCategory?,
        searchQuery: String?,
        page: Int,
        pageSize: Int
    ): Result<Pair<List<Job>, Int>> {
        return try {
            // Simulate network delay
            delay(500)

            var filteredJobs = mockJobs.filter { it.isActive }

            // Filter by category
            if (category != null && category != JobCategory.ALL) {
                filteredJobs = filteredJobs.filter { it.category == category }
            }

            // Filter by search query
            if (!searchQuery.isNullOrBlank()) {
                filteredJobs = filteredJobs.filter { job ->
                    job.title.contains(searchQuery, ignoreCase = true) ||
                    job.company.contains(searchQuery, ignoreCase = true) ||
                    job.description.contains(searchQuery, ignoreCase = true)
                }
            }

            // Pagination
            val totalPages = (filteredJobs.size + pageSize - 1) / pageSize
            val startIndex = (page - 1) * pageSize
            val endIndex = minOf(startIndex + pageSize, filteredJobs.size)

            val paginatedJobs = if (startIndex < filteredJobs.size) {
                filteredJobs.subList(startIndex, endIndex)
            } else {
                emptyList()
            }

            Result.success(Pair(paginatedJobs, totalPages))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getJobById(jobId: String): Result<Job> {
        return try {
            delay(300)
            val job = mockJobs.find { it.id == jobId }
            if (job != null) {
                Result.success(job)
            } else {
                Result.failure(Exception("Job not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFeaturedJobs(): Result<List<Job>> {
        return try {
            delay(300)
            val featured = mockJobs.filter { it.isFeatured && it.isActive }.take(5)
            Result.success(featured)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchJobs(query: String): Result<List<Job>> {
        return try {
            delay(300)
            val results = mockJobs.filter { job ->
                job.isActive && (
                    job.title.contains(query, ignoreCase = true) ||
                    job.company.contains(query, ignoreCase = true) ||
                    job.description.contains(query, ignoreCase = true)
                )
            }
            Result.success(results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun observeFavoriteJobIds(): Flow<Set<String>> {
        return _favoriteJobIds.asStateFlow()
    }

    override suspend fun toggleFavorite(jobId: String): Result<Boolean> {
        return try {
            val currentFavorites = _favoriteJobIds.value.toMutableSet()
            val isFavorite = if (currentFavorites.contains(jobId)) {
                currentFavorites.remove(jobId)
                false
            } else {
                currentFavorites.add(jobId)
                true
            }
            _favoriteJobIds.value = currentFavorites
            Result.success(isFavorite)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun isFavorite(jobId: String): Boolean {
        return _favoriteJobIds.value.contains(jobId)
    }

    override suspend fun getFavoriteJobs(): Result<List<Job>> {
        return try {
            delay(300)
            val favorites = mockJobs.filter { _favoriteJobIds.value.contains(it.id) }
            Result.success(favorites)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserApplications(): Result<List<JobApplication>> {
        return try {
            delay(300)
            Result.success(mockApplications.toList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun applyForJob(
        jobId: String,
        coverLetter: String?,
        resumeUrl: String?
    ): Result<JobApplication> {
        return try {
            delay(500)
            val job = mockJobs.find { it.id == jobId }
            if (job != null) {
                val application = JobApplication(
                    id = "app_${System.currentTimeMillis()}",
                    job = job,
                    applicantId = "user_123",
                    applicationStatus = ApplicationStatus.APPLIED,
                    appliedDate = System.currentTimeMillis(),
                    lastUpdated = System.currentTimeMillis(),
                    coverLetter = coverLetter,
                    resumeUrl = resumeUrl
                )
                mockApplications.add(application)
                Result.success(application)
            } else {
                Result.failure(Exception("Job not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun postJob(job: Job): Result<Job> {
        return try {
            delay(500)
            // In real implementation, would post to backend
            Result.success(job)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateJob(job: Job): Result<Job> {
        return try {
            delay(500)
            // In real implementation, would update in backend
            Result.success(job)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteJob(jobId: String): Result<Unit> {
        return try {
            delay(500)
            // In real implementation, would delete from backend
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun generateMockJobs(): List<Job> {
        val companies = listOf(
            "Tech Corp Bangladesh", "Digital Solutions Ltd", "Creative Agency BD",
            "StartupXYZ", "Global Tech BD", "Innovation Hub", "Smart Systems",
            "Future Tech", "Code Masters", "Design Studio BD"
        )

        val jobTitles = mapOf(
            JobCategory.IT to listOf("Android Developer", "iOS Developer", "Full Stack Developer", "Backend Engineer", "Frontend Developer"),
            JobCategory.DESIGN to listOf("UI/UX Designer", "Graphic Designer", "Product Designer", "Motion Designer"),
            JobCategory.MARKETING to listOf("Digital Marketing Manager", "SEO Specialist", "Content Writer", "Social Media Manager"),
            JobCategory.BUSINESS to listOf("Business Analyst", "Product Manager", "Project Manager", "Operations Manager"),
            JobCategory.ENGINEERING to listOf("Software Engineer", "DevOps Engineer", "QA Engineer", "Data Engineer"),
            JobCategory.EDUCATION to listOf("Training Coordinator", "Instructor", "Academic Manager"),
            JobCategory.HEALTHCARE to listOf("Health Informatics Specialist", "Medical Coder"),
            JobCategory.FINANCE to listOf("Financial Analyst", "Accountant", "Finance Manager"),
            JobCategory.SALES to listOf("Sales Executive", "Business Development Manager", "Account Manager"),
            JobCategory.HR to listOf("HR Manager", "Recruiter", "HR Business Partner")
        )

        val locations = listOf("ঢাকা", "চট্টগ্রাম", "সিলেট", "রাজশাহী", "খুলনা", "বরিশাল", "রংপুর", "ময়মনসিংহ")

        val categories = JobCategory.values().filter { it != JobCategory.ALL }

        return (1..60).map { index ->
            val category = categories[index % categories.size]
            val categoryTitles = jobTitles[category] ?: listOf("General Position")
            val title = categoryTitles[index % categoryTitles.size]

            Job(
                id = "job_$index",
                title = title,
                company = companies[index % companies.size],
                companyLogo = null,
                location = locations[index % locations.size],
                salary = SalaryRange(
                    min = 25000 + (index * 2000),
                    max = 45000 + (index * 3000),
                    isNegotiable = index % 3 == 0
                ),
                experience = ExperienceLevel.values()[index % ExperienceLevel.values().size],
                education = EducationLevel.values()[index % EducationLevel.values().size],
                description = "আমরা একজন দক্ষ এবং অভিজ্ঞ $title খুঁজছি যিনি আমাদের টিমে যোগদান করবেন এবং প্রযুক্তিগত উৎকর্ষতা নিশ্চিত করবেন।",
                requirements = listOf(
                    "${ExperienceLevel.values()[index % ExperienceLevel.values().size].banglaName} অভিজ্ঞতা প্রয়োজন",
                    "প্রাসঙ্গিক প্রযুক্তিতে দক্ষতা",
                    "টিম ওয়ার্কে পারদর্শী",
                    "চমৎকার যোগাযোগ দক্ষতা"
                ),
                responsibilities = listOf(
                    "প্রজেক্ট ডেভেলপমেন্ট এবং রক্ষণাবেক্ষণ",
                    "কোড রিভিউ এবং ডকুমেন্টেশন",
                    "টিমের সাথে সহযোগিতা",
                    "সময়মত ডেলিভারি নিশ্চিত করা"
                ),
                benefits = listOf(
                    "প্রতিযোগিতামূলক বেতন",
                    "স্বাস্থ্য বীমা",
                    "বার্ষিক বোনাস",
                    "ক্যারিয়ার উন্নয়নের সুযোগ",
                    "নমনীয় কর্ম সময়"
                ),
                postedDate = System.currentTimeMillis() - (index * 86400000L), // Days ago
                deadline = System.currentTimeMillis() + ((30 - index % 20) * 86400000L), // Days from now
                contactInfo = ContactInfo(
                    email = "hr@${companies[index % companies.size].lowercase().replace(" ", "")}.com",
                    phone = "+880181234567${index % 10}",
                    website = "www.${companies[index % companies.size].lowercase().replace(" ", "")}.com",
                    address = "${locations[index % locations.size]}, Bangladesh"
                ),
                imageUrl = "https://picsum.photos/400/200?random=${100 + index}",
                category = category,
                isFeatured = index % 7 == 0,
                positionCount = listOf(1, 2, 5, 10, 20)[index % 5],
                jobType = JobType.values()[index % JobType.values().size],
                workingHours = listOf("৮ ঘন্টা", "৬ ঘন্টা", "নমনীয়")[index % 3],
                workLocation = WorkLocationType.values()[index % WorkLocationType.values().size],
                applicationCount = (index * 3) % 50,
                isActive = true
            )
        }
    }
}
