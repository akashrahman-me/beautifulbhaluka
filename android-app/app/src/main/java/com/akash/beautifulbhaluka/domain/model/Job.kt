package com.akash.beautifulbhaluka.domain.model

/**
 * Domain model for Job
 * Clean entity used in business logic layer
 */
data class Job(
    val id: String,
    val title: String,
    val company: String,
    val companyLogo: String?,
    val location: String,
    val salary: SalaryRange,
    val experience: ExperienceLevel,
    val education: EducationLevel,
    val description: String,
    val requirements: List<String>,
    val responsibilities: List<String>,
    val benefits: List<String>,
    val postedDate: Long, // Timestamp
    val deadline: Long, // Timestamp
    val contactInfo: ContactInfo,
    val imageUrl: String?,
    val category: JobCategory,
    val isFeatured: Boolean = false,
    val positionCount: Int,
    val jobType: JobType,
    val workingHours: String,
    val workLocation: WorkLocationType,
    val applicationCount: Int = 0,
    val isActive: Boolean = true
)

data class SalaryRange(
    val min: Int,
    val max: Int,
    val currency: String = "BDT",
    val isNegotiable: Boolean = false
)

data class ContactInfo(
    val email: String,
    val phone: String,
    val website: String? = null,
    val address: String? = null
)

enum class JobCategory(val displayName: String, val banglaName: String) {
    ALL("All", "সব"),
    IT("IT & Software", "আইটি ও সফটওয়্যার"),
    ENGINEERING("Engineering", "প্রকৌশল"),
    HEALTHCARE("Healthcare", "স্বাস্থ্যসেবা"),
    EDUCATION("Education", "শিক্ষা"),
    BUSINESS("Business", "ব্যবসা"),
    MARKETING("Marketing", "মার্কেটিং"),
    DESIGN("Design", "ডিজাইন"),
    FINANCE("Finance", "অর্থ"),
    SALES("Sales", "বিক্রয়"),
    HR("Human Resources", "মানব সম্পদ"),
    OPERATIONS("Operations", "পরিচালনা")
}

enum class ExperienceLevel(val displayName: String, val banglaName: String) {
    FRESHER("Fresher", "ফ্রেশার"),
    ONE_TO_TWO("1-2 years", "১-২ বছর"),
    TWO_TO_THREE("2-3 years", "২-৩ বছর"),
    THREE_TO_FIVE("3-5 years", "৩-৫ বছর"),
    FIVE_PLUS("5+ years", "৫+ বছর")
}

enum class EducationLevel(val displayName: String, val banglaName: String) {
    SSC("SSC", "এস এস সি"),
    HSC("HSC", "এইচ এস সি"),
    BACHELORS("Bachelor's", "স্নাতক"),
    MASTERS("Master's", "স্নাতকোত্তর"),
    PHD("PhD", "পিএইচডি")
}

enum class JobType(val displayName: String, val banglaName: String) {
    FULL_TIME("Full-time", "ফুল-টাইম"),
    PART_TIME("Part-time", "পার্ট-টাইম"),
    CONTRACT("Contract", "চুক্তিভিত্তিক"),
    INTERNSHIP("Internship", "ইন্টার্নশিপ"),
    FREELANCE("Freelance", "ফ্রিল্যান্স")
}

enum class WorkLocationType(val displayName: String, val banglaName: String) {
    ON_SITE("On-site", "অন-সাইট"),
    REMOTE("Remote", "রিমোট"),
    HYBRID("Hybrid", "হাইব্রিড")
}

data class JobApplication(
    val id: String,
    val job: Job,
    val applicantId: String,
    val applicationStatus: ApplicationStatus,
    val appliedDate: Long,
    val lastUpdated: Long,
    val coverLetter: String? = null,
    val resumeUrl: String? = null
)

enum class ApplicationStatus(val displayName: String, val banglaName: String) {
    APPLIED("Applied", "আবেদন করা হয়েছে"),
    REVIEWED("Reviewed", "পর্যালোচনা করা হয়েছে"),
    SHORTLISTED("Shortlisted", "তালিকাভুক্ত"),
    INTERVIEW_SCHEDULED("Interview Scheduled", "সাক্ষাৎকার নির্ধারিত"),
    REJECTED("Rejected", "প্রত্যাখ্যাত"),
    ACCEPTED("Accepted", "গৃহীত")
}

